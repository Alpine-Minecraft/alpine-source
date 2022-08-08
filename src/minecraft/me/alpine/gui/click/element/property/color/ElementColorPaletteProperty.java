package me.alpine.gui.click.element.property.color;

import lombok.Getter;
import lombok.Setter;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.GuiUtil;
import me.alpine.util.render.RenderUtil;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ElementColorPaletteProperty {
    @Getter private final ElementColorProperty parent;

    @Getter @Setter private Color value;
    @Getter @Setter private double hue;
    @Getter @Setter private double saturation;
    @Getter @Setter private double brightness;


    @Getter @Setter protected int x;
    @Getter @Setter protected int y;
    @Getter @Setter protected int w;
    @Getter @Setter protected int h;
    @Getter @Setter protected int totalH;

    @Getter @Setter private boolean hovered;
    @Getter @Setter private boolean selected;

    @Getter @Setter private int paletteX;
    @Getter @Setter private int paletteY;
    @Getter @Setter private int paletteSize;
    @Getter @Setter private boolean paletteDragged;

    @Getter @Setter private int hueX;
    @Getter @Setter private int hueY;
    @Getter @Setter private int hueWidth;
    @Getter @Setter private int hueHeight;
    @Getter @Setter private boolean hueDragged;

    @Getter @Setter private double animExpand;

    @Getter @Setter private CFontRenderer font;

    public ElementColorPaletteProperty(ElementColorProperty parent, Color value) {
        this.parent = parent;
        this.value = value;
    }

    public void onInit() {
        font = Fonts.get("productsans 14");
        w = 70;
        h = 50;
        x = parent.getX() + parent.getW() - w - 10;
        y = parent.getY() + 2;

        int paletteMargin = 3;
        paletteSize = h - paletteMargin * 2;
        paletteX = x + w - paletteSize - paletteMargin;
        paletteY = y + paletteMargin;

        int hueMargin = 3;
        hueWidth = 5;
        hueHeight = h - hueMargin * 2;
        hueX = paletteX - hueWidth - hueMargin;
        hueY = y + hueMargin;

        hue = getParent().getProperty().getHue();
        saturation = getParent().getProperty().getSaturation();
        brightness = getParent().getProperty().getBrightness();
    }

    public void onRender(int mouseX, int mouseY) {
        if (getParent().getAnimEnable() > 0) {
            hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;

            y = parent.getY() + 2;
            int paletteMargin = 3;
            paletteY = y + paletteMargin;
            int hueMargin = 3;
            hueY = y + hueMargin;


            if (paletteDragged) {
                saturation = (MathHelper.clamp_double(mouseX - paletteX, 0, paletteSize)) / (double) paletteSize;
                brightness = 1 - (MathHelper.clamp_double(mouseY - paletteY, 0, paletteSize)) / (double) paletteSize;
            }

            if (hueDragged) {
                hue = (MathHelper.clamp_double(mouseY - hueY, 0, hueHeight)) / (double) hueHeight;
            }

            getParent().getProperty().setHue((float) hue);
            getParent().getProperty().setSaturation((float) saturation);
            getParent().getProperty().setBrightness((float) brightness);

            value = Color.getHSBColor((float) hue, (float) saturation, (float) brightness);

            GuiUtil.drawRoundedRect(x, y, x + w, y + h, 5, 0xff606060);
            GuiUtil.drawColorPalette(paletteX, paletteY, paletteSize, paletteSize, Color.getHSBColor((float) hue, 1.0F, 1.0F));
            GuiUtil.drawImage(hueX, hueY, hueWidth, hueHeight, "alpine/clickgui/hue_slider.png");
            GuiUtil.drawRect(x + 3, y + 3, hueX - 3, y + h - 3, value.getRGB());

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_POINT_SMOOTH);
            GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
            GL11.glLineWidth(1.0F);
            RenderUtil.glSetColor(-1);

            GL11.glBegin(GL11.GL_LINES);
            {
                double thingX = paletteX + paletteSize * saturation;
                double thingY = paletteY + paletteSize * (1 - brightness);

                GL11.glVertex2d(thingX + 1, thingY);
                GL11.glVertex2d(thingX - 1, thingY);
                GL11.glVertex2d(thingX, thingY + 1);
                GL11.glVertex2d(thingX, thingY - 1);

            }
            GL11.glEnd();

            GL11.glBegin(GL11.GL_LINES);
            {
                double lineY = hueY + hueHeight * hue;
                GL11.glVertex2d(hueX + hueWidth, lineY);
                GL11.glVertex2d(hueX, lineY);
            }
            GL11.glEnd();

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_POINT_SMOOTH);


        }
    }

    public boolean onClick(int mouseX, int mouseY, int mouseButton) {
        // if palette hovered
        if (parent.isExpanded()) {
            if (mouseX >= paletteX && mouseX <= paletteX + paletteSize && mouseY >= paletteY && mouseY <= paletteY + paletteSize) {
                paletteDragged = true;
                return true;
            }
            if (mouseX >= hueX && mouseX <= hueX + hueWidth && mouseY >= hueY && mouseY <= hueY + hueHeight) {
                hueDragged = true;
                return true;
            }
        }

        return false;
    }

    public void onRelease() {
        paletteDragged = false;
        hueDragged = false;
    }
}
