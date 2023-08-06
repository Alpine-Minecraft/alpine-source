package me.alpine.gui.click;

import lombok.experimental.UtilityClass;

import java.awt.*;

@UtilityClass
public final class Theme {
    public int foreground() {
        return new Color(0x1A2B37).getRGB();
    }

    public int background() {
        return new Color(0x121017).getRGB();
    }

    public int accent() {
        return new Color(0xFF579E9E).getRGB();
    }
}
