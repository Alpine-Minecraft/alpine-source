package me.alpine.mod.impl;

import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventRender2D;
import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;

public class ModPing extends Mod {
    public ModPing() {
        super("Ping", "Renders the server ping", EnumModCategory.PLAYER);
    }

    private String getPing() {
        if (mc.isSingleplayer()) return "-1";
        if (mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()) == null) return "0000";
        return mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime() + "";
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        int x = e.getSr().getScaledWidth() - 100;
        int y = 2;
        String ping = String.format("Ping: %s", getPing());
        mc.fontRendererObj.drawStringWithShadow(ping, x, y, 0x00d9ff);
    }

}

