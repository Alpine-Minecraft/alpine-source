package me.alpine.mod;

import lombok.Getter;
import me.alpine.event.impl.EventRender2D;
import me.alpine.util.render.GuiUtil;
import me.alpine.util.render.Position;

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

    public void onRenderGui(int mouseX, int mouseY) {
        this.hovered = mouseX >= position.getDrawX() && mouseX <= position.getDrawX() + size.x &&
                mouseY >= position.getDrawY() && mouseY <= position.getDrawY() + size.y;

        if (dragged) {
            position.setDrawX(mouseX - diffDragX);
            position.setDrawY(mouseY - diffDragY);
        }
    }

    public void onClick(int mouseX, int mouseY, int mouseButton) {
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
