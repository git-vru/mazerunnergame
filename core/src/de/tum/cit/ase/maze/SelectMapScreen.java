package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The SelectMapScreen class represents the screen for selecting game maps in the MazeRunnerGame.
 * It provides options to choose from pre-defined levels or upload custom maps.
 */
public class SelectMapScreen implements Screen {
    private final Stage stage;
    private final Texture backgroundTexture;
    private final SpriteBatch batch;

    /**
     * Constructs a new SelectMapScreen instance, which contains the buttons for level selection and uploading an exterior level.
     *
     * @param game The MazeRunnerGame instance.
     */
    public SelectMapScreen(MazeRunnerGame game) {
        var camera = new OrthographicCamera();
        backgroundTexture = new Texture(Gdx.files.internal("mazeinlook.jpeg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        batch = new SpriteBatch();
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(new Label(game.getLanguages().get("selectmap"), game.getSkin(), "title")).padBottom(50).row();

        for (int i = 1; i <= 5; i++) {
            TextButton levelButton = new TextButton(game.getLanguages().get("level") + i, game.getSkin());
            table.add(levelButton).width(400).padBottom(15).row();

            // Add a listener to handle the button click for each level
            int finalI = i; // Need a final variable for the lambda expression
            levelButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.input.setInputProcessor(null); //ADDED THIS BECAUSE THE LEVEL BUTTONS WERE STILL WORKING
                    //TODO: PLEASE change the path according to your device
                    //format is "level-1/2/3/4/5.properties
                    //use finalI variable instead of number
                    //game.getMazeLoader().loadMazeData("/Users/vrushabhjain/Desktop/temp/maps/level-" + finalI + ".properties");
                    String levelPath = "maps/level-" + finalI + ".properties";
                    game.getMazeLoader().loadMazeData(Gdx.files.internal(levelPath).path());
                    game.getMusicLoader().pauseMenuMusic();
                    if (!game.getMusicLoader().isForbiddenGame()) {
                        game.getMusicLoader().getCurrentMusic();
                        game.getMusicLoader().playGameMusic();
                    }
                    game.createMaze();
                    game.renderMaze();
                    game.goToGame(); // Transition to the game screen
                }
            });
        }

        TextButton uploadMapButton = new TextButton(game.getLanguages().get("uploadmap"), game.getSkin());
        uploadMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.showFileChooser();

            }
        });
        table.add(uploadMapButton).width(400).padBottom(15).row();

        TextButton backButton = new TextButton(game.getLanguages().get("back"), game.getSkin());
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu();
            }
        });
        table.add(backButton).width(400).padBottom(15).row();
    }

    /**
     * Renders the visual elements on the screen.
     *
     * @param delta The time in seconds since the last render call. It is used for frame-rate independent animation.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.draw();
    }

    /**
     * Called when the screen is resized, such as when the window is resized or the orientation changes.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2f, stage.getCamera().viewportHeight / 2f, 0);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

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