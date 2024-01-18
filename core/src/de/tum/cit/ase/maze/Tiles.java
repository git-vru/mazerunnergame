package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Tiles {
    private TextureRegion wall;
    private TextureRegion entryPoint;
    private TextureRegion exit;
    private TextureRegion trap;
    private TextureRegion enemy;
    private TextureRegion key;
    private TextureRegion grass;
    private Array<Rectangle> wallRectangles = new Array<>();

    public Tiles() {
        loadWall();
        loadEntry();
        loadEnemy();
        loadExit();
        loadKey();
        loadTrap();
        loadGrass();
    }
    public void loadWall(){
        Texture basictiles = new Texture(Gdx.files.internal("basictiles.png"));
        this.wall = new TextureRegion(basictiles,0,0,16,16);
    }
    public void loadEntry(){
        Texture things = new Texture("things.png");
        this.entryPoint = new TextureRegion(things, 0, 0, 16, 16);
    }
    public void loadExit(){
        Texture things = new Texture("things.png");
        this.exit = new TextureRegion(things, 48, 0, 16, 16);
    }
    public void loadTrap(){
        Texture basictiles = new Texture(Gdx.files.internal("basictiles.png"));
        this.trap = new TextureRegion(basictiles, 16, 32, 16, 16);
    }
    public void loadEnemy(){
        Texture mobs = new Texture("mobs.png");
        this.enemy = new TextureRegion(mobs, 0,64, 16, 16);
    }
    public void loadKey(){
        Texture objects = new Texture("objects.png");
        this.key = new TextureRegion(objects, 0, 64, 16, 16);
    }
    public void loadGrass(){
        Texture basictiles = new Texture(Gdx.files.internal("basictiles.png"));
        this.grass = new TextureRegion(basictiles,16,128,16,16);
    }

    public TextureRegion getWall() {
        return wall;
    }

    public TextureRegion getEntryPoint() {
        return entryPoint;
    }

    public TextureRegion getExit() {
        return exit;
    }

    public TextureRegion getTrap() {
        return trap;
    }

    public TextureRegion getEnemy() {
        return enemy;
    }

    public TextureRegion getKey() {
        return key;
    }

    public TextureRegion getGrass() {
        return grass;
    }
    public Array<Rectangle> getWallRectangles() {
        return wallRectangles;
    }
}
