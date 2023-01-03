package com.pahimar.ee3.util;

import java.util.ArrayList;

import com.pahimar.ee3.handler.EquivalencyHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TransmutationHelper {
    public static ItemStack previousBlockStack;
    public static ItemStack currentBlockStack;
    public static ItemStack targetBlockStack;

    public static boolean transmuteInWorld(
        final World world,
        final EntityPlayer player,
        final ItemStack stack,
        final int x,
        final int y,
        final int z,
        final Block target,
        final int targetMeta
    ) {
        world.setBlock(x, y, z, target, targetMeta, 2);
        return true;
    }

    public static String formatTargetBlockInfo(final ItemStack targetBlock) {
        if (targetBlock != null) {
            return TransmutationHelper.targetBlockStack.getUnlocalizedName() + ":"
                + TransmutationHelper.targetBlockStack.getItemDamage();
        }
        return "";
    }

    public static void
    updateTargetBlock(final World world, final int x, final int y, final int z) {
        final Block currentBlock = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if (currentBlock != null) {
            meta = currentBlock.damageDropped(meta);
            TransmutationHelper.currentBlockStack = new ItemStack(currentBlock, 1, meta);
            if (TransmutationHelper.previousBlockStack == null) {
                TransmutationHelper.previousBlockStack
                    = TransmutationHelper.currentBlockStack;
                TransmutationHelper.targetBlockStack = getNextBlock(
                    TransmutationHelper.currentBlockStack.getItem(),
                    TransmutationHelper.currentBlockStack.getItemDamage()
                );
            } else if (!EquivalencyHandler.instance().areEquivalent(
                           TransmutationHelper.previousBlockStack,
                           TransmutationHelper.currentBlockStack
                       )) {
                TransmutationHelper.previousBlockStack
                    = TransmutationHelper.currentBlockStack;
                TransmutationHelper.targetBlockStack = getNextBlock(
                    TransmutationHelper.currentBlockStack.getItem(),
                    TransmutationHelper.currentBlockStack.getItemDamage()
                );
            }
        }
    }

    public static ItemStack getNextBlock(final Item id, final int meta) {
        final ArrayList<ItemStack> list
            = EquivalencyHandler.instance().getEquivalencyList(id, meta);
        final ItemStack nextStack = null;
        if (list != null) {
            return getNextBlock(id, meta, id, meta);
        }
        return nextStack;
    }

    private static ItemStack
    getNextBlock(final Item id, final int meta, final Item origId, final int origMeta) {
        final ArrayList<ItemStack> list
            = EquivalencyHandler.instance().getEquivalencyList(id, meta);
        ItemStack nextStack = null;
        if (list == null) {
            return nextStack;
        }
        nextStack = EquivalencyHandler.instance().getNextInList(id, meta);
        nextStack.stackSize = 1;
        if (nextStack.getItem() == origId && nextStack.getItemDamage() == origMeta) {
            return nextStack;
        }
        if (nextStack.getItem() instanceof ItemBlock) {
            return nextStack;
        }
        return getNextBlock(
            nextStack.getItem(), nextStack.getItemDamage(), origId, origMeta
        );
    }

    public static ItemStack getPreviousBlock(final Item itemID, final int meta) {
        final ArrayList<ItemStack> list
            = EquivalencyHandler.instance().getEquivalencyList(itemID, meta);
        final ItemStack prevStack = null;
        if (list != null) {
            return getPreviousBlock(itemID, meta, itemID, meta);
        }
        return prevStack;
    }

    private static ItemStack getPreviousBlock(
        final Item id, final int meta, final Item origId, final int origMeta
    ) {
        final ArrayList<ItemStack> list
            = EquivalencyHandler.instance().getEquivalencyList(id, meta);
        ItemStack prevStack = null;
        if (list == null) {
            return prevStack;
        }
        prevStack = EquivalencyHandler.instance().getPrevInList(id, meta);
        prevStack.stackSize = 1;
        if (prevStack.getItem() == origId && prevStack.getItemDamage() == origMeta) {
            return prevStack;
        }
        if (prevStack.getItem() instanceof ItemBlock) {
            return prevStack;
        }
        return getPreviousBlock(
            prevStack.getItem(), prevStack.getItemDamage(), origId, origMeta
        );
    }
}
