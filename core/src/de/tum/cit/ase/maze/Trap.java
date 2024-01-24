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

public class Trap {
    private Rectangle trapRect;
    private float trapAnimationTime;
    private Animation<TextureRegion> trapAnimation;
    private Texture trapPicture;
    private static List<Trap> trapList = new ArrayList<>();

    public Trap(float x,float y) {
        this.trapRect = new Rectangle(x,y,60,60);
        loadTrapAnimation();
    }
    private void loadTrapAnimation() {
        trapPicture = new Texture(Gdx.files.internal("objects.png"));

        int frameWidth = 16;
        int frameHeight = 16;
        int animationFrames = 7;
        Array<TextureRegion> trapFrames = new Array<>(TextureRegion.class);
        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++) {
            trapFrames.add(new TextureRegion(trapPicture, (col * frameWidth)+64, 48, frameWidth, frameHeight));
        }
        this.trapAnimation = new Animation<>(0.1f, trapFrames);
    }
    public void update(float delta) {
        if (!GameScreen.isResumed())
            trapAnimationTime += delta;
    }

    public void draw(SpriteBatch spriteBatch) {
            spriteBatch.draw(
                    trapAnimation.getKeyFrame(trapAnimationTime, true),
                    trapRect.x,
                    trapRect.y,
                    trapRect.width,
                    trapRect.height
            );
    }
    public void dispose() {
        if (trapPicture != null) {
            trapPicture.dispose();
            trapPicture = null;  // Set to null to indicate disposal
        }
    }

    public Rectangle getTrapRect() {
        return trapRect;
    }

    public static List<Trap> getTrapList() {
        return trapList;
    }
}
