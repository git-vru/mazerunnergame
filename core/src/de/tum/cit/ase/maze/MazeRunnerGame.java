package de.tum.cit.ase.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    private final Array<Rectangle> wallRectangles = new Array<>();
    private Texture basictiles;
    private Texture things;
    private Texture mobs;
    private Texture objects;
    private TextureRegion wall;
    private TextureRegion entryPoint;
    private TextureRegion exit;
    private TextureRegion trap;
    private TextureRegion enemy;
    private TextureRegion key;
    private TextureRegion grass;

    /**
     * Constructor for MazeRunnerGame.
     *
     * @param fileChooser The file chooser for the game, typically used in desktop environment.
     */
    public MazeRunnerGame(NativeFileChooser fileChooser) {
        super();
        this.fileChooser = fileChooser;
        //this.gameScreen=gameScreen;
        this.maxX=0;
        this.maxY=0;

    }
    public void showFileChooser() {
        NativeFileChooserConfiguration conf = new NativeFileChooserConfiguration();
        conf.directory = Gdx.files.internal("maps");
        fileChooser.chooseFile(conf, new NativeFileChooserCallback() {
            @Override
            public void onFileChosen(FileHandle file) {
                // Do stuff with the chosen file, e.g., load maze data
                loadMazeData(file.path());
                // Optionally, you can now use mazeData to create the maze
                createMaze();
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
        this.loadCharacterAnimation(); // Load character animation
        this.loadWall();
        // Play some background music
        // Background sound
//        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
//        backgroundMusic.setLooping(true);
//        backgroundMusic.play();
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
    }
    /**
     * Loads the character animation from the character.png file.
     */
    private void loadCharacterAnimation() {
        Texture walkSheet = new Texture(Gdx.files.internal("character.png"));

        int frameWidth = 16;
        int frameHeight = 32;
        int animationFrames = 4;
        TextureRegion walkStandFrame = new TextureRegion(walkSheet, 0, 0, frameWidth, frameHeight);
        Array<TextureRegion> walkLeftFrames = new Array<>(TextureRegion.class);
        Array<TextureRegion> walkRightFrames = new Array<>(TextureRegion.class);
        Array<TextureRegion> walkUpFrames = new Array<>(TextureRegion.class);
        Array<TextureRegion> walkDownFrames = new Array<>(TextureRegion.class);

        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++) {
            walkLeftFrames.add(new TextureRegion(walkSheet, col * frameWidth, 96, frameWidth, frameHeight));
            walkDownFrames.add(new TextureRegion(walkSheet, col * frameWidth, 0, frameWidth, frameHeight));
            walkRightFrames.add(new TextureRegion(walkSheet, col * frameWidth, 32, frameWidth, frameHeight));
            walkUpFrames.add(new TextureRegion(walkSheet, col * frameWidth, 64, frameWidth, frameHeight));
        }

        characterStandAnimation = new Animation<>(0.1f, walkStandFrame);
        characterLeftAnimation = new Animation<>(0.1f, walkLeftFrames);
        characterRightAnimation = new Animation<>(0.1f, walkRightFrames);
        characterUpAnimation = new Animation<>(0.1f, walkUpFrames);
        characterDownAnimation = new Animation<>(0.1f, walkDownFrames);
    }
    public void loadMazeData(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String[] coordinates = parts[0].split(",");
                    if (coordinates.length == 2) {
                        int x = Integer.parseInt(coordinates[0]);
                        int y = Integer.parseInt(coordinates[1]);
                        int objectType = Integer.parseInt(parts[1]);
                        getMazeData().put(new Point(x, y), objectType);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void calculateMaxCoordinates(){
        for (Point point : mazeData.keySet()) {
            if (point.getX()>getMaxX()){
                setMaxX(point.getX());
            }
            if (point.getY()>getMaxY()){
                setMaxY(point.getY());
            }
        }
        setMaxX(getMaxX()+5);
        setMaxY(getMaxY()+5);
    }
    public void addGround(TextureRegion grass) {
        for (int x = -5; x <=getMaxX() ; x++) {
            for (int y = -5; y <=getMaxY() ; y++) {
                spriteBatch.begin();
                spriteBatch.draw(grass,x*60,y*60,60,60);
                spriteBatch.end();
            }
        }
    }
    public void createMaze() {
        this.basictiles = new Texture(Gdx.files.internal("basictiles.png"));
        this.things= new Texture("things.png");
        this.mobs= new Texture("mobs.png");
        this.objects= new Texture("objects.png");
        this.entryPoint = new TextureRegion(things, 0, 0, 16, 16);
        this.exit = new TextureRegion(things, 48, 0, 16, 16);
        this.trap = new TextureRegion(basictiles, 16, 32, 16, 16);
        this.enemy = new TextureRegion(mobs, 0,64, 16, 16);
        this.key = new TextureRegion(objects, 0, 64, 16, 16);
        this.grass = new TextureRegion(basictiles,16,128,16,16);
        calculateMaxCoordinates();
        //addGround(grass);

        for (Map.Entry<Point, Integer> entry : getMazeData().entrySet()) {
            Point point = entry.getKey();
            int x = point.x * 60;
            int y = point.y * 60;
            int objectType = entry.getValue();
            spriteBatch.begin();
            switch (objectType) {
                case 0:
                    spriteBatch.draw(wall, x, y, 60, 60);
                    wallRectangles.add(new Rectangle(x,y,60,60));
                    break;
                case 1:
                    this.hero = new Hero(x, y,
                            getCharacterLeftAnimation(),
                            getCharacterRightAnimation(),
                            getCharacterUpAnimation(),
                            getCharacterDownAnimation(),
                            getCharacterStandAnimation());
                    spriteBatch.draw(entryPoint, x, y, 60, 60);
                    break;
                case 2:
                    spriteBatch.draw(exit, x, y, 60, 60);
                    break;
                case 3:
                    spriteBatch.draw(trap, x, y, 60, 60);
                    break;
                case 4:
                    spriteBatch.draw(enemy, x, y, 60, 60);
                    break;
                case 5:
                    spriteBatch.draw(key, x+15, y+15, 30, 30);
                    break;
            }
            spriteBatch.end();
        }
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

    public Array<Rectangle> getWallRectangles() {
        return wallRectangles;
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

    public void loadWall()
    {
        this.basictiles = new Texture(Gdx.files.internal("basictiles.png"));
        this.wall = new TextureRegion(basictiles, 0,0 , 16, 16);
    }
}