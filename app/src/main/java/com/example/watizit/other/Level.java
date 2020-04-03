package com.example.watizit.other;

public class Level {

    private int id;
    private String word;
    private int hints;
    private int stars;

    public Level(int id, String word, int hints, int stars){
        this.id = id; this.word = word;
        this.hints = hints; this.stars = stars;
    }

    public int getID(){ return id; }

    public String getWord(){
        return word;
    }

    public int getHints(){
        return hints;
    }

    public int getStars() { return stars; }

}
