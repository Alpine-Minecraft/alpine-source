package me.alpine.util.font;

import lombok.experimental.UtilityClass;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

@UtilityClass
public class Fonts {
    HashMap<String, MCFontRenderer> fonts;

    public void setupFonts() {
        fonts = new HashMap<>();

        fonts.put("ProductSans 19", MCFontRenderer.create(new ResourceLocation("alpine/font/productsans/bold.ttf"), 19));
    }

    public MCFontRenderer get(String name) {
        return fonts.get(name);
    }
}
