package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class  Wall {
    private TextureRegion wall;

    public Wall() {
        loadWall();
    }
    public void loadWall(){
        Texture basictiles = new Texture(Gdx.files.internal("basictiles.png"));
        this.wall = new TextureRegion(basictiles,0,0,16,16);
    }

    public TextureRegion getWall() {
        return wall;
    }
}
