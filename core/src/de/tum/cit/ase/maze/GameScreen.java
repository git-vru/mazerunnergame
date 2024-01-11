package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {

    private final MazeRunnerGame game;
    private final OrthographicCamera camera;
    private final BitmapFont font;
    private float playerX;
    private float playerY;
    private float leftTimer;
    private float rightTimer;
    private float upTimer;
    private float downTimer;
    private float standTimer;

    //private float sinusInput = 0f;

    public GameScreen(MazeRunnerGame game) {
        this.game = game;
        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 0.75f;

        // Get the font from the game's skin
        font = game.getSkin().getFont("font");

    }


    // Screen interface methods with necessary functionality
    @Override
    public void render(float delta) {
        // Check for escape key press to go back to the menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.goToMenu();
        }

        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen

        camera.update(); // Update the camera

        // Move text in a circular path to have an example of a moving object
        /*sinusInput += delta;
        float textX = (float) (camera.position.x + Math.sin(sinusInput) * 100);
        float textY = (float) (camera.position.y + Math.cos(sinusInput) * 100);*/
        String direction = new String();

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerX -= 200 * Gdx.graphics.getDeltaTime();
            direction = "left";
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerX += 200 * Gdx.graphics.getDeltaTime();
            direction = "right";
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerY -= 200 * Gdx.graphics.getDeltaTime();
            direction = "down";
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerY += 200 * Gdx.graphics.getDeltaTime();
            direction = "up";
        }

        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && ((Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))))) {
            playerX -= 400 * Gdx.graphics.getDeltaTime();
            direction = "left";
        }
        if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && ((Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))))) {
            playerX += 400 * Gdx.graphics.getDeltaTime();
            direction = "right";
        }
        if((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) && ((Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))))) {
            playerY -= 400 * Gdx.graphics.getDeltaTime();
            direction = "down";
        }
        if((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) && ((Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))))) {
            playerY += 400 * Gdx.graphics.getDeltaTime();
            direction = "up";
        }



        leftTimer += Gdx.graphics.getDeltaTime();
        rightTimer += Gdx.graphics.getDeltaTime();
        upTimer += Gdx.graphics.getDeltaTime();
        downTimer += Gdx.graphics.getDeltaTime();
        standTimer += Gdx.graphics.getDeltaTime();

        // Set up and begin drawing with the sprite batch
        camera.position.set(playerX, playerY, 0);
        camera.update();
        game.getSpriteBatch().setProjectionMatrix(camera.combined);
        game.createMaze();
        game.getSpriteBatch().begin(); // Important to call this before drawing anything
        // Render the text
        font.draw(game.getSpriteBatch(), "Press ESC to go to menu", 0, 0);
        Animation<TextureRegion> currentAnimation;

        // Draw the character next to the text :) / We can reuse sinusInput here
//        if (direction.equals("left")) {
//            currentAnimation = game.getCharacterLeftAnimation();
//        } else if (direction.equals("right")) {
//            currentAnimation = game.getCharacterRightAnimation();
//        } else if (direction.equals("down")) {
//            currentAnimation = game.getCharacterDownAnimation();
//        } else if (direction.equals("up")) {
//            currentAnimation = game.getCharacterUpAnimation();
//        }
//        else {
//            currentAnimation = game.getCharacterStandAnimation();
//        }

        game.getSpriteBatch().draw(
                getCurrentFrame(direction).getKeyFrame(getAnimationTimer(direction), true),
                playerX,
                playerY,
                64,
                128
        );
        game.getSpriteBatch().end(); // Important to call this after drawing everything
        game.createMaze();
    }
    private float getAnimationTimer(String direction) {
        switch (direction) {
            case "left":
                return leftTimer;
            case "right":
                return rightTimer;
            case "up":
                return upTimer;
            case "down":
                return downTimer;
            default:
                return standTimer;
        }
    }

    private Animation<TextureRegion> getDirectionalAnimation(float timer, Animation<TextureRegion> animation) {
        // Adjust the speed by changing the multiplier
        return new Animation<>(0.1f, animation.getKeyFrame(timer, true));
    }
    private Animation<TextureRegion> getCurrentFrame(String direction) {
        //String direction = ""; // Determine the direction based on input or other conditions
        switch (direction) {
            case "left":
                return getDirectionalAnimation(leftTimer, game.getCharacterLeftAnimation());
            case "right":
                return getDirectionalAnimation(rightTimer, game.getCharacterRightAnimation());
            case "up":
                return getDirectionalAnimation(upTimer, game.getCharacterUpAnimation());
            case "down":
                return getDirectionalAnimation(downTimer, game.getCharacterDownAnimation());
            default:
                return getDirectionalAnimation(standTimer, game.getCharacterStandAnimation());
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    // Additional methods and logic can be added as needed for the game screen
}