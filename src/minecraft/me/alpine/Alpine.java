package me.alpine;

import lombok.Getter;
import me.alpine.event.EventManager;
import me.alpine.mod.ModsManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Alpine {
    /** The instance of the client */
    @Getter private static final Alpine instance = new Alpine();

    /** The client name */
    @Getter private final String name = "Alpine";
    /** The client version */
    @Getter private final String version = "0.1";
    /** The Logger implementation */
    @Getter private final Logger logger = LogManager.getLogger("Alpine");

    @Getter private final ModsManager modsManager = new ModsManager();

    /**
     * This is the method that will be called when Minecraft starts.
     */
    public void onStart() {
        EventManager.register(this);

        /* This adds a shutdown hook to the JVM that will call this.onStop. */
        Runtime.getRuntime().addShutdownHook(new Thread(this::onStop));

        logger.info("[Alpine] Starting Alpine on version " + version);
    }

    /**
     * This is the method that will be called when Minecraft stops.
     */
    public void onStop() {
        EventManager.clean();
        logger.info("[Alpine] Stopping!");
    }
}
