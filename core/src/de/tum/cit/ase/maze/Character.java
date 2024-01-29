package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The Character class is an abstract class representing a game character.
 * It extends the GameObject class and includes common functionalities for character entities.
 */

public abstract class Character extends GameObject {
    protected String direction;
    protected Animation<TextureRegion> leftAnimation;
    protected Animation<TextureRegion> rightAnimation;
    protected Animation<TextureRegion> upAnimation;
    protected Animation<TextureRegion> downAnimation;
    private float prevX;
    private float prevY;
    protected float sinusInput;

    /**
     * Constructs a Character object with the specified position and dimensions.
     *
     * @param x          The x-coordinate of the character.
     * @param y          The y-coordinate of the character.
     * @param rectWidth  The width of the character's bounding rectangle.
     * @param rectHeight The height of the character's bounding rectangle.
     */
    public Character(float x, float y, int rectWidth, int rectHeight) {
        super(x, y, rectWidth, rectHeight);
        this.prevX = x;
        this.prevY = y;
        this.direction = "down"; // Default direction
    }

    /**
     * Updates the character's state based on the delta time.
     *
     * @param delta The time in seconds since the last update.
     */
    public abstract void update(float delta);
    public abstract void draw(SpriteBatch spriteBatch);

    public Animation<TextureRegion> getCurrentAnimation() {
        return switch (getDirection()) {
            case "left" -> leftAnimation;
            case "right" -> rightAnimation;
            case "up" -> upAnimation;
            case "down" -> downAnimation;
            default -> animation;
        };
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public float getPrevX() {
        return prevX;
    }

    public void setPrevX(float prevX) {
        this.prevX = prevX;
    }

    public float getPrevY() {
        return prevY;
    }

    public void setPrevY(float prevY) {
        this.prevY = prevY;
    }

    public String getDirection() {
        return direction;
    }
}
