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
    private TextureRegion keyTile;
    private TextureRegion grass;
    private Array<Rectangle> wallRectangles = new Array<>();

    public Tiles() {
        loadWall();
        loadEntry();
        loadEnemy();
        loadExit();
        loadKeyTile();
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
        this.enemy = new TextureRegion(mobs, 48,64, 16, 16);
    }
    public void loadKeyTile(){
        Texture objects = new Texture("objects.png");
        this.keyTile = new TextureRegion(objects, objects.getWidth()-112,0 , 32, 32);
    }
    public void loadGrass(){
        Texture basictiles = new Texture(Gdx.files.internal("basictiles.png"));
        this.grass = new TextureRegion(basictiles,0,16*8,16,16);
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

    public TextureRegion getKeyTile() {
        return keyTile;
    }

    public TextureRegion getGrass() {
        return grass;
    }
    public Array<Rectangle> getWallRectangles() {
        return wallRectangles;
    }
}
