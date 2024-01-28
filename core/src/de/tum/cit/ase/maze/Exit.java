package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class Exit {
    private Rectangle exitRect;
    private float exitAnimationTime;
    private Animation<TextureRegion> exitAnimation;
    private Texture exitPicture;
    private boolean open;
    private static List<Exit> exitList=new ArrayList<>();

    public Exit(float x,float y) {
        this.open=false;
        loadExitAnimation();
        this.exitRect = new Rectangle(x,y,60,60);
    }

    public static List<Exit> getExitList() {
        return exitList;
    }

    public void loadExitAnimation() {
        this.exitPicture = new Texture(Gdx.files.internal("things.png"));
        int frameWidth = 16;
        int frameHeight = 16;
        int animationFrames = 4;
        Array<TextureRegion> exitFrames = new Array<>(TextureRegion.class);
        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++) {
            exitFrames.add(new TextureRegion(exitPicture,48, col*frameHeight, frameWidth, frameHeight));
        }
        this.exitAnimation = new Animation<>(0.2f, exitFrames);
    }
    public void update(float delta) {
        exitAnimationTime += delta;
    }

    public void draw(SpriteBatch spriteBatch) {
        if (isOpen()){
            spriteBatch.draw(
                    exitAnimation.getKeyFrame(exitAnimationTime, true),
                    exitRect.x,
                    exitRect.y,
                    exitRect.width,
                    exitRect.height
            );
            setOpen(false);
        } else {
            spriteBatch.draw(exitAnimation.getKeyFrames()[0],exitRect.x,exitRect.y,exitRect.width,exitRect.height);
        }
    }

    public Rectangle getExitRect() {
        return exitRect;
    }
    public void setExitRect(Rectangle exitRect) {
        this.exitRect = exitRect;
    }
    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }
}
