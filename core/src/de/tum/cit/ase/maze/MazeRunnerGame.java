package de.tum.cit.ase.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private List<List<int[]>> allMazes = new ArrayList<>();
    public int currentMazeIndex;

    /**
     * Constructor for MazeRunnerGame.
     *
     * @param fileChooser The file chooser for the game, typically used in desktop environment.
     */
    public MazeRunnerGame(NativeFileChooser fileChooser) {
        super();
    }
    public void setCurrentMazeIndex(int currentMazeIndex) {
        this.currentMazeIndex = currentMazeIndex;
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

        // libGDX internal Array instead of ArrayList because of performance
        Array<TextureRegion> walkFrames = new Array<>(TextureRegion.class);

        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++) {
            walkFrames.add(new TextureRegion(walkSheet, col * frameWidth, 0, frameWidth, frameHeight));
        }

        characterDownAnimation = new Animation<>(0.1f, walkFrames);
    }

    private void loadMazeData(String fileName, List<int[]> mazeData) {
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
    }
    private void loadAllMazes() {
        List<int[]> mazeData = new ArrayList<>();
        for (int i = 1 ; i <= 5 ; i++) {
            loadMazeData("C:\\Users\\emirh\\IdeaProjects\\fophn2324infun2324projectworkx-g38\\maps\\level-" + i + ".properties", mazeData);
            allMazes.add(mazeData);
        }
    }
    public void createMaze() {
        Texture wallTexture = new Texture("basictiles.png");
        Texture entryPointTexture = new Texture("things.png");
        Texture exitTexture = new Texture("things.png");
        Texture trapTexture = new Texture("things.png");
        Texture enemyTexture = new Texture("mobs.png");
        Texture keyTexture = new Texture("objects.png");
        TextureRegion wall1 = new TextureRegion(wallTexture, 0,0 /*wallTexture.getHeight() - 16*/, 16, 16);
        TextureRegion entryPoint1 = new TextureRegion(entryPointTexture, 0, entryPointTexture.getHeight() - 16, 16, 16);
        TextureRegion exit1 = new TextureRegion(exitTexture, 0, exitTexture.getHeight() - 32, 16, 16);
        TextureRegion trap1 = new TextureRegion(trapTexture, 0, 0, 16, 16);
        TextureRegion enemy1 = new TextureRegion(enemyTexture, 0, (enemyTexture.getHeight() / 2) - 16, 16, 16);
        TextureRegion key1 = new TextureRegion(keyTexture, 0, keyTexture.getHeight() - 8, 8, 8);

        List<int[]> currentMazeData = allMazes.get(currentMazeIndex);
        for (int[] point : currentMazeData) {
            int x = point[0] * 60;
            int y = point[1] * 60;
            int objectType = point[2];
            spriteBatch.begin();
            if (currentMazeIndex == 0) {
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
            else if (currentMazeIndex == 4) {
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

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}