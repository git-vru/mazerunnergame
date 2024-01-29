package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Exit extends GameObject {
    private boolean open;
    private static final List<Exit> exitList=new ArrayList<>();

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
