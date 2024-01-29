
package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import java.util.ArrayList;
import java.util.List;

/**
 * The MusicLoader class is responsible for loading and managing music and sound resources in the MazeRunnerGame.
 */
public class MusicLoader {
    private Music menuMusic;
    private Music gameMusic1;
    private Music gameMusic2;
    private Music gameMusic3;
    private Music gameMusic4;
    private Music gameMusic5;
    private Music winningMusic;
    private Music losingMusic;
    private Music currentGameMusic;
    private Music lifeLostMusic;
    private Music coinCollected;
    private Music walkingSound;
    private boolean forbiddenMenu;
    private boolean forbiddenGame;
    private int prevIndex;
    private List<Music> musicList;
    private Slider menuSlider;
    private Slider gameSlider;
    private MazeRunnerGame game;
    private boolean gameSoundsForbidden;


    /**
     * Constructs a new MusicLoader instance with the default state.
     */
    public MusicLoader() {

    }
    /**
     * Loads music and sound resources for the MazeRunnerGame.
     *
     * @param game The MazeRunnerGame instance.
     */
    public void loadMusic(MazeRunnerGame game) {
        this.game = game;
        forbiddenMenu = false;
        forbiddenGame = false;
        gameSoundsForbidden = false;
        prevIndex = -1;
        musicList = new ArrayList<>();
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("newBackground.ogg"));
        menuMusic.setLooping(true);
        gameMusic1 = Gdx.audio.newMusic(Gdx.files.internal("music/bg_sound-1.wav"));
        gameMusic1.setLooping(true);
        gameMusic2 = Gdx.audio.newMusic(Gdx.files.internal("music/bg_sound-2.wav"));
        gameMusic2.setLooping(true);
        gameMusic3 = Gdx.audio.newMusic(Gdx.files.internal("music/bg_sound-3.wav"));
        gameMusic3.setLooping(true);
        gameMusic4 = Gdx.audio.newMusic(Gdx.files.internal("music/bg_sound-4.wav"));
        gameMusic4.setLooping(true);
        gameMusic5 = Gdx.audio.newMusic(Gdx.files.internal("music/bg_sound-5.wav"));
        gameMusic5.setLooping(true);
        winningMusic = Gdx.audio.newMusic(Gdx.files.internal("WinningMusic.mp3"));
        winningMusic.setLooping(true);
        losingMusic = Gdx.audio.newMusic(Gdx.files.internal("LosingMusic.mp3"));
        losingMusic.setLooping(true);
        lifeLostMusic = Gdx.audio.newMusic(Gdx.files.internal("hurt.ogg"));
        lifeLostMusic.setLooping(false);
        coinCollected = Gdx.audio.newMusic(Gdx.files.internal("coin.ogg"));
        coinCollected.setLooping(false);
        walkingSound = Gdx.audio.newMusic(Gdx.files.internal("grasssound.mp3"));
        walkingSound.setLooping(false);
        musicList.add(gameMusic1);
        musicList.add(gameMusic2);
        musicList.add(gameMusic3);
        musicList.add(gameMusic4);
        musicList.add(gameMusic5);
    }

    public void playMenuMusic() {
        menuMusic.play();
    }

    public void playGameMusic() {
        currentGameMusic.play();
    }

    public void pauseMenuMusic() {
        menuMusic.pause();
    }

    public void stopMenuMusic() {
        menuMusic.stop();
    }

    public void stopGameMusic() {
        currentGameMusic.stop();
    }

    public void pauseGameMusic() {
        currentGameMusic.pause();
    }

    public void playWinningMusic() {
        winningMusic.play();
    }

    public void stopWinningMusic() {
        winningMusic.stop();
    }
    public void playLosingMusic() {
        losingMusic.play();
    }

    public void stopLosingMusic() {
        losingMusic.stop();
    }

    /**
     * Gets the currentMusic randomly instance in the musicList which contains five different game musics.
     */
    public void getcurrentMusic() {
        int index = MathUtils.random(0,3);
        while(index == prevIndex){
            index = MathUtils.random(0,3);
        }
        prevIndex=index;
        currentGameMusic = musicList.get(index);
    }

    public void lifeLostSoundPlay() {
        lifeLostMusic.play();
    }

    public void walkingSoundPlay() {
        walkingSound.play();
    }

    public void  coinCollectedSoundPlay() {
        coinCollected.play();
    }

    public void volumeUp() {
        if (menuMusic.getVolume() <1) {
            menuMusic.setVolume(menuMusic.getVolume() + 0.1f);
            currentGameMusic.setVolume(currentGameMusic.getVolume() + 0.1f);
            coinCollected.setVolume(coinCollected.getVolume() + 0.1f);
            walkingSound.setVolume(walkingSound.getVolume() + 0.1f);
            lifeLostMusic.setVolume(lifeLostMusic.getVolume() + 0.1f);
        }
    }

    public void volumeDown() {
        if (menuMusic.getVolume() > 0.1) {
            menuMusic.setVolume(menuMusic.getVolume() - 0.1f);
            currentGameMusic.setVolume(currentGameMusic.getVolume() - 0.1f);
            coinCollected.setVolume(coinCollected.getVolume() - 0.1f);
            walkingSound.setVolume(walkingSound.getVolume() - 0.1f);
            lifeLostMusic.setVolume(lifeLostMusic.getVolume() - 0.1f);
        }
    }

    public void setVolumes() {
        menuMusic.setVolume(0.5f);
        gameMusic1.setVolume(0.5f);
        gameMusic2.setVolume(0.5f);
        gameMusic3.setVolume(0.5f);
        gameMusic4.setVolume(0.5f);
        gameMusic5.setVolume(0.5f);
        coinCollected.setVolume(0.5f);
        walkingSound.setVolume(0.5f);
        lifeLostMusic.setVolume(0.5f);
    }
    public void dispose() {
        menuMusic.dispose();
        currentGameMusic.dispose();
    }

    public boolean isForbiddenMenu() {
        return forbiddenMenu;
    }

    public void setForbiddenMenu(boolean forbiddenMenu) {
        this.forbiddenMenu = forbiddenMenu;
    }

    public boolean isForbiddenGame() {
        return forbiddenGame;
    }

    public void setForbiddenGame(boolean forbiddenGame) {
        this.forbiddenGame = forbiddenGame;
    }

    public Slider getMenuSlider() {
        return menuSlider;
    }

    public void setMenuSlider(Slider menuSlider) {
        this.menuSlider = menuSlider;
    }

    public Slider getGameSlider() {
        return gameSlider;
    }

    public void setGameSlider(Slider gameSlider) {
        this.gameSlider = gameSlider;
    }

    public boolean isGameSoundsForbidden() {
        return gameSoundsForbidden;
    }

    public void setGameSoundsForbidden(boolean gameSoundsForbidden) {
        this.gameSoundsForbidden = gameSoundsForbidden;
    }

    public Music getMenuMusic() {
        return menuMusic;
    }
}
