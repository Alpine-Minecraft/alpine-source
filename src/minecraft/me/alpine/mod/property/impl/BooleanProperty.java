package me.alpine.mod.property.impl;

import lombok.Setter;
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
}

