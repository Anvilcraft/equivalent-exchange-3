package com.pahimar.ee3.handler;

import com.pahimar.ee3.item.ITransmutationStone;
import com.pahimar.ee3.util.TransmutationHelper;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

@SideOnly(Side.CLIENT)
public class DrawBlockHighlightHandler {
    private static int pulse;
    private static boolean doInc;
    
    @SubscribeEvent
    public void onDrawBlockHighlightEvent(final DrawBlockHighlightEvent event) {
        final Minecraft minecraft = FMLClientHandler.instance().getClient();
        if (event.currentItem != null && event.currentItem.getItem() instanceof ITransmutationStone && event.target.typeOfHit == MovingObjectType.BLOCK) {
            TransmutationHelper.updateTargetBlock(event.player.worldObj, event.target.blockX, event.target.blockY, event.target.blockZ);
            /*if (Minecraft.isGuiEnabled() && minecraft.inGameHasFocus && ConfigurationSettings.ENABLE_OVERLAY_WORLD_TRANSMUTATION) {
                this.drawInWorldTransmutationOverlay(event);
            }*/
        }
    }
    
    /*public void drawInWorldTransmutationOverlay(final DrawBlockHighlightEvent event) {
        final double x = event.target.blockX + 0.5f;
        final double y = event.target.blockY + 0.5f;
        final double z = event.target.blockZ + 0.5f;
        final double iPX = ((Entity)event.player).prevPosX + (((Entity)event.player).posX - ((Entity)event.player).prevPosX) * event.partialTicks;
        final double iPY = ((Entity)event.player).prevPosY + (((Entity)event.player).posY - ((Entity)event.player).prevPosY) * event.partialTicks;
        final double iPZ = ((Entity)event.player).prevPosZ + (((Entity)event.player).posZ - ((Entity)event.player).prevPosZ) * event.partialTicks;
        final int texture = event.context.renderEngine.func_78341_b("/mods/ee3/textures/effects/noise.png");
        float xScale = 1.0f;
        float yScale = 1.0f;
        float zScale = 1.0f;
        float xShift = 0.1f;
        float yShift = 0.1f;
        float zShift = 0.1f;
        int itemChargeLevel = 0;
        if (event.currentItem.getItem() instanceof IChargeable) {
            itemChargeLevel = ((IChargeable)event.currentItem.getItem()).getCharge(event.currentItem);
        }
        final int chargeLevel = 1 + itemChargeLevel * 2;
        final ForgeDirection sideHit = ForgeDirection.getOrientation(event.target.sideHit);
        switch (sideHit) {
            case UP: {
                xScale = chargeLevel + 0.1f;
                zScale = chargeLevel + 0.1f;
                xShift = 0.0f;
                zShift = 0.0f;
                break;
            }
            case DOWN: {
                xScale = chargeLevel + 0.1f;
                zScale = chargeLevel + 0.1f;
                xShift = 0.0f;
                yShift = -yShift;
                zShift = 0.0f;
                break;
            }
            case NORTH: {
                xScale = chargeLevel + 0.1f;
                yScale = chargeLevel + 0.1f;
                xShift = 0.0f;
                yShift = 0.0f;
                zShift = -zShift;
                break;
            }
            case SOUTH: {
                xScale = chargeLevel + 0.1f;
                yScale = chargeLevel + 0.1f;
                xShift = 0.0f;
                yShift = 0.0f;
                break;
            }
            case EAST: {
                yScale = chargeLevel + 0.1f;
                zScale = chargeLevel + 0.1f;
                yShift = 0.0f;
                zShift = 0.0f;
                break;
            }
            case WEST: {
                yScale = chargeLevel + 0.1f;
                zScale = chargeLevel + 0.1f;
                xShift = -xShift;
                yShift = 0.0f;
                zShift = 0.0f;
                break;
            }
        }
        GL11.glDepthMask(false);
        GL11.glDisable(2884);
        for (int i = 0; i < 6; ++i) {
            final ForgeDirection forgeDir = ForgeDirection.getOrientation(i);
            final int zCorrection = (i == 2) ? -1 : 1;
            GL11.glPushMatrix();
            GL11.glTranslated(-iPX + x + xShift, -iPY + y + yShift, -iPZ + z + zShift);
            GL11.glScalef(1.0f * xScale, 1.0f * yScale, 1.0f * zScale);
            GL11.glRotatef(90.0f, (float)forgeDir.offsetX, (float)forgeDir.offsetY, (float)forgeDir.offsetZ);
            GL11.glTranslated(0.0, 0.0, (double)(0.5f * zCorrection));
            GL11.glClear(256);
            renderPulsingQuad(texture, 0.75f);
            GL11.glPopMatrix();
        }
        GL11.glEnable(2884);
        GL11.glDepthMask(true);
    }
    
    public static void renderPulsingQuad(final int texture, final float maxTransparency) {
        final float pulseTransparency = getPulseValue() * maxTransparency / 3000.0f;
        GL11.glBindTexture(3553, texture);
        final Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(32826);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, pulseTransparency);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(1.0f, 1.0f, 1.0f, pulseTransparency);
        tessellator.addVertexWithUV(-0.5, 0.5, 0.0, 0.0, 1.0);
        tessellator.addVertexWithUV(0.5, 0.5, 0.0, 1.0, 1.0);
        tessellator.addVertexWithUV(0.5, -0.5, 0.0, 1.0, 0.0);
        tessellator.addVertexWithUV(-0.5, -0.5, 0.0, 0.0, 0.0);
        tessellator.draw();
        GL11.glDisable(3042);
        GL11.glDisable(32826);
    }
    
    private static int getPulseValue() {
        if (DrawBlockHighlightHandler.doInc) {
            DrawBlockHighlightHandler.pulse += 8;
        }
        else {
            DrawBlockHighlightHandler.pulse -= 8;
        }
        if (DrawBlockHighlightHandler.pulse == 3000) {
            DrawBlockHighlightHandler.doInc = false;
        }
        if (DrawBlockHighlightHandler.pulse == 0) {
            DrawBlockHighlightHandler.doInc = true;
        }
        return DrawBlockHighlightHandler.pulse;
    }*/
    
    static {
        DrawBlockHighlightHandler.pulse = 0;
        DrawBlockHighlightHandler.doInc = true;
    }
}
