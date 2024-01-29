package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

/**
 * The Exit class represents an exit object in the game, which can be opened or closed.
 * It extends the GameObject class and includes functionalities for managing exit states.
 */
public class Exit extends GameObject{
    private boolean open;
    private static final List<Exit> exitList=new ArrayList<>();

    /**
     * Constructs an Exit object with the specified position.
     *
     * @param x The x-coordinate of the exit.
     * @param y The y-coordinate of the exit.
     */
    public Exit(float x,float y) {
        super(x,y,60,60);
        this.animation = loadVerticalAnimation("things.png",48,0,16,16,4,0.25f);
        this.open=false;

    }

    public static List<Exit> getExitList() {
        return exitList;
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
}
