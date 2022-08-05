package me.alpine.mod;

import lombok.Getter;
import me.alpine.util.render.GuiUtil;
import me.alpine.util.render.Position;
import net.minecraft.client.gui.ScaledResolution;

import javax.vecmath.Tuple2d;

public class ModDraggable extends Mod {
    @Getter private final Position position;
    @Getter private final Tuple2d size;
    @Getter private boolean dragged;
    @Getter private boolean hovered;

    private double diffDragX, diffDragY;

    public ModDraggable(String name, String description, EnumModCategory category, Position defaultPosition, Tuple2d defaultSize) {
        super(name, description, category);
        this.position = defaultPosition;
        this.size = defaultSize;
    }

    public void drawOutline() {
        GuiUtil.drawRectOutline(position.getDrawX(), position.getDrawY(),
                position.getDrawX() + size.x, position.getDrawY() + size.y, 0xFF000000);
    }

    public void update(int mouseX, int mouseY) {
        this.hovered = mouseX >= position.getDrawX() && mouseX <= position.getDrawX() + size.x &&
                mouseY >= position.getDrawY() && mouseY <= position.getDrawY() + size.y;

        if (dragged) {
            position.setDrawX(position.getDrawX() + diffDragX);
            position.setDrawY(position.getDrawY() + diffDragY);
        }
    }

    public void onClick(int mouseX, int mouseY, int mouseButton) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);

        if (hovered) {
            diffDragX = mouseX - position.getDrawX();
            diffDragY = mouseY - position.getDrawY();
            dragged = true;
        }
    }

    public void onUnclick(int mouseX, int mouseY, int mouseButton) {
        dragged = false;
    }
}