package me.alpine.mod.property.impl;

import com.google.gson.JsonObject;
import lombok.Getter;
import me.alpine.Alpine;
import me.alpine.mod.property.BaseProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ComboProperty extends BaseProperty {
    @Getter private final ArrayList<String> selectedValues;
    @Getter private final List<String> values;

    public ComboProperty(final String name, final String[] selectedValues, final String[] values) {
        super(name);
        this.selectedValues = new ArrayList<>(Arrays.asList(selectedValues));
        this.values = Arrays.asList(values);
    }

    @Override
    public JsonObject toJson() {
        final JsonObject json = super.toJson();

        final JsonObject comboJson = new JsonObject();

        for (String value : values) {
            comboJson.addProperty(value, selectedValues.contains(value));
        }
        json.add("values", comboJson);

        return json;
    }

    @Override
    public void fromJson(JsonObject json) {
        if (json.has("values")) {
            final JsonObject comboJson = json.get("values").getAsJsonObject();
            for (String value : values) {
                if (comboJson.has(value)) {
                    if (comboJson.get(value).getAsBoolean()) {
                        selectedValues.add(value);
                    }
                }
            }
        } else {
            Alpine.getInstance().getLogger().warn("Malformed JSON on combo property, member 'values' not found");
        }
    }
}
