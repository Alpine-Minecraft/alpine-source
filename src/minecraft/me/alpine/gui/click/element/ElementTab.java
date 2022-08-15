package me.alpine.gui.click.element;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.GuiClick;

import java.util.ArrayList;

public class ElementTab {

    @Getter protected final GuiClick parent;

    @Getter protected final String name;
    @Getter protected final int index;

    @Getter @Setter protected boolean opened;

    @Getter @Setter protected int x;
    @Getter @Setter protected int y;
    @Getter @Setter protected int w;
    @Getter @Setter protected int h;

    public ElementTab(GuiClick parent, int index, String name) {
        this.parent = parent;
        this.name = name;
        this.index = index;
    }

    public void onInit() {

    }

    public void onClose() {

    }

    public void onRender(int mouseX, int mouseY) {

    }

    public boolean onClick(int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    public void onRelease(int mouseX, int mouseY, int state) {

    }

}
