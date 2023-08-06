package me.alpine.mod.property.impl;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import me.alpine.Alpine;
import me.alpine.event.EventManager;
import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventKey;
import me.alpine.mod.property.BaseProperty;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public final class KeybindProperty extends BaseProperty {
    @Getter @Setter private int bind;

    @Getter private final List<Runnable> actions = new ArrayList<>();

    public KeybindProperty(final String name, final int bind) {
        super(name);
        this.bind = bind;
    }

    public KeybindProperty addAction(final Runnable action) {
        this.actions.add(action);
        return this;
    }

    public String getKeybindName() {
        return Keyboard.getKeyName(this.bind);
    }

    public void onKeyPress(final EventKey event) {
        if (event.getKey() == this.bind) {
            for (final Runnable action : this.actions) {
                action.run();
            }
        }
    }

    @Override
    public JsonObject toJson() {
        final JsonObject json = super.toJson();
        json.addProperty("bind", this.bind);
        return json;
    }

    @Override
    public void fromJson(JsonObject json) {
        try {
            if (json.has("bind")) {
                this.bind = json.get("bind").getAsInt();
            } else {
                Alpine.getInstance().getLogger().warn("Malformed JSON on keybind property, member 'bind' not found");
            }
        } catch (Exception e) {
            Alpine.getInstance().getLogger().warn("Malformed JSON on keybind property, set value to default", e);
        }
    }
}

