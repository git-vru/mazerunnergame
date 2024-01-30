package de.tum.cit.ase.maze;

import com.badlogic.gdx.math.Rectangle;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * The MazeLoader class is responsible for loading and rendering the maze data.
 */
public class MazeLoader {
    private final MazeRunnerGame game;
    private Rectangle top;
    private Rectangle right;
    private Rectangle bottom;
    private Rectangle left;

    /**
     * Constructor for MazeLoader.
     *
     * @param game The MazeRunnerGame instance.
     */
    public MazeLoader(MazeRunnerGame game) {
        this.game = game;
    }


    /**
     * Loads maze data from a file and populates the game's mazeData map.
     *
     * @param fileName The name of the file containing maze data.
     */
    public void loadMazeData(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String[] coordinates = parts[0].split(",");
                    if (coordinates.length == 2) {
                        int x = Integer.parseInt(coordinates[0]);
                        int y = Integer.parseInt(coordinates[1]);
                        int objectType = Integer.parseInt(parts[1]);
                        game.getMazeData().put(new Point(x, y), objectType);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the Max and Min coordinates of the maze.
     */
    public void calculateMaxCoordinates() {
        double maxX = 0;
        double maxY = 0;
        double minX = 0;
        double minY = 0;

        for (Point point : game.getMazeData().keySet()) {
            maxX = Math.max(maxX, point.x);
            maxY = Math.max(maxY, point.y);
            minX = Math.min(minX,point.x);
            minY = Math.min(minY,point.y);
        }

        maxX += 10;
        maxY += 10;
        minX-=10;
        minY-=10;

        game.setMaxX(maxX);
        game.setMaxY(maxY);
        game.setMinX(minX);
        game.setMinY(minY);
        this.top = new Rectangle((float) minX*60, (float) (maxY-9)*60, (float) ((maxX+10)*60),9*60);
        this.bottom = new Rectangle((float) minX*60, (float) minY*60, (float) ((maxX+10)*60),9*60);
        this.right = new Rectangle((float) (maxX-9)*60, (float) (minY-10)*60, 9*60,(float)(maxY-10)*60);
        this.left = new Rectangle((float) (minX)*60, (float) (minY+10)*60, 9*60,(float)(maxY-10)*60);
    }

    /**
     * Adds the ground, which is grass.
     */
    public void addGround() {
        for (int x = (int)game.getMinX(); x <= game.getMaxX(); x++) {
            for (int y = (int)game.getMinY(); y <= game.getMaxY(); y++) {
                game.getSpriteBatch().begin();
                game.getSpriteBatch().draw(game.getAllTiles().getGrass(), x * 60, y * 60, 60, 60);
                game.getSpriteBatch().end();
            }
        }
    }

    /**
     * Creates game objects based on the loaded maze data.
     */
    public void createObjects(){
        for (Map.Entry<Point, Integer> entry : game.getMazeData().entrySet()) {
            Point point = entry.getKey();
            int x = point.x * 60;
            int y = point.y * 60;
            int objectType = entry.getValue();
            switch (objectType){
                case 1:
                    game.setEntry(new Entry(x,y));
                    game.getEntry().setMazeLeaver(new Rectangle(x,y,5,60));
                    game.getHero().setX(x+10);
                    game.getHero().setY(y);
                    game.getHero().setPrevX(game.getHero().getX());
                    game.getHero().setPrevY(game.getHero().getY());

                    break;
                case 2:
                    Exit.getExitList().add(new Exit(x,y));
                    break;
                case 3:
                    Trap.getTrapList().add(new Trap(x,y));
                    break;
                case 4:
                    Enemy.enemyList.add(new Enemy(x , y));
                    break;
                case 5:
                    game.setKey(new Key(x+10,y+10));
                    break;
            }
        }
    }

    /**
     * Renders the maze on the screen.
     */
    public void renderMaze() {
        for (Map.Entry<Point, Integer> entry : game.getMazeData().entrySet()) {
            Point point = entry.getKey();
            int x = point.x * 60;
            int y = point.y * 60;
            game.getSpriteBatch().begin();
            if (entry.getValue()==0) {
                game.getSpriteBatch().draw(game.getAllTiles().getWall(), x, y, 60, 60);
            }
            game.getSpriteBatch().end();
        }
    }

    public Rectangle getBottom() {
        return bottom;
    }

    public Rectangle getTop() {
        return top;
    }

    public Rectangle getRight() {
        return right;
    }

    public Rectangle getLeft() {
        return left;
    }
}
