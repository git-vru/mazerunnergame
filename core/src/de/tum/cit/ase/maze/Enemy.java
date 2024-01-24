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
    private int prevIndex =-1;
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
    public static List<Enemy> enemyList= new ArrayList<>();
    private final List<String> directionList;
    private float speed;
    private static final int STEP_DISTANCE = 300;
    private int stepsRemaining;
    public Enemy(float x, float y) {
        this.x=x;
        this.y=y;
        this.prevX=x;
        this.prevY=y;
        this.directionList = new ArrayList<>(Arrays.asList("up","right","down","left"));
        this.enemyHeight = 16;
        this.enemyWidth = 16;
        this.enemyRect = new Rectangle(x, y, 40, 40);
        this.enemyRect.setCenter(x+10,y+10);
        this.prevIndex = -1;
        this.speed = 100f;
        this.stepsRemaining = STEP_DISTANCE;
        loadEnemyAnimation();
        this.direction = getDirection();
    }

    public void update(float delta) {
        float movement = speed * delta;
        // Check if there are steps remaining
        if (stepsRemaining > 0) {
            move(movement,delta);
            stepsRemaining -= movement;
            if (stepsRemaining <= 0) {
                stepsRemaining = STEP_DISTANCE;
                direction = getDirection();
            }
        }
        enemyRect.setPosition(x,y);
    }
    public void changeDirection(){
        setX(prevX);
        setY(prevY);
        resetAnimationTimers();
        setDirection(getDirection());
    }
    public void move(float distance, float delta) {
        if (!GameScreen.isResumed()) {
            // Implement movement logic based on the current direction
            switch (direction) {
                case "left":
                    moveLeft(distance);
                    leftTimer += delta;
                    break;
                case "right":
                    moveRight(distance);
                    rightTimer += delta;
                    break;
                case "up":
                    moveUp(distance);
                    upTimer += delta;
                    break;
                case "down":
                    moveDown(distance);
                    downTimer += delta;
                    break;
            }
        }
    }

    public String getDirection() {
        int index = MathUtils.random(0,3);
        while(index == prevIndex){
            index = MathUtils.random(0,3);
        }
        prevIndex=index;
        return directionList.get(index);
    }
    private void resetAnimationTimers() {
        leftTimer = 0;
        rightTimer = 0;
        upTimer = 0;
        downTimer = 0;
        standTimer = 0;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    private void loadEnemyAnimation() {
        Texture walkSheet = new Texture(Gdx.files.internal("mobs.png"));

        int animationFrames = 3;
        TextureRegion walkStandFrame = new TextureRegion(walkSheet, 64, 64, enemyWidth, enemyHeight);
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
                    y, 60, 60
            );
    }

    private Animation<TextureRegion> getCurrentFrame() {
        if (!GameScreen.isResumed()){
            return switch (direction) {
                case "left" -> leftAnimation;
                case "right" -> rightAnimation;
                case "up" -> upAnimation;
                case "down" -> downAnimation;
                default -> standAnimation;
            };
        }
        else {
            return standAnimation;
        }
    }

    private float getAnimationTimer() {
        if (!GameScreen.isResumed()){
            return switch (direction) {
                case "left" -> leftTimer;
                case "right" -> rightTimer;
                case "up" -> upTimer;
                case "down" -> downTimer;
                default -> standTimer;
            };
        }else {
            return standTimer;
        }

    }
    public void moveLeft(float distance) {
        setPrevX(x);
        x -= distance;
        enemyRect.setX(x);
    }

    public void moveRight(float distance) {
        setPrevX(x);
        x +=distance;
        enemyRect.setX(x);
    }

    public void moveUp(float distance) {
        setPrevY(y);
        y += distance;
        enemyRect.setY(y);
    }

    public void moveDown(float distance) {
        setPrevY(y);
        y -= distance;
        enemyRect.setY(y);
    }

    public Rectangle getEnemyRect() {
        return enemyRect;
    }

    public void setStepsRemaining(int stepsRemaining) {
        this.stepsRemaining = stepsRemaining;
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
    public void setPrevX(float prevX) {
        this.prevX = prevX;
    }

    public float getPrevY() {
        return prevY;
    }

    public void setPrevY(float prevY) {
        this.prevY = prevY;
    }

    public void setEnemyRect(Rectangle enemyRect) {
        this.enemyRect = enemyRect;
    }

}