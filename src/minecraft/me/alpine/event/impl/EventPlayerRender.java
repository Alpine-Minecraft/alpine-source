package me.alpine.event.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.alpine.event.Event;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;

@AllArgsConstructor
public class EventPlayerRender extends Event {
    @Getter private final EntityPlayer player;
    @Getter private final RenderPlayer renderer;
    @Getter private final float partialRenderTick;
    @Getter private final double x;
    @Getter private final double y;
    @Getter private final double z;
}
