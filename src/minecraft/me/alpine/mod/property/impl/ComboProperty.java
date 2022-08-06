package me.alpine.mod.property.impl;

import lombok.Getter;
import me.alpine.mod.property.BaseProperty;

import java.util.Arrays;
import java.util.List;

public final class ComboProperty extends BaseProperty {
    @Getter private final List<String> selectedValues;
    @Getter private final List<String> values;

    public ComboProperty(final String name, final String[] selectedValues, final String[] values) {
        super(name);
        this.selectedValues = Arrays.asList(selectedValues);
        this.values = Arrays.asList(values);
    }
}
