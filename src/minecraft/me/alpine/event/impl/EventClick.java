package me.alpine.event.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.alpine.event.Event;

@AllArgsConstructor
public class EventClick extends Event {
    @Getter
    private final int button;
}
