package me.alpine.util.font;

import lombok.experimental.UtilityClass;
import me.alpine.Alpine;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

@UtilityClass
public class Fonts {
    HashMap<String, CFontRenderer> fonts;

    public void setupFonts() {
        Alpine.getInstance().getLogger().info("[Alpine] Initializing Fonts");
        fonts = new HashMap<>();

        fonts.put("productsans 14", CFontRenderer.create(new ResourceLocation("alpine/font/productsans/bold.ttf"), 14));
        fonts.put("productsans 19", CFontRenderer.create(new ResourceLocation("alpine/font/productsans/bold.ttf"), 19));
        fonts.put("nunito semibold 22", CFontRenderer.create(new ResourceLocation("alpine/font/nunito/semibold.ttf"), 22));
        Alpine.getInstance().getLogger().info("[Alpine] Initialized Fonts");
    }

    public CFontRenderer get(String name) {
        return fonts.get(name);
    }
}
