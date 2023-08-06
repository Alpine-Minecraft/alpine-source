package me.alpine.mod.impl;

import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;
import me.alpine.mod.property.impl.TextFieldProperty;
import org.lwjgl.input.Keyboard;

public class ModHotkeys extends Mod {

    public ModHotkeys() {
        super("Hotkeys", "Little HotKey Mod", EnumModCategory.MISC);

        TextFieldProperty f1 = addProperty("Command 1", "");
        addProperty("Execute 1", Keyboard.KEY_NONE).addAction(() -> {
            String command = f1.getValue();
            if (!command.isEmpty()) {
                mc.thePlayer.sendChatMessage(command);

            }
        });
    }

}
