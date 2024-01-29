package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Character extends GameObject {
    protected String direction;
    protected Animation<TextureRegion> leftAnimation;
    protected Animation<TextureRegion> rightAnimation;
    protected Animation<TextureRegion> upAnimation;
    protected Animation<TextureRegion> downAnimation;
    private float prevX;
    private float prevY;
    protected float sinusInput;

    public Character(float x, float y, int rectWidth, int rectHeight) {
        super(x, y, rectWidth, rectHeight);
        this.prevX = x;
        this.prevY = y;
        this.direction = "down"; // Default direction
    }


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
