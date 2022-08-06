package me.alpine.mod.property;

import lombok.Getter;
import lombok.Setter;

public class BaseProperty {
    @Getter private final String name;
    @Getter @Setter private String description;
    @Getter @Setter private boolean hidden;

    public BaseProperty(final String name) {
        this.name = name;
        this.description = "";
        this.hidden = false;
    }
}
