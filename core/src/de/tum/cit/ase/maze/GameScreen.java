package de.tum.cit.ase.maze;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {

    private final MazeRunnerGame game;
    private boolean isVulnerable;
    private float vulnerabilityTimer;
    private MazeLoader mazeLoader;
    private final OrthographicCamera camera;
    private final BitmapFont font;
    private final Hero hero;
    private final float boundingBoxSize;
    private final SpriteBatch batch;
    private float cameraSpeed;
    private final HUD hud;
    private final Stage stage;
    private static boolean resumed = false;

    /**
     * Creates a new GameScreen.
     *
     * @param game The MazeRunnerGame instance.
     */
    public GameScreen(MazeRunnerGame game) {
        this.game = game;
        this.mazeLoader = game.getMazeLoader();
        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 0.75f;
        stage = new Stage();
        Table table = new Table();
        stage.addActor(table);
        table.setFillParent(true);
        table.center();
        TextButton resumeButton = new TextButton(game.getLanguages().get("resume"), game.getSkin());
        table.add(resumeButton).width(400).padBottom(15).row();
        TextButton menuButton = new TextButton(game.getLanguages().get("mainmenu"), game.getSkin());
        table.add(menuButton).width(400).padBottom(15).row();
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getMusicLoader().pauseMenuMusic();
                if (!game.getMusicLoader().isForbiddenGame()) {
                    game.getMusicLoader().playGameMusic();
                }
                setResumed(false);
            }
        });
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setResumed(false);
                //game.setScreen(new MenuScreen(game));
                //game.goToMenu();
                if (!game.getMusicLoader().isForbiddenMenu()) {
                    game.getMusicLoader().playMenuMusic();
                }
                game.goToMenu();
            }
        });
        this.isVulnerable = true;
        this.vulnerabilityTimer = 2f;
        // Get the font from the game's skin
        font = game.getSkin().getFont("font");
        boundingBoxSize = 50f;
        cameraSpeed = 2f;
        batch = new SpriteBatch();
        hero = game.getHero();
        hud = new HUD(stage.getViewport(), hero, game);
    }


    // Screen interface methods with necessary functionality
    @Override
    public void render(float delta) {
        // Check for escape key press to go back to the menu
        if (hero.getLives() ==0){
            hero.setDead(true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            setResumed(true);
            game.getMusicLoader().pauseGameMusic();
            if (!game.getMusicLoader().isForbiddenMenu()) {
                game.getMusicLoader().playMenuMusic();
            }
            /*game.setScreen(new PauseScreen(game,hero.getX(),       // Current hero X position
                    hero.getY(),       // Current hero Y position
                    hero.isKeyCollected(),  // Whether the key is collected
                    hero.getLives()));*/
        }
//        if (isResumed()){
//            pauseScreen();
//            return;
//        }
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen
        updateCamera();
        if (!isResumed()){
            hero.setDirection(determineDirection());
            hero.update(delta);
            if (!isVulnerable){
                vulnerabilityTimer-=delta;
                if (vulnerabilityTimer<0f){
                    setVulnerable(true);
                    vulnerabilityTimer=2f;
                }
            }
        }
        enemyCollision();
        checkCollisions();
        game.getKey().update(delta);
        game.getEntry().update(delta);
        game.getSpriteBatch().setProjectionMatrix(camera.combined);
        game.getMazeLoader().addGround();
        game.renderMaze();
        game.getSpriteBatch().begin();
        hero.draw(game.getSpriteBatch());
        game.getSpriteBatch().end();


        game.getSpriteBatch().begin(); // Important to call this before drawing anything
        // Render the text
        for (Enemy enemy : Enemy.enemyList) {
            //enemy.draw(game.getSpriteBatch());
            enemy.update(delta);
            //enemy.move(delta); // Implement move method based on the direction
            enemy.draw(game.getSpriteBatch());
        }
        font.draw(game.getSpriteBatch(), game.getLanguages().get("esc"), 0, 0);
        hero.draw(game.getSpriteBatch());
        game.getKey().draw(game.getSpriteBatch(),!hero.isKeyCollected());
        game.getEntry().draw(game.getSpriteBatch(),game.getEntry().isOpen());
        for (Exit exit: Exit.getExitList()
             ) {
            exit.update(delta);
            exit.draw(game.getSpriteBatch(), exit.isOpen());
        }
        for (Trap trap: Trap.getTrapList()
        ) {
            trap.update(delta);
            trap.draw(game.getSpriteBatch(),true);
        }
        hud.drawLives();
        hud.setKeyStatus();
        hud.setShield(!isVulnerable);
        hud.draw();
        if (hero.isWinner()) {
            game.setScreen(new GoodEndScreen(game));
            game.getMusicLoader().stopGameMusic();
            game.getMusicLoader().stopMenuMusic();
            if (!game.getMusicLoader().isForbiddenGame()) {
                game.getMusicLoader().playWinningMusic();
            }
        }
        if (hero.isDead()) {
            game.setScreen((new BadEndScreen(game)));
            game.getMusicLoader().stopGameMusic();
            game.getMusicLoader().stopMenuMusic();
            if (!game.getMusicLoader().isForbiddenGame()) {
                game.getMusicLoader().playLosingMusic();
            }
        }
        game.getSpriteBatch().end();// Important to call this after drawing everything
        if (isResumed()){
            pauseScreen();
        }
    }

    /**
     * Displays the pause screen using the stage for UI elements.
     */
    private void pauseScreen(){
        Gdx.input.setInputProcessor(stage);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(),1/30f));
        stage.draw();
    }

    /**
     * Determines the direction based on user input and updates the hero's position.
     *
     * @return The direction in which the hero is moving.
     */

    private String determineDirection() {
        String direction = "";

        float speed = 200;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            speed = 400;
            setCameraSpeed(4f);
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))) {
            if (!game.getMusicLoader().isGameSoundsForbidden()) {
                game.getMusicLoader().walkingSoundPlay();
            }
            direction = "left";
            if(checkHeroMovement(hero.getX()-speed * Gdx.graphics.getDeltaTime(), hero.getY()+5)&&
                    checkHeroMovement(hero.getX()-speed * Gdx.graphics.getDeltaTime(), hero.getY()+35)){
                hero.moveLeft(speed * Gdx.graphics.getDeltaTime());
            }
        } else if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))) {
            if (!game.getMusicLoader().isGameSoundsForbidden()) {
                game.getMusicLoader().walkingSoundPlay();
            }
            direction = "right";
            if(checkHeroMovement(hero.getX()+speed * Gdx.graphics.getDeltaTime()+40, hero.getY()+10)&&
                    checkHeroMovement(hero.getX()+speed * Gdx.graphics.getDeltaTime()+40, hero.getY()+30)){
                hero.moveRight(speed * Gdx.graphics.getDeltaTime());
            }
        } else if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))) {
            if (!game.getMusicLoader().isGameSoundsForbidden()) {
                game.getMusicLoader().walkingSoundPlay();
            }
            direction = "down";
            if(checkHeroMovement(hero.getX(), hero.getY()-speed * Gdx.graphics.getDeltaTime())&&
                    checkHeroMovement(hero.getX()+40, hero.getY()-speed * Gdx.graphics.getDeltaTime())){
                hero.moveDown(speed * Gdx.graphics.getDeltaTime());
            }

        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (!game.getMusicLoader().isGameSoundsForbidden()) {
                game.getMusicLoader().walkingSoundPlay();
            }
            direction = "up";
            if(checkHeroMovement(hero.getX()+5, hero.getY()+speed * Gdx.graphics.getDeltaTime()+40)&&
                    checkHeroMovement(hero.getX()+35, hero.getY()+speed * Gdx.graphics.getDeltaTime()+40)){
                hero.moveUp(speed * Gdx.graphics.getDeltaTime());
            }
        }
        hero.setRect(new Rectangle(hero.getX(),hero.getY(), hero.rect.width, hero.rect.height));

        return direction;
    }
    private void updateCamera() {
        // Calculate the bounding box around the player
        float minX = hero.getX() - boundingBoxSize;
        float minY = hero.getY() - boundingBoxSize;
        float maxX = hero.getX() + boundingBoxSize;
        float maxY = hero.getY() + boundingBoxSize;

        // Move the camera towards the player if it's outside the bounding box
        if (camera.position.x < minX || camera.position.x > maxX) {
            camera.position.x += (hero.getX() - camera.position.x) * cameraSpeed * Gdx.graphics.getDeltaTime();
        }

        if (camera.position.y < minY || camera.position.y > maxY) {
            camera.position.y += (hero.getY() - camera.position.y) * cameraSpeed * Gdx.graphics.getDeltaTime();
        }

        // Update the camera matrices
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }
    private void checkCollisions(){
        for (Exit exit: Exit.getExitList()) {
            if (exit.getRect().overlaps(hero.getRect())){
                if (!hero.isKeyCollected()){
                    hero.setX(hero.getPrevX());
                    hero.setY(hero.getPrevY());
                }
                else {
                    exit.setOpen(true);
                }
            }
        }
        if (game.getKey().getRect() != null && game.getKey().getRect().overlaps(hero.getRect())){
            if (!game.getMusicLoader().isGameSoundsForbidden()) {
                game.getMusicLoader().coinCollectedSoundPlay();
            }
            hero.setKeyCollected(true);
            game.getKey().setRect(null);
        }
        if (game.getEntry().getRect().overlaps(hero.getRect())){
            game.getEntry().setOpen(true);
        }
        if (game.getEntry().getMazeLeaver().overlaps(hero.getRect())){
            hero.setX(hero.getPrevX());
            hero.setY(hero.getPrevY());
        }
        if (hero.getRect().overlaps(mazeLoader.getBottom())||hero.getRect().overlaps(mazeLoader.getLeft())||hero.getRect().overlaps(mazeLoader.getTop())||hero.getRect().overlaps(mazeLoader.getRight())){
            hero.setWinner(true);
        }
    }
    public boolean checkHeroMovement(float x, float y){
        int nx = (int) (x/60);
        int ny = (int) (y/60);
        if (game.getMazeData().get(new Point(nx,ny))==null){
            return true;
        }else {
            return game.getMazeData().get(new Point(nx, ny)) != 0;
        }
    }
    private void enemyCollision(){
        for (Enemy enemy:
             Enemy.enemyList) {
            if (enemy.getRect().overlaps(hero.getRect())&&isVulnerable){
                if (!game.getMusicLoader().isGameSoundsForbidden()) {
                    game.getMusicLoader().lifeLostSoundPlay();
                }
                hero.setLives(hero.getLives()-1);
                setVulnerable(false);
            }
        }
        for (Trap trap: Trap.getTrapList()) {
            if (trap.getRect().overlaps(hero.getRect())&&isVulnerable){
                if (!game.getMusicLoader().isGameSoundsForbidden()) {
                    game.getMusicLoader().lifeLostSoundPlay();
                }
                hero.setLives(hero.getLives()-1);
                setVulnerable(false);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false);
        camera.position.set(hero.getX(), hero.getY(), 0);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
        mazeLoader.createObjects();
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        //TODO dispose everything
        stage.dispose(); // Dispose the stage
        batch.dispose();

    }

    public void setCameraSpeed(float cameraSpeed) {
        this.cameraSpeed = cameraSpeed;
    }


    public void setMazeLoader(MazeLoader mazeLoader) {
        this.mazeLoader = mazeLoader;
    }

    public void setVulnerable(boolean vulnerable) {
        isVulnerable = vulnerable;
    }
    // Additional methods and logic can be added as needed for the game screen

    public static boolean isResumed() {
        return resumed;
    }

    public static void setResumed(boolean resumed) {
        GameScreen.resumed = resumed;
    }

}