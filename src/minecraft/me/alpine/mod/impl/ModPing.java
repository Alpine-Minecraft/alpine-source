package me.alpine.mod.impl;

import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventRender2D;
import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;
import net.minecraft.client.Minecraft;

public class ModPing extends Mod {
    public ModPing() {
        super("Ping", "Pings", EnumModCategory.PLAYER);
    }

    public String getPlayerPing() {
        return Minecraft.getMinecraft().isSingleplayer() ? "-1" : Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()) == null ? "0000" : String.valueOf(Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime());
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        int x = e.getSr().getScaledWidth() - 100;
        int y = 2;
        String ping = String.format("Ping: %s", getPlayerPing());
        mc.fontRendererObj.drawStringWithShadow(ping, x, y, 0x00d9ff);
    }

}

