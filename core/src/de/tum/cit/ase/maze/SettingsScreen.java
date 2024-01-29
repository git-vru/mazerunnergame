package de.tum.cit.ase.maze;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The SettingsScreen class represents the screen for adjusting game settings in the MazeRunnerGame.
 * It allows the player to control menu music, game music, game sounds, volume, and language selection.
 */
public class SettingsScreen implements Screen {
    private final Stage stage;
    private final Texture backgroundTexture;
    private final SpriteBatch batch;
    private final MazeRunnerGame game;
    private TextButton volumeShowButton;

    /**
     * Constructs a new SettingsScreen instance.
     *
     * @param game The MazeRunnerGame instance.
     */
    public SettingsScreen(MazeRunnerGame game) {
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

        table.add(new Label(game.getLanguages().get("settings"), game.getSkin(), "title")).padBottom(50).row();

        TextButton menuMusicButton = new TextButton(game.getLanguages().get("menumusic"), game.getSkin());
        if (!game.getMusicLoader().isForbiddenMenu()) {
            menuMusicButton.setColor(game.getSkin().getColor("green"));
        } else {
            menuMusicButton.setColor(game.getSkin().getColor("red"));
        }
        table.add(menuMusicButton).width(400).padBottom(15).row();
        menuMusicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!game.getMusicLoader().isForbiddenMenu()) {
                    game.getMusicLoader().setForbiddenMenu(true);;
                    game.getMusicLoader().stopMenuMusic();
                    menuMusicButton.setColor(game.getSkin().getColor("red"));
                }
                else {
                    game.getMusicLoader().setForbiddenMenu(false);
                    game.getMusicLoader().playMenuMusic();
                    menuMusicButton.setColor(game.getSkin().getColor("green"));
                }
                super.clicked(event, x, y);
            }
        });

        TextButton gameMusicButton = new TextButton(game.getLanguages().get("gamemusic"), game.getSkin());
        if (!game.getMusicLoader().isForbiddenGame()) {
            gameMusicButton.setColor(game.getSkin().getColor("green"));
        } else {
            gameMusicButton.setColor(game.getSkin().getColor("red"));
        }
        table.add(gameMusicButton).width(400).padBottom(15).row();
        gameMusicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!game.getMusicLoader().isForbiddenGame()) {
                    game.getMusicLoader().setForbiddenGame(true);
                    gameMusicButton.setColor(game.getSkin().getColor("red"));
                }
                else {
                    game.getMusicLoader().setForbiddenGame(false);
                    gameMusicButton.setColor(game.getSkin().getColor("green"));
                }
                super.clicked(event, x, y);
            }
        });

        TextButton gameSoundButton = new TextButton(game.getLanguages().get("gamesound"), game.getSkin());
        if (!game.getMusicLoader().isGameSoundsForbidden()) {
            gameSoundButton.setColor(game.getSkin().getColor("green"));
        } else {
            gameSoundButton.setColor(game.getSkin().getColor("red"));
        }
        table.add(gameSoundButton).width(400).padBottom(15).row();
        gameSoundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!game.getMusicLoader().isGameSoundsForbidden()) {
                    game.getMusicLoader().setGameSoundsForbidden(true);
                    gameSoundButton.setColor(game.getSkin().getColor("red"));
                }
                else {
                    game.getMusicLoader().setGameSoundsForbidden(false);
                    gameSoundButton.setColor(game.getSkin().getColor("green"));
                }
                super.clicked(event, x, y);
            }
        });

        TextButton volumeUpButton = new TextButton(game.getLanguages().get("volumeup"), game.getSkin());
        table.add(volumeUpButton).width(400).padBottom(15).row();
        volumeUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getMusicLoader().volumeUp();
                updateVolumeLabel();
                super.clicked(event, x, y);
            }
        });

        volumeShowButton = new TextButton(game.getLanguages().get("volume") + Math.round(game.getMusicLoader().getMenuMusic().getVolume() * 10), game.getSkin());
        table.add(volumeShowButton).width(400).padBottom(15).row();
        volumeShowButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });

        TextButton volumeDownButton = new TextButton(game.getLanguages().get("volumedown"), game.getSkin());
        table.add(volumeDownButton).width(400).padBottom(15).row();
        volumeDownButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getMusicLoader().volumeDown();
                updateVolumeLabel();
                super.clicked(event, x, y);
            }
        });

        TextButton languageButton = new TextButton(game.getLanguages().get("selectlanguage"), game.getSkin());
        table.add(languageButton).width(400).padBottom(15).row();
        languageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LanguageScreen(game));
            }
        });

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
     * Updates the label displaying the current volume.
     */
    private void updateVolumeLabel() {
        volumeShowButton.setText(game.getLanguages().get("volume") + Math.round(game.getMusicLoader().getMenuMusic().getVolume()*10));
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