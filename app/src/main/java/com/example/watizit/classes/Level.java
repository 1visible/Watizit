package com.example.watizit.classes;

import com.example.watizit.utils.DatabaseUtil;
import com.example.watizit.utils.MoneyUtil;

/**
 * This class represents a level for the game, level retrieved from the database.
 */
public class Level {
    // Database columns
    private int id;
    private String word;
    private int hints;
    private int stars;

    /**
     * Instantiates a new Level.
     *
     * @param id the id
     * @param word the word
     * @param hints the hints
     * @param stars the stars
     */
    public Level(int id, String word, int hints, int stars) {
        this.id = id;
        this.word = word;
        this.hints = hints;
        this.stars = stars;
    }

    /**
     * This method returns the id of the level.
     *
     * @return the id
     */
    public int getID() {
        return id;
    }

    /**
     * This method returns the word of the level.
     *
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * This method is used to buy a hint (with the hint number).
     *
     * @param hintNumber the hint number to buy
     */
    public void buyHint(int hintNumber) {
        // Set the price: 3⚡ for the #1, 5⚡ for the #2 and 7⚡ for the #3
        int price = (hintNumber == 1) ? 3 : (hintNumber == 2) ? 5 : 7;
        // Check if the player can buy the hint
        if (canBuyHint(hintNumber)) {
            /* Add the hint weight to the total hints weight. It works as follows:
                • Weight of hint #1: 1
                • Weight of hint #2: 2
                • Weight of hint #3: 4
            With this system, possible hints combinations are in the [0, 7] interval
            with 0 being no hints used and 7 (= 1 + 2 + 4) being all hints used */
            hints += (hintNumber == 3) ? 4 : hintNumber;
            // Update hints in database
            DatabaseUtil.updateHints(getID(), hints);
            // And make the player pay
            MoneyUtil.addMoney(-price);
        }
    }

    /**
     * This method checks if the player can buy the hint with hint number.
     *
     * @param hintNumber the hint number
     * @return true if the player can buy the hint, false otherwise
     */
    public boolean canBuyHint(int hintNumber) {
        // Set the price: 3⚡ for the #1, 5⚡ for the #2 and 7⚡ for the #3
        int price = (hintNumber == 1) ? 3 : (hintNumber == 2) ? 5 : 7;
        // Check if the player has already bought the item or doesn't have enough money
        return !isHintBought(hintNumber) && MoneyUtil.getMoney() >= price;
    }

    /**
     * This method checks if the hint has already been bought.
     *
     * @param hintNumber the hint number
     * @return true if the hint has been bought, false otherwise
     */
    public boolean isHintBought(int hintNumber) {
        switch (hintNumber) {
            case 1:
                // Check for the possible combinations with hint #1 in hints weight
                if (hints == 1 || hints == 3 || hints == 5 || hints == 7)
                    return true;
                break;
            case 2:
                // Check for the possible combinations with hint #2 in hints weight
                if (hints == 2 || hints == 3 || hints == 6 || hints == 7)
                    return true;
                break;
            case 3:
                // Check for the possible combinations with hint #3 in hints weight
                if (hints == 4 || hints == 5 || hints == 6 || hints == 7)
                    return true;
                break;
        }
        return false;
    }

    /**
     * This method gets the number of stars in the level, returns -1 if the level has not been done.
     *
     * @return the number of stars between 0 and 3.
     */
    public int getStars() { return stars; }

    /**
     * This method sets the number of stars of the level.
     *
     * @param time the time the player spent on the level
     */
    public void setStars(long time) {
        /* Calculate the penalty and add it to the time:
          We add a quota of 12'000 points to the total weight of hints.
          This avoids abuse of hints and prevents a player who directly
          skips a level from winning the 3 stars */
        time += hints * 12000;
        // Convert time in milliseconds to seconds
        time /= 1000;
        stars =
                (time < 46) ? 3 :   // 0 ≤ time ≤ 45 seconds 🡲 3 stars
                (time < 61) ? 2 :   // 46 ≤ time ≤ 60 seconds 🡲 2 stars
                (time < 76) ? 1 :   // 61 ≤ time ≤ 75 seconds 🡲 1 star
                0;                  // 76 ≤ time 🡲 0 stars
        // Update the number of stars in the database
        DatabaseUtil.updateStars(getID(), getStars());
    }

    /**
     * This method checks if the level is done.
     *
     * @return true if the level is done, false otherwise
     */
    public boolean isDone() {
        // getStars() returns -1 if the level hasn't been done (-1 is the default value in the database)
        return getStars() > -1;
    }

    /**
     * This method checks if the level is locked. A level is locked if the previous level
     * is not done.
     *
     * @return true if the level is locked, false otherwise
     */
    public boolean isLocked() {
        // Get the previous level
        Level previousLevel = DatabaseUtil.getLevel(getID() - 1);
        // Check if the level exists and if it has been done
        return !(previousLevel == null || previousLevel.isDone());
    }

}
