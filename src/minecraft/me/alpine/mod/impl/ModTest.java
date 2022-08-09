package me.alpine.mod.impl;

import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;
import me.alpine.mod.property.impl.BooleanProperty;
import me.alpine.mod.property.impl.ComboProperty;
import me.alpine.mod.property.impl.EnumProperty;
import me.alpine.mod.property.impl.NumberProperty;

import java.awt.*;

public class ModTest extends Mod {
    public ModTest() {
        super("TEST MOD", "This is a test mod", EnumModCategory.MISC);

        addProperty("Boolean Property", true);
        addProperty("Number Property", 0.0, 0.0, 1.0, 0.1);
        addProperty("Color Property", Color.WHITE);
        addProperty("Color Property with alpha", Color.WHITE).setRenderAlphaSlider(true);
        addProperty("Enum Property", "Value 1", "Value 1", "Value 2", "Value 3");
        addProperty("Combo Property", new String[]{"Value 1", "Value 3"}, new String[]{"Value 1", "Value 2", "Value 3"});
        addProperty("Folder Property",
                new BooleanProperty("Boolean Property", true),
                new NumberProperty("Number Property", 0.0, 0.0, 1.0, 0.1),
                new ComboProperty("Combo Property", new String[]{"Value 1", "Value 3"}, new String[]{"Value 1", "Value 2", "Value 3"}),
                new EnumProperty("Enum Property", "Value 1", "Value 1", "Value 2", "Value 3"));
        addProperty("Another Boolean Property", true);
        addProperty("Yet Another Boolean Property", true);
        addProperty("Still Another Boolean Property", true);
        addProperty("And Another Boolean Property", true);
        addProperty("And Still Another Boolean Property", true);
        addProperty("And Again, a Boolean Property", true);
        addProperty("it's like the 8th Boolean Property", true);
        addProperty("and the 9th Boolean Property", true);
        addProperty("wow, thats 10 :skull:", true);
        addProperty("bruh what to write now", true);
        addProperty("69420 ?", true);
        addProperty("69 ?", true);
        addProperty("420 ?", true);
        addProperty("911 ?", true);
        addProperty("8008135 ?", true);




    }
}
