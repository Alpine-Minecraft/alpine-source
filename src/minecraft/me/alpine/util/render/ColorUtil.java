package me.alpine.util.render;

import lombok.experimental.UtilityClass;

import java.awt.*;

@UtilityClass
public class ColorUtil {
    /* interpolate between two colors */
    public int interpolate(int c1, int c2, double p) {
        int a1 = (c1 >> 24) & 0xff;
        int r1 = (c1 >> 16) & 0xff;
        int g1 = (c1 >> 8) & 0xff;
        int b1 = c1 & 0xff;
        int a2 = (c2 >> 24) & 0xff;
        int r2 = (c2 >> 16) & 0xff;
        int g2 = (c2 >> 8) & 0xff;
        int b2 = c2 & 0xff;
        int a = (int) (a1 + (a2 - a1) * p);
        int r = (int) (r1 + (r2 - r1) * p);
        int g = (int) (g1 + (g2 - g1) * p);
        int b = (int) (b1 + (b2 - b1) * p);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /* interpolate between two java.awt.Color */
    public Color interpolate(Color c1, Color c2, double p) {
        int a1 = c1.getAlpha();
        int r1 = c1.getRed();
        int g1 = c1.getGreen();
        int b1 = c1.getBlue();
        int a2 = c2.getAlpha();
        int r2 = c2.getRed();
        int g2 = c2.getGreen();
        int b2 = c2.getBlue();
        int a = (int) (a1 + (a2 - a1) * p);
        int r = (int) (r1 + (r2 - r1) * p);
        int g = (int) (g1 + (g2 - g1) * p);
        int b = (int) (b1 + (b2 - b1) * p);
        return new Color(r, g, b, a);
    }
}
