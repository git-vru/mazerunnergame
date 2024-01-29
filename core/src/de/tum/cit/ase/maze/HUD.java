package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD extends Stage{
    private final Label livesLabel;
    private final Label keyStatusLabel;
    private final MazeRunnerGame game;
    private final Hero hero;
    private Texture heartTexture;
    private TextureRegion livesTextures;
    private final Label vulnerability;

    public HUD(Viewport viewport, Hero hero, MazeRunnerGame game) {
        super(viewport);
        BitmapFont font = new BitmapFont();
        font.getData().setScale(3f); // Adjust the scale factor as needed

        livesLabel = new Label(game.getLanguages().get("lives"), new Label.LabelStyle(font, Color.WHITE));
        keyStatusLabel = new Label(game.getLanguages().get("keystatusnotok"), new Label.LabelStyle(font, Color.WHITE));
        vulnerability = new Label(game.getLanguages().get("shieldno"), new Label.LabelStyle(font, Color.WHITE));

        // Set the position of the labels
        livesLabel.setPosition(10, viewport.getWorldHeight() - 50);
        keyStatusLabel.setPosition(10, viewport.getWorldHeight() - 110);
        vulnerability.setPosition(10, viewport.getWorldHeight() - 170);

        // Add UI elements to the stage
        addActor(livesLabel);
        addActor(keyStatusLabel);
        addActor(vulnerability);
        this.hero = hero;
        this.game = game;
        this.heartTexture = new Texture("objects.png");
        this.livesTextures = new TextureRegion(heartTexture,64,0,16,16);
    }
    public void drawLives(){
        int x = 0;

        for (int i = 0; i < hero.getLives(); i++, x+=60) {
            game.getSpriteBatch().draw(livesTextures, livesLabel.getX() + 120+x, livesLabel.getY() - 10, 64, 64);
        }
        //game.getSpriteBatch().draw(tiles.getKeyTile(),livesLabel.getX()+300+120,livesLabel.getY()-10,32,32);
    }
    public void setKeyStatus() {
        if (hero.isKeyCollected()) {
            keyStatusLabel.setText(game.getLanguages().get("keystatusok"));
        }
    }

    public void setShield(boolean shield) {
        if (shield){
            vulnerability.setText(game.getLanguages().get("shieldyes"));
        }else{
            vulnerability.setText(game.getLanguages().get("shieldno"));
        }
    }

}