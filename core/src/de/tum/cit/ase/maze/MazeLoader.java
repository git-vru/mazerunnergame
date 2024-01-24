package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MazeLoader {
    private final MazeRunnerGame game;
    private Rectangle top;
    private Rectangle right;
    private Rectangle bottom;
    private Rectangle left;

    public MazeLoader(MazeRunnerGame game) {
        this.game = game;
    }

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

        maxX += 5;
        maxY += 5;
        minX-=5;
        minY-=5;

        game.setMaxX(maxX);
        game.setMaxY(maxY);
        game.setMinX(minX);
        game.setMinY(minY);
        this.top = new Rectangle((float) minX*60, (float) (maxY-4)*60, (float) ((maxX+5)*60),4*60);
        this.bottom = new Rectangle((float) minX*60, (float) minY*60, (float) ((maxX+5)*60),4*60);
        this.right = new Rectangle((float) (maxX-4)*60, (float) (minY-5)*60, 4*60,(float)(maxY-5)*60);
        this.left = new Rectangle((float) (minX)*60, (float) (minY+5)*60, 4*60,(float)(maxY-5)*60);
    }

    public void addGround() {
        for (int x = (int)game.getMinX(); x <= game.getMaxX(); x++) {
            for (int y = (int)game.getMinY(); y <= game.getMaxY(); y++) {
                game.getSpriteBatch().begin();
                game.getSpriteBatch().draw(game.getAllTiles().getGrass(), x * 60, y * 60, 60, 60);
                game.getSpriteBatch().end();
            }
        }
    }
    public void createEnemies(){
        for (Map.Entry<Point, Integer> entry : game.getMazeData().entrySet()) {
            if (entry.getValue() == 4){
                Enemy.enemyList.add(new Enemy(((float) entry.getKey().x*60), ((float) entry.getKey().y*60)));
            }
            if (entry.getValue() == 2){
                Exit.getExitList().add(new Exit(((float) entry.getKey().x*60),((float) entry.getKey().y*60)));
                //Exit.getExitList().get(Exit.getExitList().size()-1).setExitRect(new Rectangle(((float) entry.getKey().x*60), ((float) entry.getKey().y*60),60,60));
            }
            if (entry.getValue() == 5){
                game.getKey().setKeyRect(new Rectangle(((float) entry.getKey().x*60)+10,((float) entry.getKey().y*60)+10,40,40));
            }
            if (entry.getValue()==3){
                Trap.getTrapList().add(new Trap(((float) entry.getKey().x*60),((float) entry.getKey().y*60)));
            }
        }
    }

    public void renderMaze() {
        addGround();
        for (Map.Entry<Point, Integer> entry : game.getMazeData().entrySet()) {
            Point point = entry.getKey();
            int x = point.x * 60;
            int y = point.y * 60;
            int objectType = entry.getValue();

            game.getSpriteBatch().begin();
            switch(objectType) {
                case 0:
                    game.getSpriteBatch().draw(game.getAllTiles().getWall(), x, y, 60, 60);
                    game.getAllTiles().getWallRectangles().add(new Rectangle(x, y, 60, 60));
                    break;
                case 1:
                    game.getEntry().setEntryRect(new Rectangle(x,y,60,60));
                    game.getEntry().setMazeLeaver(new Rectangle(x,y,5,60));
                    game.setHero(new Hero(x+10, y ));
//                    if (!game.getEntry().isOpen()){
//                        game.getSpriteBatch().draw(game.getAllTiles().getEntryPoint(), x, y, 60, 60);
//                    }
                    break;
                case 2, 4:
                    break;
                case 3:
                    //game.getSpriteBatch().draw(game.getAllTiles().getTrap(), x, y, 60, 60);
                    break;
                case 5:
                    //game.getSpriteBatch().draw(game.getAllTiles().getKeyTile(), x, y+5, 60, 60);
                    game.getKey().setKeyRect(new Rectangle(x+10,y+10,40,40));
                    break;
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
