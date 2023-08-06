package me.alpine.gui.click.element.property.singlecombo;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.Theme;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.GuiUtil;

public class ElementEnumChoiceProperty {
    @Getter private final ElementEnumProperty parent;
    @Getter private final String name;
    @Getter private final int index;

    @Getter private final String value;

    @Getter @Setter protected int x;
    @Getter @Setter protected int y;
    @Getter @Setter protected int w;
    @Getter @Setter protected int h;
    @Getter @Setter protected int totalH;

    @Getter @Setter private boolean hovered;
    @Getter @Setter private boolean selected;

    @Getter @Setter private double animExpand;

    @Getter @Setter private double scroll;

    @Getter @Setter private CFontRenderer font;

    public ElementEnumChoiceProperty(ElementEnumProperty parent, String value, int index) {
        this.parent = parent;
        this.name = value;
        this.index = index;
        this.value = value;
    }

    public void onInit() {
        font = Fonts.get("productsans 14");
        w = font.getStringWidth(getName()) + 6;
        h = 12;
        x = (int) parent.getSelectedBoxX();
        y = (int) (getParent().getSelectedBoxY() + getParent().getSelectedBoxHeight());
        totalH = h;

        if (getIndex() - 1 >= 0) {
            ElementEnumChoiceProperty previous = getParent().getChoices().get(getIndex() - 1);
            y = previous.getY() + previous.getTotalH();
        }
    }

    public void onRender(int mouseX, int mouseY) {
        if (getParent().isExtended() || getParent().getAnimExpand() > 0) {
            hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;

            y = (int) (getParent().getSelectedBoxY() + getParent().getSelectedBoxHeight());

            if (getIndex() - 1 >= 0) {
                ElementEnumChoiceProperty previous = getParent().getChoices().get(getIndex() - 1);
                y = previous.getY() + previous.getTotalH();
            }

            int color = selected ? Theme.accent() : 0xff606060;
            int r = index == getParent().getChoices().size() - 1 ? 5 : 0;
            GuiUtil.drawRoundedRect(x, y, x + w, y + h, 0, 0, r, r, color);

            if (index == 0) {
                GuiUtil.drawRoundedRect(x, y, x + w, y + 1, 0, 0xff212131);
            }

            CFontRenderer font = Fonts.get("productsans 14");
            font.drawCenteredString(getName(), x + w / 2.0, y + h / 2.0 - font.getHeight() / 2.0, -1);
        }
    }

    public boolean onClick(int mouseX, int mouseY, int mouseButton) {
        if (hovered) {
            if (mouseButton == 0) {
                selected = true;
                getParent().setSelectedValue(getValue());
                return true;
            }
        }
        return false;
    }
}
