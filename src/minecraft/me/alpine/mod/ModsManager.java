package me.alpine.mod;

import lombok.Getter;
import me.alpine.event.EventManager;
import me.alpine.event.impl.EventKey;
import me.alpine.mod.impl.*;
import me.alpine.mod.property.BaseProperty;
import me.alpine.mod.property.impl.KeybindProperty;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModsManager {
    @Getter final CopyOnWriteArrayList<Mod> mods;

    public ModsManager() {
        this.mods = new CopyOnWriteArrayList<>();
        EventManager.register(this);
        registerMods();
    }

    public void registerMods() {
        this.mods.add(new ModFPS());
        this.mods.add(new ModCPS());
        this.mods.add(new ModWatermark());
        this.mods.add(new ModTest());
        this.mods.add(new ModPing());
        this.mods.add(new ModMemory());
        this.mods.add(new ModBlockOverlay());
        this.mods.add(new ModOldAnims());
        this.mods.add(new ModAutoGG());
        this.mods.add(new ModHotkeys());
    }

    public <T extends Mod> T getMod(Class<T> clazz) {
        for (Mod mod : mods) {
            if (mod.getClass() == clazz) {
                return (T) mod;
            }
        }
        return null;
    }

    public Mod getMod(String name) {
        for (Mod mod : mods) {
            if (mod.getName().equals(name)) {
                return mod;
            }
        }
        return null;
    }

    public ArrayList<ModDraggable> getDraggableMods() {
        ArrayList<ModDraggable> draggableMods = new ArrayList<>();
        for (Mod mod : mods) {
            if (mod instanceof ModDraggable) {
                draggableMods.add((ModDraggable) mod);
            }
        }
        return draggableMods;
    }

    public void onKey(EventKey eventKey) {
        for (Mod mod: mods) {
            for (BaseProperty property : mod.getProperties()) {
                if (property instanceof KeybindProperty) {
                    ((KeybindProperty) property).onKeyPress(eventKey);
                }
            }
        }
    }
}
