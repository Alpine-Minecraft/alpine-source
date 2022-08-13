package me.alpine.mod.property;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class BaseProperty {
    @Getter private final String name;
    @Getter @Setter private String description;
    @Getter @Setter private boolean hidden;
    protected final List<IChangeListener> listeners = new ArrayList<>();

    public BaseProperty(final String name) {
        this.name = name;
        this.description = "";
        this.hidden = false;
    }

    public <T extends BaseProperty> T as(Class<T> clazz) {
        return clazz.cast(this);
    }

    public void addChangeListener(final IChangeListener listener) {
        listeners.add(listener);
    }

    public JsonObject toJson() {
        final JsonObject json = new JsonObject();
        return json;
    }

    public void fromJson(JsonObject json) {
        // no-op
    }
}
