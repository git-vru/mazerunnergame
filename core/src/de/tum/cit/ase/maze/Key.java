package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Key {
    private Rectangle keyRect;
    private float keyAnimationTime;
    private Animation<TextureRegion> keyAnimation;
    private Texture keyPicture;

    public Key() {
        loadKeyAnimation();
    }
    private void loadKeyAnimation() {
        keyPicture = new Texture(Gdx.files.internal("objects.png"));

        int frameWidth = 16;
        int frameHeight = 16;
        int animationFrames = 4;
        Array<TextureRegion> keyFrames = new Array<>(TextureRegion.class);
        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++) {
            keyFrames.add(new TextureRegion(keyPicture, col * frameWidth, 64, frameWidth, frameHeight));
        }
        this.keyAnimation = new Animation<>(0.2f, keyFrames);
    }
    public void update(float delta) {
        keyAnimationTime += delta;
    }

    public void draw(SpriteBatch spriteBatch, boolean isKeyCollected) {
        if (!isKeyCollected){
            spriteBatch.draw(
                    keyAnimation.getKeyFrame(keyAnimationTime, true),
                    keyRect.x,
                    keyRect.y,
                    keyRect.width,
                    keyRect.height
            );
        }

    }
    public void dispose() {
        if (keyPicture != null) {
            keyPicture.dispose();
            keyPicture = null;  // Set to null to indicate disposal
        }
    }

    public Rectangle getKeyRect() {
        return keyRect;
    }

    public void setKeyRect(Rectangle keyRect) {
        this.keyRect = keyRect;
    }

}
