package me.alpine.mod.impl;

import lombok.Getter;
import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventRender2D;
import me.alpine.event.impl.EventTick;
import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;
import me.alpine.util.render.DeltaTime;
import net.minecraft.client.Minecraft;

import java.text.DecimalFormat;

public class ModFPS extends Mod {
    @Getter double fps;
    @Getter double fpsSmoothed;

    public ModFPS() {
        super("FPS", "Displays the current FPS", EnumModCategory.HUD);
        this.setEnabled(true);
    }

    @EventTarget
    public void onTick(EventTick e) {
        this.fps = 1000 / DeltaTime.get();
        this.fpsSmoothed += (this.fps - this.fpsSmoothed) * 0.001 * DeltaTime.get();
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        DecimalFormat df = new DecimalFormat("#");
        String s = df.format(this.fpsSmoothed);
        mc.fontRendererObj.drawStringWithShadow(String.format("FPS: %s", s), 2, 2, 0x00d9ff);
    }
}
