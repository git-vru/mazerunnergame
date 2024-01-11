package de.tum.cit.ase.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    // UI Skin
    private Skin skin;

    // Character animation downwards
    private Animation<TextureRegion> characterDownAnimation;
    private Animation<TextureRegion> characterLeftAnimation;
    private Animation<TextureRegion> characterRightAnimation;
    private Animation<TextureRegion> characterUpAnimation;
    private Animation<TextureRegion> characterStandAnimation;
    private NativeFileChooser fileChooser;
    private Map<Point, Integer> currentMazeData; // Change the type to Map<Point, Integer>
    private List<Map<Point, Integer>> allMazes;

    //private List<List<int[]>> allMazes = new ArrayList<>();
    public int currentMazeIndex;

    /**
     * Constructor for MazeRunnerGame.
     *
     * @param fileChooser The file chooser for the game, typically used in desktop environment.
     */
    public MazeRunnerGame(NativeFileChooser fileChooser) {
        super();
        this.fileChooser = fileChooser;

    }
    public void setCurrentMazeIndex(int currentMazeIndex) {
        this.currentMazeIndex = currentMazeIndex;
    }
    public void showFileChooser() {
        NativeFileChooserConfiguration conf = new NativeFileChooserConfiguration();
        conf.directory = Gdx.files.internal("maps");

        Map<Point, Integer> mazeData = new HashMap<>();

        fileChooser.chooseFile(conf, new NativeFileChooserCallback() {
            @Override
            public void onFileChosen(FileHandle file) {
                // Do stuff with the chosen file, e.g., load maze data
                loadMazeData(file.path(), mazeData);
                // Optionally, you can now use mazeData to create the maze
                createMaze();
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
        loadAllMazes();
        createMaze();
        // Play some background music
        // Background sound
        /* Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        */
        goToMenu(); // Navigate to the menu screen
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
        Array<TextureRegion> walkStandFrames = new Array<>(TextureRegion.class);
        Array<TextureRegion> walkLeftFrames = new Array<>(TextureRegion.class);
        Array<TextureRegion> walkRightFrames = new Array<>(TextureRegion.class);
        Array<TextureRegion> walkUpFrames = new Array<>(TextureRegion.class);
        Array<TextureRegion> walkDownFrames = new Array<>(TextureRegion.class);

        // Add all frames to the animation
        walkStandFrames.add(new TextureRegion(walkSheet, frameWidth, 0, frameWidth, frameHeight));
        for (int col = 0; col < animationFrames; col++) {
            walkLeftFrames.add(new TextureRegion(walkSheet, col * frameWidth, 96, frameWidth, frameHeight));
            walkDownFrames.add(new TextureRegion(walkSheet, col * frameWidth, 0, frameWidth, frameHeight));
            walkRightFrames.add(new TextureRegion(walkSheet, col * frameWidth, 32, frameWidth, frameHeight));
            walkUpFrames.add(new TextureRegion(walkSheet, col * frameWidth, 64, frameWidth, frameHeight));
        }

        characterStandAnimation = new Animation<>(0.1f, walkStandFrames);
        characterLeftAnimation = new Animation<>(0.1f, walkLeftFrames);
        characterRightAnimation = new Animation<>(0.1f, walkRightFrames);
        characterUpAnimation = new Animation<>(0.1f, walkUpFrames);
        characterDownAnimation = new Animation<>(0.1f, walkDownFrames);
    }
    /*private void loadMazeData(String fileName, List<int[]> mazeData) {
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
                        mazeData.add(new int[] {x, y, objectType});
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    private void loadMazeData(String fileName, Map<Point, Integer> mazeData) {
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
                        mazeData.put(new Point(x, y), objectType);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadAllMazes() {
        allMazes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Map<Point, Integer> mazeData = new HashMap<>();
            loadMazeData("/Users/vrushabhjain/IdeaProjects/fophn2324infun2324projectworkx-g38/maps/level-" + i + ".properties", mazeData);
            allMazes.add(mazeData);
        }
    }
    public void loadCurrentMaze() {
        Map<Point, Integer> currentMazeData = allMazes.get(currentMazeIndex);
        if (currentMazeIndex >= 0 && currentMazeIndex < allMazes.size()) {
            currentMazeData = new HashMap<>(allMazes.get(currentMazeIndex)); // Clone the map for the current maze
        } else {
            currentMazeData = new HashMap<>(); // Default to an empty maze data
        }
    }


    //    private void loadAllMazes() {
//        List<int[]> mazeData = new ArrayList<>();
//        for (int i = 1 ; i <= 5 ; i++) {
//            loadMazeData("/Users/vrushabhjain/IdeaProjects/fophn2324infun2324projectworkx-g38/maps/level-"+i+".properties", mazeData);
//            allMazes.add(mazeData);
//        }
//    }
    public void createMaze() {
        loadAllMazes();
        Map<Point, Integer> currentMazeData = allMazes.get(currentMazeIndex);


        Texture wallTexture = new Texture("basictiles.png");
        Texture entryPointTexture = new Texture("things.png");
        Texture exitTexture = new Texture("things.png");
        Texture trapTexture = new Texture("things.png");
        Texture enemyTexture = new Texture("mobs.png");
        Texture keyTexture = new Texture("objects.png");
        TextureRegion wall1 = new TextureRegion(wallTexture, 0,0 /*wallTexture.getHeight() - 16*/, 16, 16);
        TextureRegion entryPoint1 = new TextureRegion(entryPointTexture, 0, 0, 16, 16);
        TextureRegion exit1 = new TextureRegion(exitTexture, 0, exitTexture.getHeight() - 32, 16, 16);
        TextureRegion trap1 = new TextureRegion(trapTexture, 0, 0, 16, 16);
        TextureRegion enemy1 = new TextureRegion(enemyTexture, 0, (enemyTexture.getHeight() / 2) - 16, 16, 16);
        TextureRegion key1 = new TextureRegion(keyTexture, 0, keyTexture.getHeight() - 8, 8, 8);

        //List<int[]> currentMazeData = allMazes.get(currentMazeIndex);
        for (Map.Entry<Point, Integer> entry : currentMazeData.entrySet()) {
            Point point = entry.getKey();
            int x = point.x * 60;
            int y = point.y * 60;
            int objectType = entry.getValue();
            spriteBatch.begin();
            /*if (currentMazeIndex == 0) {
                switch (objectType) {
                    case 0:
                        spriteBatch.draw(entryPoint1, x, y, 60, 60);
                        break;
                    case 1:
                        spriteBatch.draw(entryPoint1, x, y, 60, 60);
                        break;
                    case 2:
                        spriteBatch.draw(exit1, x, y, 60, 60);
                        break;
                    case 3:
                        spriteBatch.draw(trap1, x, y, 60, 60);
                        break;
                    case 4:
                        spriteBatch.draw(enemy1, x, y, 60, 60);
                        break;
                    case 5:
                        spriteBatch.draw(key1, x, y, 60, 60);
                        break;
                }
            }
            else if (currentMazeIndex == 1) {
                switch (objectType) {
                    case 0:
                        spriteBatch.draw(entryPoint1, x, y, 60, 60);
                        break;
                    case 1:
                        spriteBatch.draw(entryPoint1, x, y, 60, 60);
                        break;
                    case 2:
                        spriteBatch.draw(exit1, x, y, 60, 60);
                        break;
                    case 3:
                        spriteBatch.draw(trap1, x, y, 60, 60);
                        break;
                    case 4:
                        spriteBatch.draw(enemy1, x, y, 60, 60);
                        break;
                    case 5:
                        spriteBatch.draw(key1, x, y, 60, 60);
                        break;
                }
            }
            else if (currentMazeIndex == 2) {
                switch (objectType) {
                    case 0:
                        spriteBatch.draw(entryPoint1, x, y, 60, 60);
                        break;
                    case 1:
                        spriteBatch.draw(entryPoint1, x, y, 60, 60);
                        break;
                    case 2:
                        spriteBatch.draw(exit1, x, y, 60, 60);
                        break;
                    case 3:
                        spriteBatch.draw(trap1, x, y, 60, 60);
                        break;
                    case 4:
                        spriteBatch.draw(enemy1, x, y, 60, 60);
                        break;
                    case 5:
                        spriteBatch.draw(key1, x, y, 60, 60);
                        break;
                }
            }
            else if (currentMazeIndex == 3) {
                switch (objectType) {
                    case 0:
                        spriteBatch.draw(entryPoint1, x, y, 60, 60);
                        break;
                    case 1:
                        spriteBatch.draw(entryPoint1, x, y, 60, 60);
                        break;
                    case 2:
                        spriteBatch.draw(exit1, x, y, 60, 60);
                        break;
                    case 3:
                        spriteBatch.draw(trap1, x, y, 60, 60);
                        break;
                    case 4:
                        spriteBatch.draw(enemy1, x, y, 60, 60);
                        break;
                    case 5:
                        spriteBatch.draw(key1, x, y, 60, 60);
                        break;
                }
            }
            else if (currentMazeIndex == 4) {*/
                switch (objectType) {
                    case 0:
                        spriteBatch.draw(entryPoint1, x, y, 60, 60);
                        break;
                    case 1:
                        spriteBatch.draw(entryPoint1, x, y, 60, 60);
                        break;
                    case 2:
                        spriteBatch.draw(exit1, x, y, 60, 60);
                        break;
                    case 3:
                        spriteBatch.draw(trap1, x, y, 60, 60);
                        break;
                    case 4:
                        spriteBatch.draw(enemy1, x, y, 60, 60);
                        break;
                    case 5:
                        spriteBatch.draw(key1, x, y, 60, 60);
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
}