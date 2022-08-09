package me.alpine;

import lombok.Getter;
import lombok.Setter;
import me.alpine.event.EventManager;
import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventKey;
import me.alpine.gui.click.GuiClick;
import me.alpine.mod.ModsManager;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.shader.BlurUtil;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

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
        EventManager.register(this);

        this.name = "Alpine";
        this.version = "0.3";
        this.owners = new String[]{"iSTeeWx_", "Audi"};

        this.logger = LogManager.getLogger("Alpine");

        this.modsManager = new ModsManager();

        /* Disable fast render to prevent rendering issues (i.e. blur) */
        Minecraft.getMinecraft().gameSettings.ofFastRender = false;
    }

    /**
     * This is the method that will be called when Minecraft starts.
     */
    public void onStart() {
        logger.info("[Alpine] Starting Alpine on version " + version);
        Fonts.setupFonts();

        /* This adds a shutdown hook to the JVM that will call this.onStop. */
        Runtime.getRuntime().addShutdownHook(new Thread(this::onStop));

        /* Does an inital set for the framebuffer for the blur */
        BlurUtil.onFrameBufferResize(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
    }

    /**
     * This is the method that will be called when Minecraft stops.
     */
    public void onStop() {
        logger.info("[Alpine] Stopping!");
        EventManager.clean();
    }

    @EventTarget
    public void onKey(EventKey e) {
        if (e.getKey() == Keyboard.KEY_RSHIFT) {
            Minecraft.getMinecraft().displayGuiScreen(GuiClick.getInstance());
        }
    }
}
