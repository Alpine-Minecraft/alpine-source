package me.alpine.mod.property.impl;

import lombok.Getter;
import lombok.Setter;
import me.alpine.mod.property.BaseProperty;

import java.awt.*;

public class ColorProperty extends BaseProperty {
    @Getter @Setter private float hue;
    @Getter @Setter private float saturation;
    @Getter @Setter private float brightness;

    public ColorProperty(final String name, final Color color) {
        super(name);
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        this.hue = hsb[0];
        this.saturation = hsb[1];
        this.brightness = hsb[2];
    }

    public Color getColor() {
        return Color.getHSBColor(hue, saturation, brightness);
    }

    public void setColor(final Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        this.hue = hsb[0];
        this.saturation = hsb[1];
        this.brightness = hsb[2];
    }

    public double getRed() {
        return getColor().getRed() / 255.0D;
    }

    public double getGreen() {
        return getColor().getGreen() / 255.0D;
    }

    public double getBlue() {
        return getColor().getBlue() / 255.0D;
    }

    public double getAlpha() {
        return getColor().getAlpha() / 255.0D;
    }

    public void setRed(final double red) {
        setColor(new Color((float) (red / 255.0), (float) getGreen(), (float) getBlue(), (float) getAlpha()));
    }

    public void setGreen(final double green) {
        setColor(new Color((float) getRed(), (float) (green / 255.0), (float) getBlue(), (float) getAlpha()));
    }

    public void setBlue(final double blue) {
        setColor(new Color((float) getRed(), (float) getGreen(), (float) (blue / 255.0), (float) getAlpha()));
    }

    public void setAlpha(final double alpha) {
        setColor(new Color((float) getRed(), (float) getGreen(), (float) getBlue(), (float) (alpha / 255.0)));
    }
}
