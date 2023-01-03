package com.pahimar.ee3.item;

import com.pahimar.ee3.reference.Key;
import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.util.IKeyBound;
import com.pahimar.ee3.util.IOverlayItem;
import com.pahimar.ee3.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPhilosophersStone
    extends ItemEE implements IKeyBound, IOverlayItem, ITransmutationStone {
    private int maxChargeLevel;

    public ItemPhilosophersStone() {
        super();
        this.setUnlocalizedName(Names.Items.PHILOSOPHERS_STONE);
        this.setMaxDamage(1000);
        this.maxChargeLevel = 3;
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack copiedStack = itemStack.copy();

        copiedStack.setItemDamage(copiedStack.getItemDamage() + 1);
        copiedStack.stackSize = 1;

        return copiedStack;
    }

    @Override
    public void
    doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, Key key) {
        LogHelper.info(
            "{} {} {}", entityPlayer.toString(), itemStack.toString(), key.toString()
        );
    }

    @Override
    public void openPortableCraftingGUI(EntityPlayer p0, ItemStack p1) {}

    @Override
    public void openPortableTransmutationGUI(EntityPlayer p0, ItemStack p1) {}

    @Override
    public void transmuteBlock(
        ItemStack p0, EntityPlayer p1, World p2, int p3, int p4, int p5, int p6
    ) {}
}
