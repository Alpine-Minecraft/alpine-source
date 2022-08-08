package me.alpine.gui.click.element.property;

import lombok.Getter;
import lombok.Setter;
import me.alpine.gui.click.element.ElementMod;

public class ElementBaseProperty {
    @Getter @Setter private ElementMod parent;

    @Getter @Setter private String name;
    @Getter @Setter private int index;

    @Getter @Setter protected int x;
    @Getter @Setter protected int y;
    @Getter @Setter protected int w;
    @Getter @Setter protected int h;
    @Getter @Setter protected int totalH;

    public void onInit() {}

    public void onClose() {}

    public void onRender(int mouseX, int mouseY) {}

    public void onClick(int mouseX, int mouseY, int mouseButton) {}

    public void onRelease(int mouseX, int mouseY, int state) {}
}
