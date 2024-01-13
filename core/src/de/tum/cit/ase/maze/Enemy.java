package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Enemy {
    private Vector2 position;
    private int direction;
    private Animation<TextureRegion> animation;
    private float stateTime;

    public Enemy(Vector2 position, Animation<TextureRegion> animation) {
        this.position = position;
        this.direction = MathUtils.random(0, 3);
        this.animation = animation;
        this.stateTime = 0;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public void update(float delta) {
        stateTime += delta;
        updatePosition(delta);
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    private void updatePosition(float delta) {
        float speed = 60f;
        float enemyX = position.x;
        float enemyY = position.y;

        switch (direction) {
            case 0: // Up
                enemyY += speed * delta;
                break;
            case 1: // Right
                enemyX += speed * delta;
                break;
            case 2: // Down
                enemyY -= speed * delta;
                break;
            case 3: // Left
                enemyX -= speed * delta;
                break;
        }
        position.set(enemyX, enemyY);
    }

    public static Animation<TextureRegion> createEnemyAnimation() {
        Texture enemySheet = new Texture(Gdx.files.internal("mobs.png"));

        int frameWidth = 16;
        int frameHeight = 64;
        int animationFrames = 3;
        Array<TextureRegion> enemyFrames = new Array<>(TextureRegion.class);

        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++) {
            enemyFrames.add(new TextureRegion(enemySheet, col * frameWidth, frameHeight, frameWidth, frameHeight));
        }
        return new Animation<>(0.1f, enemyFrames);
    }
}