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

public class GoodEndScreen implements Screen {
    private final Stage stage;
    private final Texture backgroundTexture;
    private final SpriteBatch batch;
    private final MazeRunnerGame game;
    private final Hero hero;
    public GoodEndScreen(MazeRunnerGame game) {
        this.game = game;
        var camera = new OrthographicCamera();
        backgroundTexture = new Texture(Gdx.files.internal("foto.jpg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        batch = new SpriteBatch();
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        hero = game.getHero();
        table.add(new Label("YOU WON!", game.getSkin(), "title")).padBottom(300).row();
        TextButton goToMenu = new TextButton("Go To Menu", game.getSkin());
        table.add(goToMenu).width(300).padBottom(15).row();
        goToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
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
        game.getSpriteBatch().begin();
        hero.setDanceTimer(hero.getDanceTimer() + delta);
        game.getSpriteBatch().draw(hero.getDanceAnimation().getKeyFrame(hero.getDanceTimer(), true), (stage.getWidth()/2) - 70, (stage.getHeight()/2) - 130, 150,300);
        game.getSpriteBatch().end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2f, stage.getCamera().viewportHeight / 2f, 0);
    }

    @Override
    public void dispose() {
        if (game.getScreen() != null) {
            game.getScreen().hide();
            game.getScreen().dispose();
        }
        // Dispose SpriteBatch and Skin
        game.getSpriteBatch().dispose();
        game.getSkin().dispose();
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
