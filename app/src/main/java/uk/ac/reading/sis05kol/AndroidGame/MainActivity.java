package uk.ac.reading.sis05kol.AndroidGame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;


public class MainActivity extends Activity {

    private static final int MENU_RESUME = 1;
    private static final int MENU_START = 2;
    private static final int MENU_STOP = 3;

    private GameThread mGameThread;
    private GameView mGameView;

    MediaPlayer song; //Setup game song
    Level level; //Helps setup level


    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int levelNumber; //Initialize level number
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            levelNumber = extras.getInt("LEVEL");
            if (levelNumber < 4) {
                level = new Level(levelNumber);
            } else {
                /* Help setup custom level */
                int lives = extras.getInt("LIVES");
                int points = extras.getInt("POINTS");
                int sadFaces = extras.getInt("SAD_FACES");
                int angryFaces = extras.getInt("ANGRY_FACES");
                int veryAngryFaces = extras.getInt("VERY_ANGRY_FACES");
                level = new Level(lives, points, sadFaces, angryFaces, veryAngryFaces);
            }
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        mGameView = (GameView)findViewById(R.id.gamearea);
        mGameView.setStatusView((TextView)findViewById(R.id.text));
        mGameView.setScoreView((TextView)findViewById(R.id.score));
        mGameView.setLivesView((TextView)findViewById(R.id.lives));

        //MusicThread
        song = MediaPlayer.create(MainActivity.this, R.raw.menu);
        song.start(); //Starts song
        song.setLooping(true); // Repeat song in a loop


        this.startGame(mGameView, null, level); //Start level depending on the level
    }

    private void startGame(GameView gView, GameThread gThread, Level level) {
        //Start music thread
        MusicThread.setMainActivity(this);
        //Set up a new game, we don't care about previous states
        mGameThread = new TheGame(mGameView);
        mGameThread.setLevel(level); //Set level
        mGameView.setThread(mGameThread);
        mGameThread.setState(GameThread.STATE_READY);
        mGameView.startSensor((SensorManager)getSystemService(Context.SENSOR_SERVICE));
        mGameThread.setActivity(this);
    }

    /*
     * Activity state functions
     */

    @Override
    protected void onPause() {
        super.onPause();

        if(mGameThread.getMode() == GameThread.STATE_RUNNING) {
            mGameThread.setState(GameThread.STATE_PAUSE);
        }
        song.release(); //Stop song onPause
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mGameView.cleanup();
        mGameView.removeSensor((SensorManager)getSystemService(Context.SENSOR_SERVICE));
        mGameThread = null;
        mGameView = null;
    }

    /*
     * UI Functions
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, MENU_START, 0, R.string.menu_start);
        menu.add(0, MENU_STOP, 0, R.string.menu_stop);
        menu.add(0, MENU_RESUME, 0, R.string.menu_resume);

        return true;
    }

    /**
     * Option menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_START:
                mGameThread.doStart();
                return true;
            case MENU_STOP:
                mGameThread.setState(GameThread.STATE_LOSE,  getText(R.string.message_stopped));
                return true;
            case MENU_RESUME:
                mGameThread.unpause();
                return true;
        }

        return false;
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // Do nothing if nothing is selected
    }

    /**
     * Methods for using the intent to display the win and lost activity in TheGame class
     */

    public void won(){
        Intent intent = new Intent(this, Win.class);
        startActivity(intent);
    }

    public void lost(){
        Intent intent = new Intent(this, Lose.class);
        startActivity(intent);
    }

}

// This file is part of the course "Begin Programming: Build your first mobile game" from futurelearn.com
// Copyright: University of Reading and Karsten Lundqvist
// It is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// It is is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
//
// You should have received a copy of the GNU General Public License
// along with it.  If not, see <http://www.gnu.org/licenses/>.