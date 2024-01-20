package de.tum.cit.ase.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


/**
 * The MazeRunnerGame class represents the core of the Maze Runner game.
 * It manages the screens and global resources like SpriteBatch and Skin.
 */
public class MazeRunnerGame extends Game{
    // Screens
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    // Sprite Batch for rendering
    private SpriteBatch spriteBatch;
    private double maxX;
    private double maxY;
    // UI Skin
    private Skin skin;
    private Hero hero;
    // Character animation downwards
    private Animation<TextureRegion> characterDownAnimation;
    private Animation<TextureRegion> characterLeftAnimation;
    private Animation<TextureRegion> characterRightAnimation;
    private Animation<TextureRegion> characterUpAnimation;
    private Animation<TextureRegion> characterStandAnimation;
    private final NativeFileChooser fileChooser;
    private final Map<Point, Integer> mazeData = new HashMap<>();
    private Tiles allTiles;
    private Key key;
    private Entry entry;
    private MazeLoader mazeLoader;
    /**
     * Constructor for MazeRunnerGame.
     *
     * @param fileChooser The file chooser for the game, typically used in desktop environment.
     */
    public MazeRunnerGame(NativeFileChooser fileChooser) {
        super();
        this.fileChooser = fileChooser;
        this.maxX=0;
        this.maxY=0;
        //this.optionScreen = new OptionScreen(this);
        this.mazeLoader = new MazeLoader(this);
    }
    public void showFileChooser() {
        NativeFileChooserConfiguration conf = new NativeFileChooserConfiguration();
        conf.directory = Gdx.files.internal("maps");
        fileChooser.chooseFile(conf, new NativeFileChooserCallback() {
            @Override
            public void onFileChosen(FileHandle file) {
                mazeLoader.loadMazeData(file.path());
                createMaze();
                renderMaze();
                goToGame();
            }

            @Override
            public void onCancellation() {
                // Handle cancellation if needed
            }

            @Override
            public void onError(Exception exception) {
                // Handle error (use exception type)
            }
        });
    }
    /**
     * Called when the game is created. Initializes the SpriteBatch and Skin.
     */
    @Override
    public void create() {
        spriteBatch = new SpriteBatch(); // Create SpriteBatch
        skin = new Skin(Gdx.files.internal("craft/craftacular-ui.json")); // Load UI skin
        //this.loadCharacterAnimation();// Load character animation
        this.allTiles = new Tiles();
        this.key = new Key();
        this.entry = new Entry();
        createMaze();
//        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
//        backgroundMusic.setLooping(true);
//        backgroundMusic.play();
        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
        backgroundMusic.setLooping(true);
        /*
        if (optionScreen.getClickedTimes() % 2 == 0) {
            backgroundMusic.play();
        }
        else {
            backgroundMusic.dispose();
        }
         */
        // Play some background music
        // Background sound
        goToMenu();// Navigate to the menu screen
    }

    /**
     * Switches to the menu screen.
     */
    public void goToMenu() {
        this.setScreen(new MenuScreen(this)); // Set the current screen to MenuScreen
        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }
    }
    /**
     * Switches to the game screen.
     */
    public void goToGame() {
        this.setScreen(new GameScreen(this)); // Set the current screen to GameScreen
        if (menuScreen != null) {
            menuScreen.dispose(); // Dispose the menu screen if it exists
            menuScreen = null;
        }
        if (gameScreen != null) {
            gameScreen.setMazeLoader(mazeLoader);
        }
    }
    /**
     * Loads the character animation from the character.png file.
     */
    public void createMaze() {
        mazeLoader.calculateMaxCoordinates();
        mazeLoader.addGround();
    }
    public void renderMaze() {
        mazeLoader.renderMaze();
    }
    /**
     * Cleans up resources when the game is disposed.
     */
    @Override
    public void dispose() {
        getScreen().hide(); // Hide the current screen
        getScreen().dispose(); // Dispose the current screen
        spriteBatch.dispose(); // Dispose the spriteBatch
        skin.dispose(); // Dispose the skin
    }

    // Getter methods
    public Skin getSkin() {
        return skin;
    }

    public Animation<TextureRegion> getCharacterDownAnimation() {
        return characterDownAnimation;
    }

    public Animation<TextureRegion> getCharacterLeftAnimation() {
        return characterLeftAnimation;
    }

    public Animation<TextureRegion> getCharacterRightAnimation() {
        return characterRightAnimation;
    }

    public Animation<TextureRegion> getCharacterUpAnimation() {
        return characterUpAnimation;
    }

    public Animation<TextureRegion> getCharacterStandAnimation() {
        return characterStandAnimation;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Map<Point, Integer> getMazeData() {
        return mazeData;
    }

    public Hero getHero() {
        return hero;
    }


    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Tiles getAllTiles() {
        return allTiles;
    }

    public MazeLoader getMazeLoader() {
        return mazeLoader;
    }

    public Key getKey() {
        return key;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setKey(Key key) {
        this.key = key;
    }
}