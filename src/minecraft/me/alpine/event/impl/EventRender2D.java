package me.alpine.event.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.alpine.event.Event;
import net.minecraft.client.gui.ScaledResolution;

@AllArgsConstructor
public class EventRender2D extends Event {
    @Getter private final ScaledResolution sr;
}
