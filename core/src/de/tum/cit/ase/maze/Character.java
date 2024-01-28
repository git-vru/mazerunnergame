package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public abstract class Character {
    private float x;
    private float y;
    private float leftTimer;
    private float rightTimer;
    private float upTimer;
    private float downTimer;
    private float standTimer;
    private String direction;
    private Animation<TextureRegion> leftAnimation;
    private Animation<TextureRegion> rightAnimation;
    private Animation<TextureRegion> upAnimation;
    private Animation<TextureRegion> downAnimation;
    private Animation<TextureRegion> standAnimation;
    private int width;
    private int height;
    private Rectangle rectangle;
    float prevX,prevY;

    public Character(float x, float y, float leftTimer,
                     float rightTimer, float upTimer, float downTimer,
                     float standTimer, String direction, Animation<TextureRegion> leftAnimation,
                     Animation<TextureRegion> rightAnimation, Animation<TextureRegion> upAnimation,
                     Animation<TextureRegion> downAnimation, Animation<TextureRegion> standAnimation,
                     int width, int height, Rectangle rectangle, float prevX, float prevY) {
        this.x = x;
        this.y = y;
        this.leftTimer = leftTimer;
        this.rightTimer = rightTimer;
        this.upTimer = upTimer;
        this.downTimer = downTimer;
        this.standTimer = standTimer;
        this.direction = direction;
        this.leftAnimation = leftAnimation;
        this.rightAnimation = rightAnimation;
        this.upAnimation = upAnimation;
        this.downAnimation = downAnimation;
        this.standAnimation = standAnimation;
        this.width = width;
        this.height = height;
        this.rectangle = rectangle;
        this.prevX = prevX;
        this.prevY = prevY;
    }

    public abstract void loadAnimation();
    public abstract void update(float delta, String direction);
    public abstract void draw(SpriteBatch spriteBatch);
    public abstract Animation<TextureRegion> getCurrentFrame();
    public abstract float getAnimationTimer();
    public abstract void moveLeft(float delta);
    public abstract void moveRight(float delta);
    public abstract void moveUp(float delta);
    public abstract void moveDown(float delta);

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

    public float getLeftTimer() {
        return leftTimer;
    }

    public void setLeftTimer(float leftTimer) {
        this.leftTimer = leftTimer;
    }

    public float getRightTimer() {
        return rightTimer;
    }

    public void setRightTimer(float rightTimer) {
        this.rightTimer = rightTimer;
    }

    public float getUpTimer() {
        return upTimer;
    }

    public void setUpTimer(float upTimer) {
        this.upTimer = upTimer;
    }

    public float getDownTimer() {
        return downTimer;
    }

    public void setDownTimer(float downTimer) {
        this.downTimer = downTimer;
    }

    public float getStandTimer() {
        return standTimer;
    }

    public void setStandTimer(float standTimer) {
        this.standTimer = standTimer;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Animation<TextureRegion> getLeftAnimation() {
        return leftAnimation;
    }

    public void setLeftAnimation(Animation<TextureRegion> leftAnimation) {
        this.leftAnimation = leftAnimation;
    }

    public Animation<TextureRegion> getRightAnimation() {
        return rightAnimation;
    }

    public void setRightAnimation(Animation<TextureRegion> rightAnimation) {
        this.rightAnimation = rightAnimation;
    }

    public Animation<TextureRegion> getUpAnimation() {
        return upAnimation;
    }

    public void setUpAnimation(Animation<TextureRegion> upAnimation) {
        this.upAnimation = upAnimation;
    }

    public Animation<TextureRegion> getDownAnimation() {
        return downAnimation;
    }

    public void setDownAnimation(Animation<TextureRegion> downAnimation) {
        this.downAnimation = downAnimation;
    }

    public Animation<TextureRegion> getStandAnimation() {
        return standAnimation;
    }

    public void setStandAnimation(Animation<TextureRegion> standAnimation) {
        this.standAnimation = standAnimation;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
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
}
