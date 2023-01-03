package com.pahimar.ee3.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ITransmutationStone {
    void openPortableCraftingGUI(final EntityPlayer p0, final ItemStack p1);

    void openPortableTransmutationGUI(final EntityPlayer p0, final ItemStack p1);

    void transmuteBlock(
        final ItemStack p0,
        final EntityPlayer p1,
        final World p2,
        final int p3,
        final int p4,
        final int p5,
        final int p6
    );
}
