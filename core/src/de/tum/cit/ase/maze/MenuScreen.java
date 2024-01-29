package de.tum.cit.ase.maze;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The MenuScreen class is responsible for displaying the main menu of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 */
public class MenuScreen implements Screen {

    private final Stage stage;
    private final Texture backgroundTexture;
    private final SpriteBatch batch;


    /**
     * Creates a new MenuScreen, which contains buttons like start game, exit game, and settings.
     *
     * @param game The MazeRunnerGame instance.
     */
    public MenuScreen(MazeRunnerGame game) {
        var camera = new OrthographicCamera();
        backgroundTexture = new Texture(Gdx.files.internal("mazeinlook.jpeg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        batch = new SpriteBatch();
        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements
        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add a label as a title
        table.add(new Label(game.getLanguages().get("greeting"), game.getSkin(), "title")).padBottom(400).row();

        TextButton startGameButton = new TextButton(game.getLanguages().get("newgame"), game.getSkin());
        table.add(startGameButton).width(400).padBottom(15).row();

        TextButton settingsButton = new TextButton(game.getLanguages().get("settings"), game.getSkin());
        table.add(settingsButton).width(400).padBottom(15).row();

        TextButton exitGameButton = new TextButton(game.getLanguages().get("exitgame"), game.getSkin());
        table.add(exitGameButton).width(400).row();


        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Exit.getExitList().clear();
                Enemy.enemyList.clear();
                Trap.getTrapList().clear();
                game.getMazeData().clear();
                game.setHero(new Hero(0,0));
                game.setScreen(new SelectMapScreen(game));
            }
        });

        settingsButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game));
            }
        });


        exitGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

    }

    /**
     * Renders the visual elements on the screen.
     *
     * @param delta The time in seconds since the last render call. It is used for frame-rate independent animation.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        batch.draw(backgroundTexture,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.draw(); // Draw the stage
    }

    /**
     * Called when the screen is resized, such as when the window is resized or the orientation changes.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2f, stage.getCamera().viewportHeight / 2f, 0);
    }

    /**
     * Disposes of resources and performs cleanup when the screen is no longer in use.
     * It is called when the game switches to another screen or exits.
     */
    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        backgroundTexture.dispose();
        batch.dispose();
        stage.dispose();
    }

    /**
     * Called when the screen is set as the current screen in the game.
     * This method is typically used to initialize resources or set up the initial state of the screen.
     */
    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
    }

    // The following methods are part of the Screen interface but are not used in this screen.
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}