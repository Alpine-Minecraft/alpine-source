package me.alpine.mod.property.impl;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import me.alpine.Alpine;
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

    @Override
    public JsonObject toJson() {
        final JsonObject json = super.toJson();
        json.addProperty("value", this.value);
        return json;
    }

    @Override
    public void fromJson(JsonObject json) {
        try {
            if (json.has("value")) {
                this.value = json.get("value").getAsDouble();
                /* clamp value */
                this.value = MathHelper.clamp_double(this.value, this.min, this.max);
                /* use the proper rounding */
                setValue(this.value);
            } else {
                Alpine.getInstance().getLogger().warn("Malformed JSON on number property, member 'value' not found");
            }
        } catch (NumberFormatException e) {
            Alpine.getInstance().getLogger().warn("Malformed JSON on number property, set value to default", e);
        }
    }
}
