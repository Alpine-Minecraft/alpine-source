package me.alpine.mod.impl;

import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventRender2D;
import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;

public class ModMemory extends Mod {
    public ModMemory() {
        super("Memory", "Shows memory usage", EnumModCategory.HUD);
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        int x = e.getSr().getScaledWidth() - 100;
        int y = 2;
        String memory = String.format("Memory: %s%%", (int) (Runtime.getRuntime().totalMemory() * 100 / Runtime.getRuntime().maxMemory()));
        mc.fontRendererObj.drawStringWithShadow(memory, x, y, 0x00d9ff);
    }
}

