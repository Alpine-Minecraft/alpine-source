package me.alpine.mod.property.impl;

import lombok.Getter;
import lombok.Setter;
import me.alpine.mod.property.BaseProperty;
import net.minecraft.util.MathHelper;

public final class NumberProperty extends BaseProperty {
    @Getter private double value;
    @Getter @Setter private double min;
    @Getter @Setter private double max;
    @Getter @Setter private double step;

    public NumberProperty(final String name, final double value, final double min, final double max, final double step) {
        super(name);
        this.value = value;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public double setValue(double value) {
        double d = 1 / this.step;

        /* This will clamp the value and round it to the nearest step */
        this.value = Math.round(MathHelper.clamp_double(value, min, max) * d) / d;
        return this.value;
    }
}
