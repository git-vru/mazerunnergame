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

public class PauseScreen implements Screen {
    private final Stage stage;
    private final Texture backgroundTexture;
    private final SpriteBatch batch;
    private TextButton resumeButton;
    private TextButton menuButton;
    private final MazeRunnerGame game;
    private final float heroX;
    private final float heroY;
    private final boolean keyCollected;
    private final int lives;


    public PauseScreen(MazeRunnerGame game, float heroX, float heroY, boolean keyCollected, int lives) {
        var camera = new OrthographicCamera();
        backgroundTexture = new Texture("/Users/vrushabhjain/Downloads/_f074ce88-b80c-4c25-b3d2-7f380e36de68.jpeg");
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        batch = new SpriteBatch();
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        resumeButton = new TextButton("Resume", game.getSkin());
        table.add(resumeButton).width(300).padBottom(15).row();
        menuButton = new TextButton("Main Menu", game.getSkin());
        table.add(menuButton).width(300).padBottom(15).row();
        this.game = game;
        this.heroX = heroX;
        this.heroY = heroY;
        this.keyCollected = keyCollected;
        this.lives = lives;
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameScreen.setResumed(false);
                //game.setScreen(new GameScreen(game, heroX, heroY, keyCollected, lives));
            }
        });
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu();
            }
        });
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