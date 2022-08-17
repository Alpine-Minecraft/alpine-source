package me.alpine.mod;

import me.alpine.event.EventManager;
import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventRender2D;
import me.alpine.mod.property.impl.BooleanProperty;
import me.alpine.mod.property.impl.ColorProperty;
import me.alpine.mod.property.impl.NumberProperty;
import me.alpine.util.font.CFontRenderer;
import me.alpine.util.font.Fonts;
import me.alpine.util.render.GuiUtil;
import me.alpine.util.render.Position;

import javax.vecmath.Vector2d;
import java.awt.*;

public abstract class ModText extends ModDraggable {

    private final ColorProperty textColor = new ColorProperty("Text Color", Color.WHITE);
    private final BooleanProperty textShadow = new BooleanProperty("Text Shadow", false);
    private final BooleanProperty renderBackground = new BooleanProperty("Background", true);
    private final ColorProperty backgroundColor = new ColorProperty("Background Color", new Color(0, 0, 0, 100));
    private final NumberProperty backgroundRoundness = new NumberProperty("Background Roundness", 0, 0, 100, 1);
    private final BooleanProperty renderBorder = new BooleanProperty("Border", true);
    private final ColorProperty borderColor = new ColorProperty("Border Color", Color.WHITE);
    private final NumberProperty borderThickness = new NumberProperty("Border Thickness", 2.0, 0.1, 5.0, 0.1);


    public ModText(String name, String description, EnumModCategory category, Position defaultPosition) {
        super(name, description, category, defaultPosition, new Vector2d());

        renderBackground.addChangeListener(b -> {
            backgroundColor.setHidden(!(boolean) b);
            backgroundRoundness.setHidden(!(boolean) b);
        });

        backgroundColor.setRenderAlphaSlider(true);

        renderBorder.addChangeListener(b -> {
            borderColor.setHidden(!(boolean) b);
            borderThickness.setHidden(!(boolean) b);
        });

        addProperties(textColor, textShadow, renderBackground, backgroundColor, backgroundRoundness, renderBorder, borderColor, borderThickness);
    }

    public abstract String getText();

    @Override
    public void onRenderGui(int mouseX, int mouseY) {
        super.onRenderGui(mouseX, mouseY);
    }

    public void onAlwaysRender(EventRender2D e) {
        CFontRenderer fontRenderer = Fonts.get("productsans 19");

        /* Sets the size of the background */
        int x = (int) getPosition().getDrawX();
        int y = (int) getPosition().getDrawY();
        int paddingX = 8;
        int paddingY = 6;
        int width = fontRenderer.getStringWidth(getText()) + paddingX * 2;
        int height = fontRenderer.getHeight() + paddingY * 2;
        getSize().x = width;
        getSize().y = height;

        /* Draws the background */
        double d = Math.min(width, height) * backgroundRoundness.getValue() / 100;
        if (renderBackground.getValue()) {

            GuiUtil.drawRoundedRect(getPosition().getDrawX(), getPosition().getDrawY(),
                    getPosition().getDrawX() + width, getPosition().getDrawY() + height, d,
                    backgroundColor.getColor().getRGB());
        }
        if (renderBorder.getValue()) {
            GuiUtil.drawRoundedRectOutline(getPosition().getDrawX(), getPosition().getDrawY(),
                    getPosition().getDrawX() + width, getPosition().getDrawY() + height, borderThickness.getValue(), d,
                    borderColor.getColor().getRGB());
        }

        /* Draws the text */
        int textX = x + width / 2 - fontRenderer.getStringWidth(getText()) / 2;
        int textY = y + height / 2 - fontRenderer.getHeight() / 2;
        fontRenderer.drawString(getText(), textX, textY, textColor.getColor().getRGB(), textShadow.getValue());
    }
}
