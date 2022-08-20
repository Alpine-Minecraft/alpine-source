package me.alpine.util.font;

import lombok.experimental.UtilityClass;
import me.alpine.Alpine;

import java.awt.*;
import java.util.HashMap;

@UtilityClass
public class Fonts {
    private final CFontRenderer fallbackFont = new CFontRenderer(new Font("Arial", Font.PLAIN, 12), true, true);
    HashMap<String, CFontRenderer> fonts;

    public void setupFonts() {
        Alpine.getInstance().getLogger().info("[Alpine] Initializing Fonts");
        final long start = System.currentTimeMillis();
        fonts = new HashMap<>();

        fonts.put("productsans 10", CFontRenderer.create("/assets/minecraft/alpine/font/productsans/bold.ttf", 10));
        fonts.put("productsans 14", CFontRenderer.create("/assets/minecraft/alpine/font/productsans/bold.ttf", 14));
        fonts.put("productsans 19", CFontRenderer.create("/assets/minecraft/alpine/font/productsans/bold.ttf", 19));
        fonts.put("nunito semibold 22", CFontRenderer.create("/assets/minecraft/alpine/font/nunito/semibold.ttf", 22));
        fonts.put("raleway semibold 18", CFontRenderer.create("/assets/minecraft/alpine/font/raleway/semibold.ttf", 18));

        Alpine.getInstance().getLogger().info("[Alpine] Initialized Fonts, took " + (System.currentTimeMillis() - start) + "ms");
    }

    public CFontRenderer get(String name) {
        return fonts.getOrDefault(name, fallbackFont);
    }
}
