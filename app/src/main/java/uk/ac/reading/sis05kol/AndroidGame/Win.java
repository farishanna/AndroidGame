package uk.ac.reading.sis05kol.AndroidGame;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Class to display the winning activity
 */
public class Win extends Activity {
    private Button playButn;
    private Button exitButn;

    /**
     * onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win);

        /*
            Functions for the buttons
         */

        playButn = (Button) findViewById(R.id.playAgainBtn);
        playButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
            }
        });

        exitButn = (Button) findViewById(R.id.exitButton);
        exitButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitMenu();
            }
        });

        //Plays win song
        Music.playWin();
    }

    /**
     * Play again
     */
    public void playAgain(){
       // try {
            Intent intent = new Intent(this, SelectLevel.class);
            startActivity(intent);
     //   } catch ()
    }

    /**
     * Exit back to the main menu
     */
    public void exitMenu () {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
