package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LanguageScreen implements Screen {
    private final Stage stage;
    private final Texture backgroundTexture;
    private final SpriteBatch batch;
    private final MazeRunnerGame game;
    public LanguageScreen(MazeRunnerGame game) {
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

        table.add(new Label("Select Language", game.getSkin(), "title")).padBottom(50).row();

        TextButton turkish = new TextButton("Turkce", game.getSkin());
        turkish.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getLanguages().setLanguageCode("tr");
                game.getLanguages().setLanguage();
                game.setScreen(new SettingsScreen(game));
                super.clicked(event, x, y);
            }
        });
        table.add(turkish).width(300).padBottom(5).row();

        TextButton english = new TextButton("English", game.getSkin());
        english.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getLanguages().setLanguageCode("en");
                game.getLanguages().setLanguage();
                game.setScreen(new SettingsScreen(game));
                super.clicked(event, x, y);
            }
        });
        table.add(english).width(300).padBottom(5).row();

        TextButton hindi = new TextButton("indian", game.getSkin());
        hindi.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getLanguages().setLanguageCode("hn");
                game.getLanguages().setLanguage();
                game.setScreen(new SettingsScreen(game));
                super.clicked(event, x, y);
            }
        });
        table.add(hindi).width(300).padBottom(5).row();

        TextButton Spanish = new TextButton("Espanol", game.getSkin());
        Spanish.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getLanguages().setLanguageCode("sp");
                game.getLanguages().setLanguage();
                game.setScreen(new SettingsScreen(game));
                super.clicked(event, x, y);
            }
        });
        table.add(Spanish).width(300).padBottom(5).row();

        TextButton german = new TextButton("Deutsch", game.getSkin());
        german.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.getLanguages().setLanguageCode("gm");
                game.getLanguages().setLanguage();
                game.setScreen(new SettingsScreen(game));
                super.clicked(event, x, y);
            }
        });
        table.add(german).width(300).padBottom(5).row();

        TextButton french = new TextButton("Francais", game.getSkin());
        french.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getLanguages().setLanguageCode("fr");
                game.getLanguages().setLanguage();
                game.setScreen(new SettingsScreen(game));
                super.clicked(event, x, y);
            }
        });
        table.add(french).width(300).padBottom(5).row();

        TextButton arabic = new TextButton("arabian", game.getSkin());;
        arabic.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getLanguages().setLanguageCode("ar");
                game.setScreen(new SettingsScreen(game));
                super.clicked(event, x, y);
            }
        });
        table.add(arabic).width(300).padBottom(5).row();

        TextButton russian = new TextButton("russian", game.getSkin());
        russian.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getLanguages().setLanguageCode("ru");
                game.getLanguages().setLanguage();
                game.setScreen(new SettingsScreen(game));
                super.clicked(event, x, y);
            }
        });
        table.add(russian).width(300).padBottom(5).row();

        TextButton chinese = new TextButton("chinese", game.getSkin());
        chinese.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getLanguages().setLanguageCode("cn");
                game.getLanguages().setLanguage();
                game.setScreen(new SettingsScreen(game));
                super.clicked(event, x, y);
            }
        });
        table.add(chinese).width(300).padBottom(5).row();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        //batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
