package me.alpine.gui.click.element;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.GuiClick;
import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.ColorUtil;
import me.alpine.util.render.DeltaTime;
import me.alpine.util.render.GuiUtil;
import net.minecraft.util.MathHelper;

import java.awt.*;
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

    @Getter @Setter private double animHover;
    @Getter @Setter private boolean hovered;

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

        animHover = 0;

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
        this.hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;

        if (hovered) {
            animHover += DeltaTime.get() * 0.008;
        } else {
            animHover -= DeltaTime.get() * 0.008;
        }
        animHover = Math.max(0, Math.min(1, animHover));
        animHover = MathHelper.clamp_double(animHover, 0, 1);


        Color colorRect = ColorUtil.interpolate(new Color(0x579E9E), new Color(0xDCF9F3), animHover);
        Color colorText = ColorUtil.interpolate(new Color(0xDCF9F3), new Color(0x001524), animHover);

        GuiUtil.drawRoundedRect(x - 0.5, y - 0.5, x + w + 0.5, y + h + 0.5, 2, 0xFF000000);
        GuiUtil.drawRoundedRect(x, y, x + w, y + h, 2, colorRect.getRGB());
        Fonts.get("productsans 19").drawCenteredString(name, x + w / 2.0D, y + 3, colorText.getRGB());
    }

    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (hovered) {
            parent.setRenderedCategory(this.getCategory());
        }

        children.forEach(e -> e.onClick(mouseX, mouseY, mouseButton));
    }

    public void onRelease(int mouseX, int mouseY, int state) {
        children.forEach(e -> e.onRelease(mouseX, mouseY, state));
    }
}
