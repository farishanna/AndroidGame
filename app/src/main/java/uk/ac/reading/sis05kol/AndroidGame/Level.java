package uk.ac.reading.sis05kol.AndroidGame;

/**
 * Determine the amount of objects of the levels
 */
public class Level {

    //Variables that builds up the game
    //Keep Smiley Faces as 1
    private int sadFaces;
    private int angryFaces;
    private int veryAngryFaces;
    private int lives;
    private int pointsToWin;

    /**
     * Set the parameters for the objects for each level
     * @param levelNumber -- determines which level
     */
    public Level(int levelNumber){
        if (levelNumber == 1){
            sadFaces = 2;
            angryFaces = 1;
            veryAngryFaces = 0;
            lives = 5;
            pointsToWin = 1;
        } else if (levelNumber == 2){
            sadFaces = 3;
            angryFaces = 1;
            veryAngryFaces = 1;
            lives = 5;
            pointsToWin = 1;
        } else if (levelNumber == 3){
            sadFaces = 5;
            angryFaces = 1;
            veryAngryFaces = 2;
            lives = 5;
            pointsToWin = 1;
        }
    }

    /**
     * Used to help onCreate method in MainActivity with creating custom levels
     * @param lives -- Amount of Lives
     * @param pointsToWin -- Amount of Points
     * @param sadFaces -- Amount of Sad Faces
     * @param angryFaces -- Amount of Angry Faces
     * @param veryAngryFaces -- Amount of Very Angry Faces
     */
    public Level(int lives, int pointsToWin, int sadFaces, int angryFaces, int veryAngryFaces){
        this.sadFaces = sadFaces;
        this.angryFaces = angryFaces;
        this.veryAngryFaces = veryAngryFaces;
        this.lives = lives;
        this.pointsToWin = pointsToWin;
    }

    /**
     * Getters
     */

    public int getSadFaces() { return sadFaces; }

    public int getAngryFaces() { return angryFaces; }

    public int getVeryAngryFaces() { return veryAngryFaces; }

    public int getLives() { return lives; }

    public int getPointsToWin() { return pointsToWin; }

}
