package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Tiles {
    private TextureRegion wall;
    private TextureRegion grass;
    Texture basictiles;
    private static final Array<Rectangle> wallRectangles = new Array<>();

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
