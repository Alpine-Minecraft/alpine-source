package me.alpine.gui.click.element.property;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.Theme;
import me.alpine.gui.click.element.ElementMod;
import me.alpine.mod.property.impl.TextFieldProperty;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.GuiUtil;

public class ElementTextFieldProperty extends ElementBaseProperty {
    @Getter private final TextFieldProperty property;

    @Getter @Setter private boolean hovered;

    @Getter @Setter private double animEnable;
    @Getter @Setter private double animHover;

    public ElementTextFieldProperty(ElementMod parent, TextFieldProperty property, int index) {
        setParent(parent);
        setName(property.getName());
        setIndex(index);

        this.property = property;
    }

    @Override
    public void onInit() {
        super.onInit();
        property.setFocused(false);
    }

    @Override
    public void onRender(int mouseX, int mouseY) {
        updatePositionY();

        if (updateTotalHeight(property.isHidden())) {
            return;
        }

        if (getParent().isOpened()) {
            hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;

            CFontRenderer font = Fonts.get("productsans 14");
            GuiUtil.drawRoundedRect(x, y, x + w, y + h, 5, Theme.background());

            int nameX = x + 5;
            int nameY = y + (h - font.getHeight()) / 2;

            if (property.isFocused() || !property.getValue().isEmpty()) {
                font = Fonts.get("productsans 10");
                nameX -= 2;
                nameY -= 5;

                /* draw a line right of the name */
                int lineX = nameX + font.getStringWidth(getName() + "__");
                int lineY = nameY + font.getHeight() / 2;
                GuiUtil.drawRect(lineX, lineY - 0.5, x + w - 5, lineY, -1);

            }

            font.drawString(getName(), nameX, nameY, -1, false);

            font = Fonts.get("productsans 14");
            int valueX = x + 5;
            int valueY = y + (h - font.getHeight()) / 2 + 3;
            double valueWidth = font.getStringWidth(property.getValue());

            if (!property.getValue().isEmpty()) {

                font.drawString(property.getValue(), valueX, valueY, -1, false);
            }

            if (property.isFocused()) {

                int cursorX = (int) (valueX + valueWidth);
                int cursorY = valueY;

                font.drawString("|", cursorX, cursorY, -1, false);

            }
        }
    }

    @Override
    public void onKey(char typedchar, int keyCode) {
        System.out.println(typedchar + " " + keyCode);

        property.onKey(typedchar, keyCode);


        super.onKey(typedchar, keyCode);
    }

    @Override
    public boolean onClick(int mouseX, int mouseY, int mouseButton) {
        if (updateTotalHeight(property.isHidden())) {
            return false;
        }

        if (hovered) {
            property.setFocused(true);
            return true;
        } else {
            property.setFocused(false);
        }

        return false;
    }
}
