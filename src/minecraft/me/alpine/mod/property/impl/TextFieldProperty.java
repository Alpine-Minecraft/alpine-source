package me.alpine.mod.property.impl;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import me.alpine.Alpine;
import me.alpine.mod.property.BaseProperty;
import net.minecraft.client.gui.GuiScreen;

import java.util.Arrays;

public class TextFieldProperty extends BaseProperty {

    @Getter @Setter String value;
    @Getter @Setter int cursor;
    @Getter @Setter int selectionStart;
    @Getter @Setter int selectionEnd;
    @Getter @Setter boolean focused;
    @Getter @Setter boolean enabled;

    public TextFieldProperty(String name, String value) {
        super(name);
        this.value = value;
    }

    public void onKey(char typedchar, int keyCode) {
        if (!focused) return;

        /* If the user hits backspace */
        if (keyCode == 14) {
            /* If there is text */
            if (value.length() > 0) {
                if (GuiScreen.isCtrlKeyDown()) {
                    /* Remove all the text until there is a space */
                    value = value.substring(0, value.lastIndexOf(" ") + 1);
                    /* This removes the space */
                    if (value.length() > 0) {
                        value = value.substring(0, value.length() - 1);
                    }
                } else {
                    /* Remove the last letter of value */
                    value = value.substring(0, value.length() - 1);
                }
            }
        } else if (typedchar == '\r') {
            focused = false;
        } else if (Arrays.asList(new Integer[]{
                1, 15, 58, 42, 29, 219, 56, 184, 220, 221, 157, 54,
                183, 70, 197, 210, 211, 199, 207, 201, 209, 200, 203, 205, 208, 69,
                59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 87, 88 }).contains(keyCode)) {
            /*
            Ignore keys that are not normal characters
            1 -> Escape key                                 197 -> Pause
            15 -> Tab                                       210 -> Insert
            58 -> Caps Lock                                 211 -> Delete
            42 -> Left Shift                                199 -> Home
            29 -> left Control                              207 -> End
            219 -> Left Windows Key                         201 -> Page Up
            56 -> Left Alt                                  209 -> Page Down
            184 -> Right Alt                                200 -> Up Arrow
            220 -> Right Windows Key                        203 -> Left Arrow
            221 -> The key that emulates a right click      205 -> Right Arrow
            157 -> Right Control                            208 -> Down Arrow
            54 -> Right Shift                               69 -> Num Lock
            183 -> Print Screen
            70 -> Scroll Lock

            59-68 && 87-88 -> Function keys
             */
        } else {
            value += typedchar;
        }
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = super.toJson();
        json.addProperty("value", value);
        return json;
    }

    @Override
    public void fromJson(JsonObject json) {
        if (json.has("value")) {
            this.value = json.get("value").getAsString();
        } else {
            Alpine.getInstance().getLogger().warn("Malformed JSON on textfield property, member 'value' not found");
        }
    }
}
