package me.alpine.mod.property.impl;

import lombok.Getter;
import lombok.Setter;
import me.alpine.mod.property.BaseProperty;

import java.util.List;

public final class FolderProperty extends BaseProperty {
    @Getter private final List<BaseProperty> properties;
    @Getter @Setter private boolean expanded;

    public FolderProperty(final String name, final List<BaseProperty> properties) {
        super(name);
        this.properties = properties;
    }
}
