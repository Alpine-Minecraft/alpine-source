package me.alpine.util.render;

import lombok.experimental.UtilityClass;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;

@UtilityClass
public class RenderUtil {
    public void glSetColor(final int color) {
        final double red = (color >> 16 & 0xFF) / 255.0D;
        final double green = (color >> 8 & 0xFF) / 255.0D;
        final double blue = (color & 0xFF) / 255.0D;
        final double alpha = (color >> 24 & 0xFF) / 255.0D;

        GL11.glColor4d(red, green, blue, alpha);
    }

    public boolean glEnableBlend() {
        final boolean wasEnabled = glIsEnabled(GL_BLEND);

        if (!wasEnabled) {
            glEnable(GL_BLEND);
            glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        }

        return wasEnabled;
    }

    public void glRestoreBlend(final boolean wasEnabled) {
        if (!wasEnabled) {
            glDisable(GL_BLEND);
        }
    }
}
