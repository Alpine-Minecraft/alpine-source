package me.alpine.mod.property.impl;

import lombok.Getter;
import lombok.Setter;
import me.alpine.mod.property.BaseProperty;

public final class BooleanProperty extends BaseProperty {
    @Getter @Setter private boolean value;

    public BooleanProperty(final String name, final boolean value) {
        super(name);
        this.value = value;
    }

    public void toggle() {
        this.value = !this.value;
    }
}

