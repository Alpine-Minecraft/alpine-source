package me.alpine.gui.click.element;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.GuiClick;
import me.alpine.gui.click.Theme;
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

public class ElementTabCategory extends ElementTab {

    @Getter private final EnumModCategory category;

    @Getter private final ArrayList<ElementMod> children = new ArrayList<>();
    @Getter @Setter private ElementMod selectedMod;

    @Getter @Setter private double animHover;
    @Getter @Setter private double animSelected;
    @Getter @Setter private boolean hovered;

    public ElementTabCategory(GuiClick parent, String name, EnumModCategory category, int index) {
        super(parent, index, name);
        this.category = category;

        for (int i = 0; i < category.getMods().size(); i++) {
            Mod mod = category.getMods().get(i);
            children.add(new ElementMod(this, mod, i));
        }
    }

    @Override
    public void onInit() {
        CFontRenderer font = Fonts.get("productsans 19");

        x = parent.getBgX() + 3;
        y = parent.getBgY() + 3;
        w = font.getStringWidth(name) + 8;
        h = font.getHeight() + 4;


        if (index - 1 >= 0) {
            ElementTab prev = parent.getChildren().get(index - 1);
            x = prev.getX() + prev.getW() + 3;
        }
        animHover = 0;

        children.forEach(ElementMod::onInit);
    }

    @Override
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

        if (getParent().getRenderedTab() == this) {
            animSelected += DeltaTime.get() * 0.008;
        } else {
            animSelected -= DeltaTime.get() * 0.008;
        }
        animSelected = Math.max(0, Math.min(1, animSelected));
        animSelected = MathHelper.clamp_double(animSelected, 0, 1);

        Color colorText = ColorUtil.interpolate(new Color(0xDCF9F3), new Color(0x001524), animHover);

        Color colorBg1 = ColorUtil.interpolate(new Color(Theme.background()), new Color(0x579E9E), animSelected);
        Color colorBg2 = ColorUtil.interpolate(colorBg1, new Color(Theme.accent()), animHover);


        GuiUtil.drawRoundedRect(x - 0.5, y - 0.5, x + w + 0.5, y + h + 0.5, 2, 0xFF000000);
        GuiUtil.drawRoundedRect(x, y, x + w, y + h, 2, colorBg2.getRGB());
        Fonts.get("productsans 19").drawCenteredString(name, x + w / 2.0D, y + 3, colorText.getRGB());
    }

    @Override
    public boolean onClick(int mouseX, int mouseY, int mouseButton) {
        if (hovered) {
            parent.setRenderedTab(this);
        }

        children.forEach(e -> e.onClick(mouseX, mouseY, mouseButton));
        return false;
    }

    @Override
    public void onRelease(int mouseX, int mouseY, int state) {
        children.forEach(e -> e.onRelease(mouseX, mouseY, state));
    }

    @Override
    public void onKey(char typedChar, int keyCode) {
        children.forEach(e -> e.onKey(typedChar, keyCode));
    }
}
