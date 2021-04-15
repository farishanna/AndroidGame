package uk.ac.reading.sis05kol.AndroidGame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Class for the settings activity
 */
public class Settings extends Activity {
    private Button exitToMenu;
    private Button musicOnButton;
    private Button musicOffButton;

    static MainActivity music;

    public static void setMainActivity(MainActivity activity){
        music = activity;
    }

    /**
     * onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        /*
            Functions for the buttons
         */

        exitToMenu = (Button) findViewById(R.id.return_to_menu);
        exitToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitToMenu();
            }
        });

        musicOnButton = (Button) findViewById(R.id.on_button);
        musicOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //turnOn();
            }
        });

        musicOffButton = (Button) findViewById(R.id.off_button);
        musicOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //turnOff();
            }
        });
    }

    /**
     * Exits back to the main menu
     */
    public void exitToMenu(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    /**
     * TODO: Create method to turn button on or off
     */
    public void turnOnOff(){
        if (music.musicBtn == true){
            music.musicBtn = false;
        } else{
            music.musicBtn = true;
        }
    }
}
