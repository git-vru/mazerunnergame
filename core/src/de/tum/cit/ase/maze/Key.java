package de.tum.cit.ase.maze;
/**
 * The Key class represents a key object in the MazeRunnerGame.
 */
public class Key extends GameObject {
    /**
     * Constructs a Key object with the specified position (x, y) and dimensions (40x40).
     * It also loads a horizontal animation from the "objects.png" file.
     *
     * @param x The x-coordinate of the key's position.
     * @param y The y-coordinate of the key's position.
     */
    public Key(float x,float y) {
        super(x,y,40,40);
        this.animation = loadHorizontalAnimation("objects.png",0,64,16,16,4,0.1f);
    }
}
