package me.alpine.mod.impl;

import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;

public class ModHotkeys extends Mod {
    public ModHotkeys() {
        super("Hotkeys", "Little HotKey Mod", EnumModCategory.MISC);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        // when g is pressed, send message to chat

    }
}
