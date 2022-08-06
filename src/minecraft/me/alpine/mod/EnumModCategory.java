package me.alpine.mod;

import me.alpine.Alpine;

import java.util.ArrayList;

public enum EnumModCategory {
    PLAYER,
    HUD,
    RENDER,
    MISC;

    public ArrayList<Mod> getMods() {
        ArrayList<Mod> mods = new ArrayList<>();
        for (Mod mod : Alpine.getInstance().getModsManager().getMods()) {
            if (mod.getCategory() == this) {
                mods.add(mod);
            }
        }
        return mods;
    }
}
