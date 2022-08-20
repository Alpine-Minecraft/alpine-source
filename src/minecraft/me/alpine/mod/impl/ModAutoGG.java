package me.alpine.mod.impl;

import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventPacket;
import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;

public class ModAutoGG extends Mod {
    public ModAutoGG() {
        super("AutoGG", "AutoGG's", EnumModCategory.PLAYER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventTarget
    public void onPacket(EventPacket e) {
        Packet packet = e.getPacket();
        if (!(packet instanceof S02PacketChat)) return;

        S02PacketChat chat = (S02PacketChat) packet;

        if (!chat.getChatComponent().getUnformattedText().equals("Want to play again?")) return;

        String S = "gg";
        if (Math.random() > 0.5) S = "good game";

        mc.thePlayer.sendChatMessage(S);
    }
}




