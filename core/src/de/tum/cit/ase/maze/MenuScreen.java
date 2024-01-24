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
        var camera = new OrthographicCamera();
        //camera.zoom = 1.5f; // Set camera zoom for a closer view
        backgroundTexture = new Texture(Gdx.files.internal("foto.jpg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        batch = new SpriteBatch();
        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements
        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage
        game.getLanguages().loadLanguageBundles();


        // Add a label as a title
        table.add(new Label(game.getLanguages().getString("greeting"), game.getSkin(), "title")).padBottom(300).row();
        //table.add(new Label(language.get("greeting"), game.getSkin(), "title")).padBottom(300).row();
        // Create and add a button to go to the game screen
        TextButton startGameButton = new TextButton(game.getLanguages().getString("newgame"), game.getSkin());
        table.add(startGameButton).width(300).padBottom(15).row();

        TextButton continueGameButton = new TextButton(game.getLanguages().getString("continuegame"), game.getSkin());
        table.add(continueGameButton).width(300).padBottom(15).row();

        TextButton settingsButton = new TextButton(game.getLanguages().getString("settings"), game.getSkin());
        table.add(settingsButton).width(300).padBottom(15).row();

//        TextButton selectMapButton = new TextButton("Select Map", game.getSkin());
//        table.add(selectMapButton).width(300).padBottom(15).row();
//
//        TextButton uploadMapButton = new TextButton("Upload Map", game.getSkin());
//        table.add(uploadMapButton).width(300).padBottom(15).row();

        TextButton exitGameButton = new TextButton(game.getLanguages().getString("exitgame"), game.getSkin());
        table.add(exitGameButton).width(300).row();


        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                if (game.currentMazeIndex != 0 && game.currentMazeIndex != 1 && game.currentMazeIndex != 2 && game.currentMazeIndex != 3 && game.currentMazeIndex != 4) {
//                    game.setCurrentMazeIndex(0);
//                }
                //game.goToGame(); // Change to the game screen when button is pressed
                game.setScreen(new SelectMapScreen(game));
            }
        });

        settingsButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game));
                System.out.println("你好");
            }
        });

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            continueGameButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.goToGame();
                    super.clicked(event, x, y);
                }
            });
        }
        else {
            continueGameButton.setDisabled(true);
        }
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

/*        ButtonGroup<Button> buttonGroupDifficulty = new ButtonGroup<>();
        TextButton easy = new TextButton("Easy", game.getSkin());
        TextButton normal = new TextButton("Normal", game.getSkin());
        TextButton hard = new TextButton("Hard", game.getSkin());
        buttonGroupDifficulty.add(easy, normal, hard);
        buttonGroupDifficulty.setMinCheckCount(1);
        buttonGroupDifficulty.setMaxCheckCount(1);
        difficultyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                difficultyButton.add(easy, normal, hard);
                difficultyButton.setText("");
                super.clicked(event, x, y);
            }
        });
        easy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.goToMenu();
                super.clicked(event, x, y);
            }
        });
        normal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.goToMenu();
                super.clicked(event, x, y);
            }
        });
        hard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.goToMenu();
                super.clicked(event, x, y);
            }
        });

        ButtonGroup<Button> buttonGroupLevel = new ButtonGroup<>();
        TextButton level_1 = new TextButton("Level-1", game.getSkin());
        TextButton level_2 = new TextButton("Level-2", game.getSkin());
        TextButton level_3 = new TextButton("Level-3", game.getSkin());
        TextButton level_4 = new TextButton("Level-4", game.getSkin());
        TextButton level_5 = new TextButton("Level-5", game.getSkin());
        buttonGroupLevel.add(level_1, level_2, level_3, level_4, level_5);
        buttonGroupLevel.setMaxCheckCount(1);
        buttonGroupLevel.setMinCheckCount(1);
        selectMapButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    selectMapButton.add(level_1, level_2, level_3, level_4, level_5);
                    selectMapButton.setText("");
                    super.clicked(event, x, y);
            }
        });
        level_1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setCurrentMazeIndex(0);
                game.goToMenu();
                System.out.println(game.currentMazeIndex);
                super.clicked(event, x, y);
            }
        });
        level_2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setCurrentMazeIndex(1);
                game.goToMenu();
                System.out.println(game.currentMazeIndex);
                super.clicked(event, x, y);
            }
        });
        level_3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setCurrentMazeIndex(2);
                game.goToMenu();
                System.out.println(game.currentMazeIndex);
                super.clicked(event, x, y);
            }
        });
        level_4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setCurrentMazeIndex(3);
                game.goToMenu();
                System.out.println(game.currentMazeIndex);
                super.clicked(event, x, y);
            }
        });
        level_5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setCurrentMazeIndex(4);
                game.goToMenu();
                System.out.println(game.currentMazeIndex);
                super.clicked(event, x, y);
            }
        });
*/
        /*
        FileHandle mapsDirectory = Gdx.files.internal("maps");
        FileHandle[] mapFiles = mapsDirectory.list();
        */

        /*
        selectMapButton.addListener(new ClickListener() {
            int currentIndex = 0;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                String selectedMap = mapFiles[currentIndex].nameWithoutExtension();
                currentIndex = (currentIndex + 1) % mapFiles.length;
                selectMapButton.setText(selectedMap);
            }
        });
        */

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