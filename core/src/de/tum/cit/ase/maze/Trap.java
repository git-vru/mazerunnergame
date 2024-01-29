package de.tum.cit.ase.maze;

import java.util.ArrayList;
import java.util.List;

public class Trap extends GameObject{
    private static List<Trap> trapList = new ArrayList<>();

    public Trap(float x,float y) {
        super(x,y,60,60);
        this.animation = loadHorizontalAnimation("objects.png",64,48,16,16,7,0.1f);
    }

    public static List<Trap> getTrapList() {
        return trapList;
    }
}
