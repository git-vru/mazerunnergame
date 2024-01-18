package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD extends Stage{
    private Label livesLabel;
    private Label keyStatusLabel;
    private MazeRunnerGame game;
    private Hero hero;
    private TextureRegion[] livesTextures;
    private Label numberOfEnemiesKilled;

    public HUD(Viewport viewport, Hero hero, MazeRunnerGame game) {
        super(viewport);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(3f); // Adjust the scale factor as needed

        livesLabel = new Label("Lives: ", new Label.LabelStyle(font, Color.WHITE));
        keyStatusLabel = new Label("Key Collected: ", new Label.LabelStyle(font, Color.WHITE));
        numberOfEnemiesKilled = new Label("Enemies Killed: ", new Label.LabelStyle(font, Color.WHITE));

        // Set the position of the labels
        livesLabel.setPosition(10, viewport.getWorldHeight() - 50);
        keyStatusLabel.setPosition(10, viewport.getWorldHeight() - 110);
        numberOfEnemiesKilled.setPosition(10, viewport.getWorldHeight() - 170);

        // Add UI elements to the stage
        addActor(livesLabel);
        addActor(keyStatusLabel);
        addActor(numberOfEnemiesKilled);

        this.hero = hero;
        this.game = game;

        livesTextures = new TextureRegion[5];
        Texture texture = new Texture("objects.png");
        livesTextures[4]= new TextureRegion(texture, 64, 0, 16, 16);
        livesTextures[3]= new TextureRegion(texture, 80, 0, 16, 16);
        livesTextures[2]= new TextureRegion(texture, 96, 0, 16, 16);
        livesTextures[1]= new TextureRegion(texture, 112, 0, 16, 16);
        livesTextures[0]= new TextureRegion(texture, 128, 0, 16, 16);
    }

    public void setLives() {
        if (hero.getLives() == 4) game.getSpriteBatch().draw(livesTextures[4], livesLabel.getX() + 120, livesLabel.getY() - 10, 64, 64);
        if (hero.getLives() == 3) game.getSpriteBatch().draw(livesTextures[3], livesLabel.getX() + 120, livesLabel.getY() - 10, 64, 64);
        if (hero.getLives() == 2) game.getSpriteBatch().draw(livesTextures[2], livesLabel.getX() + 120, livesLabel.getY() - 10, 64, 64);
        if (hero.getLives() == 1) game.getSpriteBatch().draw(livesTextures[1], livesLabel.getX() + 120, livesLabel.getY() - 10, 64, 64);
        if (hero.getLives() == 0) game.getSpriteBatch().draw(livesTextures[0], livesLabel.getX() + 120, livesLabel.getY() - 10, 64, 64);
    }

    public void setKeyStatus() {
        if (hero.isKeyCollected()) {
            keyStatusLabel.setText("Key Collected: Yes");
        }
        else {
            keyStatusLabel.setText("Key Collected: No");
        }
    }

    public void setNumberOfEnemiesKilled() {
        numberOfEnemiesKilled.setText("Enemies Killed: " + hero.getEnemiesKilled());
    }
}