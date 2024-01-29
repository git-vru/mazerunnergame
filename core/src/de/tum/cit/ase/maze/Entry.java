package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Entry extends GameObject {
    private Rectangle mazeLeaver;
    private boolean open;

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
