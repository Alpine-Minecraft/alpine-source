package me.alpine.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public final class Position {
    private double x, y;

    /**
     * @param x The x position as a fraction of screen width.
     * @param y The y position as a fraction of screen height.
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return The x position as a fraction of screen width.
     */
    public double getFractionX() {
        return x;
    }

    /**
     * @return The y position as a fraction of screen height.
     */
    public double getFractionY() {
        return y;
    }

    public double getDrawX()
    {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        return x * scaledResolution.getScaledWidth();
    }

    public double getDrawY()
    {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        return y * scaledResolution.getScaledHeight();
    }

    public void setFractionX(double x) {
        this.x = x;
    }

    public void setFractionY(double y) {
        this.y = y;
    }

    public void setDrawX(double x) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        this.x = x / scaledResolution.getScaledWidth();
    }

    public void setDrawY(double y) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        this.y = y / scaledResolution.getScaledHeight();
    }
}
