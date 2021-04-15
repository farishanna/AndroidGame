package uk.ac.reading.sis05kol.AndroidGame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Class to create levels
 */
public class CreateLevel extends Activity {
    //Setup buttons
    private Button playButton;
    private Button exitToSelect;
    private Button addSadFace;
    private Button addAngryFace;
    private Button addVeryAngryFace;
    private Button addLives;
    private Button addPoints;

    //Initialize game options
    private static int sadFaces = 1;
    private static int angryFaces = 1;
    private static int veryAngryFaces = 0;
    private static int lives = 1;
    private static int points = 1;


    /**
     * onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createlevel);

        /*
            Functions for what the buttons do onCreate
         */

        playButton = (Button) findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playLevel();
            }
        });

        addSadFace = (Button) findViewById(R.id.addsadface);
        addSadFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sadFaces++; //Adds one on click
                addSadFace.setText("Add Sad Faces: "+sadFaces); //Edits the text
            }
        });

        addAngryFace = (Button) findViewById(R.id.addangryface);
        addAngryFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (angryFaces < 5) {
                    angryFaces++; //Adds one on click
                    addAngryFace.setText("Add Angry Faces: " + angryFaces); //Edits the text
                }
            }
        });

        addVeryAngryFace = (Button) findViewById(R.id.addvangryface);
        addVeryAngryFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                veryAngryFaces++; //Adds one on click
                addVeryAngryFace.setText("Add Very Angry Faces: "+veryAngryFaces); //Edits the text
            }
        });

        addLives = (Button) findViewById(R.id.addlives);
        addLives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lives++; //Adds one on click
                addLives.setText("Add lives: "+lives); //Edits the text
            }
        });

        addPoints = (Button) findViewById(R.id.addpoints);
        addPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                points++; //Adds one on click
                addPoints.setText("Add Points to Win: "+points); //Edits the text
            }
        });

        exitToSelect = (Button) findViewById(R.id.retselectlevel);
        exitToSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitToSelect();
            }
        });
    }

    /**
     * Plays levels with the amount of objects in the game inputted
     */
    public void playLevel(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("LEVEL", 4); //Setting to 4 since its not either 1-3
        //Set the objects with the intent based on the amount inputted
        intent.putExtra("LIVES", lives);
        intent.putExtra("ANGRY_FACES", angryFaces);
        intent.putExtra("VERY_ANGRY_FACES", veryAngryFaces);
        intent.putExtra("POINTS", points);
        intent.putExtra("SAD_FACES", sadFaces);
        startActivity(intent);
    }

    /**
     * Exit back to Select Level menu
     */
    public void exitToSelect(){
        Intent intent = new Intent(this, SelectLevel.class);
        startActivity(intent);
    }
}
