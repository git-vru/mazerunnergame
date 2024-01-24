package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Entry {
    private Rectangle entryRect;
    private float entryAnimationTime;
    private Rectangle mazeLeaver;
    private Animation<TextureRegion> entryAnimation;
    private Texture entryPicture;
    private boolean open;

    public Entry() {
        this.open=true;
        loadEntryAnimation();
    }
    public void loadEntryAnimation() {
        this.entryPicture = new Texture(Gdx.files.internal("things.png"));
        int frameWidth = 16;
        int frameHeight = 16;
        int animationFrames = 4;
        Array<TextureRegion> entryFrames = new Array<>(TextureRegion.class);
        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++) {
            entryFrames.add(new TextureRegion(entryPicture,frameWidth, col*frameHeight, frameWidth, frameHeight));
        }
        this.entryAnimation = new Animation<>(0.25f, entryFrames);
        this.entryAnimationTime=1f;
    }
    public void update(float delta) {
        entryAnimationTime += delta;
    }

    public void draw(SpriteBatch spriteBatch) {
       if (isOpen()){
           spriteBatch.draw(
                   entryAnimation.getKeyFrame(entryAnimationTime, true),
                   entryRect.x,
                   entryRect.y,
                   entryRect.width,
                   entryRect.height
           );
           setOpen(false);
       }else spriteBatch.draw(entryAnimation.getKeyFrames()[0],entryRect.x,entryRect.y,entryRect.width,entryRect.height);
    }

    public Rectangle getEntryRect() {
        return entryRect;
    }

    public void setEntryRect(Rectangle entryRect) {
        this.entryRect = entryRect;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setMazeLeaver(Rectangle mazeLeaver) {
        this.mazeLeaver = mazeLeaver;
    }

    public Rectangle getMazeLeaver() {
        return mazeLeaver;
    }
}
