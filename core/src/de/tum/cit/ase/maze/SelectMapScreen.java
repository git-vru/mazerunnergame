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

public class SelectMapScreen implements Screen {

    private final Stage stage;
    private final Texture backgroundTexture;
    private final SpriteBatch batch;
    private final MazeRunnerGame game;

    public SelectMapScreen(MazeRunnerGame game) {
        this.game = game;
        var camera = new OrthographicCamera();
        backgroundTexture = new Texture("/Users/vrushabhjain/Downloads/_f074ce88-b80c-4c25-b3d2-7f380e36de68.jpeg");
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        batch = new SpriteBatch();
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(new Label("Select a Map", game.getSkin(), "title")).padBottom(50).row();

        for (int i = 1; i <= 5; i++) {
            TextButton levelButton = new TextButton("Level " + i, game.getSkin());
            table.add(levelButton).width(300).padBottom(15).row();

            // Add a listener to handle the button click for each level
            int finalI = i; // Need a final variable for the lambda expression
            levelButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.input.setInputProcessor(null); //ADDED THIS BECAUSE THE LEVEL BUTTONS WERE STILL WORKING
                    game.loadMazeData("/Users/vrushabhjain/IdeaProjects/fophn2324infun2324projectworkx-g38/maps/level-" + finalI + ".properties");
                    game.createMaze();
                    game.goToGame(); // Transition to the game screen
                }
            });
        }

        TextButton uploadMapButton = new TextButton("Upload Map", game.getSkin());
        uploadMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.showFileChooser();

            }
        });
        table.add(uploadMapButton).width(300).padBottom(15).row();

        TextButton backButton = new TextButton("Back", game.getSkin());
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu();
            }
        });
        table.add(backButton).width(300).padBottom(15).row();
    }

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
        //ADDED THIS BECAUSE THE LEVEL BUTTONS WERE STILL WORKING
        //Gdx.input.setInputProcessor(null);
    }
}