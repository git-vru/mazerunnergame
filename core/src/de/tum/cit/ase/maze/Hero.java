package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.*;

import java.awt.*;

public class Hero extends Character{
   private String direction;
   private int lives = 5;
   private boolean keyCollected;
   private boolean winner;
   private boolean dead;
   private float danceTimer;
   private final Animation<TextureRegion> danceAnimation;
   private final Animation<TextureRegion> cryAnimation;


    public Hero(float x, float y) {
        super(x,y,40,40);
      this.keyCollected = false;
      this.dead = false;
      this.winner = false;
      this.leftAnimation = loadHorizontalAnimation("character.png",0,96,16,32,4,0.1f);
      this.downAnimation = loadHorizontalAnimation("character.png",0,0,16,32,4,0.1f);
      this.rightAnimation = loadHorizontalAnimation("character.png",0,32,16,32,4,0.1f);
      this.upAnimation = loadHorizontalAnimation("character.png",0,64,16,32,4,0.1f);
      this.animation = loadHorizontalAnimation("character.png",0,0,16,32,1,0.1f);
      this.cryAnimation = loadHorizontalAnimation("character.png",80,0,16,32,1,0.25f);
      this.danceAnimation = loadHorizontalAnimation("character.png",96,0,16,32,2,0.25f);
   }
    @Override
    public void update(float delta) {
        setDirection(direction);
        sinusInput += delta;
    }

    public void draw(SpriteBatch spriteBatch) {
      spriteBatch.draw(
              super.getCurrentAnimation().getKeyFrame(sinusInput, true),
              x,
              y,rect.width,rect.height*2
      );
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
   // Getters & Setters

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
    public Animation<TextureRegion> getCryAnimation() {
        return cryAnimation;
    }
}
