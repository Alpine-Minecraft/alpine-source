package me.alpine.util.render;

import me.alpine.event.EventManager;
import me.alpine.event.EventTarget;
import me.alpine.event.impl.EventPlayerRender;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;
import java.util.List;

public class WavyCapeRenderer {

    public WavyCapeRenderer() {
        EventManager.register(this);
    }

    Minecraft mc = Minecraft.getMinecraft();
    private final int horzNodes = 20;
    private final double targetDist = 1 / 30.0;
    private final double shoulderWidth = 0.13;
    private final double crouchWidthOffset = -0.05;
    private final List<List<Node>> nodes = new ArrayList<>();

    private void resetNodes(final EntityPlayer player) {
        nodes.clear();
        for (int i = 0; i < 50; i++) {
            final List<Node> list = new ArrayList<>();
            for (int j = 0; j < horzNodes; j++)
                list.add(new Node(player.posX - 1,
                        player.posY + 2 - i * targetDist,
                        player.posZ + ((double) j) / (horzNodes - 1),
                        i,
                        j));
            nodes.add(list);
        }
    }

    @EventTarget
    public void onRenderPlayer(final EventPlayerRender e) {
        if (mc.theWorld == null) return;
        final double partialTicks = e.getPartialRenderTick();
        final EntityPlayer player = e.getPlayer();
        final boolean check = true; /* TODO put your if check */
        if (check) {
            final Entity viewer = mc.getRenderViewEntity();
            final double pX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
            final double pY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
            final double pZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
            if (nodes.size() < 1) resetNodes(player);
            for (final List<Node> nodes2 : nodes) for (final Node node : nodes2) {
                double gravity = 0.04;
                node.aY -= gravity / 2; node.update(pX, pY, pZ, player); }
            updateFixedNodes(pX, pY, pZ, player);
            physics: {
                for (int step = 0; step < 6; step++) for (int i = 0; i < nodes.size(); i++) for (int j = 0; j < horzNodes; j++) {
                    final Node node = nodes.get(i).get(j);
                    final List<Node> struct = new ArrayList<Node>();
                    final List<Node> shear = new ArrayList<Node>();
                    final List<Node> bend = new ArrayList<Node>();
                    if (i + 1 < nodes.size()) struct.add(nodes.get(i + 1).get(j));
                    if (j + 1 < horzNodes) struct.add(nodes.get(i).get(j + 1));
                    if (i - 1 >= 0) struct.add(nodes.get(i - 1).get(j));
                    if (j - 1 >= 0) struct.add(nodes.get(i).get(j - 1));
                    if (i + 1 < nodes.size() && j + 1 < horzNodes) shear.add(nodes.get(i + 1).get(j + 1));
                    if (i + 1 < nodes.size() && j - 1 >= 0) shear.add(nodes.get(i + 1).get(j - 1));
                    if (i - 1 >= 0 && j + 1 < horzNodes) shear.add(nodes.get(i - 1).get(j + 1));
                    if (i - 1 >= 0 && j - 1 >= 0) shear.add(nodes.get(i - 1).get(j - 1));
                    if (i + 2 < nodes.size()) bend.add(nodes.get(i + 2).get(j));
                    if (j + 2 < horzNodes) bend.add(nodes.get(i).get(j + 2));
                    if (i - 2 >= 0) bend.add(nodes.get(i - 2).get(j));
                    if (j - 2 >= 0) bend.add(nodes.get(i).get(j - 2));
                    try {
                        updateNode(node, struct, shear, bend);
                    } catch (final Exception ex) {
                        ex.printStackTrace();
                        System.exit(0);
                    }
                }
                for (int i = 0; i < nodes.size(); i++) for (int j = 0; j < horzNodes; j++) {
                    Node up = null , down = null , left = null , right = null;
                    Node up2 = null , down2 = null , left2 = null , right2 = null;
                    if (i + 1 < nodes.size()) down = nodes.get(i + 1).get(j);
                    if (j + 1 < horzNodes) right = nodes.get(i).get(j + 1);
                    if (i - 1 >= 0) up = nodes.get(i - 1).get(j);
                    if (j - 1 >= 0) left = nodes.get(i).get(j - 1);
                    if (i + 2 < nodes.size()) down2 = nodes.get(i + 2).get(j);
                    if (j + 2 < horzNodes) right2 = nodes.get(i).get(j + 2);
                    if (i - 2 >= 0) up2 = nodes.get(i - 2).get(j);
                    if (j - 2 >= 0) left2 = nodes.get(i).get(j - 2);
                    /* this drops fps */
                    nodes.get(i).get(j).updateNormal(up, left, right, down, up2, left2, right2, down2);
                }
            }
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GlStateManager.enableTexture2D();
            final int currTex = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
            final double vX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks;
            final double vY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks;
            final double vZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks;
            GlStateManager.disableCull();
            final ResourceLocation capeTex = new ResourceLocation("Alpine/cape/cape.png"); // 1024 * 768
            final SimpleTexture simpleTexture = new SimpleTexture(capeTex);
            GL11.glPushMatrix();
            final double scaleamount = 2;
            GL11.glScaled(scaleamount, scaleamount, scaleamount);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, simpleTexture.getGlTextureId());
            mc.getRenderEngine().bindTexture(capeTex);
            GL11.glPopMatrix();
            for (int i = 0; i < nodes.size(); i++) for (int j = 0; j < horzNodes; j++) {
                final Node node = nodes.get(i).get(j);
                if (i + 1 < nodes.size() && j + 1 < horzNodes) {
                    GlStateManager.color(1F, 1F, 1F, 1F);
                    renderNodeConnection(vX, vY, vZ, node, nodes.get(i + 1).get(j), nodes.get(i).get(j + 1), nodes.get(i + 1).get(j + 1), true);
                    GlStateManager.color(1F, 1F, 1F, 1F);
                    renderNodeConnection(vX, vY, vZ, node, nodes.get(i + 1).get(j), nodes.get(i).get(j + 1), nodes.get(i + 1).get(j + 1), false);
                }
            }
            GlStateManager.color(1F, 1F, 1F, 1F);
            for (int i = 0; i < nodes.size(); i++) if (i + 1 < nodes.size()) {
                renderSideConnection(vX, vY, vZ, nodes.get(i).get(0), nodes.get(i + 1).get(0));
                renderSideConnection(vX, vY, vZ, nodes.get(i).get(horzNodes - 1), nodes.get(i + 1).get(horzNodes - 1));
            }
            for (int j = 0; j < horzNodes; j++) if (j + 1 < horzNodes) {
                renderSideConnection(vX, vY, vZ, nodes.get(0).get(j), nodes.get(0).get(j + 1));
                renderSideConnection(vX, vY, vZ, nodes.get(nodes.size() - 1).get(j), nodes.get(nodes.size() - 1).get(j + 1));
            }
            GL20.glUseProgram(0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, currTex);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
            GlStateManager.color(1F, 1F, 1F, 1F);
        }
    }

    private Vec3 node2vec(final Node node) { return new Vec3(node.x, node.y, node.z); }

    private void renderSideConnection(double pX, double pY, double pZ, Node node1, Node node2) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_NORMAL);
        worldrenderer.pos(node1.x - pX, node1.y - pY, node1.z - pZ).normal((float) node1.normalX, (float) node1.normalY, (float) node1.normalZ).endVertex();
        worldrenderer.pos(node2.x - pX, node2.y - pY, node2.z - pZ).normal((float) node2.normalX, (float) node2.normalY, (float) node2.normalZ).endVertex();
        worldrenderer.pos(node1.x - pX + node1.normalX / 15, node1.y - pY + node1.normalY / 15, node1.z - pZ + node1.normalZ / 15).normal((float) node1.normalX, (float) node1.normalY, (float) node1.normalZ).endVertex();
        worldrenderer.pos(node2.x - pX + node2.normalX / 15, node2.y - pY + node2.normalY / 15, node2.z - pZ + node2.normalZ / 15).normal((float) node2.normalX, (float) node2.normalY, (float) node2.normalZ).endVertex();
        tessellator.draw();
    }

    private void renderNodeConnection(double pX, double pY, double pZ, Node node1, Node node2, Node node3, Node node4, boolean offset) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        if (offset) {
            worldrenderer.begin(5, DefaultVertexFormats.POSITION_TEX_NORMAL);
            worldrenderer.pos(node1.x - pX + node1.normalX / 15, node1.y - pY + node1.normalY / 15, node1.z - pZ + node1.normalZ / 15).tex(((double) node1.jIndex) / (horzNodes - 1), ((double) node1.iIndex) / (nodes.size() - 1))
                    .normal((float) node1.normalX, (float) node1.normalY, (float) node1.normalZ).endVertex();
            worldrenderer.pos(node2.x - pX + node2.normalX / 15, node2.y - pY + node2.normalY / 15, node2.z - pZ + node2.normalZ / 15).tex(((double) node2.jIndex) / (horzNodes - 1), ((double) node2.iIndex) / (nodes.size() - 1))
                    .normal((float) node2.normalX, (float) node2.normalY, (float) node2.normalZ).endVertex();
            worldrenderer.pos(node3.x - pX + node3.normalX / 15, node3.y - pY + node3.normalY / 15, node3.z - pZ + node3.normalZ / 15).tex(((double) node3.jIndex) / (horzNodes - 1), ((double) node3.iIndex) / (nodes.size() - 1))
                    .normal((float) node3.normalX, (float) node3.normalY, (float) node3.normalZ).endVertex();
            worldrenderer.pos(node4.x - pX + node4.normalX / 15, node4.y - pY + node4.normalY / 15, node4.z - pZ + node4.normalZ / 15).tex(((double) node4.jIndex) / (horzNodes - 1), ((double) node4.iIndex) / (nodes.size() - 1))
                    .normal((float) node4.normalX, (float) node4.normalY, (float) node4.normalZ).endVertex();
        } else {
            worldrenderer.begin(5, DefaultVertexFormats.POSITION_NORMAL);
            worldrenderer.pos(node1.x - pX, node1.y - pY, node1.z - pZ).normal((float) node1.normalX, (float) node1.normalY, (float) node1.normalZ).endVertex();
            worldrenderer.pos(node2.x - pX, node2.y - pY, node2.z - pZ).normal((float) node2.normalX, (float) node2.normalY, (float) node2.normalZ).endVertex();
            worldrenderer.pos(node3.x - pX, node3.y - pY, node3.z - pZ).normal((float) node3.normalX, (float) node3.normalY, (float) node3.normalZ).endVertex();
            worldrenderer.pos(node4.x - pX, node4.y - pY, node4.z - pZ).normal((float) node4.normalX, (float) node4.normalY, (float) node4.normalZ).endVertex();
        }
        tessellator.draw();
    }

    private Vec3 scale(final Vec3 vector, final double amount) { return new Vec3(vector.xCoord * amount, vector.yCoord * amount, vector.zCoord * amount); }

    private void updateNode(final Node node, final List<Node> struct, final List<Node> shear, final List<Node> bend) {
        final double shearDist = 1.414 * targetDist;
        final double bendDist = 2 * targetDist;
        for (final Node bendNode : bend) resolve(node, bendNode, bendDist);
        for (final Node shearNode : shear) resolve(node, shearNode, shearDist);
        for (final Node structNode : struct) resolve(node, structNode, targetDist);
    }

    public void resolve(final Node node1, final Node node2, final double targetDist) {
        double dX = node1.x - node2.x;
        double dY = node1.y - node2.y;
        double dZ = node1.z - node2.z;
        final double distSq = dX * dX + dY * dY + dZ * dZ;
        final double dist = Math.sqrt(distSq);
        dX *= (1 - targetDist / dist) * 0.5;
        dY *= (1 - targetDist / dist) * 0.5;
        dZ *= (1 - targetDist / dist) * 0.5;
        if (node1.fixed || node2.fixed) {
            dX *= 2;
            dY *= 2;
            dZ *= 2;
        }
        if (!node1.fixed) {
            node1.x -= dX;
            node1.y -= dY;
            node1.z -= dZ;
        }
        if (!node2.fixed) {
            node2.x += dX;
            node2.y += dY;
            node2.z += dZ;
        }
    }

    private void updateFixedNodes(final double pX, final double pY, final double pZ, final EntityPlayer player) {
        final double angle = Math.toRadians(player.rotationYaw);
        double shoulderWidth2 = shoulderWidth + (player.isSneaking() ? crouchWidthOffset : 0);
        if (player.getCurrentArmor(1) != null || player.getCurrentArmor(2) != null) if (player.isSneaking()) shoulderWidth2 += 0.15;
        else shoulderWidth2 += 0.06;
        final double vertoffset = 1.4;
        final double xAngle = 0;
        final double zAngle = 0;
        Node node = nodes.get(0).get(0);
        double shoulderLength = 0.3;
        node.x = pX + Math.cos(angle) * shoulderLength - xAngle * shoulderWidth2;
        node.y = pY + vertoffset - (player.isSneaking() ? 0.2 : 0);
        node.z = pZ + Math.sin(angle) * shoulderLength - zAngle * shoulderWidth2;
        node.fixed = true;
        node = nodes.get(0).get(nodes.get(0).size() - 1);
        node.x = pX - Math.cos(angle) * shoulderLength - xAngle * shoulderWidth2;
        node.y = pY + vertoffset - (player.isSneaking() ? 0.2 : 0);
        node.z = pZ - Math.sin(angle) * shoulderLength - zAngle * shoulderWidth2;
        node.fixed = true;
    }

    class Node {
        public int iIndex;
        public int jIndex;
        public boolean fixed = false;
        public double x;
        public double y;
        public double z;
        public double xOld;
        public double yOld;
        public double zOld;
        public double aX;
        public double aY;
        public double aZ;
        public double normalX;
        public double normalY;
        public double normalZ;

        public Node(final double x, final double y, final double z, final int iIndex, final int jIndex) {
            this.x = xOld = x;
            this.y = xOld = y;
            this.z = xOld = z;
            this.iIndex = iIndex;
            this.jIndex = jIndex;
        }

        private void updateNormal(final Node up, final Node left, final Node right, final Node down, final Node up2, final Node left2, final Node right2, final Node down2) {
            final Vec3 normal1 = normal(up, left);
            final Vec3 normal2 = normal(right, up);
            final Vec3 normal3 = normal(down, right);
            final Vec3 normal4 = normal(left, down);
            final Vec3 normal5 = normal(up2, left2);
            final Vec3 normal6 = normal(right2, up2);
            final Vec3 normal7 = normal(down2, right2);
            final Vec3 normal8 = normal(left2, down2);
            final Vec3 avgNormal = normal1.add(normal2).add(normal3).add(normal4).add(normal5).add(normal6).add(normal7).add(normal8).normalize();
            normalX = avgNormal.xCoord;
            normalY = avgNormal.yCoord;
            normalZ = avgNormal.zCoord;
        }

        private Vec3 normal(final Node node1, final Node node2) {
            if (node1 == null || node2 == null) return new Vec3(0, 0, 0);
            final Vec3 thisNode = node2vec(this);
            final Vec3 node1Vec = node2vec(node1);
            final Vec3 node2Vec = node2vec(node2);
            final Vec3 thisTo1 = node1Vec.subtract(thisNode);
            final Vec3 thisTo2 = node2Vec.subtract(thisNode);
            return thisTo1.crossProduct(thisTo2);
        }

        public void update(final double pX, final double pY, final double pZ, final EntityPlayer player) {
            if (fixed) return;
            final double xTemp = x;
            final double yTemp = y;
            final double zTemp = z;
            double resistance = 0.08;
            final double x$ = resistance * 2 * 2173;
            final double y$ = (resistance + 0.001) * 2 * 2173;
            final double temp$ = y$ - x$;
            final double temp2$ = temp$ / (resistance * 2);
            double res = temp$ / temp2$;
            final BlockPos pos = new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
            final Block block = mc.theWorld.getBlockState(pos).getBlock();
            if (block.getMaterial().isLiquid()) {
                aX /= 5;
                aY /= 5;
                aZ /= 5;
                res = Math.sqrt(res);
            }
            double xDiff = x - xOld;
            double yDiff = y - yOld;
            double zDiff = z - zOld;
            xDiff = MathHelper.clamp_double(xDiff, -0.5, 0.5);
            yDiff = MathHelper.clamp_double(yDiff, -0.5, 0.5);
            zDiff = MathHelper.clamp_double(zDiff, -0.5, 0.5);
            x = x + xDiff * (1 - res) + aX * 0.2;
            y = y + yDiff * (1 - res) + aY * 0.2;
            z = z + zDiff * (1 - res) + aZ * 0.2;
            resolvePlayerCollision(pX, pY, pZ, player);
            if (!checkCollision(xTemp, yTemp, zTemp)) {
                xOld = xTemp;
                yOld = yTemp;
                zOld = zTemp;
            }
            if (checkCollision(x, y, z)) updateFromBoundingBox();
            aX = 0;
            aY = 0;
            aZ = 0;
        }

        public boolean resolvePlayerCollision(final double pX, final double pY, final double pZ, final EntityPlayer player) {
            final double angle = Math.toRadians(player.renderYawOffset);
            double offset = 0;
            if (player.getCurrentArmor(1) != null) if (player.isSneaking()) offset += 0.15;
            else offset += 0.06;
            if (player.isSneaking()) {
                offset -= crouchWidthOffset;
                final double dY = y - player.posY;
                double maxCrouchOffset = 0.35;
                if (dY < 0.65) offset += maxCrouchOffset;
                else if (dY < 1.2) offset += maxCrouchOffset * (1.2 - dY) / 0.55;
            }
            final double x1 = pX + Math.cos(angle) * 2 - Math.cos(angle + Math.PI / 2) * (shoulderWidth + offset);
            final double z1 = pZ + Math.sin(angle) * 2 - Math.sin(angle + Math.PI / 2) * (shoulderWidth + offset);
            final double x2 = pX - Math.cos(angle) * 2 - Math.cos(angle + Math.PI / 2) * (shoulderWidth + offset);
            final double z2 = pZ - Math.sin(angle) * 2 - Math.sin(angle + Math.PI / 2) * (shoulderWidth + offset);
            final boolean crossed = ((x2 - x1) * (z - z1) < (z2 - z1) * (x - x1));
            if (crossed) {
                final double dot1 = ((x - x2) * (x1 - x2) + (z - z2) * (z1 - z2));
                final double dot2 = (x1 - x2) * (x1 - x2) + (z1 - z2) * (z1 - z2);
                final double k = dot1 / dot2;
                x = xOld = (x1 - x2) * k + x2;
                z = zOld = (z1 - z2) * k + z2;
                return true;
            }
            return false;
        }

        public void updateFromBoundingBox() {
            final BlockPos pos = new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
            final Block block = mc.theWorld.getBlockState(pos).getBlock();
            block.setBlockBoundsBasedOnState(mc.theWorld, pos);
            final AxisAlignedBB bb = block.getSelectedBoundingBox(mc.theWorld, pos);
            final Vec3 center = new Vec3((bb.minX + bb.maxX) / 2, (bb.minY + bb.maxY) / 2, (bb.minZ + bb.maxZ) / 2);
            final MovingObjectPosition mop = bb.calculateIntercept(center.add(new Vec3(x, y, z).subtract(center).normalize()), center);
            if (mop == null) return;
            final Vec3 vec = mop.hitVec;
            if (vec == null) return;
            final double dX = vec.xCoord - x;
            final double dY = vec.yCoord - y;
            final double dZ = vec.zCoord - z;
            final double adX = Math.abs(dX);
            final double adY = Math.abs(dY);
            final double adZ = Math.abs(dZ);
            final double tot = adX + adY + adZ;
            if (tot < 0.15 || checkCollision(vec.xCoord, vec.yCoord, vec.zCoord)) {
                x = xOld;
                y = yOld;
                z = zOld;
                return;
            }
            if (adX / tot > 0.3) x = xOld = vec.xCoord;
            if (adY / tot > 0.3) y = yOld = vec.yCoord;
            if (adZ / tot > 0.3) z = zOld = vec.zCoord;
        }

        public boolean checkCollision(final double x, final double y, final double z) {
            final BlockPos pos = new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
            final Block block = mc.theWorld.getBlockState(pos).getBlock();
            if (block.getMaterial().isSolid()) {
                block.setBlockBoundsBasedOnState(mc.theWorld, pos);
                final AxisAlignedBB bb = block.getSelectedBoundingBox(mc.theWorld, pos);
                return bb.isVecInside(new Vec3(x, y, z));
            } else return false;
        }
    }
}
