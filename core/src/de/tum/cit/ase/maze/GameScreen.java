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
import de.tum.cit.ase.maze.Hero;
import de.tum.cit.ase.maze.MazeRunnerGame;

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
    private Music lifeLostMusic;
    private Music coinCollected;
    private float cameraSpeed;
    private HUD hud;
    private Stage stage;
    private TextButton resumeButton;
    private TextButton menuButton;
    private static boolean resumed = false;
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
        resumeButton = new TextButton("Resume", game.getSkin());
        table.add(resumeButton).width(300).padBottom(15).row();
        menuButton = new TextButton("Main Menu", game.getSkin());
        table.add(menuButton).width(300).padBottom(15).row();
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setResumed(false);
            }
        });
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu();
            }
        });
        this.isVulnerable = true;
        this.vulnerabilityTimer = 2f;
        lifeLostMusic = Gdx.audio.newMusic(Gdx.files.internal("hurt.ogg"));
        lifeLostMusic.setLooping(false);
        coinCollected = Gdx.audio.newMusic(Gdx.files.internal("coin.ogg"));
        coinCollected.setLooping(false);
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            setResumed(true);
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
            hero.update(delta, determineDirection());
        }
        enemyCollision();
        if (!isVulnerable){
            vulnerabilityTimer-=delta;
            if (vulnerabilityTimer<0f){
                setVulnerable(true);
                vulnerabilityTimer=2f;
            }
        }
        checkCollisions();
        game.getKey().update(delta);
        game.getEntry().update(delta);
        game.getSpriteBatch().setProjectionMatrix(camera.combined);
        game.renderMaze();
        game.getSpriteBatch().begin(); // Important to call this before drawing anything
        // Render the text
        for (Enemy enemy : Enemy.enemyList) {
            //enemy.draw(game.getSpriteBatch());
            enemy.update(delta);
            //enemy.move(delta); // Implement move method based on the direction
            enemy.draw(game.getSpriteBatch());
        }
        font.draw(game.getSpriteBatch(), "Press ESC to go to menu", 0, 0);
        hero.draw(game.getSpriteBatch());
        game.getKey().draw(game.getSpriteBatch(),hero.isKeyCollected());
        game.getEntry().draw(game.getSpriteBatch());
        for (Exit exit: Exit.getExitList()
             ) {
            exit.update(delta);
            exit.draw(game.getSpriteBatch());
        }
        for (Trap trap: Trap.getTrapList()
        ) {
            trap.update(delta);
            trap.draw(game.getSpriteBatch());
        }
        //hero.updateKeyCollected();
        hud.drawLives();
        hud.setKeyStatus();
        //hero.updateLives();
        hud.setShield(!isVulnerable);
        hero.updateEnemiesKilled();
        hud.draw();
        if (hero.isWinner()) {
            game.setScreen(new GoodEndScreen(game));
        }
        if (hero.isDead()) {
            game.setScreen((new BadEndScreen(game)));
        }
        game.getSpriteBatch().end(); // Important to call this after drawing everything
        if (isResumed()){
            pauseScreen();
            return;
        }
    }
    private void pauseScreen(){
        Gdx.input.setInputProcessor(stage);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(),1/30f));
        stage.draw();
    }
    private String determineDirection() {
        String direction = "";
        float speed = 200;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            speed = 400;
            setCameraSpeed(4f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            hero.moveLeft(speed * Gdx.graphics.getDeltaTime());
            direction = "left";
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            hero.moveRight(speed * Gdx.graphics.getDeltaTime());
            direction = "right";
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            hero.moveDown(speed * Gdx.graphics.getDeltaTime());
            direction = "down";
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            hero.moveUp(speed * Gdx.graphics.getDeltaTime());
            direction = "up";
        }
        hero.setHeroRect(new Rectangle(hero.getX(),hero.getY()+5, hero.getHeroWidth(), hero.getHeroHeight()/2));

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
        if (hero.getX()>= game.getMaxX()*60||hero.getX()<=game.getMinX()*60||hero.getY()>= game.getMaxY()*60||hero.getY()<=game.getMinY()*60){
            hero.setX(hero.getPrevX());
            hero.setY(hero.getPrevY());
        }
        for (Rectangle rectangle: game.getAllTiles().getWallRectangles()) {
            if (rectangle.overlaps(hero.getHeroRect())){
                hero.setX(hero.getPrevX());
                hero.setY(hero.getPrevY());
            }
            for (Enemy enemy: Enemy.enemyList) {
                if (rectangle.overlaps(enemy.getEnemyRect())||game.getEntry().getEntryRect().overlaps(enemy.getEnemyRect())){
                    enemy.changeDirection();
                }
                for (Exit exit: Exit.getExitList()) {
                    //game.getSpriteBatch().draw(game.getAllTiles().getExit(), exit.getExitRect().x, exit.getExitRect().y, 60, 60);
                    if (enemy.getEnemyRect().overlaps(exit.getExitRect())){
                        enemy.changeDirection();
                    }
                    if (exit.getExitRect().overlaps(hero.getHeroRect())){
                        if (!hero.isKeyCollected()){
                            hero.setX(hero.getPrevX());
                            hero.setY(hero.getPrevY());
                        }
                        else {
                            exit.setOpen(true);
                        }
                    }
                }
            }
        }

        if (game.getKey().getKeyRect().overlaps(hero.getHeroRect())){
            coinCollected.play();
            hero.setKeyCollected(true);
            game.getKey().dispose();
        }
        if (game.getEntry().getEntryRect().overlaps(hero.getHeroRect())){
            game.getEntry().setOpen(true);
        }
        if (game.getEntry().getMazeLeaver().overlaps(hero.getHeroRect())){
            hero.setX(hero.getPrevX());
            hero.setY(hero.getPrevY());
        }
    }
    private void enemyCollision(){
        for (Enemy enemy:
             Enemy.enemyList) {
            if (enemy.getEnemyRect().overlaps(hero.getHeroRect())&&isVulnerable){
                lifeLostMusic.play();
                hero.setLives(hero.getLives()-1);
                System.out.println(hero.getLives());
                setVulnerable(false);
            }
        }
        for (Trap trap: Trap.getTrapList()) {
            if (trap.getTrapRect().overlaps(hero.getHeroRect())&&isVulnerable){
                lifeLostMusic.play();
                hero.setLives(hero.getLives()-1);
                System.out.println(hero.getLives());
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
        //game.createMaze();
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    public void setCameraSpeed(float cameraSpeed) {
        this.cameraSpeed = cameraSpeed;
    }

    public OrthographicCamera getCamera() {
        return camera;
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