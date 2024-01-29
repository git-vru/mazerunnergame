package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The HUD class represents the Heads-Up Display in the MazeRunnerGame.
 */
public class HUD extends Stage{
    private final Label livesLabel;
    private final Label keyStatusLabel;
    private final MazeRunnerGame game;
    private final Hero hero;
    private final TextureRegion livesTextures;
    private final Label vulnerability;

    /**
     * Constructs a HUD with the specified viewport, hero, and MazeRunnerGame.
     *
     * @param viewport The viewport to use for the HUD stage.
     * @param hero     The hero instance of the game.
     * @param game     The MazeRunnerGame instance.
     */
    public HUD(Viewport viewport, Hero hero, MazeRunnerGame game) {
        super(viewport);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-BoldItalic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;  // Set the desired font size
        BitmapFont customFont = generator.generateFont(parameter);// Adjust the scale factor as needed

        livesLabel = new Label(game.getLanguages().get("lives"), new Label.LabelStyle(customFont, Color.WHITE));
        keyStatusLabel = new Label(game.getLanguages().get("keystatusnotok"), new Label.LabelStyle(customFont, Color.WHITE));
        vulnerability = new Label(game.getLanguages().get("shieldno"), new Label.LabelStyle(customFont, Color.WHITE));

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
        Texture heartTexture = new Texture("objects.png");
        this.livesTextures = new TextureRegion(heartTexture,64,0,16,16);
    }

    /**
     * Draws the heart icons representing the number of lives the hero has.
     */
    public void drawLives(){
        int x = 0;

        for (int i = 0; i < hero.getLives(); i++, x+=60) {
            game.getSpriteBatch().draw(livesTextures, livesLabel.getWidth() +15+x, livesLabel.getY() - 10, 64, 64);
        }
        //game.getSpriteBatch().draw(tiles.getKeyTile(),livesLabel.getX()+300+120,livesLabel.getY()-10,32,32);
    }

    /**
     *  If hero collected the key, it changes the keystatus label to "keystatusok" key in the language bundles.
     */
    public void setKeyStatus() {
        if (hero.isKeyCollected()) {
            keyStatusLabel.setText(game.getLanguages().get("keystatusok"));
        }
    }

    /**
     * According to the state of the shield it changes vulnerability text to "shieldyes" or "shieldno" key in the language bundles.
     * @param shield It is a boolean which states that shield is enabled or not.
     */
    public void setShield(boolean shield) {
        if (shield){
            vulnerability.setText(game.getLanguages().get("shieldyes"));
        }else{
            vulnerability.setText(game.getLanguages().get("shieldno"));
        }
    }

}