package me.alpine.gui.click.element.property.color;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.element.ElementMod;
import me.alpine.gui.click.element.property.ElementBaseProperty;
import me.alpine.mod.property.impl.ColorProperty;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.DeltaTime;
import me.alpine.util.render.Easings;
import me.alpine.util.render.GuiUtil;
import me.alpine.util.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ElementColorProperty extends ElementBaseProperty {
    @Getter private final ColorProperty property;
    @Getter private final ElementColorPaletteProperty palette;

    @Getter @Setter private boolean hovered;
    @Getter @Setter private boolean expanded;
    @Getter @Setter private double animEnable;


    public ElementColorProperty(ElementMod parent, ColorProperty property, int index) {
        setParent(parent);
        setName(property.getName());
        setIndex(index);

        this.property = property;
        this.palette = new ElementColorPaletteProperty(this, property.getColor());
    }

    @Override
    public void onInit() {
        x = getParent().getX() + getParent().getW() + 10;
        y = getParent().getParent().getY() + getParent().getH() + 8;
        w = (int) (getParent().getParent().getParent().getBgWidth() - getParent().getParent().getParent().getBgWidth() / 3.5) - 20;
        h = 20;
        totalH = h + 3;

        if (getIndex() - 1 >= 0) {
            ElementBaseProperty previous = getParent().getChildren().get(getIndex() - 1);
            y = previous.getY() + previous.getTotalH();
        }

        palette.onInit();
    }

    @Override
    public void onRender(int mouseX, int mouseY) {
        if (getParent().isOpened()) {
            hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;

            y = getParent().getParent().getY() + getParent().getH() + 8 - (int) getScrollOffset();

            if (getIndex() - 1 >= 0) {
                ElementBaseProperty previous = getParent().getChildren().get(getIndex() - 1);
                y = previous.getY() + previous.getTotalH();
            }

            GuiUtil.drawRoundedRect(x, y, x + w, y + h, 5, 0xFF151525);

            CFontRenderer font = Fonts.get("productsans 14");
            font.drawString(getName(), x + 3, y + h / 2.0 - font.getHeight() / 2.0, -1);

            double cardMargin = 2;
            double cardSize = h - cardMargin * 2;
            double cardX = x + w - cardSize - cardMargin - 8;
            double cardY = y + cardMargin;

            GuiUtil.drawRoundedRect(cardX, cardY, cardX + cardSize, cardY + cardSize, 5, property.getColor().getRGB());

            animEnable += (expanded ? 1 : -1) * DeltaTime.get() * 0.005;
            animEnable = MathHelper.clamp_double(animEnable, 0, 1);
            double animExpandEased = Easings.easeInOutExponential(animEnable);

            String s = "#" + Integer.toHexString(property.getColor().getRGB());
            double d = (cardX - 3 - font.getStringWidth(s)) * (1 - animExpandEased) + (palette.getX() - 3 - font.getStringWidth(s)) * (animExpandEased);
            font.drawString(s, d, y + h / 2.0 - font.getHeight() / 2.0, 0xFF3080ed);


            double arrowX = getX() + getW() - 6;
            double arrowY = getY() + getH() / 2.0 - 1;

            GlStateManager.pushMatrix();
            GlStateManager.translate(arrowX, arrowY, 0);
            GlStateManager.rotate((float) (180 * animExpandEased) + 180, 0.0F, 0.0F, 1.0F);

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glLineWidth(2f);
            RenderUtil.glSetColor(0xFF3080ed);

            GL11.glBegin(GL11.GL_LINE_STRIP);
            {
                GL11.glVertex2d(2, 2);
                GL11.glVertex2d(0, -2);
                GL11.glVertex2d(-2, 2);
            }
            GL11.glEnd();

            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translate(cardX + cardSize, cardY, 0);
            GlStateManager.scale(animExpandEased, animExpandEased, 1);
            GlStateManager.translate(-(cardX + cardSize), -cardY, 0);

            palette.onRender(mouseX, mouseY);

            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean onClick(int mouseX, int mouseY, int mouseButton) {
        if (palette.onClick(mouseX, mouseY, mouseButton)) {
            return true;
        }

        if (hovered) {
            expanded = !expanded;
            return true;
        }
        return false;
    }

    @Override
    public void onRelease(int mouseX, int mouseY, int state) {
        palette.onRelease();
    }
}