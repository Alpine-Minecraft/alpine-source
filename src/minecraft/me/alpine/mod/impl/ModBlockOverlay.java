package me.alpine.mod.impl;

import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventRender2D;
import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;
import me.alpine.mod.property.impl.BooleanProperty;
import me.alpine.util.render.GuiUtil;

public class ModBlockOverlay extends Mod {
    private final BooleanProperty fillSetting;
    private final BooleanProperty colorSetting;
    private final BooleanProperty opacitySetting;

    public ModBlockOverlay() {
        super("BlockOverlay", "BlockOverlay", EnumModCategory.HUD);
        
        fillSetting = addProperty("Fill", true);
        colorSetting = addProperty("Color", true);
        opacitySetting = addProperty("Opacity", true);
    }

    public float getRed()
    {
        return 1.0F;
    }

    public float getGreen()
    {
        return 1.0F;
    }

    public float getBlue()
    {
        return 1.0F;
    }

    public float getOpacity()
    {
        return 1.0F;
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {

    }
}

