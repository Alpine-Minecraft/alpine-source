package me.alpine.gui.click.element.property.multicombo;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.element.ElementMod;
import me.alpine.gui.click.element.property.ElementBaseProperty;
import me.alpine.mod.property.impl.ComboProperty;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.DeltaTime;
import me.alpine.util.render.Easings;
import me.alpine.util.render.GuiUtil;
import me.alpine.util.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class ElementComboProperty extends ElementBaseProperty {
    @Getter private final ComboProperty property;
    @Getter @Setter private ArrayList<ElementComboChoiceProperty> choices;
    @Getter @Setter private ArrayList<String> selectedChoices;

    @Getter @Setter private boolean hovered;
    @Getter @Setter private boolean extended;

    @Getter @Setter private double animExpand;

    @Getter @Setter private double scroll;

    @Getter @Setter private double selectedBoxWidth;
    @Getter @Setter private double selectedBoxHeight;
    @Getter @Setter private double selectedBoxX;
    @Getter @Setter private double selectedBoxY;

    public ElementComboProperty(ElementMod parent, ComboProperty property, int index) {
        setParent(parent);
        setName(property.getName());
        setIndex(index);

        this.property = property;
        this.selectedChoices = property.getSelectedValues();

        this.choices = new ArrayList<>();
        property.getValues().forEach(value -> {
            ElementComboChoiceProperty choice = new ElementComboChoiceProperty(this, value, choices.size());
            choices.add(choice);
        });
    }

    @Override
    public void onInit() {
        super.onInit();
        choices.forEach(ElementComboChoiceProperty::onInit);

        selectedBoxWidth = 0;
        for (ElementComboChoiceProperty choice : choices) {
            if (choice.getW() > selectedBoxWidth) {
                selectedBoxWidth = choice.getW() + 6;
            }
        }
        selectedBoxHeight = choices.get(0).getFont().getHeight() + 6;
        selectedBoxX = this.x + this.w - selectedBoxWidth - 10;
        selectedBoxY = this.y + this.h / 2.0 - selectedBoxHeight / 2.0;

        for (ElementComboChoiceProperty choice : choices) {
            choice.setW((int) selectedBoxWidth);
        }
    }

    @Override
    public void onRender(int mouseX, int mouseY) {
        if (updateTotalHeight(property.isHidden())) {
            return;
        }

        if (getParent().isOpened()) {
            hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;

            y = getParent().getParent().getY() + getParent().getH() + 8 - (int) getScrollOffset();

            if (getIndex() - 1 >= 0) {
                ElementBaseProperty previous = getParent().getChildren().get(getIndex() - 1);
                y = previous.getY() + previous.getTotalH();
            }
            selectedBoxY = this.y + this.h / 2.0 - selectedBoxHeight / 2.0;

            GuiUtil.drawRoundedRect(x, y, x + w, y + h, 5, 0xFF151525);

            CFontRenderer font = Fonts.get("productsans 14");
            font.drawString(getName(), x + 3, y + h / 2.0 - font.getHeight() / 2.0, -1);

            double r = extended ? 0 : 5;
            GuiUtil.drawRoundedRect(selectedBoxX, selectedBoxY,
                    selectedBoxX + selectedBoxWidth, selectedBoxY + selectedBoxHeight,
                    5, 5, r, r, 0xff6a6a6a);
            choices.get(0).getFont().drawCenteredString(selectedChoices.size() + "/" + choices.size(),
                    selectedBoxX + selectedBoxWidth / 2,
                    selectedBoxY + selectedBoxHeight / 2.0 - 1, -1);

            animExpand += (extended ? 1 : -1) * DeltaTime.get() * 0.005;
            animExpand = MathHelper.clamp_double(animExpand, 0, 1);
            double animExpandEased = Easings.easeInOutExponential(animExpand);

            double arrowX = selectedBoxX + selectedBoxWidth + 5;
            double arrowY = selectedBoxY + selectedBoxHeight / 2.0 - 1;

            GlStateManager.pushMatrix();
            GlStateManager.translate(arrowX, arrowY, 0);
            GlStateManager.rotate((float) (180 * animExpandEased) + 180, 0.0F, 0.0F, 1.0F);

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glLineWidth(2f);
            RenderUtil.glSetColor(0xFF3080ed);

            GL11.glBegin(GL11.GL_LINE_STRIP);
            {
                GL11.glVertex2d(2, 2);
                GL11.glVertex2d(0, -2);
                GL11.glVertex2d(-2, 2);
            }
            GL11.glEnd();

            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translate(selectedBoxX, selectedBoxY + selectedBoxHeight, 0);
            GlStateManager.scale(1, animExpandEased, 1);
            GlStateManager.translate(-selectedBoxX, -(selectedBoxY + selectedBoxHeight), 0);

            choices.forEach(choice -> {
                choice.onRender(mouseX, mouseY);
                choice.setSelected(selectedChoices.contains(choice.getValue()));
            });

            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean onClick(int mouseX, int mouseY, int mouseButton) {
        if (updateTotalHeight(property.isHidden())) {
            return false;
        }

        // if hovering selected box
        for (ElementComboChoiceProperty choice : choices) {
            if (choice.onClick(mouseX, mouseY, mouseButton)) {
                return true;
            }
        }

        if (hovered) {
            extended = !extended;
            return true;
        }
        return false;
    }

    @Override
    public void onRelease(int mouseX, int mouseY, int state) {
    }
}
