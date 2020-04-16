package com.example.watizit.classes;

import com.example.watizit.utils.DatabaseUtil;
import com.example.watizit.utils.MoneyUtil;

/**
 * The type Level.
 */
public class Level {

    private int id;
    private String word;
    private int hints;
    private int stars;

    /**
     * Instantiates a new Level.
     *
     * @param id    the id
     * @param word  the word
     * @param hints the hints
     * @param stars the stars
     */
    public Level(int id, String word, int hints, int stars)
    {
        this.id = id;
        this.word = word;
        this.hints = hints;
        this.stars = stars;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getID()
    {
        return id;
    }

    /**
     * Gets word.
     *
     * @return the word
     */
    public String getWord()
    {
        return word;
    }

    /**
     * Buy hint.
     *
     * @param hintNumber the hint number
     */
    public void buyHint(int hintNumber)
    {
        int price = (hintNumber == 1) ? 3 : (hintNumber == 2) ? 5 : 7;

        if(canBuyHint(hintNumber))
        {
            hints += (hintNumber == 3) ? 4 : hintNumber;

            DatabaseUtil.updateHints(getID(), hints);
            MoneyUtil.addMoney(-price);
        }
    }

    /**
     * Can buy hint boolean.
     *
     * @param hintNumber the hint number
     * @return the boolean
     */
    public boolean canBuyHint(int hintNumber)
    {
        int price = (hintNumber == 1) ? 3 : (hintNumber == 2) ? 5 : 7;

        return !isHintBought(hintNumber) && MoneyUtil.getMoney() >= price;
    }

    /**
     * Is hint bought boolean.
     *
     * @param hintNumber the hint number
     * @return the boolean
     */
    public boolean isHintBought(int hintNumber)
    {
        switch(hintNumber)
        {
            case 1:
                if(hints == 1 || hints == 3 || hints == 5 || hints == 7)
                    return true;
                break;
            case 2:
                if(hints == 2 || hints == 3 || hints == 6 || hints == 7)
                    return true;
                break;
            case 3:
                if(hints == 4 || hints == 5 || hints == 6 || hints == 7)
                    return true;
                break;
        }

        return false;
    }

    /**
     * Gets stars.
     *
     * @return the stars
     */
    public int getStars()
    {
        return stars;
    }

    /**
     * Sets stars.
     *
     * @param time the time
     */
    public void setStars(long time)
    {
        time += hints*12000; // Penalty calculation
        time /= 1000; // Milliseconds to seconds
        stars =
            (time < 46) ? 3 :   // 0 <= time <= 45 seconds -> 3 stars
            (time < 61) ? 2 :   // 46 <= time <= 60 seconds -> 2 stars
            (time < 76) ? 1 :   // 61 <= time <= 75 seconds -> 1 star
            0;                  // 76 <= time -> 0 stars

        DatabaseUtil.updateStars(getID(), getStars());
    }

    /**
     * Is done boolean.
     *
     * @return the boolean
     */
    public boolean isDone()
    {
        return getStars() > -1;
    }

    /**
     * Is locked boolean.
     *
     * @return the boolean
     */
    boolean isLocked()
    {
        Level previousLevel = DatabaseUtil.getLevel(getID() - 1);
        return previousLevel != null && !previousLevel.isDone();
    }

}
