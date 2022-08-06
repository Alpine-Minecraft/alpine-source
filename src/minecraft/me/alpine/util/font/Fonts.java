package me.alpine.util.font;

import lombok.experimental.UtilityClass;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

@UtilityClass
public class Fonts {
    HashMap<String, CFontRenderer> fonts;

    public void setupFonts() {
        fonts = new HashMap<>();

        fonts.put("productsans 14", CFontRenderer.create(new ResourceLocation("alpine/font/productsans/bold.ttf"), 14));
        fonts.put("productsans 19", CFontRenderer.create(new ResourceLocation("alpine/font/productsans/bold.ttf"), 19));
    }

    public CFontRenderer get(String name) {
        return fonts.get(name);
    }
}
