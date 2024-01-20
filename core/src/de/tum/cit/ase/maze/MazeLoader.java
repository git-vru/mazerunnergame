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

        for (Point point : game.getMazeData().keySet()) {
            maxX = Math.max(maxX, point.x);
            maxY = Math.max(maxY, point.y);
        }

        maxX += 5;
        maxY += 5;

        game.setMaxX(maxX);
        game.setMaxY(maxY);
    }

    public void addGround() {

        for (int x = -5; x <= game.getMaxX(); x++) {
            for (int y = -5; y <= game.getMaxY(); y++) {
                game.getSpriteBatch().begin();
                game.getSpriteBatch().draw(game.getAllTiles().getGrass(), x * 60, y * 60, 60, 60);
                game.getSpriteBatch().end();
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
            switch (objectType) {
                case 0:
                    game.getSpriteBatch().draw(game.getAllTiles().getWall(), x, y, 60, 60);
                    game.getAllTiles().getWallRectangles().add(new Rectangle(x, y, 60, 60));
                    break;
                case 1:
                    game.getEntry().setEntryRect(new Rectangle(x,y,60,60));
                    game.setHero(new Hero(x-100, y ));
                    if (!game.getEntry().isOpen()){
                        game.getSpriteBatch().draw(game.getAllTiles().getEntryPoint(), x, y, 60, 60);
                    }
                    break;
                case 2:
                    game.getSpriteBatch().draw(game.getAllTiles().getExit(), x, y, 60, 60);
                    break;
                case 3:
                    game.getSpriteBatch().draw(game.getAllTiles().getTrap(), x, y, 60, 60);
                    break;
                case 4:
                    game.getSpriteBatch().draw(game.getAllTiles().getEnemy(), x, y, 60, 60);
                    break;
                case 5:
                    //game.getSpriteBatch().draw(game.getAllTiles().getKeyTile(), x, y+5, 60, 60);
                    game.getKey().setKeyRect(new Rectangle(x+10,y+10,40,40));
                    break;
            }
            game.getSpriteBatch().end();
        }
    }
}
