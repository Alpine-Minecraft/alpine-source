package me.alpine.gui.click.element;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.Theme;
import me.alpine.gui.click.element.property.*;
import me.alpine.gui.click.element.property.color.ElementColorProperty;
import me.alpine.gui.click.element.property.multicombo.ElementComboProperty;
import me.alpine.gui.click.element.property.singlecombo.ElementEnumProperty;
import me.alpine.mod.Mod;
import me.alpine.mod.property.BaseProperty;
import me.alpine.mod.property.impl.*;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.*;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class ElementMod {
    @Getter private final ElementTabCategory parent;

    @Getter private final String name;
    @Getter private final Mod mod;
    @Getter private final int index;
    @Getter private final ArrayList<ElementBaseProperty> children = new ArrayList<>();
    @Getter @Setter private boolean opened;
    @Getter @Setter private ElementBaseProperty selectedProperty;

    @Getter @Setter private int x;
    @Getter @Setter private int y;
    @Getter @Setter private int w;
    @Getter @Setter private int h;

    @Getter @Setter private double animEnable;

    @Getter @Setter private double currentScroll;
    @Getter @Setter private double targetScroll;

    public ElementMod(ElementTabCategory parent, Mod mod, int index) {
        this.parent = parent;
        this.name = mod.getName();
        this.mod = mod;
        this.index = index;

        for (int i = 0; i < mod.getProperties().size(); i++) {
            BaseProperty property = mod.getProperties().get(i);
            addProperty(property);
        }
    }

    public void addProperty(BaseProperty property) {
        if (property instanceof BooleanProperty) {
            children.add(new ElementBooleanProperty(this, (BooleanProperty) property, children.size()));
        } else if (property instanceof NumberProperty) {
            children.add(new ElementNumberProperty(this, (NumberProperty) property, children.size()));
        } else if (property instanceof EnumProperty) {
            children.add(new ElementEnumProperty(this, (EnumProperty) property, children.size()));
        } else if (property instanceof ComboProperty) {
            children.add(new ElementComboProperty(this, (ComboProperty) property, children.size()));
        } else if (property instanceof ColorProperty) {
            children.add(new ElementColorProperty(this, (ColorProperty) property, children.size()));
        } else if (property instanceof TextFieldProperty) {
            children.add(new ElementTextFieldProperty(this, (TextFieldProperty) property, children.size()));
        } else if (property instanceof KeybindProperty) {
            children.add(new ElementKeybindProperty(this, (KeybindProperty) property, children.size()));
        }
    }

    public void onInit() {
        x = parent.getParent().getBgX() + 5;
        y = parent.getY() + 8 + parent.getH();
        w = (int) (getParent().getParent().getBgWidth() / 3.5);
        h = 16;

        getParent().getParent().setModsX(x);
        getParent().getParent().setModsWidth(w);

        animEnable = mod.isEnabled() ? 1 : 0;

        if (index - 1 >= 0) {
            ElementMod prev = parent.getChildren().get(index - 1);
            y = prev.getY() + prev.getH() + 3;
        }

        children.forEach(ElementBaseProperty::onInit);
    }

    public void onClose() {
        children.forEach(ElementBaseProperty::onClose);
    }

    public void onRender(int mouseX, int mouseY) {
        if (parent.isOpened()) {

            GuiUtil.drawRoundedRect(x, y, x + w + (isOpened() ? 6 : 0), y + h, 3, Theme.foreground());

            animEnable += (DeltaTime.get() * 0.01) * (mod.isEnabled() ? 1 : -1);
            animEnable = MathHelper.clamp_double(animEnable, 0, 1);

            double testX = x + w + 5;
            double testY = getParent().getY() + getParent().getH() + 2;
            double testX2 = getParent().getParent().getBgX() + getParent().getParent().getBgWidth();
            double testY2 = getParent().getParent().getBgY() + getParent().getParent().getBgHeight();

            if (mouseX > testX && mouseX < testX2 && mouseY > testY && mouseY < testY2) {
                if (Mouse.hasWheel()) {
                    int wheel = Mouse.getDWheel();
                    if (wheel < 0) {
                        targetScroll += 10;
                    } else if (wheel > 0) {
                        targetScroll -= 10;
                    }
                }
            }

            targetScroll = MathHelper.clamp_double(targetScroll, 0, Math.max(0, children.stream().mapToInt(ElementBaseProperty::getTotalH).sum() - (testY2 - testY) + 10));
            currentScroll += (targetScroll - currentScroll) * DeltaTime.get() * 0.01;


            int radioButtonX = x + w - h / 4 - 3;
            int radioButtonY = y + h / 2;
            Color radioButtonColor = ColorUtil.interpolate(Color.WHITE, new Color(Theme.accent()), animEnable);

            if (animEnable > 0.0D) {
                GuiUtil.drawCircle(radioButtonX, radioButtonY, (h / 6.5) * Easings.easeInOutExponential(animEnable), radioButtonColor.getRGB());
            }
            GuiUtil.drawCircleOutline(radioButtonX, radioButtonY, h / 4.0, 1, radioButtonColor.getRGB());


            final CFontRenderer fr = Fonts.get("productsans 19");
            final double textY = y + h / 2.0 - fr.getHeight() / 2.0;
            fr.drawString(name, x + 2, textY, -1);

            RenderUtil.prepareScissorBox(testX, testY, testX2 - testX, testY2 - testY);
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            /* renders children from last to first so that combo settings overlap the next setting */
            for (int i = children.size() - 1; i >= 0; i--) {
                children.get(i).onRender(mouseX, mouseY);
                children.get(i).setScrollOffset(currentScroll);
            }
            GL11.glDisable(GL11.GL_SCISSOR_TEST);

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

        double testY = getParent().getY() + getParent().getH() + 2;
        double testY2 = getParent().getParent().getBgY() + getParent().getParent().getBgHeight();

        for (ElementBaseProperty child: children) {
            if (child.getY() + child.getTotalH() < testY) continue;
            if (child.getY() > testY2) continue;

            if (child.onClick(mouseX, mouseY, mouseButton)) {
                return;
            }
        }
//        children.forEach(e -> e.onClick(mouseX, mouseY, mouseButton));
    }

    public void onRelease(int mouseX, int mouseY, int state) {
        children.forEach(e -> e.onRelease(mouseX, mouseY, state));
    }

    public void onKey(char key, int keyCode) {
        children.forEach(e -> e.onKey(key, keyCode));
    }
}
