package me.alpine.gui.click.element;

import lombok.Getter;
import lombok.Setter;
import me.alpine.mod.Mod;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.ColorUtil;
import me.alpine.util.render.DeltaTime;
import me.alpine.util.render.Easings;
import me.alpine.util.render.GuiUtil;
import net.minecraft.util.MathHelper;

import java.awt.*;
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

    @Getter @Setter private double animEnable;

    public ElementMod(ElementCategory parent, Mod mod, int index) {
        this.parent = parent;
        this.name = mod.getName();
        this.mod = mod;
        this.index = index;
    }

    public void onInit() {
        x = parent.getParent().getBgX() + 5;
        y = parent.getY() + 8 + parent.getH();
        w = (int) (getParent().getParent().getBgWidth() / 3.5);
        h = 16;

        animEnable = mod.isEnabled() ? 1 : 0;

        if (index - 1 >= 0) {
            ElementMod prev = parent.getChildren().get(index - 1);
            y = prev.getY() + prev.getH() + 3;
        }
    }

    public void onClose() {

    }

    public void onRender(int mouseX, int mouseY) {
        if (parent.isOpened()) {

            GuiUtil.drawRoundedRect(x, y, x + w, y + h, 3, 0xFF252535);

            animEnable += (DeltaTime.get() * 0.01) * (mod.isEnabled() ? 1 : -1);
            animEnable = MathHelper.clamp_double(animEnable, 0, 1);

            int radioButtonX = x + w - h / 4 - 3;
            int radioButtonY = y + h / 2;
            Color radioButtonColor = ColorUtil.interpolate(Color.WHITE, new Color(0xFF3080ed), animEnable);

            if (animEnable > 0.0D) {
                GuiUtil.drawCircle(radioButtonX, radioButtonY, (h / 6.5) * Easings.easeInOutExponential(animEnable), radioButtonColor.getRGB());
            }
            GuiUtil.drawCircleOutline(radioButtonX, radioButtonY, h / 4.0, 1, radioButtonColor.getRGB());


            final CFontRenderer fr = Fonts.get("productsans 19");
            final double textY = y + h / 2.0 - fr.getHeight() / 2.0;
            fr.drawString(name, x + 2, textY, -1);
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
