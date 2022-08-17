package me.alpine.gui.click;

import lombok.Getter;
import lombok.Setter;
import me.alpine.Alpine;
import me.alpine.gui.click.element.ElementTab;
import me.alpine.gui.click.element.ElementTabCategory;
import me.alpine.gui.click.element.ElementTabProfiles;
import me.alpine.mod.EnumModCategory;
import me.alpine.util.font.Fonts;
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
    @Getter @Setter private ElementTab renderedTab;
    @Getter private final ArrayList<ElementTab> children;
    @Getter @Setter private int bgWidth;
    @Getter @Setter private int bgHeight;
    @Getter @Setter private int bgX;
    @Getter @Setter private int bgY;

    @Getter @Setter private int modsX;
    @Getter @Setter private int modsWidth;


    public GuiClick() {
        children = new ArrayList<>();

        for (int i = 0; i < EnumModCategory.values().length; i++) {
            EnumModCategory category = EnumModCategory.values()[i];
            children.add(new ElementTabCategory(this, category.getFormattedName(), category, i));
        }

        children.add(new ElementTabProfiles(this, children.size(), "Profiles"));
    }

    @Override
    public void initGui() {
        if (renderedTab == null) {
            renderedTab = children.get(0);
        }

        this.onOpenAnimation = 0;

        this.bgWidth = this.width / 3;
        this.bgHeight = this.height / 2;
        this.bgX = this.width / 2 - bgWidth / 2;
        this.bgY = this.height / 2 - bgHeight / 2;

        children.forEach(ElementTab::onInit);

        final String s = Alpine.getInstance().getName() + " v" + Alpine.getInstance().getVersion();
        int buttonsWidth = children.stream().mapToInt(e -> e.getW() + 3).sum() + Fonts.get("nunito semibold 22").getStringWidth(s) + 8;
        this.bgWidth = Math.max(this.bgWidth, buttonsWidth + 3);
        this.bgX = this.width / 2 - bgWidth / 2;

        children.forEach(ElementTab::onInit);
    }

    @Override
    public void onGuiClosed() {
        children.forEach(ElementTab::onClose);

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

        /* Outline of the background */
        GuiUtil.drawRoundedRect(bgX - 0.5, bgY - 0.5, bgX + bgWidth + 0.5, bgY + bgHeight + 0.5, 8, 0xFF000000);
        /* General background */
        GuiUtil.drawRoundedRect(bgX, bgY, bgX + bgWidth, bgY + bgHeight, 8, Theme.foreground());

        final int barY = getChildren().get(0).getY() + getChildren().get(0).getH() + 2;
        GuiUtil.drawRect(bgX, barY, bgX + bgWidth, barY - 1, 0xff579E9E);
        /* Background of modules list */
        GuiUtil.drawRoundedRect(bgX, barY, modsX + modsWidth + 5, bgY + bgHeight, 0, 0, 8, 0, Theme.background());
        final String s = Alpine.getInstance().getName() + " v" + Alpine.getInstance().getVersion();
        Fonts.get("nunito semibold 22").drawString(s, (bgX + bgWidth - Fonts.get("nunito semibold 22").getStringWidth(s) - 3) * animEased, bgY + 3, 0xFFFFFFFF);


        for (ElementTab tab : children) {
            tab.setOpened(tab == renderedTab);
            tab.onRender(mouseX, mouseY);
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
