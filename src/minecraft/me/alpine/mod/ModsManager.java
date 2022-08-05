package me.alpine.mod;

import lombok.Getter;
import me.alpine.event.EventManager;
import me.alpine.mod.impl.ModCPS;
import me.alpine.mod.impl.ModFPS;
import me.alpine.mod.impl.ModWatermark;

import java.util.concurrent.CopyOnWriteArrayList;

public class ModsManager {
    @Getter final CopyOnWriteArrayList<Mod> mods;

    public ModsManager() {
        this.mods = new CopyOnWriteArrayList<>();
        EventManager.register(this);
        registerMods();
    }

    public void registerMods() {
        this.mods.add(new Mod("ALPINE", "A descriptive description", EnumModCategory.MISC));
        this.mods.add(new ModFPS());
        this.mods.add(new ModCPS());
        this.mods.add(new ModWatermark());
    }
}
