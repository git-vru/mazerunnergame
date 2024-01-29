package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Hero extends Character {
   private float x;
   private float y;
   private float leftTimer;
   private float rightTimer;
   private float upTimer;
   private float downTimer;
   private float standTimer;
   private float danceTimer;
   private float cryTimer;
   private String direction;
   private Animation<TextureRegion> downAnimation;
   private Animation<TextureRegion> leftAnimation;
   private Animation<TextureRegion> rightAnimation;
   private Animation<TextureRegion> upAnimation;
   private Animation<TextureRegion> standAnimation;
   private Rectangle rectangle;
   private int height;
   private int width;
   float prevX,prevY;
   private int lives = 5;
   private boolean keyCollected;
   private int enemiesKilled;
   private boolean winner;
   private boolean dead;
   private Animation<TextureRegion> danceAnimation;
   private Animation<TextureRegion> cryAnimation;


    public Hero(float x, float y, float leftTimer,
                float rightTimer, float upTimer, float downTimer,
                float standTimer, String direction, Animation<TextureRegion> leftAnimation,
                Animation<TextureRegion> rightAnimation, Animation<TextureRegion> upAnimation,
                Animation<TextureRegion> downAnimation, Animation<TextureRegion> standAnimation,
                int width, int height, Rectangle rectangle, float prevX, float prevY) {
        super(x,y,leftTimer, rightTimer, upTimer, downTimer, standTimer, direction, leftAnimation, rightAnimation, upAnimation, downAnimation, standAnimation, width, height, rectangle, prevX, prevY);
        this.keyCollected = false;
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.dead = false;
        this.winner = false;
        this.width = 40;
        this.height=80;
        this.rectangle = new Rectangle(x,y+5,width,height/2);
        loadAnimation();

   }

    @Override
    public void loadAnimation() {
        Texture walkSheet = new Texture(Gdx.files.internal("character.png"));
        int frameWidth = 16;
        int frameHeight = 32;
        int animationFrames = 4;
        TextureRegion walkStandFrame = new TextureRegion(walkSheet, 0, 0, frameWidth, frameHeight);
        Array<TextureRegion> walkLeftFrames = new Array<>(TextureRegion.class);
        Array<TextureRegion> walkRightFrames = new Array<>(TextureRegion.class);
        Array<TextureRegion> walkUpFrames = new Array<>(TextureRegion.class);
        Array<TextureRegion> walkDownFrames = new Array<>(TextureRegion.class);
        // Add all frames to the animation
        for (int col = 0; col < animationFrames; col++) {
            walkLeftFrames.add(new TextureRegion(walkSheet, col * frameWidth, 96, frameWidth, frameHeight));
            walkDownFrames.add(new TextureRegion(walkSheet, col * frameWidth, 0, frameWidth, frameHeight));
            walkRightFrames.add(new TextureRegion(walkSheet, col * frameWidth, 32, frameWidth, frameHeight));
            walkUpFrames.add(new TextureRegion(walkSheet, col * frameWidth, 64, frameWidth, frameHeight));
        }

        leftAnimation = new Animation<>(0.1f, walkLeftFrames);
        rightAnimation = new Animation<>(0.1f, walkRightFrames);
        upAnimation = new Animation<>(0.1f, walkUpFrames);
        downAnimation = new Animation<>(0.1f, walkDownFrames);
        standAnimation = new Animation<>(0.1f, walkStandFrame);
    }

    @Override
    public void update(float delta, String direction) {
        setDirection(direction);
        switch (getDirection()) {
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

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(
                getCurrentFrame().getKeyFrame(getAnimationTimer(), true),
                x,
                y,getWidth(),getHeight()
        );
    }

    @Override
    public void moveLeft(float delta) {
        setPrevX(x);
        x -= delta;
    }

    @Override
    public void moveRight(float delta) {
        setPrevX(x);
        x += delta;
    }

    @Override
    public void moveUp(float delta) {
        setPrevY(y);
        y += delta;
    }

    @Override
    public void moveDown(float delta) {
        setPrevY(y);
        y -= delta;
    }

   public void loadDanceAnimation() {
      Texture danceSheet = new Texture(Gdx.files.internal("character.png"));

      int frameWidth = 16;
      int frameHeight = 32;
      Array<TextureRegion> danceAnimationFrame = new Array<>(TextureRegion.class);

      // Add all frames to the animation
      for (int col = 0; col < 2; col++) {
         danceAnimationFrame.add(new TextureRegion(danceSheet, (col * frameWidth) + 96, 0, frameWidth, frameHeight));
      }
      danceAnimation = new Animation<>(0.25f, danceAnimationFrame);
   }

   public void loadCryAnimation() {
      Texture danceSheet = new Texture(Gdx.files.internal("character.png"));

      int frameWidth = 16;
      int frameHeight = 32;
      Array<TextureRegion> danceAnimationFrame = new Array<>(TextureRegion.class);

      // Add all frames to the animation
      danceAnimationFrame.add(new TextureRegion(danceSheet, frameWidth + 80, 0, frameWidth, frameHeight));
      cryAnimation = new Animation<>(0.25f, danceAnimationFrame);
   }

    @Override
    public Animation<TextureRegion> getCurrentFrame() {
        return switch (getDirection()) {
            case "left" -> leftAnimation;
            case "right" -> rightAnimation;
            case "up" -> upAnimation;
            case "down" -> downAnimation;
            default -> standAnimation;
        };
    }

    @Override
    public float getAnimationTimer() {
        return switch (getDirection()) {
            case "left" -> leftTimer;
            case "right" -> rightTimer;
            case "up" -> upTimer;
            case "down" -> downTimer;
            default -> standTimer;
        };
    }

    public void updateLives() {
         if (lives > 0) {
            lives -= 1;
         }
         else {
            lives = 0;
         }
   }

   public void updateEnemiesKilled() {
      if(Gdx.input.isKeyPressed(Input.Keys.C)) {
         enemiesKilled += 1;
      }
   }

   public void updateWinning() {
      if(Gdx.input.isKeyPressed(Input.Keys.B)) {
         setWinner(true);
      }
   }

   public void updateDead() {
      if(Gdx.input.isKeyPressed(Input.Keys.M)) {
         setDead(!isDead());
      }
   }


   // Getters for x and y
   public float getX() {
      return x;
   }

   public float getY() {
      return y;
   }

   public void setX(float x) {
      this.x = x;
   }

   public void setY(float y) {
      this.y = y;
   }

   public void setPrevX(float prevX) {
      this.prevX = prevX;
   }

   public void setPrevY(float prevY) {
      this.prevY = prevY;
   }

   public float getPrevX() {
      return prevX;
   }

   public float getPrevY() {
      return prevY;
   }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setHeroRect(Rectangle rectangle) {
      this.rectangle = rectangle;
   }

   public int getHeroHeight() {
      return height;
   }

   public int getHeroWidth() {
      return width;
   }

   public String getDirection() {
      return direction;
   }

   public void setDirection(String direction) {
      this.direction = direction;
   }
   public int getLives() {
      return lives;
   }

   public void setLives(int lives) {
      this.lives = lives;
   }

   public boolean isKeyCollected() {
      return keyCollected;
   }

   public void setKeyCollected(boolean keyCollected) {
      this.keyCollected = keyCollected;
   }

   public int getEnemiesKilled() {
      return enemiesKilled;
   }

   public void setEnemiesKilled(int enemiesKilled) {
      this.enemiesKilled = enemiesKilled;
   }
    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
    public float getDanceTimer() {
        return danceTimer;
    }

    public void setDanceTimer(float danceTimer) {
        this.danceTimer = danceTimer;
    }

    public Animation<TextureRegion> getDanceAnimation() {
        return danceAnimation;
    }

    public void setDanceAnimation(Animation<TextureRegion> danceAnimation) {
        this.danceAnimation = danceAnimation;
    }

    public Animation<TextureRegion> getCryAnimation() {
        return cryAnimation;
    }

    public void setCryAnimation(Animation<TextureRegion> cryAnimation) {
        this.cryAnimation = cryAnimation;
    }

    public float getCryTimer() {
        return cryTimer;
    }

    public void setCryTimer(float cryTimer) {
        this.cryTimer = cryTimer;
    }
}
