package net.minecraft.client.renderer;

import net.minecraft.client.Minecraft;

import java.util.concurrent.Callable;

class EntityRenderer2 implements Callable {
    final EntityRenderer field_90025_c;
    
    EntityRenderer2(EntityRenderer p_i46419_1_) {
        this.field_90025_c = p_i46419_1_;
    }

    public String call() throws Exception {
        return Minecraft.getMinecraft().currentScreen.getClass().getCanonicalName();
    }
}
