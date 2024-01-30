package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * This class is responsible for drawing the game objects like wall, key, etc. according to their animations.
 */
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
     * Loads animation if the textures for a significant object is lined up in horizontal way like enemy, hero, etc.
     * @param path        The file path to the sprite sheet image.
     * @param imageX      The starting x-coordinate of the first frame in the sprite sheet.
     * @param imageY      The y-coordinate of the frames in the sprite sheet.
     * @param frameWidth  The width of each frame in the sprite sheet.
     * @param frameHeight The height of each frame in the sprite sheet.
     * @param frames      The number of frames in the animation.
     * @param duration    The duration of each frame in seconds.
     * @return A horizontal animation created from the specified sprite sheet parameters.
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

    /**
     * Loads animation if the textures for a significant object is lined up in vertical way like enemy, hero, etc.
     * @param path        The file path to the sprite sheet image.
     * @param imageX      The starting x-coordinate of the first frame in the sprite sheet.
     * @param imageY      The y-coordinate of the frames in the sprite sheet.
     * @param frameWidth  The width of each frame in the sprite sheet.
     * @param frameHeight The height of each frame in the sprite sheet.
     * @param frames      The number of frames in the animation.
     * @param duration    The duration of each frame in seconds.
     * @return A vertical animation created from the specified sprite sheet parameters.
     */
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

    /**
     * Updates the Game objects state based on the elapsed time.
     *
     * @param delta The time in seconds since the last update.
     */
    public void update(float delta) {
        if (!GameScreen.isResumed()) {
            animationTime += delta;
        }
    }

    /**
     * Draws the game objects
     * @param spriteBatch The SpriteBatch used for rendering graphics.
     * @param toDraw It is a boolean method used in the if statement.
     */
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