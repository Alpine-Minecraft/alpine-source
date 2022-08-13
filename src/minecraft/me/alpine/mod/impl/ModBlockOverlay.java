package me.alpine.mod.impl;

import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;
import me.alpine.mod.property.impl.BooleanProperty;
import me.alpine.mod.property.impl.ColorProperty;
import me.alpine.mod.property.impl.NumberProperty;

import java.awt.*;

public class ModBlockOverlay extends Mod {
    private final BooleanProperty shouldFill;
    private final ColorProperty fillColor;

    private final BooleanProperty outlineSetting;
    private final ColorProperty outlineColor;
    private final NumberProperty outlineThickness;



    public ModBlockOverlay() {
        super("BlockOverlay", "BlockOverlay", EnumModCategory.RENDER);

        shouldFill = addProperty("Fill", true);
        fillColor = addProperty("Fill Color", Color.WHITE);
        fillColor.setRenderAlphaSlider(true);
        fillColor.setAlpha(0.4F);

        outlineSetting = addProperty("Outline", true);
        outlineColor = addProperty("Outline Color", Color.BLACK);
        outlineColor.setRenderAlphaSlider(true);
        outlineColor.setAlpha(0.4F);
        outlineThickness = addProperty("Outline Thickness", 1, 1, 5, 0.1);

        shouldFill.addChangeListener(b -> fillColor.setHidden(!((boolean) b)));
        outlineSetting.addChangeListener(b -> {
            outlineColor.setHidden(!((boolean) b));
            outlineThickness.setHidden(!((boolean) b));
        });
    }

    public boolean shouldOutline() {
        return !isEnabled() || outlineSetting.getValue();
    }

    public float getOutlineRed() {
        return isEnabled() ? (float) (outlineColor.getRed()): 0.0F;
    }

    public float getOutlineGreen() {
        return isEnabled() ? (float) (outlineColor.getGreen()) : 0.0F;
    }

    public float getOutlineBlue() {
        return isEnabled() ? (float) (outlineColor.getBlue()) : 0.0F;
    }

    public float getOutlineAlpha() {
        return isEnabled() ? (float) outlineColor.getAlpha() : 0.4F;
    }

    public float getOutlineThickness() {
        return isEnabled() ? (float) outlineThickness.getValue() : 2.0F;
    }

    public boolean shouldFill() {
        return isEnabled() && shouldFill.getValue();
    }

    public float getFillRed() {
        return isEnabled() ? (float) (fillColor.getRed()) : 0.0F;
    }

    public float getFillGreen() {
        return isEnabled() ? (float) (fillColor.getGreen()) : 0.0F;
    }

    public float getFillBlue() {
        return isEnabled() ? (float) (fillColor.getBlue()) : 0.0F;
    }

    public float getFillAlpha() {
        return isEnabled() ? (float) fillColor.getAlpha() : 0.4F;
    }
}

