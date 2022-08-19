package me.alpine.util.render.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * @author Shoroa_
 * @link <a href="https://discord.com/channels/594335572173258752/632743056113926154/982430113029316648">discord</a>
 */
public class RoundedRectShader {

    static Minecraft mc = Minecraft.getMinecraft();

    static RoundedRectShader INSTANCE;
    static int program;

    public static RoundedRectShader getInstance() {
        if (INSTANCE == null) {
            program = glCreateProgram();
            int fragID, vertexID;
            try {
                fragID = createShader(mc.getResourceManager().getResource(new ResourceLocation("alpine/shader/rounded.frag")).getInputStream(), GL_FRAGMENT_SHADER);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                vertexID = createShader(Minecraft.getMinecraft().getResourceManager()
                        .getResource(new ResourceLocation("alpine/shader/vertex.vert")).getInputStream(), GL_VERTEX_SHADER);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            glAttachShader(program, fragID); //attach fragment shader to program
            glAttachShader(program, vertexID); //attach vertex shader to program

            glLinkProgram(program); //link program
            int status = glGetProgrami(program, GL_LINK_STATUS);

            if (status == 0) {
                throw new IllegalStateException("Shader failed to link!");
            }
            INSTANCE = new RoundedRectShader();
        }
        return INSTANCE;
    }

    static int createShader(InputStream inputStream, int shaderType) {
        int shader = glCreateShader(shaderType);
        glShaderSource(shader, readInputStream(inputStream));
        glCompileShader(shader);


        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            System.out.println(glGetShaderInfoLog(shader, 4096));
            throw new IllegalStateException("Shader failed to compile! : " + shaderType);
        }

        return shader;
    }

    static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void load() {
        glUseProgram(program);
    }

    public void unload() {
        glUseProgram(0);
    }

    public void drawRound(float x, float y, float width, float height, float radius, Color color) {
        GlStateManager.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        getInstance().load();

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        getInstance().setUniformFloat("loc", x * sr.getScaleFactor(),
                (Minecraft.getMinecraft().displayHeight - (height * sr.getScaleFactor())) - (y * sr.getScaleFactor()));
        getInstance().setUniformFloat("size", width * sr.getScaleFactor(), height * sr.getScaleFactor());
        getInstance().setUniformFloat("radius", radius * sr.getScaleFactor());
        getInstance().setUniformFloat("color", color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(x - 1, y - 1);
        glTexCoord2f(0, 1);
        glVertex2f(x - 1, y + height + 1);
        glTexCoord2f(1, 1);
        glVertex2f(x + width + 1, y + height + 1);
        glTexCoord2f(1, 0);
        glVertex2f(x + width + 1, y - 1);
        glEnd();
        getInstance().unload();
        GlStateManager.disableBlend();
    }


    public void setUniformFloat(String name, float... arguments) {
        int loc = glGetUniformLocation(program, name);
        switch (arguments.length) {
            case 1:
                glUniform1f(loc, arguments[0]);
                break;
            case 2:
                glUniform2f(loc, arguments[0], arguments[1]);
                break;
            case 3:
                glUniform3f(loc, arguments[0], arguments[1], arguments[2]);
                break;
            case 4:
                glUniform4f(loc, arguments[0], arguments[1], arguments[2], arguments[3]);
                break;
        }
    }
}
