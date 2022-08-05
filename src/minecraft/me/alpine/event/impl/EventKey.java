package me.alpine.event.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import me.alpine.event.Event;

@AllArgsConstructor @ToString
public class EventKey extends Event {
    @Getter private final int key;
}
