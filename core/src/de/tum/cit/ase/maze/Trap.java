package de.tum.cit.ase.maze;

import java.util.ArrayList;
import java.util.List;

/**
 * The Trap class represents a trap object in the MazeRunnerGame. It is a subclass of GameObject
 * and includes functionality specific to traps and other objects in the game.
 */
public class Trap extends GameObject{
    private static List<Trap> trapList = new ArrayList<>(); // List to store instances of Trap objects

    /**
     * Constructs a new Trap instance with the specified position.
     *
     * @param x The x-coordinate of the trap's position.
     * @param y The y-coordinate of the trap's position.
     */
    public Trap(float x,float y) {
        super(x,y,60,60); // Initializes the GameObject with the specified dimensions
        this.animation = loadHorizontalAnimation("objects.png",64,48,16,16,7,0.1f);
    }

    public static List<Trap> getTrapList() {
        return trapList;
    }
}
