package me.alpine.mod.impl;

import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventRender2D;
import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;
import me.alpine.util.render.DeltaTime;
import net.minecraft.client.Minecraft;

import java.text.DecimalFormat;

public class ModFPS extends Mod {
    public ModFPS() {
        super("FPS", "Displays the current FPS", EnumModCategory.HUD);
        this.setEnabled(true);
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        DecimalFormat df = new DecimalFormat("#.##");
        String fps = df.format(1000.0D / DeltaTime.get());
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(String.format("FPS: %s", fps), 2, 2, 0x00d9ff);
    }
}
