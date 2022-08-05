package me.alpine.util.render;

import lombok.experimental.UtilityClass;

/**
 * Utility class for getting the difference of time between to render ticks.
 * This class is useful to make animations that are not tied to framerate
 * by multiplying the change rate by the delta time.
 * The longer the time between to frames, the bigger the delta time
 *
 * TLDR: Delta time is the time between to render ticks.
 */
@UtilityClass public class DeltaTime {
    private long lastNano;

    public void setNano() {
        lastNano = System.nanoTime();
    }

    /**
     * @return 1000 / FPS (technically)
     */
    public double get() {
        /* returns the nano delta as millis delta */
        return (System.nanoTime() - lastNano) / 1000000.0D;
    }
}
