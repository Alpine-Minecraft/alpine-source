package me.alpine.gui.click.element.property;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.Theme;
import me.alpine.gui.click.element.ElementMod;
import me.alpine.mod.property.impl.KeybindProperty;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.GuiUtil;

public class ElementKeybindProperty extends ElementBaseProperty {
    @Getter private final KeybindProperty property;

    @Getter @Setter private boolean hovered;
    @Getter @Setter private boolean listening;

    @Getter @Setter private double animEnable;
    @Getter @Setter private double animHover;

    public ElementKeybindProperty(ElementMod parent, KeybindProperty property, int index) {
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
        updatePositionY();

        if (updateTotalHeight(property.isHidden())) {
            return;
        }

        if (getParent().isOpened()) {
            hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;

            CFontRenderer font = Fonts.get("productsans 14");
            GuiUtil.drawRoundedRect(x, y, x + w, y + h, 5, Theme.background());

            font.drawString(getName(), x + 3, y + h / 2.0 - font.getHeight() / 2.0, -1);

            String bindName = listening ? "..." : property.getKeybindName();

            int boxWidth = font.getStringWidth(bindName) + 6;
            int boxHeight = font.getHeight() + 6;
            int boxX = this.x + this.w - boxWidth - 5;
            double boxY = this.y + this.h / 2.0 - boxHeight / 2.0;

            GuiUtil.drawRoundedRect(boxX, boxY, boxX + boxWidth, boxY + boxHeight, 5, 0xff6a6a6a);
            font.drawString(bindName, boxX + 3, boxY + boxHeight / 2.0 - font.getHeight() / 2.0, -1);
        }
    }

    @Override public void onKey(char typedchar, int keyCode) {
        super.onKey(typedchar, keyCode);

        if (listening) {
            if (keyCode != 1) {
                property.setBind(keyCode);
            } else {
                property.setBind(0);
            }
            listening = false;
        }
    }

    @Override
    public boolean onClick(int mouseX, int mouseY, int mouseButton) {
        if (updateTotalHeight(property.isHidden())) {
            return false;
        }

        if (hovered) {
            listening = true;
            return true;
        }

        return false;
    }
}
