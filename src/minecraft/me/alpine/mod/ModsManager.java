package me.alpine.mod;

import lombok.Getter;
import me.alpine.event.EventManager;
import me.alpine.mod.impl.*;

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
    }

    public <T extends Mod> T getMod(Class<T> clazz) {
        for (Mod mod : mods) {
            if (mod.getClass() == clazz) {
                return (T) mod;
            }
        }
        return null;
    }
}
