package me.alpine.gui.click.element.elementproperty;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.element.ElementMod;
import me.alpine.mod.property.impl.EnumProperty;
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

public class ElementEnumProperty extends ElementBaseProperty {
    @Getter private final EnumProperty property;
    @Getter @Setter private String selectedValue;
    @Getter @Setter private ArrayList<ElementEnumChoiceProperty> choices;

    @Getter @Setter private boolean hovered;
    @Getter @Setter private boolean extended;

    @Getter @Setter private double animExpand;

    @Getter @Setter private double scroll;

    @Getter @Setter private double selectedBoxWidth;
    @Getter @Setter private double selectedBoxHeight;
    @Getter @Setter private double selectedBoxX;
    @Getter @Setter private double selectedBoxY;

    public ElementEnumProperty(ElementMod parent, EnumProperty property, int index) {
        setParent(parent);
        setName(property.getName());
        setIndex(index);

        this.property = property;
        this.selectedValue = property.getSelectedValue();

        if (true) {
            this.choices = new ArrayList<>();
            property.getValues().forEach(value -> {
                ElementEnumChoiceProperty choice = new ElementEnumChoiceProperty(this, value, choices.size());
                choices.add(choice);
            });
        }
    }

    @Override
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
        if (true) {
            choices.forEach(ElementEnumChoiceProperty::onInit);
        }

        selectedBoxWidth = 0;
        for (ElementEnumChoiceProperty choice : choices) {
            if (choice.getW() > selectedBoxWidth) {
                selectedBoxWidth = choice.getW() + 6;
            }
        }
        selectedBoxHeight = choices.get(0).getFont().getHeight() + 6;
        selectedBoxX = this.x + this.w - selectedBoxWidth - 10;
        selectedBoxY = this.y + this.h / 2.0 - selectedBoxHeight / 2.0;

        for (ElementEnumChoiceProperty choice : choices) {
            choice.setW((int) selectedBoxWidth);
        }
    }

    @Override
    public void onRender(int mouseX, int mouseY) {
        if (getParent().isOpened()) {
            hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;

            GuiUtil.drawRoundedRect(x, y, x + w, y + h, 5, 0xFF151525);

            CFontRenderer font = Fonts.get("productsans 14");
            font.drawString(getName(), x + 3, y + h / 2.0 - font.getHeight() / 2.0, -1);

            if (true) {

                double r = extended ? 0 : 5;
                GuiUtil.drawRoundedRect(selectedBoxX, selectedBoxY,
                        selectedBoxX + selectedBoxWidth, selectedBoxY + selectedBoxHeight,
                        5, 5, r, r, 0xff6a6a6a);
                choices.get(0).getFont().drawCenteredString(selectedValue, selectedBoxX + selectedBoxWidth / 2, selectedBoxY + selectedBoxHeight / 2.0 - 1, -1);

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
                    choice.setSelected(choice.getValue().equals(selectedValue));
                });

                GlStateManager.popMatrix();
            }
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        // if hovering selected box
        if (mouseX >= selectedBoxX && mouseX <= selectedBoxX + selectedBoxWidth && mouseY >= selectedBoxY && mouseY <= selectedBoxY + selectedBoxHeight) {
            extended = !extended;
        }
        for (ElementEnumChoiceProperty choice : choices) {
            choice.onClick(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onRelease(int mouseX, int mouseY, int state) {
    }
}
