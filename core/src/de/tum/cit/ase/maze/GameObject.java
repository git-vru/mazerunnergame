package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class GameObject {
    protected float x;
    protected float y;
    protected Rectangle rect;
    protected Animation<TextureRegion> animation;
    protected float animationTime;
    protected Texture texture;

    public GameObject(float x, float y, String texturePath, int frameWidth, int frameHeight,int rectWidth,int rectHeight, int animationFrames) {
        this.x = x;
        this.y = y;
        this.rect = new Rectangle(x, y, rectWidth,rectHeight);
        this.texture = new Texture(Gdx.files.internal(texturePath));
        loadAnimation(frameWidth, frameHeight, animationFrames);
    }

    private void loadAnimation(int frameWidth, int frameHeight, int animationFrames) {
        Array<TextureRegion> frames = new Array<>(TextureRegion.class);
        for (int col = 0; col < animationFrames; col++) {
            frames.add(new TextureRegion(texture, (col * frameWidth+64), 48, frameWidth, frameHeight));
        }
        this.animation = new Animation<>(0.1f, frames);
    }

    public void update(float delta) {
        if (!GameScreen.isResumed()) {
            animationTime += delta;
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(
                animation.getKeyFrame(animationTime, true),
                x,
                y,
                rect.width,
                rect.height
        );
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
            texture = null;
        }
    }

    public Rectangle getRect() {
        return rect;
    }
}