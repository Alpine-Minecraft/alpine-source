package me.alpine.gui.click.element;

import lombok.Getter;
import lombok.Setter;
import me.alpine.Alpine;
import me.alpine.gui.click.GuiClick;
import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.GuiUtil;

import java.util.ArrayList;

public class ElementCategory {

    @Getter private final GuiClick parent;

    @Getter private final String name;
    @Getter private final EnumModCategory category;
    @Getter private final int index;

    @Getter @Setter private boolean opened;

    @Getter private final ArrayList<ElementMod> children = new ArrayList<>();
    @Getter @Setter private ElementMod selectedMod;
    @Getter @Setter private int x;
    @Getter @Setter private int y;
    @Getter @Setter private int w;
    @Getter @Setter private int h;

    public ElementCategory(GuiClick parent, String name, EnumModCategory category, int index) {
        this.parent = parent;
        this.name = name;
        this.category = category;
        this.index = index;

        for (int i = 0; i < category.getMods().size(); i++) {
            Mod mod = category.getMods().get(i);
            children.add(new ElementMod(this, mod, i));
        }
    }

    public void onInit() {
        CFontRenderer font = Fonts.get("productsans 19");

        x = parent.getBgX() + 3;
        y = parent.getBgY() + 3;
        w = font.getStringWidth(name) + 8;
        h = font.getHeight() + 4;

        if (index - 1 >= 0) {
            ElementCategory prev = parent.getChildren().get(index - 1);
            x = prev.getX() + prev.getW() + 3;
        }

        children.forEach(ElementMod::onInit);
    }

    public void onClose() {}

    public void onRender(int mouseX, int mouseY) {
        children.forEach(e -> {
            e.setOpened(e == selectedMod);
            e.onRender(mouseX, mouseY);
        });

        int colorRect = opened ? 0xFF3080ed : 0xFFFFFFFF;
        int colorText = opened ? 0xFFFFFFFF : 0xFF000000;

        GuiUtil.drawRoundedRect(x - 0.5, y - 0.5, x + w + 0.5, y + h + 0.5, 2, 0xFF000000);
        GuiUtil.drawRoundedRect(x, y, x + w, y + h, 2, colorRect);
        Fonts.get("productsans 19").drawCenteredString(name, x + w / 2.0D, y + 3, colorText);
    }

    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
            parent.setRenderedCategory(this.getCategory());
        }

        children.forEach(e -> e.onClick(mouseX, mouseY, mouseButton));
    }

    public void onRelease(int mouseX, int mouseY, int state) {}
}
