package me.alpine.mod;

import lombok.Getter;
import lombok.Setter;
import me.alpine.event.EventManager;
import me.alpine.mod.property.BaseProperty;
import me.alpine.mod.property.impl.*;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mod {
    protected final Minecraft mc = Minecraft.getMinecraft();

    @Getter private final String name;
    @Getter private final String description;
    @Getter private final EnumModCategory category;
    @Getter @Setter private String displayName;
    private boolean enabled;

    @Getter private final List<BaseProperty> properties;

    public Mod(String name, String description, EnumModCategory category) {
        this.name = name;
        this.displayName = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
        this.properties = new ArrayList<>();
    }

    public BooleanProperty addProperty(String name, boolean value) {
        BooleanProperty property = new BooleanProperty(name, value);
        this.properties.add(property);
        return property;
    }

    public NumberProperty addProperty(String name, double value, double min, double max, double step) {
        NumberProperty property = new NumberProperty(name, value, min, max, step);
        this.properties.add(property);
        return property;
    }

    public EnumProperty addProperty(String name, String value, String... values) {
        EnumProperty property = new EnumProperty(name, value, values);
        this.properties.add(property);
        return property;
    }

    public ComboProperty addProperty(String name, String[] selectedValues, String[] values) {
        ComboProperty property = new ComboProperty(name, selectedValues, values);
        this.properties.add(property);
        return property;
    }

    public FolderProperty addProperty(String name, BaseProperty... properties) {
        FolderProperty property = new FolderProperty(name, Arrays.asList(properties));
        this.properties.add(property);
        return property;
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
