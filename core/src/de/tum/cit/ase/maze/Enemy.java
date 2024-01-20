package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Enemy {
    private String direction;
    private int prevIndex;
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
    private Animation<TextureRegion> leftAnimation;
    private Animation<TextureRegion> rightAnimation;
    private Animation<TextureRegion> upAnimation;
    private Animation<TextureRegion> downAnimation;
    private Animation<TextureRegion> standAnimation;
    float prevX,prevY;
    private static List<Enemy> enemyList;
    private final List<String> directionList;

    public Enemy(float x, float y) {
        this.directionList = new ArrayList<>(Arrays.asList("up","right","down","left","stand"));
        this.stateTime = 0;
        this.enemyHeight = 16;
        this.enemyWidth = 16;
        this.enemyRect = new Rectangle(x, y, enemyWidth, enemyHeight);
        prevIndex = 0;
    }

    public void update(float delta) {
        this.direction=getDirection();
        switch (direction) {
            case "left":
                leftTimer += delta;
                break;
            case "right":
                rightTimer += delta;
                break;
            case "up":
                upTimer += delta;
                break;
            case "down":
                downTimer += delta;
                break;
            default:
                standTimer += delta;
                break;
        }
    }

    public String getDirection() {
        int index = MathUtils.random(0,5);
        while(index == prevIndex){
            index = MathUtils.random(0,5);
        }
        return directionList.get(index);
//        switch(index){
//            case 0:
//                setDirection(directionList.get(MathUtils.random(0)));
//                break;
//            case 1:
//                setDirection("up");
//                break;
//            case 2:
//                setDirection("up");
//                break;
//            case 3:
//                setDirection("up");
//                break;
//            default:
//                break;
//        }
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
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
    private void loadEnemyAnimation() {
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

        standAnimation = new Animation<>(0.1f, walkStandFrame);
        leftAnimation = new Animation<>(0.1f, walkLeftFrames);
        rightAnimation = new Animation<>(0.1f, walkRightFrames);
        upAnimation = new Animation<>(0.1f, walkUpFrames);
        downAnimation = new Animation<>(0.1f, walkDownFrames);
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(
                getCurrentFrame().getKeyFrame(getAnimationTimer(), true),
                x,
                y,getEnemyWidth(),getEnemyHeight()
        );
    }

    private Animation<TextureRegion> getCurrentFrame() {
        return switch (direction) {
            case "left" -> leftAnimation;
            case "right" -> rightAnimation;
            case "up" -> upAnimation;
            case "down" -> downAnimation;
            default -> standAnimation;
        };
    }
    private float getAnimationTimer() {
        return switch (direction) {
            case "left" -> leftTimer;
            case "right" -> rightTimer;
            case "up" -> upTimer;
            case "down" -> downTimer;
            default -> standTimer;
        };
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
//    public int determineEnemyDirection() {
//        int direction = -1;
//
//        float speed = 100;
//        for (Rectangle rectangle: game.getWallRectangles()) {
//            if (rectangle.overlaps(this.getEnemyRect())){
//                this.setX(this.getPrevX());
//                this.setY(this.getPrevY());
//            }
//        }
//
//        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
//            this.moveLeft(speed * Gdx.graphics.getDeltaTime());
//            direction = 0;
//        } else if (Gdx.input.isKeyPressed(Input.Keys.O)) {
//            this.moveRight(speed * Gdx.graphics.getDeltaTime());
//            direction = 1;
//        } else if (Gdx.input.isKeyPressed(Input.Keys.I)) {
//            this.moveDown(speed * Gdx.graphics.getDeltaTime());
//            direction = 2;
//        } else if (Gdx.input.isKeyPressed(Input.Keys.U)) {
//            this.moveUp(speed * Gdx.graphics.getDeltaTime());
//            direction = 3;
//        }
//        this.setEnemyRect(new Rectangle(this.getX(),this.getY(), this.getEnemyWidth(), this.getEnemyHeight()));
//
//        return direction;
//    }

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

    public Animation<TextureRegion> getLeftAnimation() {
        return leftAnimation;
    }

    public Animation<TextureRegion> getRightAnimation() {
        return rightAnimation;
    }

    public Animation<TextureRegion> getUpAnimation() {
        return upAnimation;
    }

    public Animation<TextureRegion> getDownAnimation() {
        return downAnimation;
    }

    public Animation<TextureRegion> getStandAnimation() {
        return standAnimation;
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