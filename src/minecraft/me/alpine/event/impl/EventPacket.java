package me.alpine.event.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.alpine.event.Event;
import net.minecraft.network.Packet;

@AllArgsConstructor
public final class EventPacket extends Event {

    @Getter private final Packet packet;
    @Getter private final Direction direction;


    public enum Direction {
        INCOMING,
        OUTGOING
    }
}
