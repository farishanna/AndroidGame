package uk.ac.reading.sis05kol.AndroidGame;

import android.media.MediaPlayer;
import android.util.Log;

/**
 * Class to create music and sound effects
 */
public class Music {
    static MainActivity m;

    public static void setMainActivity(MainActivity main){
        m = main;
    }

    /**
     * Play the menu music
     * @param
     */
    public static void playMenu(){
        // Play the music in the background
        MediaPlayer song = MediaPlayer.create(m, R.raw.menu);
        song.start(); //Starts song
        song.setLooping(true);
    }

    /**
     * Play the bounce sound
     * @param
     */
    public static void playBounce(){
        // Play the music in the background
        MediaPlayer song = MediaPlayer.create(m, R.raw.bounce);
        song.start(); //Starts song
    }

    /**
     * Play the losing animation sound
     * @param
     */
    public static void playLose(){
        // Play the music in the background
        try {
            MediaPlayer song = MediaPlayer.create(m, R.raw.lose);
            song.start(); //Starts song
        } catch (NullPointerException ne){
            Log.d("MUSIC", "Failed from NullPE");
        }
    }

    /**
     * Play the winning animation sound
     * @param
     */
    public static void playWin(){
        // Play the music in the background
        MediaPlayer song = MediaPlayer.create(m, R.raw.win);
        song.start(); //Starts song
    }

    /**
     * Play the achievement point animation sound
     * @param
     */
    public static void playPoint(){
        // Play the music in the background
        MediaPlayer song = MediaPlayer.create(m, R.raw.point);
        song.start(); //Starts song
    }

}
