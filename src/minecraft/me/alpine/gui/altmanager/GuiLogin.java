package me.alpine.gui.altmanager;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import me.alpine.util.render.GuiUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Session;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class GuiLogin extends GuiScreen {
    CompletableFuture<MicrosoftAuthResult> future;

    @Override
    public void initGui() {
        super.initGui();

        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 - 10, "Login"));
        this.buttonList.add(new GuiButton(1, buttonList.get(0).xPosition, buttonList.get(0).yPosition + 20, "Cancel"));
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        GuiUtil.drawRect(0, 0, this.width, this.height, 0xFF000000);
        GuiUtil.drawRoundedRect(0, 0, this.width, this.height, 0, 0xFF000000);

        super.drawScreen(mouseX, mouseY, partialTicks);

        if (future != null) {
            if (future.isDone()) {
                try {
                    MicrosoftAuthResult result = future.get();
                    System.out.println(result.getAccessToken());
                    System.out.println(result.getRefreshToken());
                    System.out.println(result.getProfile().getId());
                    System.out.println(result.getProfile().getName());

                    this.mc.setSession(new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "mojang"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                future = null;
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button.id == 0) {
            MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
            future = authenticator.loginWithAsyncWebview();
        } else if (button.id == 1) {
            /* completes the future */
            if (future != null) {
                future.completeExceptionally(new Exception("Cancelled"));
            }
            this.future = null;
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }
}
