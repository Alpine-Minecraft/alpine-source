package me.alpine.mod.impl;

import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventRender2D;
import me.alpine.mod.EnumModCategory;
import me.alpine.mod.ModText;
import me.alpine.mod.property.impl.*;
import me.alpine.util.render.Position;

import java.awt.*;

public class ModTest extends ModText {
    public ModTest() {
        super("TEST MOD", "This is a test mod", EnumModCategory.MISC, new Position(0, 0));

        BooleanProperty bProp = addProperty("Boolean Property", true);
        addProperty("Hide Boolean Property", false).addChangeListener(hidden -> bProp.setHidden((Boolean) hidden));
        NumberProperty nProp = addProperty("Number Property", 0.0, 0.0, 1.0, 0.1);
        addProperty("Hide Number Property", false).addChangeListener(hidden -> nProp.setHidden((Boolean) hidden));
        ColorProperty cPropSolid = addProperty("Color Property", Color.WHITE);
        addProperty("Hide Color Property", false).addChangeListener(hidden -> cPropSolid.setHidden((Boolean) hidden));
        ColorProperty cPropAlpha = addProperty("Color Property with alpha", Color.WHITE).setRenderAlphaSlider(true);
        addProperty("Hide Color Property with alpha", false).addChangeListener(hidden -> cPropAlpha.setHidden((Boolean) hidden));
        EnumProperty eProp = addProperty("Enum Property", "Value 1", "Value 1", "Value 2", "Value 3");
        addProperty("Hide Enum Property", false).addChangeListener(hidden -> eProp.setHidden((Boolean) hidden));
        ComboProperty cProp = addProperty("Combo Property", new String[]{"Value 1", "Value 3"}, new String[]{"Value 1", "Value 2", "Value 3"});
        addProperty("Hide Combo Property", false).addChangeListener(hidden -> cProp.setHidden((Boolean) hidden));
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

    @Override
    public String getText() {
        return toString();
    }

    @Override
    public String toString() {
        return "ModTest{" +
                "name='" + getName() +
                '}';
    }

    @Override
    @EventTarget
    public void onAlwaysRender(EventRender2D e) {
        super.onAlwaysRender(e);
    }
}
