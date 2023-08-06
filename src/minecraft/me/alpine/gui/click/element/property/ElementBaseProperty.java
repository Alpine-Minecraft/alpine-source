package me.alpine.gui.click.element.property;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.element.ElementMod;

public class ElementBaseProperty {
    @Getter @Setter private ElementMod parent;

    @Getter @Setter private String name;
    @Getter @Setter private int index;

    @Getter @Setter protected int x;
    @Getter @Setter protected int y;
    @Getter @Setter protected int w;
    @Getter @Setter protected int h;
    @Getter @Setter protected int totalH;

    @Getter @Setter protected double scrollOffset;

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

    public void onClose() {}

    public void onRender(int mouseX, int mouseY) {}

    public boolean onClick(int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    public void onRelease(int mouseX, int mouseY, int state) {}

    public void onKey(char typedchar, int keyCode) {}

    public final boolean updateTotalHeight(boolean hidden) {
        if (hidden) {
            totalH = 0;
        } else {
            totalH = h + 3;
        }
        return hidden;
    }

    public void updatePositionY() {
        y = getParent().getParent().getY() + getParent().getH() + 8 - (int) getScrollOffset();
        totalH = h + 3;

        if (getIndex() - 1 >= 0) {
            ElementBaseProperty previous = getParent().getChildren().get(getIndex() - 1);
            y = previous.getY() + previous.getTotalH();
        }
    }
}
