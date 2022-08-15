package me.alpine.gui.click.element;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.GuiClick;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.ColorUtil;
import me.alpine.util.render.DeltaTime;
import me.alpine.util.render.GuiUtil;
import net.minecraft.util.MathHelper;

import java.awt.*;

public class ElementTabProfiles extends ElementTab {

    @Getter @Setter private double animHover;
    @Getter @Setter private boolean hovered;

    public ElementTabProfiles(GuiClick parent, int index, String name) {
        super(parent, index, name);
    }

    @Override
    public void onInit() {
        super.onInit();

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
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public void onRender(int mouseX, int mouseY) {
        super.onRender(mouseX, mouseY);

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

    @Override
    public boolean onClick(int mouseX, int mouseY, int mouseButton) {
        if (hovered) {
            parent.setRenderedTab(this);
        }

        return super.onClick(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onRelease(int mouseX, int mouseY, int state) {
        super.onRelease(mouseX, mouseY, state);
    }
}
