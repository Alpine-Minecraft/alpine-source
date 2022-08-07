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
    }

    public Mod getMod(Class<Mod> clazz) {
        for (Mod mod : mods) {
            if (mod.getClass() == clazz) {
                return mod;
            }
        }
        return null;
    }
}
