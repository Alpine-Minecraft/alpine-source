package me.alpine.mod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.alpine.Alpine;

import java.util.ArrayList;

@AllArgsConstructor
public enum EnumModCategory {
    PLAYER("Player"),
    HUD("HUD"),
    RENDER("Render"),
    MISC("Misc");

    @Getter private final String formattedName;

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
