package de.tum.cit.ase.maze;
public class Key extends GameObject {
    public Key(float x,float y) {
        super(x,y,40,40);
        this.animation = loadHorizontalAnimation("objects.png",0,64,16,16,4,0.1f);
    }
}
