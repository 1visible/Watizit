package com.example.watizit.classes;

public class BounceInInterpolator implements android.view.animation.Interpolator {
    double amplitude = 1;
    double frequency = 10;

    public BounceInInterpolator(double amplitude, double frequency) {
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    public float getInterpolation(float time)
    {
        return (float) (-Math.pow(Math.E, -time/amplitude) * Math.cos(frequency*time) + 1);
    }
}