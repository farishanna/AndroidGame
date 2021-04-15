package uk.ac.reading.sis05kol.AndroidGame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Class to select which levels/activities
 */
public class SelectLevel extends Activity {
    private Button playButton;
    private Button exitToMenu;
    private Button createLevel;

    /**
     * onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);

        /*
             Functions for what the buttons do with their onClick methods
         */

        playButton = (Button) findViewById(R.id.level1Btn);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playLevel(1);
            }
        });

        playButton = (Button) findViewById(R.id.level2Btn);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playLevel(2);
            }
        });

        playButton = (Button) findViewById(R.id.level3Btn);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playLevel(3);
            }
        });

        createLevel = (Button) findViewById(R.id.createlevel);
        createLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLevel();
            }
        });

        exitToMenu = (Button) findViewById(R.id.retmenu);
        exitToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitToMenu();
            }
        });
    }

    /**
     * Plays the level and begins the main activity
     * @param level -- specifies which level is chosen
     */
    public void playLevel(int level){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("LEVEL", level);
        startActivity(intent);
    }

    /**
     * Open the create level activity
     */
    public void createLevel(){
        Intent intent = new Intent(this, CreateLevel.class);
        startActivity(intent);
    }

    /**
     * Exits back to the main menu
     */
    public void exitToMenu(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

}
