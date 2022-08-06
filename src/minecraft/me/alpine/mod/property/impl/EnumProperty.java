package me.alpine.mod.property.impl;

import lombok.Getter;
import lombok.Setter;
import me.alpine.mod.property.BaseProperty;

import java.util.Arrays;
import java.util.List;

public final class EnumProperty extends BaseProperty {
    @Getter @Setter private String selectedValue;
    @Getter private final List<String> values;

    public EnumProperty(final String name, final String value, final String... values) {
        super(name);
//        this.selectedValue = value;
        this.values = Arrays.asList(values);
        this.selectedValue = this.values.contains(value) ? value : this.values.get(0);
    }

    public void cycle(boolean forward) {
        int index = this.values.indexOf(this.selectedValue);
        if (forward) {
            index++;
        } else {
            index--;
        }
        if (index >= this.values.size()) {
            index = 0;
        } else if (index < 0) {
            index = this.values.size() - 1;
        }
        this.selectedValue = this.values.get(index);
    }
}
