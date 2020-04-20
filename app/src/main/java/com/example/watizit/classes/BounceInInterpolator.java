package com.example.watizit.classes;

/**
 * This class represents a bounce-in interpolator, useful for animations.
 */
public class BounceInInterpolator implements android.view.animation.Interpolator {
    private double amplitude;
    private double frequency;

    /**
     * Instantiates a new bounce-in interpolator.
     *
     * @param amplitude the amplitude
     * @param frequency the frequency
     */
    public BounceInInterpolator(double amplitude, double frequency) {
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    /**
     * This method gives the result of a bouce-in function as a function of time.
     *
     * @param time the time-point of the function
     * @return the value of the bounce-in function at a given time
     */
    public float getInterpolation(float time) {
        return (float) (-Math.pow(Math.E, -time / amplitude) * Math.cos(frequency * time) + 1);
    }
}