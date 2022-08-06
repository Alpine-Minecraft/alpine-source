package me.alpine.gui.click.element;

import lombok.Getter;
import lombok.Setter;
import me.alpine.mod.Mod;
import me.alpine.mod.property.BaseProperty;
import me.alpine.util.font.Fonts;

import java.util.ArrayList;

public class ElementMod {
    @Getter private final ElementCategory parent;

    @Getter private final String name;
    @Getter private final Mod mod;
    @Getter private final int index;

    @Getter @Setter private boolean opened;

    @Getter private final ArrayList<ElementProperty> children = new ArrayList<>();

    @Getter @Setter private int x;
    @Getter @Setter private int y;
    @Getter @Setter private int w;
    @Getter @Setter private int h;

    public ElementMod(ElementCategory parent, Mod mod, int index) {
        this.parent = parent;
        this.name = mod.getName();
        this.mod = mod;
        this.index = index;

        for (int i = 0; i < mod.getProperties().size(); i++) {
            BaseProperty property = mod.getProperties().get(i);
//            children.add(new ElementProperty(this, property, i));
        }
    }

    public void onInit() {
        x = parent.getParent().getBgX() + 3;
        y = parent.getY() + 3 + parent.getH() + (index * (Fonts.get("productsans 19").getHeight() + 4));
        w = Fonts.get("productsans 19").getStringWidth(name) + 8;
        h = Fonts.get("productsans 19").getHeight() + 4;
    }

    public void onClose() {

    }

    public void onRender(int mouseX, int mouseY) {
        if (parent.isOpened()) {


            Fonts.get("productsans 19").drawString(name, x, y, mod.isEnabled() ? 0xFF22AA22 : 0xFF000000);
        }
    }

    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (!parent.isOpened()) {
            return;
        }

        if (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
            if (mouseButton == 0) {
                mod.toggle();
            } else if (mouseButton == 1) {
                parent.setSelectedMod(this);
            }
        }
    }

    public void onRelease(int mouseX, int mouseY, int state) {

    }
}
