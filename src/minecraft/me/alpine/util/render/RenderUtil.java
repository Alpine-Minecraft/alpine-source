package me.alpine.util.render;

import lombok.experimental.UtilityClass;
import net.minecraft.client.renderer.GlStateManager;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;

@UtilityClass
public class RenderUtil {
    public void glSetColor(final int color) {
        final float red = (color >> 16 & 0xFF) / 255.0F;
        final float green = (color >> 8 & 0xFF) / 255.0F;
        final float blue = (color & 0xFF) / 255.0F;
        final float alpha = (color >> 24 & 0xFF) / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
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
