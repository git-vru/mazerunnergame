package de.tum.cit.ase.maze;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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



    public MenuScreen(MazeRunnerGame game) {
        OrthographicCamera camera = new OrthographicCamera();
        //camera.zoom = 1.5f; // Set camera zoom for a closer view
        camera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundTexture = new Texture(Gdx.files.internal("foto.jpg"));
        //backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        batch = new SpriteBatch();
        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements
        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage


        // Add a label as a title
        table.add(new Label(game.getLanguages().get("greeting"), game.getSkin(), "title")).padBottom(300).row();
        //table.add(new Label(language.get("greeting"), game.getSkin(), "title")).padBottom(300).row();
        // Create and add a button to go to the game screen
        TextButton startGameButton = new TextButton(game.getLanguages().get("newgame"), game.getSkin());
        table.add(startGameButton).width(300).padBottom(15).row();

        TextButton continueGameButton = new TextButton(game.getLanguages().get("continuegame"), game.getSkin());
        table.add(continueGameButton).width(300).padBottom(15).row();

        TextButton settingsButton = new TextButton(game.getLanguages().get("settings"), game.getSkin());
        table.add(settingsButton).width(300).padBottom(15).row();

//        TextButton selectMapButton = new TextButton("Select Map", game.getSkin());
//        table.add(selectMapButton).width(300).padBottom(15).row();
//
//        TextButton uploadMapButton = new TextButton("Upload Map", game.getSkin());
//        table.add(uploadMapButton).width(300).padBottom(15).row();

        TextButton exitGameButton = new TextButton(game.getLanguages().get("exitgame"), game.getSkin());
        table.add(exitGameButton).width(300).row();


        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                if (game.currentMazeIndex != 0 && game.currentMazeIndex != 1 && game.currentMazeIndex != 2 && game.currentMazeIndex != 3 && game.currentMazeIndex != 4) {
//                    game.setCurrentMazeIndex(0);
//                }
                //game.goToGame(); // Change to the game screen when button is pressed
                Exit.getExitList().clear();
                Enemy.enemyList.clear();
                Trap.getTrapList().clear();
                game.setScreen(new SelectMapScreen(game));
            }
        });

        settingsButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game));
            }
        });

//        selectMapButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new SelectMapScreen(game));
//            }
//        });

//        uploadMapButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.showFileChooser();
//                super.clicked(event, x, y);
//            }
//        });

        exitGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

    }

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

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2f, stage.getCamera().viewportHeight / 2f, 0);
    }

    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        backgroundTexture.dispose();
        batch.dispose();
        stage.dispose();
    }

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