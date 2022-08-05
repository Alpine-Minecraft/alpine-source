package me.alpine.mod.impl;

import me.alpine.Alpine;
import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventRender2D;
import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;
import me.alpine.util.render.GuiUtil;
import me.alpine.util.render.shader.BlurUtil;
import net.minecraft.client.gui.ScaledResolution;

public class ModWatermark extends Mod {
    public ModWatermark() {
        super("Watermark", "A descriptive description", EnumModCategory.HUD);
        this.setEnabled(true);
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        double y = scaledResolution.getScaledHeight() - 10;
        mc.fontRendererObj.drawStringWithShadow("  ALPINE " + Alpine.getInstance().getVersion(), (float) 0, (float) y, 0x00fff7);
    }
}
