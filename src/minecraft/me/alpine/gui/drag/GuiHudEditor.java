package me.alpine.gui.drag;

import lombok.Getter;
import lombok.Setter;
import me.alpine.Alpine;
import me.alpine.mod.ModDraggable;
import me.alpine.util.render.Position;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

@Getter @Setter
public class GuiHudEditor extends GuiScreen {
    @Getter private static final GuiHudEditor instance = new GuiHudEditor();

    private final ArrayList<ModDraggable> children;

    public GuiHudEditor() {
        children = new ArrayList<>();
    }

    @Override
    public void initGui() {
        super.initGui();
        children.clear();
        children.addAll(Alpine.getInstance().getModsManager().getDraggableMods());
        children.forEach(m -> {
            m.getPosition().setDrawX(10);
            m.getPosition().setDrawY(10);
        });
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        for (ModDraggable mod: children) {
            mod.drawOutline();
            mod.update(mouseX, mouseY);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        for (ModDraggable mod: children) {
            mod.onClick(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        for (ModDraggable mod: children) {
            mod.onUnclick(mouseX, mouseY, state);
        }
    }


    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
