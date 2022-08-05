package me.alpine.mod.impl;

import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventClick;
import me.alpine.event.impl.EventRender2D;
import me.alpine.mod.EnumModCategory;
import me.alpine.mod.Mod;
import me.alpine.util.render.GuiUtil;
import me.alpine.util.render.shader.BlurUtil;

import java.util.ArrayList;
import java.util.List;

public class ModCPS extends Mod {

    private final List<Long> leftClicks = new ArrayList<>();
    private final List<Long> rightClicks = new ArrayList<>();

    public ModCPS() {
        super("CPS", "Displays the current CPS", EnumModCategory.HUD);

        this.setEnabled(true);
    }

    @EventTarget
    public void onClick(EventClick e) {
        if (e.getButton() == 0) {
            leftClicks.add(System.currentTimeMillis());
        } else if (e.getButton() == 1) {
            rightClicks.add(System.currentTimeMillis());
        }
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        BlurUtil.blurArea(() ->
        {
//            GuiUtil.drawRect(10, 10, 100, 100,-1);
            GuiUtil.drawCircle(100, 100, 10, -1);
        });

        int x = e.getSr().getScaledWidth() - 100;
        int y = 2;
        leftClicks.removeIf(l -> System.currentTimeMillis() - l > 1000);
        rightClicks.removeIf(l -> System.currentTimeMillis() - l > 1000);
        String cps = String.format("CPS: %s | %s", leftClicks.size(), rightClicks.size());
        mc.fontRendererObj.drawStringWithShadow(cps, x, y, 0x00d9ff);
    }
}
