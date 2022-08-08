package me.alpine.gui.click.element.property;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.element.ElementMod;
import me.alpine.mod.property.impl.BooleanProperty;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.DeltaTime;
import me.alpine.util.render.GuiUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

public class ElementBooleanProperty extends ElementBaseProperty {
    @Getter private final BooleanProperty property;

    @Getter @Setter private boolean hovered;

    @Getter @Setter private double animEnable;
    @Getter @Setter private double animHover;

    public ElementBooleanProperty(ElementMod parent, BooleanProperty property, int index) {
        setParent(parent);
        setName(property.getName());
        setIndex(index);

        this.property = property;
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
    }

    @Override
    public void onRender(int mouseX, int mouseY) {
        if (getParent().isOpened()) {
            hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;

            y = getParent().getParent().getY() + getParent().getH() + 8 - (int) getScrollOffset();
            totalH = h + 3;

            if (getIndex() - 1 >= 0) {
                ElementBaseProperty previous = getParent().getChildren().get(getIndex() - 1);
                y = previous.getY() + previous.getTotalH();
            }

            double targetAnim;

            if (hovered) {
                if (property.getValue()) {
                    targetAnim = 0.75;
                } else {
                    targetAnim = 0.25;
                }
            } else {
                if (property.getValue()) {
                    targetAnim = 1;
                } else {
                    targetAnim = 0;
                }
            }

            animHover += (targetAnim - animHover) * DeltaTime.get() * 0.01;
            animHover = MathHelper.clamp_double(animHover, 0, 1);
            animHover = Math.round(animHover * 10000) / 10000.0;

            GuiUtil.drawRoundedRect(x, y, x + w, y + h, 5, 0xFF151525);

            CFontRenderer font = Fonts.get("productsans 14");
            font.drawString(getName(), x + 3, y + h / 2.0 - font.getHeight() / 2.0, -1);

            int checkMargin = h / 3;
            int checkSize = h - checkMargin * 2;
            int checkX = x + w - checkSize - checkMargin;
            int checkY = y + checkMargin;

            GlStateManager.disableBlend();
            GuiUtil.drawRoundedRectOutline(checkX + 0.5, checkY + 0.5, checkX + checkSize - 0.5, checkY + checkSize - 0.5, 0, 0xff6a6a6a);

            if (animHover > 0.01) {
                GuiUtil.drawRoundedRect(checkX, checkY, checkX + checkSize * animHover, checkY + checkSize, 0, 0xFF3080ed);
            }

            GlStateManager.enableBlend();
        }
    }

    @Override
    public boolean onClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
            property.toggle();
            return true;
        }
        return false;
    }
}
