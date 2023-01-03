package com.pahimar.ee3.inventory;

import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerPortableCrafting extends ContainerWorkbench {
    public ContainerPortableCrafting(
        InventoryPlayer inv, World world, int x, int y, int z
    ) {
        super(inv, world, x, y, z);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        if (!player.worldObj.isRemote) {
            InventoryPlayer invPlayer = player.inventory;
            for (ItemStack itemStack : invPlayer.mainInventory) {
                if (itemStack == null
                    || !NBTHelper.hasKey(itemStack, Names.NBT.CRAFTING_GUI_OPEN))
                    continue;
                NBTHelper.removeTag(itemStack, Names.NBT.CRAFTING_GUI_OPEN);
            }
        }
    }
}
