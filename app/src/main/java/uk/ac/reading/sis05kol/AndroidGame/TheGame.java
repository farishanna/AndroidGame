package uk.ac.reading.sis05kol.AndroidGame;

//Other parts of the android libraries that we use
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TheGame extends GameThread{

    //This will store the min distance allowed between a big ball and the small ball
    //This is used to check collisions
    private float mMinDistanceBetweenBallAndPaddle = 0;
    private float mMinDistanceBetweenEnemyAndPaddle = 0;
    private DatabaseReference myRef;

    private Level level;
    public MainActivity activity;
    private Music music;
    public boolean loseIntent = false;


    //Will store the image of the Paddle used to hit the ball
    private final Bitmap mPaddle;
    //Will store the image of a ball
    private final Bitmap mBall;
    //Will store the image of the smiley ball (score ball)
    private final Bitmap mSmileyBall;
    //Will store the image of the smiley ball (score ball)
    private final Bitmap mSadBall;
    //Will store the image of the smiley ball (score ball)
    private final Bitmap mAngryBall;
    //Will store the image of the smiley ball (score ball)
    private final Bitmap mVeryAngryBall;

    //Paddle's x position. Y will always be the bottom of the screen
    private float mPaddleX = 0;
    //The speed (pixel/second) of the paddle in direction X and Y
    private float mPaddleSpeedX = 0;

    //The X and Y position of the ball on the screen
    //The point is the top left corner, not middle, of the ball
    //Initially at -100 to avoid them being drawn in 0,0 of the screen
    private float mBallX = -100;
    private float mBallY = -100;
    //The speed (pixel/second) of the ball in direction X and Y
    private float mBallSpeedX = 0;
    private float mBallSpeedY = 0;

    //The X and Y position of the ball on the screen
    //The point is the top left corner, not middle, of the ball
    //Initially at -100 to avoid them being drawn in 0,0 of the screen
    private float mSmileyBallX = -100;
    private float mSmileyBallY = -100;

    //The X and Y position of the SadBalls on the screen
    //The point is the top left corner, not middle, of the balls
    //Initially at -100 to avoid them being drawn in 0,0 of the screen
    private float[] mSadBallX = {-100, -100, -100};
    private float[] mSadBallY = {-100, -100, -100};

    //private float[] mSadBallX = {-100, -100};
    //private float[] mSadBallY = {-100, -100};

    //The X and Y position of the SadBalls on the screen
    //The point is the top left corner, not middle, of the balls
    //Initially at -100 to avoid them being drawn in 0,0 of the screen
    private float[] mEnemiesX = {-100, -100, -100};
    private float[] mEnemiesY = {-100, -100, -100};

    //The speed (pixel/second) of the ball in direction X and Y
    private float[] mEnemiesSpeedX = {0, 0, 0};
    private float[] mEnemiesSpeedY = {0, 0, 0};


    public int highScore; //Retrieve high score
    public String winScore; //Retrieve win score

    Music bounce;

   // MediaPlayer bounce;
   // Music m = new Music();

    //Used to add a point to every second played
 /*   final Handler handler = new Handler();
    final int delay = 1000; */

    public void setLevel(Level newLevel){
        try {
            level = newLevel;
            setLives(level.getLives());
            getHighScore();
        } catch (NullPointerException ne){
            Log.d("THE_GAME", "Failed from NullPE");
        }
    }

    //This is run before anything else, so we can prepare things here
    public TheGame(GameView gameView) {
        //House keeping
        super(gameView);

        //Prepare the image so we can draw it on the screen (using a canvas)
        mBall = BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.small_red_ball);

        //Prepare the image of the paddle so we can draw it on the screen (using a canvas)
        mPaddle = BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.yellow_ball);

        //Prepare the image of the SmileyBall so we can draw it on the screen (using a canvas)
        mSmileyBall =  BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.smiley_ball);

        //Prepare the image of the SadBall(s) so we can draw it on the screen (using a canvas)
        mSadBall =  BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.sad_ball);

        //Prepare the image of the AngryBall(s) so we can draw it on the screen (using a canvas)
        mAngryBall = BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.enemy1);
        mVeryAngryBall = BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.enemy2);
    }

    //This is run before a new game (also after an old game)
    @Override
    public void setupBeginning() {
        //Initialise speeds
        //mCanvasWidth and mCanvasHeight are declared and managed elsewhere
        mBallSpeedX = mCanvasWidth / 3;
        mBallSpeedY = mCanvasHeight / 3;
        try {
            for (int i = 0; i < level.getAngryFaces(); i++) {
                mEnemiesSpeedY[i] = mCanvasHeight / 3;
            }
            for (int i = 0; i < level.getVeryAngryFaces(); i++) {
                mEnemiesSpeedY[i + level.getAngryFaces()] = mCanvasWidth / 3;
                mEnemiesSpeedX[i + level.getAngryFaces()] = mCanvasWidth / 3;
            }
        } catch (NullPointerException ne){
            Log.d("THE_GAME", "Failed from NullPE");
        }

        //Place the ball in the middle of the screen.
        //mBall.Width() and mBall.getHeigh() gives us the height and width of the image of the ball
        mBallX = mCanvasWidth / 2;
        mBallY = mCanvasHeight / 2;

        //Place Paddle in the middle of the screen
        mPaddleX = mCanvasWidth / 2;

        //Place SmileyBalls in the top middle of the screen
        mSmileyBallX = mCanvasWidth / 2;
        mSmileyBallY = mSmileyBall.getHeight()/2;

        try {
            //Place all SadBalls forming a pyramid underneath the SmileyBall
            mSadBallX = new float[level.getSadFaces()];
            mSadBallY = new float[level.getSadFaces()];
            mSadBallX[0] = mCanvasWidth / 3;
            mSadBallY[0] = mCanvasHeight / 3;
            if (mSadBallX.length > 1) {
                mSadBallX[1] = mCanvasWidth - mCanvasWidth / 3;
                mSadBallY[1] = mCanvasHeight / 3;
            }
            if (mSadBallX.length > 2) {
                mSadBallX[2] = mCanvasWidth / 2;
                mSadBallY[2] = mCanvasHeight / 5;
            }
            if (mSadBallX.length > 3) {
                mSadBallX[3] = mCanvasWidth - mCanvasWidth / 3;
                mSadBallY[3] = mCanvasHeight / 2;
            }
            if (mSadBallX.length > 4) {
                mSadBallX[4] = mCanvasWidth / 3;
                mSadBallY[4] = mCanvasHeight / 2;
            }
            mEnemiesX = new float[level.getAngryFaces() + level.getVeryAngryFaces()];
            mEnemiesY = new float[level.getAngryFaces() + level.getVeryAngryFaces()];
            for (int i = 0; i < mEnemiesX.length; i++) {
                mEnemiesX[i] = (mCanvasWidth / (1 + mEnemiesX.length)) * (i + 1);
                mEnemiesY[i] = mAngryBall.getHeight() / 2;
            }
        } catch (NullPointerException ne) {
            Log.d("THE_GAME_ENEMIES", "Failed from NullPE");
        }
        //Get the minimum distance between a small ball and a bigball
        //We leave out the square root to limit the calculations of the program
        //Remember to do that when testing the distance as well
        mMinDistanceBetweenBallAndPaddle = (mPaddle.getWidth() / 2 + mBall.getWidth() / 2) * (mPaddle.getWidth() / 2 + mBall.getWidth() / 2);
        mMinDistanceBetweenEnemyAndPaddle = (mPaddle.getWidth() / 2 + mSmileyBall.getWidth() / 2) * (mPaddle.getWidth() / 2 + mSmileyBall.getWidth() / 2);
    }

    @Override
    protected void doDraw(Canvas canvas) {
        try{
            //If there isn't a canvas to do nothing
            //It is ok not understanding what is happening here
            if(canvas == null) return;

            //House keeping
            super.doDraw(canvas);

            //canvas.drawBitmap(bitmap, x, y, paint) uses top/left corner of bitmap as 0,0
            //we use 0,0 in the middle of the bitmap, so negate half of the width and height of the ball to draw the ball as expected
            //A paint of null means that we will use the image without any extra features (called Paint)

            //draw the image of the ball using the X and Y of the ball
            canvas.drawBitmap(mBall, mBallX - mBall.getWidth() / 2, mBallY - mBall.getHeight() / 2, null);

            //Draw Paddle using X of paddle and the bottom of the screen (top/left is 0,0)
            canvas.drawBitmap(mPaddle, mPaddleX - mPaddle.getWidth() / 2, mCanvasHeight - mPaddle.getHeight() / 2, null);

            //Draw SmileyBall
            canvas.drawBitmap(mSmileyBall, mSmileyBallX - mSmileyBall.getWidth() / 2, mSmileyBallY - mSmileyBall.getHeight() / 2, null);


            //Loop through all SadBall
            for(int i = 0; i < mSadBallX.length; i++) {
                //Draw SadBall in position i
                canvas.drawBitmap(mSadBall, mSadBallX[i] - mSadBall.getWidth() / 2, mSadBallY[i] - mSadBall.getHeight() / 2, null);
            }

            //Loop through all Angry Balls
            for (int i = 0; i < level.getAngryFaces(); i++) {
                canvas.drawBitmap(mAngryBall, mEnemiesX[i] - mAngryBall.getWidth() / 2, mEnemiesY[i] - mAngryBall.getHeight() / 2, null);
            }

            //Loop through all Very Angry Balls
            for (int i = 0; i < level.getVeryAngryFaces(); i++) {
                canvas.drawBitmap(mVeryAngryBall, mEnemiesX[i + level.getAngryFaces()] - mVeryAngryBall.getWidth() / 2, mEnemiesY[i + level.getAngryFaces()] - mVeryAngryBall.getHeight() / 2, null);
            }

        } catch (NullPointerException ne) {
            Log.d("THE_GAME_DRAW", "Failed from NullPE");
        }
    }


    //This is run whenever the phone is touched by the user
    @Override
    protected void actionOnTouch(float x, float y) {
        //Move the ball to the x position of the touch
        mPaddleX = x;
    }


    //This is run whenever the phone moves around its axises
    @Override
    protected void actionWhenPhoneMoved(float xDirection, float yDirection, float zDirection) {
        //Change the paddle speed
        mPaddleSpeedX = mPaddleSpeedX + 70f * xDirection;

        //If paddle is outside the screen and moving further away
        //Move it into the screen and set its speed to 0
        if(mPaddleX <= 0 && mPaddleSpeedX < 0) {
            mPaddleSpeedX = 0;
            mPaddleX = 0;
        }
        if(mPaddleX >= mCanvasWidth && mPaddleSpeedX > 0) {
            mPaddleSpeedX = 0;
            mPaddleX = mCanvasWidth;
        }

    }


    //This is run just before the game "scenario" is printed on the screen
    @Override
    protected void updateGame(float secondsElapsed) {
        //If the ball moves down on the screen
        if (mBallSpeedY > 0) {
            //Check for a paddle collision
            if(updateBallCollision(mPaddleX, mCanvasHeight)){
                Music.playBounce();
            }
            //setState(GameThread.STATE_LOSE);
        }

        /*//If the ball moves down on the screen
        if (mEnemiesSpeedY > 0) {
            //Check for a paddle collision
            updateBallCollision(mPaddleX, mCanvasHeight);
            //setState(GameThread.STATE_LOSE);
        }*/

        //Move the ball's X and Y using the speed (pixel/sec)
        mBallX = mBallX + secondsElapsed * mBallSpeedX;
        mBallY = mBallY + secondsElapsed * mBallSpeedY;

        //Update enemies's X and Y using the speed (pixel/sec)
        for (int i=0; i<mEnemiesY.length; i++) {
            mEnemiesY[i] = mEnemiesY[i] + secondsElapsed * mEnemiesSpeedY[i];
            mEnemiesX[i] = mEnemiesX[i] + secondsElapsed * mEnemiesSpeedX[i];
        }

        //Move the paddle's X and Y using the speed (pixel/sec)
        mPaddleX = mPaddleX + secondsElapsed * mPaddleSpeedX;


        //Check if the ball hits either the left side or the right side of the screen
        //But only do something if the ball is moving towards that side of the screen
        //If it does that => change the direction of the ball in the X direction
        if ((mBallX <= mBall.getWidth() / 2 && mBallSpeedX < 0) || (mBallX >= mCanvasWidth - mBall.getWidth() / 2 && mBallSpeedX > 0)) {
            mBallSpeedX = -mBallSpeedX;
            Music.playBounce();
        }

        //Check if the enemy hits either the left side or the right side of the screen
        //But only do something if the enemy is moving towards that side of the screen
        //If it does that => change the direction of the enemy in the X direction
        for (int i=0; i<mEnemiesX.length; i++) {
            if ((mEnemiesX[i] <= mAngryBall.getWidth() / 2 && mEnemiesSpeedX[i] < 0) || (mEnemiesX[i] >= mCanvasWidth - mAngryBall.getWidth() / 2 && mEnemiesSpeedX[i] > 0)) {
                mEnemiesSpeedX[i] = -mEnemiesSpeedX[i];
            }
            if ((mEnemiesY[i] <= mAngryBall.getHeight() / 2 && mEnemiesSpeedY[i] < 0) || (mEnemiesY[i] >= mCanvasHeight - mAngryBall.getHeight() / 2 && mEnemiesSpeedY[i] > 0)) {
                mEnemiesSpeedY[i] = -mEnemiesSpeedY[i];
            }
        }

        //Check for SmileyBall collision
        if (updateBallCollision(mSmileyBallX, mSmileyBallY)) {
            //Increase score
            updateScore(1);
            //Plays point sound
            Music.playPoint();
        }

        // will add to the score every second
    /*    handler.postDelayed(new Runnable() {
            public void run() {
                updateScore(1);
            }
        }, delay); */

        //Loop through all SadBalls
        for (int i = 0; i < mSadBallX.length; i++) {
            //Perform collisions (if necessary) between SadBall in position i and the red ball
           if(updateBallCollision(mSadBallX[i], mSadBallY[i])) {
               //Play bounce sound if collided with SadBall(s)
               Music.playBounce();
           }
        }

        //Check for enemy ball collision
        for (int i=0; i<mEnemiesX.length; i++) {
            if (updateBallCollision(mEnemiesX[i], mEnemiesY[i])) {
                updateLives(-1);
                setState(GameThread.STATE_LOSE);
            }
        }

        //Check for enemy paddle collision
        for (int i=0; i<mEnemiesX.length; i++) {
            if (updatePaddleCollision(mEnemiesX[i], mEnemiesY[i])) {
                updateLives(-1);
                setState(GameThread.STATE_LOSE);
            }
        }

        //If the ball goes out of the top of the screen and moves towards the top of the screen =>
        //change the direction of the ball in the Y direction
        if (mBallY <= mBall.getWidth() / 2 && mBallSpeedY < 0) {
            mBallSpeedY = -mBallSpeedY;
            Music.playBounce();
        }

        //If the ball goes out of the bottom of the screen => lose the game
        if (mBallY >= mCanvasHeight) {
            updateLives(-1);
            setState(GameThread.STATE_LOSE);
        }

        try {
            // Win the game depending on levels score and store high score
            int score = (int) getScore();
            if (score >= level.getPointsToWin()) {
                //Store score
                winScore = String.valueOf(score);

                // Store high score
                if (score > highScore) {
                    highScore = score;
                    setHighScore();
                    Log.d("THE_GAME", "New high score: " + score);
                }
                setState(GameThread.STATE_WIN);
            }
        } catch (NullPointerException ne){
                Log.d("THE_GAME", "Failed from NullPE");
            }

        // Determines if player has lost their lives and loses
        if (getLives() == 0){
            loseIntent = true;
            //activity.startLose();
            setState(GameThread.STATE_LOSE_FINAL);
        }

    }

    public String getWinScore() {
        return winScore;
    }

    private void getHighScore(){
        myRef = FirebaseDatabase.getInstance().getReference("Root");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    int value = dataSnapshot.getValue(int.class);
                    Log.d("THE_GAME", "High score came back as: " + value);
                    highScore = value;
                } catch (NullPointerException ne){
                    Log.d("THE_GAME", "Failed from NullPE");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("THE_GAME", "Failed to read value.", error.toException());
            }
        });
    }

    public void setHighScore() {
        // Reading a message from the database
        myRef.setValue(highScore);
    }

    //Collision control between mBall and another big ball
    private boolean updateBallCollision(float x, float y) {
        //Get actual distance (without square root - remember?) between the mBall and the ball being checked
        float distanceBetweenBallAndPaddle = (x - mBallX) * (x - mBallX) + (y - mBallY) *(y - mBallY);

        //Check if the actual distance is lower than the allowed => collision
        if(mMinDistanceBetweenBallAndPaddle >= distanceBetweenBallAndPaddle) {
            //Get the present speed (this should also be the speed going away after the collision)
            float speedOfBall = (float) Math.sqrt(mBallSpeedX*mBallSpeedX + mBallSpeedY*mBallSpeedY);

            //Change the direction of the ball
            mBallSpeedX = mBallX - x;
            mBallSpeedY = mBallY - y;

            //Get the speed after the collision
            float newSpeedOfBall = (float) Math.sqrt(mBallSpeedX*mBallSpeedX + mBallSpeedY*mBallSpeedY);

            //using the fraction between the original speed and present speed to calculate the needed
            //velocities in X and Y to get the original speed but with the new angle.
            mBallSpeedX = mBallSpeedX * speedOfBall / newSpeedOfBall;
            mBallSpeedY = mBallSpeedY * speedOfBall / newSpeedOfBall;

            return true;
        }

        return false;
    }

    //Collision control between mPaddle and another big ball
    private boolean updatePaddleCollision(float x, float y) {
        //Get actual distance (without square root - remember?) between the mBall and the ball being checked
        float distanceBetweenObjectAndPaddle = (x - mPaddleX) * (x - mPaddleX) + (y - mCanvasHeight) * (y - mCanvasHeight);

        //Check if the actual distance is lower than the allowed => collision
        if(mMinDistanceBetweenEnemyAndPaddle >= distanceBetweenObjectAndPaddle) {
            return true;
        }
        return false;
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
