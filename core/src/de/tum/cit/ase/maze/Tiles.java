package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * The Tiles class manages and provides textures for different types of tiles in the MazeRunnerGame.
 * It includes texture which provides textures for walls and grass tiles, as well as functionality to retrieve wall rectangles.
 */
public class Tiles {
    private TextureRegion wall;// Texture region for wall tiles
    private TextureRegion grass; // Texture region for grass tiles
    private Texture basictiles; // Texture containing basic tiles
    private static final Array<Rectangle> wallRectangles = new Array<>(); // Array to store wall rectangles

    /**
     * Constructs a new Tiles instance and loads textures for walls and grass tiles.
     */
    public Tiles() {
        this.basictiles = new Texture(Gdx.files.internal("basictiles.png"));
        loadWall();
        loadGrass();
    }
    public void loadWall(){
        this.wall = new TextureRegion(basictiles,0,0,16,16);
    }
    public void loadGrass(){
        //Texture basictiles = new Texture(Gdx.files.internal("basictiles.png"));
        this.grass = new TextureRegion(basictiles,0,16*8,16,16);
    }

    public TextureRegion getWall() {
        return wall;
    }
    public TextureRegion getGrass() {
        return grass;
    }
    public static Array<Rectangle> getWallRectangles() {
        return wallRectangles;
    }
}
