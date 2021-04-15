package uk.ac.reading.sis05kol.AndroidGame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Class to display the losing activity
 */
public class Lose extends Activity {
    private Button playAgainButn;
    private Button exitButn;

    /**
     * onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lose);

        /*
            Functions for the buttons
         */

        playAgainButn = (Button) findViewById(R.id.playAgainButton);
        playAgainButn.setOnClickListener(new View.OnClickListener() {
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

        //Plays lose sound
        MusicThread.playLose();
    }

    /**
     * Play the game again
     */
    public void playAgain(){
        Intent intent = new Intent(this, SelectLevel.class);
        startActivity(intent);
    }

    /**
     * Exit back to the main menu
     */
    public void exitMenu () {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
