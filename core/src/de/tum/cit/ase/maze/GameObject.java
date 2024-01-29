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
    protected Animation<TextureRegion> animation;
    protected Rectangle rect;
    protected float animationTime;
    protected Texture texture;

    public GameObject(float x, float y,int rectWidth,int rectHeight) {
        this.x = x;
        this.y = y;
        this.rect = new Rectangle(x, y, rectWidth,rectHeight);
    }
    /**
     * Loads animation from the given file at the given coordinates.
     */
    public Animation<TextureRegion> loadHorizontalAnimation(String path, int imageX, int imageY, int frameWidth, int frameHeight, int frames, float duration) {
        this.texture = new Texture(Gdx.files.internal(path));
        // libGDX internal Array instead of ArrayList because of performance
        Array<TextureRegion> Frames = new Array<>(TextureRegion.class);

        // Add all frames to the animation
        for (int col = 0; col < frames; col++) {
            Frames.add(new TextureRegion(texture, col * frameWidth + imageX, imageY, frameWidth, frameHeight));
        }

        return new Animation<>(duration, Frames);
    }
    public Animation<TextureRegion> loadVerticalAnimation(String path, int imageX, int imageY, int frameWidth, int frameHeight, int frames, float duration) {
        this.texture = new Texture(Gdx.files.internal(path));
        // libGDX internal Array instead of ArrayList because of performance
        Array<TextureRegion> Frames = new Array<>(TextureRegion.class);

        // Add all frames to the animation
        for (int col = 0; col < frames; col++) {
            Frames.add(new TextureRegion(texture, imageX, col * frameHeight + imageY, frameWidth, frameHeight));
        }

        return new Animation<>(duration, Frames);
    }

    public void update(float delta) {
        if (!GameScreen.isResumed()) {
            animationTime += delta;
        }
    }

    public void draw(SpriteBatch spriteBatch,boolean toDraw) {
        if (toDraw){
            spriteBatch.draw(
                    animation.getKeyFrame(animationTime, true),
                    x,
                    y,
                    rect.width,
                    rect.height
            );
        }
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

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}