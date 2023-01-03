package com.pahimar.ee3.client.renderer.overlay_item;

import com.pahimar.ee3.client.renderer.IOverlayItemRenderer;
import com.pahimar.ee3.client.util.RenderUtils;
import com.pahimar.ee3.util.TransmutationHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class MiniumStoneOverlayItemRenderer implements IOverlayItemRenderer {
    public static final MiniumStoneOverlayItemRenderer INSTANCE
        = new MiniumStoneOverlayItemRenderer();

    @Override
    public void renderOverlayItem(EntityPlayer player, ItemStack stack) {
        Minecraft minecraft = Minecraft.getMinecraft();
        //float overlayScale = ConfigurationSettings.TARGET_BLOCK_OVERLAY_SCALE;
        float overlayScale = 2.0f;
        float blockScale = overlayScale / 2.0F;
        //float overlayOpacity = ConfigurationSettings.TARGET_BLOCK_OVERLAY_OPACITY;
        float overlayOpacity = 1.0f;
        GL11.glPushMatrix();
        ScaledResolution sr = new ScaledResolution(
            minecraft, minecraft.displayWidth, minecraft.displayHeight
        );
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(
            0.0,
            sr.getScaledWidth_double(),
            sr.getScaledHeight_double(),
            0.0,
            1000.0,
            3000.0
        );
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glEnable(32826);
        GL11.glEnable(2903);
        GL11.glEnable(2896);
        int hudBlockX = 0;
        int hudBlockY = 0;
        hudBlockX
            = (int) ((float) sr.getScaledWidth() - 16.0F * overlayScale / 2.0F - 8.0F);
        hudBlockY
            = (int) ((float) sr.getScaledHeight() - 16.0F * overlayScale / 2.0F - 8.0F);

        if (TransmutationHelper.targetBlockStack != null
            && TransmutationHelper.targetBlockStack.getItem() instanceof ItemBlock) {
            RenderUtils.renderRotatingBlockIntoGUI(
                minecraft.fontRenderer,
                TransmutationHelper.targetBlockStack,
                hudBlockX,
                hudBlockY,
                -90.0F,
                blockScale
            );
        }

        GL11.glDisable(2896);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
