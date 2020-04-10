package com.example.watizit.objects;

public class Level {

    private int id;
    private String word;
    private int hints;
    private int stars;

    public Level(int id, String word, int hints, int stars){
        this.id = id; this.word = word;
        this.hints = hints; this.stars = stars;
    }

    public int getID(){
        return id;
    }

    public String getWord(){
        return word;
    }

    public int getHints(){
        return hints;
    }

    public void setHints() {

    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {

    }

    public boolean isDone() {
        return getStars() > -1;
    }

    public int calculateStars(int time) {
        time += getHints()*12000; // Penalty calculation
        time /= 1000; // Milliseconds to seconds
        int stars =
                (time < 46) ? 3 :   // 0 <= time <= 45 seconds -> 3 stars
                (time < 61) ? 2 :   // 46 <= time <= 60 seconds -> 2 stars
                (time < 76) ? 1 :   // 61 <= time <= 75 seconds -> 1 star
                0;                  // 76 <= time -> 0 stars

        return stars;
    }

}
