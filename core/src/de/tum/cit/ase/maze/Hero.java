package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Hero {
   private float x;
   private float y;
   private float leftTimer;
   private float rightTimer;
   private float upTimer;
   private float downTimer;
   private float standTimer;
   private String direction;
   private Animation<TextureRegion> downAnimation;
   private Animation<TextureRegion> leftAnimation;
   private Animation<TextureRegion> rightAnimation;
   private Animation<TextureRegion> upAnimation;
   private Animation<TextureRegion> standAnimation;
   private Animation<TextureRegion> celebrationAnimation;
   private Rectangle heroRect;
   private int heroHeight;
   private int heroWidth;
   float prevX,prevY;
   private int lives = 4;
   private boolean keyCollected;
   private int enemiesKilled;

   public Hero(float x, float y) {
      this.keyCollected = false;
      this.x = x;
      this.y = y;
      this.prevX = x;
      this.prevY = y;
      this.heroWidth = 40;
      this.heroHeight=80;
      this.heroRect = new Rectangle(x,y+5,heroWidth,heroHeight/2);
      loadCharacterAnimation();
   }
   public void loadCelebration(){
      Texture walkSheet = new Texture(Gdx.files.internal("character.png"));

      int frameWidth = 16;
      int frameHeight = 32;
      int animationFrames = 3;
      Array<TextureRegion> celebrationFrames = new Array<>(TextureRegion.class);
      for (int col = 0; col < animationFrames; col++) {
         celebrationFrames.add(new TextureRegion(walkSheet, 80+(col * frameWidth), 0, frameWidth, frameHeight));
      }
      celebrationAnimation = new Animation<>(0.1f, celebrationFrames);
   }
   private void loadCharacterAnimation() {
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

   public void draw(SpriteBatch spriteBatch) {
      spriteBatch.draw(
              getCurrentFrame().getKeyFrame(getAnimationTimer(), true),
              x,
              y,getHeroWidth(),getHeroHeight()
      );
   }

   private Animation<TextureRegion> getCurrentFrame() {
      return switch (getDirection()) {
         case "left" -> leftAnimation;
         case "right" -> rightAnimation;
         case "up" -> upAnimation;
         case "down" -> downAnimation;
         default -> standAnimation;
      };
   }

   private float getAnimationTimer() {
      return switch (getDirection()) {
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

   public void updateKeyCollected() {
      if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
         keyCollected = true;
      }
   }

   public void updateLives() {
      if (Gdx.input.isKeyPressed(Input.Keys.X)) {
         if (lives > 0) {
            lives -= 1;
         }
         else {
            lives = 0;
         }
      }
   }

   public void updateEnemiesKilled() {
      if(Gdx.input.isKeyPressed(Input.Keys.C)) {
         enemiesKilled += 1;
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

   public Rectangle getHeroRect() {
      return heroRect;
   }

   public void setHeroRect(Rectangle heroRect) {
      this.heroRect = heroRect;
   }

   public int getHeroHeight() {
      return heroHeight;
   }

   public int getHeroWidth() {
      return heroWidth;
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
}