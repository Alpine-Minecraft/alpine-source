package me.alpine.mod.property.impl;

import com.google.gson.JsonObject;
import lombok.Setter;
import me.alpine.Alpine;
import me.alpine.mod.property.BaseProperty;

public final class BooleanProperty extends BaseProperty {
    @Setter private boolean value;

    public BooleanProperty(final String name, final boolean value) {
        super(name);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void toggle() {
        this.value = !this.value;

        listeners.forEach(listener -> listener.onChange(value));
    }

    @Override
    public JsonObject toJson() {
        final JsonObject json = super.toJson();
        json.addProperty("value", value);
        return json;
    }

    @Override
    public void fromJson(JsonObject json) {
        if (json.has("value")) {
            this.value = json.get("value").getAsBoolean();
        } else {
            Alpine.getInstance().getLogger().warn("Malformed JSON on boolean property, member 'value' not found");
        }
    }
}

