package com.coffeebland.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by dagothig on 8/24/14.
 */
public class MusicManager {
    private static Maybe<Music> currentMusic = new Maybe<Music>();
    private static String currentRef = "";

    public static void play(String ref) {
        play(ref, true);
    }
    public static void play(String ref, boolean looping) {
        if (ref.equals(currentRef))
            return;

        currentRef = ref;

        if (currentMusic.hasValue()) {
            currentMusic.getValue().dispose();
        }

        com.badlogic.gdx.audio.Music music = Gdx.audio.newMusic(new FileHandle(ref));
        currentMusic = new Maybe<Music>(music);
        music.setLooping(looping);
        music.play();
    }

    private MusicManager() {

    }
}
