/*package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.security.AllPermission;

public class Enemy {
    private int direction;
    private float stateTime;
    private int enemyWidth;
    private int enemyHeight;
    private Rectangle enemyRect;
    private float x;
    private float y;
    private float leftTimer;
    private float rightTimer;
    private float upTimer;
    private float downTimer;
    private float standTimer;
    float prevX,prevY;
    private Array<Enemy> enemyArray;
    private Tiles allTiles;
    private Animation<TextureRegion> enemyLeftAnimation;
    private Animation<TextureRegion> enemyRightAnimation;
    private Animation<TextureRegion> enemyUpAnimation;
    private Animation<TextureRegion> enemyDownAnimation;
    private Animation<TextureRegion> enemyStandAnimation;

    public Enemy(float x, float y) {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.stateTime = 0;
        this.enemyHeight = 16;
        this.enemyWidth = 16;
        this.enemyRect = new Rectangle(x, y, enemyWidth, enemyHeight);
    }

    public void update(float delta, int direction) {
        setDirection(direction);
        switch (direction) {
            case 0:
                leftTimer += delta;
                break;
            case 1:
                rightTimer += delta;
                break;
            case 2:
                upTimer += delta;
                break;
            case 3:
                downTimer += delta;
                break;
            default:
                standTimer += delta;
                break;
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        for (Enemy enemy : getEnemyArray()) {
            spriteBatch.draw(
                    getCurrentFrame().getKeyFrame(getAnimationTimer(), true), enemy.getEnemyWidth(), enemy.getEnemyHeight()
            );
        }
    }

    private Animation<TextureRegion> getCurrentFrame() {
        return switch (getDirection()) {
            case 0 -> getEnemyLeftAnimation();
            case 1 -> getEnemyRightAnimation();
            case 2 -> getEnemyUpAnimation();
            case 3 -> getEnemyDownAnimation();
            default -> getEnemyStandAnimation();
        };
    }

    private float getAnimationTimer() {
        return switch (getDirection()) {
            case 0 -> leftTimer;
            case 1 -> rightTimer;
            case 2 -> upTimer;
            case 3 -> downTimer;
            default -> standTimer;
        };
    }
    public void loadEnemyAnimation() {
        Texture walkSheet = new Texture(Gdx.files.internal("mobs.png"));

        int frameWidth = 16;
        int frameHeight = 16;
        int animationFrames = 3;
        TextureRegion walkStandFrame = new TextureRegion(walkSheet, 0, 0, frameWidth, frameHeight);
        Array<TextureRegion> walkLeftFrames = new Array<>(TextureRegion.class);
        Array<TextureRegion> walkRightFrames = new Array<>(TextureRegion.class);
        Array<TextureRegion> walkUpFrames = new Array<>(TextureRegion.class);
        Array<TextureRegion> walkDownFrames = new Array<>(TextureRegion.class);

        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++) {
            walkLeftFrames.add(new TextureRegion(walkSheet, col * frameWidth, 16, frameWidth, frameHeight));
            walkDownFrames.add(new TextureRegion(walkSheet, col * frameWidth, 0, frameWidth, frameHeight));
            walkRightFrames.add(new TextureRegion(walkSheet, col * frameWidth, 32, frameWidth, frameHeight));
            walkUpFrames.add(new TextureRegion(walkSheet, col * frameWidth, 48, frameWidth, frameHeight));
        }
        enemyStandAnimation = new Animation<>(0.1f, walkStandFrame);
        enemyLeftAnimation = new Animation<>(0.1f, walkLeftFrames);
        enemyRightAnimation = new Animation<>(0.1f, walkRightFrames);
        enemyUpAnimation = new Animation<>(0.1f, walkUpFrames);
        enemyDownAnimation = new Animation<>(0.1f, walkDownFrames);
    }

    public void moveLeft(float delta) {
        setPrevX(x);
        x -= delta;
    }

    public void moveRight(float delta) {
        setPrevX(x);
        x += delta;
    }

    public void moveUp(float delta) {
        setPrevY(y);
        y += delta;
    }

    public void moveDown(float delta) {
        setPrevY(y);
        y -= delta;
    }



    public int determineEnemyDirection() {
        float speed = 100;
        int randomDirection = MathUtils.random(0,3);
        for (Rectangle rectangle: allTiles.getWallRectangles()) {
            if (rectangle.overlaps(this.getEnemyRect())){
                this.setX(this.getPrevX());
                this.setY(this.getPrevY());
            }
        }
        switch (randomDirection) {
            case 0 : this.moveLeft(speed * Gdx.graphics.getDeltaTime());
            case 1 : this.moveRight(speed * Gdx.graphics.getDeltaTime());
            case 2 : this.moveDown(speed * Gdx.graphics.getDeltaTime());
            case 3 : this.moveUp(speed * Gdx.graphics.getDeltaTime());
        }
        this.setEnemyRect(new Rectangle(this.getX(),this.getY(), this.getEnemyWidth(), this.getEnemyHeight()));

        return direction;
    }


    public int getEnemyWidth() {
        return enemyWidth;
    }

    public void setEnemyWidth(int enemyWidth) {
        this.enemyWidth = enemyWidth;
    }

    public int getEnemyHeight() {
        return enemyHeight;
    }

    public void setEnemyHeight(int enemyHeight) {
        this.enemyHeight = enemyHeight;
    }

    public Rectangle getEnemyRect() {
        return enemyRect;
    }

    public void setEnemyRect(Rectangle enemyRect) {
        this.enemyRect = enemyRect;
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
    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
    public Animation<TextureRegion> getEnemyLeftAnimation() {
        return enemyLeftAnimation;
    }

    public void setEnemyLeftAnimation(Animation<TextureRegion> enemyLeftAnimation) {
        this.enemyLeftAnimation = enemyLeftAnimation;
    }

    public Animation<TextureRegion> getEnemyRightAnimation() {
        return enemyRightAnimation;
    }

    public void setEnemyRightAnimation(Animation<TextureRegion> enemyRightAnimation) {
        this.enemyRightAnimation = enemyRightAnimation;
    }

    public Animation<TextureRegion> getEnemyUpAnimation() {
        return enemyUpAnimation;
    }

    public void setEnemyUpAnimation(Animation<TextureRegion> enemyUpAnimation) {
        this.enemyUpAnimation = enemyUpAnimation;
    }

    public Animation<TextureRegion> getEnemyDownAnimation() {
        return enemyDownAnimation;
    }

    public void setEnemyDownAnimation(Animation<TextureRegion> enemyDownAnimation) {
        this.enemyDownAnimation = enemyDownAnimation;
    }

    public Animation<TextureRegion> getEnemyStandAnimation() {
        return enemyStandAnimation;
    }

    public void setEnemyStandAnimation(Animation<TextureRegion> enemyStandAnimation) {
        this.enemyStandAnimation = enemyStandAnimation;
    }

    public Array<Enemy> getEnemyArray() {
        return enemyArray;
    }

    public void setEnemyArray(Array<Enemy> enemyArray) {
        this.enemyArray = enemyArray;
    }
}
*/