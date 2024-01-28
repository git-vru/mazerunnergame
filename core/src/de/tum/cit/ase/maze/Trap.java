package de.tum.cit.ase.maze;

import java.util.ArrayList;
import java.util.List;

public class Trap extends GameObject{
    private static List<Trap> trapList = new ArrayList<>();

    public Trap(float x,float y) {
        super(x,y,"objects.png",16,16,60,60,7);
    }

    public static List<Trap> getTrapList() {
        return trapList;
    }
}
