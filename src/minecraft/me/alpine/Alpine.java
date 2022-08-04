package me.alpine;

import lombok.Getter;

public enum Alpine {
    /** The instance of the client */
    INSTANCE;

    /** The client name */
    @Getter private final String name = "Alpine";
    /** The client version */
    @Getter private final String version = "0.1";
}
