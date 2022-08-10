package me.alpine.gui.click.element.property;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.element.ElementMod;
import me.alpine.mod.property.impl.NumberProperty;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.DeltaTime;
import me.alpine.util.render.GuiUtil;
import me.alpine.util.render.RenderUtil;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ElementNumberProperty extends ElementBaseProperty {
    @Getter private final NumberProperty property;
    @Getter @Setter private double value;
    @Getter @Setter private double renderValue;

    @Getter @Setter private boolean hovered;
    @Getter @Setter private boolean dragged;

    @Getter @Setter private double animEnable;
    @Getter @Setter private double animHover;

    public ElementNumberProperty(ElementMod parent, NumberProperty property, int index) {
        setParent(parent);
        setName(property.getName());
        setIndex(index);

        this.property = property;
    }

    @Override
    public void onInit() {
        super.onInit();
    }

    @Override
    public void onRender(int mouseX, int mouseY) {
        if (updateTotalHeight(property.isHidden())) {
            return;
        }

        if (getParent().isOpened()) {
            hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;

            y = getParent().getParent().getY() + getParent().getH() + 8 - (int) getScrollOffset();

            if (getIndex() - 1 >= 0) {
                ElementBaseProperty previous = getParent().getChildren().get(getIndex() - 1);
                y = previous.getY() + previous.getTotalH();
            }

            final double MIN = property.getMin();
            final double MAX = property.getMax();

            if (dragged)
            {
                final double diff = MAX - MIN;
                final double mouse = MathHelper.clamp_double((mouseX - x) / (w + 0.0D), 0, 1);
                final double newVal = MIN + mouse * diff;

                renderValue = newVal;
            }

            if (value != renderValue)
            {
                value += (renderValue - value) * DeltaTime.get() * 0.01;
            }

            GuiUtil.drawRoundedRect(x, y, x + w, y + h, 5, 0xFF151525);

            GuiUtil.drawRoundedRect(x, y + h - 3, x + w, y + h, 0, 0, 5, 5, 0xff6a6a6a);

            double percent = (value - MIN) / (MAX - MIN);
//            RenderUtil.prepareScissorBox(x, y + h - 3, w * percent, 3);
//            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GuiUtil.drawRoundedRect(x, y, x + w * percent, y + h, 5, 0xFF3080ed);
//            GL11.glDisable(GL11.GL_SCISSOR_TEST);



            CFontRenderer font = Fonts.get("productsans 14");
            font.drawString(getName(), x + 3, y + h / 2.0 - font.getHeight() / 2.0, -1);


            renderValue = property.setValue(value);
            font.drawString(""+renderValue, x + w - font.getStringWidth(renderValue+"") - 3, y + h / 2.0 - font.getHeight() / 2.0, -1);
        }
    }

    @Override
    public boolean onClick(int mouseX, int mouseY, int mouseButton) {
        if (updateTotalHeight(property.isHidden())) {
            return false;
        }

        if (hovered) {
            if (mouseButton == 0) {
                dragged = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRelease(int mouseX, int mouseY, int state) {
        if (dragged)
        {
            final double MIN = property.getMin();
            final double MAX = property.getMax();
            final double diff = MAX - MIN;
            final double mouse = MathHelper.clamp_double((mouseX - x) / (w + 0.0D), 0, 1);
            final double newVal = MIN + mouse * diff;
            renderValue = value = property.setValue(newVal);
        }
        dragged = false;
    }
}
