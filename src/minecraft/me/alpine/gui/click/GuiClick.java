package me.alpine.gui.click;

import lombok.Getter;
import me.alpine.util.render.DeltaTime;
import me.alpine.util.render.Easings;
import me.alpine.util.render.GuiUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

import java.io.IOException;

public class GuiClick extends GuiScreen {
    @Getter private static final GuiClick instance = new GuiClick();

    protected double onOpenAnimation = 0;

    @Override
    public void initGui() {
        super.initGui();
        this.onOpenAnimation = 0;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.onOpenAnimation += DeltaTime.get() * 0.002;
        this.onOpenAnimation = Math.min(1, this.onOpenAnimation);
        double animEased = Easings.easeOutBack(this.onOpenAnimation);

        int bgWidth = this.width / 3;
        int bgHeight = this.height / 2;
        int bgX = this.width / 2 - bgWidth / 2;
        int bgY = this.height / 2 - bgHeight / 2;

        GlStateManager.pushMatrix();
        GlStateManager.translate(this.width / 2.0D, this.height / 2.0D, 0);
        GlStateManager.scale(animEased, animEased, 1);
        GlStateManager.translate(-this.width / 2.0D, -this.height / 2.0D, 0);
//        GuiUtil.drawFrostedGlassLight(bgX, bgY, bgX + bgWidth, bgY + bgHeight, 8);
        GuiUtil.drawRoundedRect(bgX, bgY, bgX + bgWidth, bgY + bgHeight, 8, 0xE0FFFFFF);
        GlStateManager.popMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
