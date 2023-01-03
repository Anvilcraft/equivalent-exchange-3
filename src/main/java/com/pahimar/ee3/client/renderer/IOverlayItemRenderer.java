package com.pahimar.ee3.client.renderer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * A renderer to render IOverlayItems in the HUD.
 */
public interface IOverlayItemRenderer {
    void renderOverlayItem(EntityPlayer player, ItemStack stack);
}
