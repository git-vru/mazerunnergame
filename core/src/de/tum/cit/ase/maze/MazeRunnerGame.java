package de.tum.cit.ase.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
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
    private double minX;
    private double minY;
    // UI Skin
    private Skin skin;
    private Hero hero;
    private final NativeFileChooser fileChooser;
    private final Map<Point, Integer> mazeData = new HashMap<>();
    private Tiles allTiles;
    private Key key;
    private Entry entry;
    private MazeLoader mazeLoader;
    private boolean clicked;
    private Languages languages;
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
        this.clicked = true;
    }
    public void playMusic() {
        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
        backgroundMusic.setLooping(true);
        if (clicked) {
            backgroundMusic.play();
        }
        else {
            backgroundMusic.stop();
        }
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
        mazeLoader.createEnemies();
//        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
//        backgroundMusic.setLooping(true);
//        backgroundMusic.play();

//        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
//        backgroundMusic.setLooping(true);
        /*
        if (optionScreen.getClickedTimes() % 2 == 0) {
            backgroundMusic.play();
        }
        else {
            backgroundMusic.dispose();
        }
         */
        //this.loadCharacterAnimation();// Load character animation
        this.allTiles = new Tiles();
        //playMusic();
        this.languages = new Languages();
        //languages.setDefaultLanguage();
        // Play some background music
        // Background sound
        //playMusic();
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
    /*public void saveGameState(float heroX, float heroY, boolean keyCollected, int lives) {
        savedHeroX = heroX;
        savedHeroY = heroY;
        savedKeyCollected = keyCollected;
        savedLives = lives;
    }*/
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

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public Languages getLanguages() {
        return languages;
    }

    public void setLanguages(Languages languages) {
        this.languages = languages;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }
}