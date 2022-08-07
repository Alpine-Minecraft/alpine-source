package me.alpine.mod.property.impl;

import lombok.Getter;
import lombok.Setter;
import me.alpine.mod.property.BaseProperty;

import java.awt.*;

public class ColorProperty extends BaseProperty {
    @Getter @Setter private Color color;

    public ColorProperty(final String name, final Color color) {
        super(name);
        this.color = color;
    }

    public float getRed() {
        return color.getRed() / 255f;
    }

    public float getGreen() {
        return color.getGreen() / 255f;
    }

    public float getBlue() {
        return color.getBlue() / 255f;
    }

    public float getAlpha() {
        return color.getAlpha() / 255f;
    }

    public void setRed(final float red) {
        color = new Color(red, color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public void setGreen(final float green) {
        color = new Color(color.getRed(), green, color.getBlue(), color.getAlpha());
    }

    public void setBlue(final float blue) {
        color = new Color(color.getRed(), color.getGreen(), blue, color.getAlpha());
    }

    public void setAlpha(final float alpha) {
        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public float getHue() {
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return hsb[0];
    }

    public float getSaturation() {
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return hsb[1];
    }

    public float getBrightness() {
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return hsb[2];
    }

    public void setHue(final float hue) {
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        color = Color.getHSBColor(hue, hsb[1], hsb[2]);
    }

    public void setSaturation(final float saturation) {
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        color = Color.getHSBColor(hsb[0], saturation, hsb[2]);
    }

    public void setBrightness(final float brightness) {
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        color = Color.getHSBColor(hsb[0], hsb[1], brightness);
    }
}
