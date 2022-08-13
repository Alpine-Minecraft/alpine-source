package me.alpine.mod.property.impl;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import me.alpine.Alpine;
import me.alpine.mod.property.BaseProperty;

import java.awt.*;

public class ColorProperty extends BaseProperty {
    @Getter @Setter private float hue;
    @Getter @Setter private float saturation;
    @Getter @Setter private float brightness;
    /**
     * The alpha value of the color from 0 to 1.
     */
    private float alpha = 1.0f;

    private boolean renderAlphaSlider;

    public ColorProperty(final String name, final Color color) {
        super(name);
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        this.hue = hsb[0];
        this.saturation = hsb[1];
        this.brightness = hsb[2];
    }

    public Color getColor() {
        Color c = Color.getHSBColor(hue, saturation, brightness);
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) (alpha * 255.0D));
    }

    public void setColor(final Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        this.hue = hsb[0];
        this.saturation = hsb[1];
        this.brightness = hsb[2];
        this.alpha = color.getAlpha();
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
        if (renderAlphaSlider) {
            return alpha;
        } else {
            return 1.0D;
        }
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

    /**
     * @param alpha alpha from 0 to 1
     */
    public void setAlpha(final float alpha) {
        this.alpha = alpha;
    }

    public ColorProperty setRenderAlphaSlider(final boolean renderAlphaSlider) {
        this.renderAlphaSlider = renderAlphaSlider;
        this.alpha = getColor().getAlpha() / 255.0F;
        return this;
    }

    public boolean shouldRenderAlphaSlider() {
        return renderAlphaSlider;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = super.toJson();
        json.addProperty("hue", hue);
        json.addProperty("saturation", saturation);
        json.addProperty("brightness", brightness);
        json.addProperty("alpha", alpha);
        return json;
    }

    @Override
    public void fromJson(JsonObject json) {
        if (json.has("hue")) {
            this.hue = json.get("hue").getAsFloat();
        } else {
            Alpine.getInstance().getLogger().warn("Malformed JSON at Color property member 'hue' not found");
        }
        if (json.has("saturation")) {
            this.saturation = json.get("saturation").getAsFloat();
        } else {
            Alpine.getInstance().getLogger().warn("Malformed JSON at Color property member 'saturation' not found");
        }
        if (json.has("brightness")) {
            this.brightness = json.get("brightness").getAsFloat();
        } else {
            Alpine.getInstance().getLogger().warn("Malformed JSON at Color property member 'brightness' not found");
        }
        if (json.has("alpha")) {
            this.alpha = json.get("alpha").getAsFloat();
        } else {
            Alpine.getInstance().getLogger().warn("Malformed JSON at Color property member 'alpha' not found");
        }

        /* clamp all to reasonable values */
        this.hue = Math.max(0.0f, Math.min(1.0f, this.hue));
        this.saturation = Math.max(0.0f, Math.min(1.0f, this.saturation));
        this.brightness = Math.max(0.0f, Math.min(1.0f, this.brightness));
        this.alpha = Math.max(0.0f, Math.min(1.0f, this.alpha));
    }
}
