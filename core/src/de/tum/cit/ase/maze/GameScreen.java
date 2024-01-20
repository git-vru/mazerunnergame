package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import de.tum.cit.ase.maze.Hero;
import de.tum.cit.ase.maze.MazeRunnerGame;
import com.badlogic.gdx.graphics.GL20;


/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {

    private final MazeRunnerGame game;
    private MazeLoader mazeLoader;
    private final OrthographicCamera camera;
    private final BitmapFont font;
    private final Hero hero;
    private final float boundingBoxSize;
    private final SpriteBatch batch;
    private float cameraSpeed;
    private HUD hud;
    private Stage stage;
    private Enemy enemy;
    public GameScreen(MazeRunnerGame game) {
        this.game = game;
        this.mazeLoader = game.getMazeLoader();
        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 0.75f;
        stage = new Stage();

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
            game.goToMenu();
        }

        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen
        updateCamera();
        hero.update(delta,determineDirection());
        checkCollisions();
        game.getKey().update(delta);
        game.getEntry().update(delta);
        game.getSpriteBatch().setProjectionMatrix(camera.combined);
        game.renderMaze();
        game.getSpriteBatch().begin(); // Important to call this before drawing anything
        // Render the text
        font.draw(game.getSpriteBatch(), "Press ESC to go to menu", 0, 0);
        hero.draw(game.getSpriteBatch());
        game.getKey().draw(game.getSpriteBatch(),hero.isKeyCollected());
        game.getEntry().draw(game.getSpriteBatch());
        hud.setKeyStatus();
        hero.updateKeyCollected();
        hud.setLives();
        hero.updateLives();
        hud.setNumberOfEnemiesKilled();
        hero.updateEnemiesKilled();
        hud.draw();
        game.getSpriteBatch().end(); // Important to call this after drawing everything

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
        for (Rectangle rectangle: game.getAllTiles().getWallRectangles()) {
            if (rectangle.overlaps(hero.getHeroRect())){
                hero.setX(hero.getPrevX());
                hero.setY(hero.getPrevY());
            }
        }
        if (game.getKey().getKeyRect().overlaps(hero.getHeroRect())){
            hero.setKeyCollected(true);
            game.getKey().dispose();
        }
        if (game.getEntry().getEntryRect().overlaps(hero.getHeroRect())){
            game.getEntry().setOpen(true);
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
    // Additional methods and logic can be added as needed for the game screen
}