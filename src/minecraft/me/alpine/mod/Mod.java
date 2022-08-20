package me.alpine.mod;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import me.alpine.Alpine;
import me.alpine.event.EventManager;
import me.alpine.mod.property.BaseProperty;
import me.alpine.mod.property.impl.*;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mod {
    protected final Minecraft mc = Minecraft.getMinecraft();

    @Getter private final String name;
    @Getter private final String description;
    @Getter private final EnumModCategory category;
    @Getter private final List<BaseProperty> properties;
    @Getter @Setter private String displayName;
    private boolean enabled;

    public Mod(String name, String description, EnumModCategory category) {
        this.name = name;
        this.displayName = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
        this.properties = new ArrayList<>();
    }

    public <T extends BaseProperty> T addProperty(T property) {
        this.properties.add(property);
        return property;
    }

    public void addProperties(BaseProperty... properties) {
        this.properties.addAll(Arrays.asList(properties));
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

    public ColorProperty addProperty(String name, Color color) {
        ColorProperty property = new ColorProperty(name, color);
        this.properties.add(property);
        return property;
    }

    public TextFieldProperty addProperty(String name, String value) {
        TextFieldProperty property = new TextFieldProperty(name, value);
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

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("enabled", enabled);

        JsonObject properties = new JsonObject();

        for (BaseProperty property: this.properties) {
            properties.add(property.getName(), property.toJson());
        }

        json.add("properties", properties);

        return json;
    }

    public void fromJson(JsonObject json) {

        if (json.has("enabled")) {
            this.setEnabled(json.get("enabled").getAsBoolean());
        } else {
            Alpine.getInstance().getLogger().warn("Malformed JSON at mod '" + this.name + "', member 'enabled' not found");
        }

        if (json.has("properties")) {
            JsonObject properties = json.get("properties").getAsJsonObject();
            for (BaseProperty property: this.properties) {
                if (properties.has(property.getName())) {
                    property.fromJson(properties.get(property.getName()).getAsJsonObject());
                }
            }
        } else {
            Alpine.getInstance().getLogger().warn("Malformed JSON at mod '" + this.name + "', member 'properties' not found");
        }
    }
}
