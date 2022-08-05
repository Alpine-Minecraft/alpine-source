package me.alpine.util.render;

import lombok.experimental.UtilityClass;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

@UtilityClass
public final class GuiUtil {
    /**
     * Draw a colored rectangle
     *
     * @param x The position of the left edge of the rectangle.
     * @param y The position of the top edge of the rectangle.
     * @param x2 The position of the right edge of the rectangle.
     * @param y2 The position of the bottom edge of the rectangle.
     * @param color The color of the rectangle as ARGB.
     */
    public void drawRect(double x, double y, double x2, double y2, int color) {
        /* Invert x and x2 if x2 is more to the left than x */
        if (x < x2) {
            final double temp = x;
            x = x2;
            x2 = temp;
        }

        /* Invert y and y2 if y2 is more to the top than y */
        if (y < y2) {
            final double temp = y;
            y = y2;
            y2 = temp;
        }

        final float red = (color >> 16 & 0xFF) / 255.0F;
        final float green = (color >> 8 & 0xFF) / 255.0F;
        final float blue = (color & 0xFF) / 255.0F;
        final float alpha = (color >> 24 & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y2, 0.0D).endVertex();
        worldRenderer.pos(x2, y2, 0.0D).endVertex();
        worldRenderer.pos(x2, y, 0.0D).endVertex();
        worldRenderer.pos(x, y, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
    }

    /**
     * Draw a colored rectangle outline
     *
     * @param x The position of the left edge of the rectangle.
     * @param y The position of the top edge of the rectangle.
     * @param x2 The position of the right edge of the rectangle.
     * @param y2 The position of the bottom edge of the rectangle.
     * @param color The color of the rectangle as ARGB.
     */
    public void drawRectOutline(double x, double y, double x2, double y2, int color) {
        /* Invert x and x2 if x2 is more to the left than x */
        if (x < x2) {
            final double temp = x;
            x = x2;
            x2 = temp;
        }

        /* Invert y and y2 if y2 is more to the top than y */
        if (y < y2) {
            final double temp = y;
            y = y2;
            y2 = temp;
        }

        final float red = (color >> 16 & 0xFF) / 255.0F;
        final float green = (color >> 8 & 0xFF) / 255.0F;
        final float blue = (color & 0xFF) / 255.0F;
        final float alpha = (color >> 24 & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        worldRenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y2, 0.0D).endVertex();
        worldRenderer.pos(x2, y2, 0.0D).endVertex();
        worldRenderer.pos(x2, y, 0.0D).endVertex();
        worldRenderer.pos(x, y, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public void drawCircle(double x, double y, double radius, int color) {
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0D;
        y *= 2.0D;
        radius *= 2.0D;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        // enable anti-aliasing
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);

        RenderUtil.glSetColor(color);
        GL11.glBegin(GL11.GL_POLYGON);
        /* Iterate backwards so that the polygon is not culled (drawing should be anti-clockwise),
        also, step 3 times for optimisation */
        for (int i = 360; i >= 0; i -= 3) {
            double angle = i * Math.PI / 180;
            GL11.glVertex2d(x + Math.cos(angle) * radius, y + Math.sin(angle) * radius);
        }
        GL11.glEnd();
        GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GL11.glPopAttrib();
    }

}