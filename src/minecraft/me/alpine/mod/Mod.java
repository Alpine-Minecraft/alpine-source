package me.alpine.mod;

import lombok.Getter;
import lombok.Setter;
import me.alpine.event.EventManager;

public class Mod {
    @Getter private final String name;
    @Getter private final String description;
    @Getter private final EnumModCategory category;
    @Getter @Setter private String displayName;
    private boolean enabled;

    public Mod(String name, String description, EnumModCategory category) {
        this.name = name;
        this.displayName = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
    }

    public void onEnable() {
        EventManager.register(this);
    }

    public void onDisable() {
        EventManager.unregister(this);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void toggle() {
        setEnabled(!isEnabled());
    }
}
