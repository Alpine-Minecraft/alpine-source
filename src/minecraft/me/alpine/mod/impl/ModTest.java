package me.alpine.mod.impl;

import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;
import me.alpine.mod.property.impl.BooleanProperty;
import me.alpine.mod.property.impl.ComboProperty;
import me.alpine.mod.property.impl.EnumProperty;
import me.alpine.mod.property.impl.NumberProperty;

public class ModTest extends Mod {
    public ModTest() {
        super("TEST MOD", "This is a test mod", EnumModCategory.MISC);

        addProperty("Boolean Property", true);
        addProperty("Number Property", 0.0, 0.0, 1.0, 0.1);
        addProperty("Combo Property", "Value 1", "Value 1", "Value 2", "Value 3");
        addProperty("Enum Property", new String[]{"Value 1", "Value 3"}, new String[]{"Value 1", "Value 2", "Value 3"});
        addProperty("Folder Property",
                new BooleanProperty("Boolean Property", true),
                new NumberProperty("Number Property", 0.0, 0.0, 1.0, 0.1),
                new ComboProperty("Combo Property", new String[]{"Value 1", "Value 3"}, new String[]{"Value 1", "Value 2", "Value 3"}),
                new EnumProperty("Enum Property", "Value 1", "Value 1", "Value 2", "Value 3"));
    }
}
