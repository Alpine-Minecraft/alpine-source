package me.alpine;

import lombok.Getter;
import lombok.Setter;
import me.alpine.event.EventManager;
import me.alpine.mod.ModsManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;

@Getter @Setter
public class Alpine {
    /** The instance of the client */
    @Getter private static final Alpine instance = new Alpine();

    /** The client name */
    private final String name;
    /** The client version */
    private final String version;
    /** The client owners */
    private final String[] owners;
    /** The Logger implementation */
    private final Logger logger;

    /** The mods manager instance */
    private final ModsManager modsManager;

    private Alpine() {
        this.name = "Alpine";
        this.version = "0.1";
        this.owners = new String[]{"iSTeeWx_", "Audi"};

        this.logger = LogManager.getLogger("Alpine");

        this.modsManager = new ModsManager();
    }

    /**
     * This is the method that will be called when Minecraft starts.
     */
    public void onStart() {
        logger.info("[Alpine] Starting Alpine on version " + version);

        /* This adds a shutdown hook to the JVM that will call this.onStop. */
        Runtime.getRuntime().addShutdownHook(new Thread(this::onStop));
    }

    /**
     * This is the method that will be called when Minecraft stops.
     */
    public void onStop() {
        logger.info("[Alpine] Stopping!");
        EventManager.clean();
    }
}
