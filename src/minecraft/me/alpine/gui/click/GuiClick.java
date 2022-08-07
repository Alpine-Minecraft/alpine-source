package me.alpine.gui.click;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.element.ElementCategory;
import me.alpine.mod.EnumModCategory;
import me.alpine.util.render.DeltaTime;
import me.alpine.util.render.Easings;
import me.alpine.util.render.GuiUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

import java.io.IOException;
import java.util.ArrayList;

public class GuiClick extends GuiScreen {
    @Getter private static final GuiClick instance = new GuiClick();

    protected double onOpenAnimation = 0;
    @Getter @Setter private EnumModCategory renderedCategory;
    @Getter private final ArrayList<ElementCategory> children;
    @Getter @Setter private int bgWidth;
    @Getter @Setter private int bgHeight;
    @Getter @Setter private int bgX;
    @Getter @Setter private int bgY;

    public GuiClick() {
        children = new ArrayList<>();

        for (int i = 0; i < EnumModCategory.values().length; i++) {
            EnumModCategory category = EnumModCategory.values()[i];
            children.add(new ElementCategory(this, category.toString(), category, i));
        }
    }

    @Override
    public void initGui() {
        if (renderedCategory == null) {
            renderedCategory = EnumModCategory.values()[0];
        }

        this.onOpenAnimation = 0;

        this.bgWidth = this.width / 3;
        this.bgHeight = this.height / 2;
        this.bgX = this.width / 2 - bgWidth / 2;
        this.bgY = this.height / 2 - bgHeight / 2;

        children.forEach(ElementCategory::onInit);
    }

    @Override
    public void onGuiClosed() {
        children.forEach(ElementCategory::onClose);

        super.onGuiClosed();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.onOpenAnimation += DeltaTime.get() * 0.002;
        this.onOpenAnimation = Math.min(1, this.onOpenAnimation);
        double animEased = Easings.easeOutBack(this.onOpenAnimation);

        GlStateManager.pushMatrix();
        GlStateManager.translate(this.width / 2.0D, this.height / 2.0D, 0);
        GlStateManager.scale(animEased, animEased, 1);
        GlStateManager.translate(-this.width / 2.0D, -this.height / 2.0D, 0);

        GuiUtil.drawRoundedRect(bgX - 0.5, bgY - 0.5, bgX + bgWidth + 0.5, bgY + bgHeight + 0.5, 8, 0xFF000000);
        GuiUtil.drawRoundedRect(bgX, bgY, bgX + bgWidth, bgY + bgHeight, 8, 0xFFFFFFFF);


        for (ElementCategory category : children) {
            category.setOpened(category.getCategory() == renderedCategory);
            category.onRender(mouseX, mouseY);
        }

        GlStateManager.popMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        children.forEach(e -> e.onClick(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        children.forEach(e -> e.onRelease(mouseX, mouseY, state));
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
