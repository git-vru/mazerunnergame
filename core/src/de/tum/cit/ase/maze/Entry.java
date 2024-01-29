package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * The Entry class represents an entry object in the game, which makes an animation, and states a starting point for hero.
 * It extends the GameObject class and includes functionalities for managing entry states.
 */
public class Entry extends GameObject {
    private Rectangle mazeLeaver;
    private boolean open;

    /**
     * Constructs an Entry object with the specified position.
     *
     * @param x The x-coordinate of the entry.
     * @param y The y-coordinate of the entry.
     */
    public Entry(float x,float y) {
        super(x,y,60,60);
        this.animation = loadVerticalAnimation("things.png",0,0,16,16,4,0.25f);
        this.open=true;
    }
    @Override
    public void draw(SpriteBatch spriteBatch,boolean open) {
       if (open){
           spriteBatch.draw(
                   animation.getKeyFrame(animationTime, true),
                   x,
                   y,
                   rect.width,
                   rect.height
           );
           setOpen(false);
       }else spriteBatch.draw(animation.getKeyFrames()[0],x,y,rect.width,rect.height);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setMazeLeaver(Rectangle mazeLeaver) {
        this.mazeLeaver = mazeLeaver;
    }

    public Rectangle getMazeLeaver() {
        return mazeLeaver;
    }
}
