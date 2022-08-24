package me.alpine.mod.impl;

import lombok.Getter;
import lombok.Setter;
import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventRender2D;
import me.alpine.mod.EnumModCategory;
import me.alpine.mod.ModDraggable;
import me.alpine.mod.property.impl.ColorProperty;
import me.alpine.mod.property.impl.NumberProperty;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.GuiUtil;
import me.alpine.util.render.Position;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.input.Keyboard;

import javax.vecmath.Vector2d;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModKeystrokes extends ModDraggable {
    @Getter @Setter private List<Key> keys = new ArrayList<>();

    @Getter private final ColorProperty bgClrUnpressed = new ColorProperty("Background Color Unpressed", new Color(0, 0, 0, 60)).setRenderAlphaSlider(true);
    @Getter private final ColorProperty bgClrPressed = new ColorProperty("Background Color Pressed", new Color(0, 0, 0, 120)).setRenderAlphaSlider(true);
    @Getter private final ColorProperty txtClrUnpressed = new ColorProperty("Text Color Unpressed", new Color(255, 255, 255, 255)).setRenderAlphaSlider(true);
    @Getter private final ColorProperty txtClrPressed = new ColorProperty("Text Color Pressed", new Color(144, 255, 241, 255)).setRenderAlphaSlider(true);
    @Getter private final NumberProperty bgCornerSize = new NumberProperty("Corner Size", 1, 0, 100, 1);

    public ModKeystrokes() {
        super("Keystrokes", "Shows a selection of pressed keys", EnumModCategory.HUD, new Position(0, 0), new Vector2d(0, 0));

        this.addProperties(bgClrUnpressed, bgClrPressed, txtClrUnpressed, txtClrPressed, bgCornerSize);

        GameSettings s = mc.gameSettings;
        keys.add(new Key(s.keyBindForward.getKeyCode(), keys.size(), 1, 0, 1));
        keys.add(new Key(s.keyBindLeft.getKeyCode(), keys.size(), 0, 1, 1));
        keys.add(new Key(s.keyBindBack.getKeyCode(), keys.size(), 1, 1, 1));
        keys.add(new Key(s.keyBindRight.getKeyCode(), keys.size(), 2, 1, 1));

    }

    @EventTarget
    public void render(EventRender2D e) {

        /* Basically sets the width so that it borders the right most key and the bottom most key */ {
            double x2 = keys.stream().mapToDouble(k -> k.x + k.width).max().orElse(0);
            double y2 = keys.stream().mapToDouble(k -> k.y + k.height).max().orElse(0);
            Key rightMostKey = null;
            Key bottomMostKey = null;
            for (Key k: keys) {
                if (k.x + k.width == x2) {
                    rightMostKey = k;
                }
                if (k.y + k.height == y2) {
                    bottomMostKey = k;
                }
            }
            if (rightMostKey != null && bottomMostKey != null) {
                x2 = rightMostKey.x + rightMostKey.width;
                y2 = bottomMostKey.y + bottomMostKey.height;
            } else {
                x2 = 0;
                y2 = 0;
            }

            this.getSize().setX(x2 - getPosition().getDrawX());
            this.getSize().setY(y2 - getPosition().getDrawY());
        }

        for (Key key : keys) {
            key.render();
        }
    }

    @Getter
    @Setter
    class Key {
        private final int code;
        private final int index;
        private boolean pressed;
        private double x, y;
        private double indexX, indexY;
        private double width, height;

        private String textOverride;

        public Key(int code, int index, int indexX, int indexY, int width) {
            this.code = code;
            this.index = index;
            this.width = 25 * width;
            this.height = 25;
            this.indexX = indexX;
            this.indexY = indexY;
            this.x = getPosition().getDrawX() + indexX * (this.width + 1);
            this.y = getPosition().getDrawY() + indexY * (this.height + 1);
        }

        protected void update() {
            pressed = Keyboard.isKeyDown(code);

            this.x = getPosition().getDrawX() + indexX * (this.width + 1);
            this.y = getPosition().getDrawY() + indexY * (this.height + 1);
        }

        protected void render() {
            update();

            CFontRenderer fr = Fonts.get("productsans 19");
            Color backgroundColor = pressed ? bgClrPressed.getColor() : bgClrUnpressed.getColor();
            Color textColor = pressed ? txtClrPressed.getColor() : txtClrUnpressed.getColor();
            double roundness = (bgCornerSize.getValue() / 100.0D) * this.height;

            GuiUtil.drawRoundedRect(x, y, x + width, y + height, roundness, backgroundColor.getRGB());
            String s = textOverride != null ? textOverride : Keyboard.getKeyName(code);
            fr.drawString(s, x + (width - fr.getStringWidth(s)) / 2, y + (height - fr.getHeight()) / 2, textColor.getRGB());
        }

        protected String getName() {
            return Keyboard.getKeyName(code);
        }
    }
}