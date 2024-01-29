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

public class HUD extends Stage{
    private final Label livesLabel;
    private final Label keyStatusLabel;
    private final MazeRunnerGame game;
    private final Hero hero;
    private final TextureRegion livesTextures;
    private final Label vulnerability;

    public HUD(Viewport viewport, Hero hero, MazeRunnerGame game) {
        super(viewport);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-BoldItalic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;  // Set the desired font size
        BitmapFont customFont = generator.generateFont(parameter);// Adjust the scale factor as needed

        livesLabel = new Label("Lives: ",new Label.LabelStyle(customFont, Color.WHITE));
        keyStatusLabel = new Label("Key Collected: No", new Label.LabelStyle(customFont, Color.WHITE));
        vulnerability = new Label("Shield: No", new Label.LabelStyle(customFont, Color.WHITE));
        //vulnerability.setColor(Color.NAVY);
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
    public void drawLives(){
        int x = 0;

        for (int i = 0; i < hero.getLives(); i++, x+=60) {
            game.getSpriteBatch().draw(livesTextures, livesLabel.getWidth() +10+x, livesLabel.getY() - 10, 64, 64);
        }
        //game.getSpriteBatch().draw(tiles.getKeyTile(),livesLabel.getX()+300+120,livesLabel.getY()-10,32,32);
    }
    public void setKeyStatus() {
        if (hero.isKeyCollected()) {
            keyStatusLabel.setText("Key Collected: Yes");
        }
    }

    public void setShield(boolean shield) {
        if (shield){
            vulnerability.setText("Shield: Yes");
        }else{
            vulnerability.setText("Shield: No");
        }
    }

}