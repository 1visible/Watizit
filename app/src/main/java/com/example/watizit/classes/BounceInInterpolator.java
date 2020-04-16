package com.example.watizit.classes;

/**
 * The type Bounce in interpolator.
 */
public class BounceInInterpolator implements android.view.animation.Interpolator {
    private double amplitude, frequency;

    /**
     * Instantiates a new Bounce in interpolator.
     *
     * @param amplitude the amplitude
     * @param frequency the frequency
     */
    public BounceInInterpolator(double amplitude, double frequency) {
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    public float getInterpolation(float time)
    {
        return (float) (-Math.pow(Math.E, -time/amplitude) * Math.cos(frequency*time) + 1);
    }
}