package com.example.watizit.classes;

import com.example.watizit.utils.MoneyUtil;

public class Level {

    private int id;
    private String word;
    private int hints;
    private int stars;

    public Level(int id, String word, int hints, int stars)
    {
        this.id = id;
        this.word = word;
        this.hints = hints;
        this.stars = stars;
    }

    public int getID()
    {
        return id;
    }

    public String getWord()
    {
        return word;
    }

    public int getHints()
    {
        return hints;
    }

    public void buyHint(int hintNumber)
    {
        // TODO
    }

    public boolean canBuyHint(int hintNumber)
    {
        int hints = getHints();
        int price = (hintNumber == 1) ? 3 : (hintNumber == 2) ? 5 : 7;

        switch(hintNumber)
        {
            case 1:
                if(hints == 1 || hints == 3 || hints == 5 || hints == 7)
                    return false;
                break;
            case 2:
                if(hints == 2 || hints == 3 || hints == 6 || hints == 7)
                    return false;
                break;
            case 3:
                if(hints == 4 || hints == 5 || hints == 6 || hints == 7)
                    return false;
                break;
        }

        return MoneyUtil.getMoney() >= price;
    }

    public int getStars()
    {
        return stars;
    }

    public void setStars(int time)
    {
        time += getHints()*12000; // Penalty calculation
        time /= 1000; // Milliseconds to seconds
        int stars =
                (time < 46) ? 3 :   // 0 <= time <= 45 seconds -> 3 stars
                (time < 61) ? 2 :   // 46 <= time <= 60 seconds -> 2 stars
                (time < 76) ? 1 :   // 61 <= time <= 75 seconds -> 1 star
                0;                  // 76 <= time -> 0 stars

        // TODO
    }

    public boolean isDone()
    {
        return getStars() > -1;
    }

}
