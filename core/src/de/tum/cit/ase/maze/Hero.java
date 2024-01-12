package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.*;

public class Hero {
   private float x;
   private float y;
   private float leftTimer;
   private float rightTimer;
   private float upTimer;
   private float downTimer;
   private float standTimer;
   private String direction;
   private final Animation<TextureRegion> leftAnimation;
   private final Animation<TextureRegion> rightAnimation;
   private final Animation<TextureRegion> upAnimation;
   private final Animation<TextureRegion> downAnimation;
   private final Animation<TextureRegion> standAnimation;

   public Hero(float x, float y, Animation<TextureRegion> leftAnimation,
               Animation<TextureRegion> rightAnimation, Animation<TextureRegion> upAnimation,
               Animation<TextureRegion> downAnimation, Animation<TextureRegion> standAnimation) {
      this.x = x;
      this.y = y;
      this.leftAnimation = leftAnimation;
      this.rightAnimation = rightAnimation;
      this.upAnimation = upAnimation;
      this.downAnimation = downAnimation;
      this.standAnimation = standAnimation;
   }

   public void update(float delta, String direction) {
      this.direction=direction;
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

   public void draw(SpriteBatch spriteBatch) {
      spriteBatch.draw(
              getCurrentFrame().getKeyFrame(getAnimationTimer(), true),
              x,
              y,
              40,
              80
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
      x -= delta;
   }

   public void moveRight(float delta) {
      x += delta;
   }

   public void moveUp(float delta) {
      y += delta;
   }

   public void moveDown(float delta) {
      y -= delta;
   }

   // Getters for x and y
   public float getX() {
      return x;
   }

   public float getY() {
      return y;
   }
}