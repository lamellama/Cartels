package yio.tro.antiyoy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {


    private static MusicManager instance = null;

    public Music music;


    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }

        return instance;
    }


    public void onMusicStatusChanged() {
        if (Settings.musicEnabled && Settings.soundEnabled) {
            if (music.isPlaying()) return;

            play();
        } else {
            if (!music.isPlaying()) return;

            stop();
        }
    }


    public void play() {
        if (music == null) return;

        music.play();
        music.setLooping(true);
    }


    public void stop() {
        if (music == null) return;

        music.stop();
    }


    public void load() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sound/music.ogg"));
    }

}
