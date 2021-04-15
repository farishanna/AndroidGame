package uk.ac.reading.sis05kol.AndroidGame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Class for forming the HighScore page
 */
public class HighScore extends Activity {
    private Button exitToMenu;
    private TextView highScoreDisplay;

    static String TAG = "HIGH_SCORE";


    /**
     * onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore); //xml file to choose

        // Reading a message from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Log.d(TAG, "Fetching high score");
        // Read from the database
        /*myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int value = dataSnapshot.getValue(int.class);
                Log.d(TAG, "Value is: " + value);

                //Toast.makeText(HighScore.this, "High Score is: " + value, Toast.LENGTH_SHORT).show();
                highScoreDisplay = (TextView) findViewById(R.id.highscoretxt);
                highScoreDisplay.setText("High Score is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/
        myRef.child("Root").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()){
                    Log.d(TAG, "Firebase had an error getting data", task.getException());
                } else{
                    String value = String.valueOf(task.getResult().getValue());
                    Log.d(TAG, "Value is: " + value);

                    //Toast.makeText(HighScore.this, "High Score is: " + value, Toast.LENGTH_SHORT).show();
                    highScoreDisplay = (TextView) findViewById(R.id.highscoretxt);
                    highScoreDisplay.setText("High Score is: " + value);
                }
            }
        });


        //Steps to create the button to go back
        exitToMenu = (Button) findViewById(R.id.return__to_menu);
        exitToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitToMenu();
            }
        });
    }

    /**
     * Exit back to the main menu
     */
    public void exitToMenu(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

}
