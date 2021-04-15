package uk.ac.reading.sis05kol.AndroidGame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Class to create the main menu
 */
public class MainMenu extends Activity {
    //Setup buttons
    private Button playButton;
    private Button exitButton;
    private Button settingsButton;
    private Button aboutButton;
    private Button highScoresButton;


    /**
     * onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        /*
            Buttons functions with their onClick methods
        */

        playButton = (Button) findViewById(R.id.buttonPlay);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playGame();
            }
        });

        exitButton = (Button) findViewById(R.id.buttonExit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitMenu();
            }
        });

        settingsButton = (Button) findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });

        aboutButton = (Button) findViewById(R.id.buttonAbout);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAbout();
            }
        });

        highScoresButton = (Button) findViewById(R.id.buttonScores);
        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHighScores();
            }
        });
    }


    /**
     * Opens the select level activity
     */
    public void playGame(){
        Intent intent = new Intent(this, SelectLevel.class);
        startActivity(intent);
    }

    /**
     * Opens the settings activity
     */
    public void openSettings(){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    /**
     * Opens the about activity
     */
    public void openAbout(){
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    /**
     * Opens the high scores activity
     */
    public void openHighScores(){
        Intent intent = new Intent(this, HighScore.class);
        startActivity(intent);
    }

    /**
     * Exits the application
     */
    public void exitMenu () {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
