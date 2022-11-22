package com.pahimar.ee3.item;

import com.pahimar.ee3.EquivalentExchange3;
import com.pahimar.ee3.reference.GUIs;
import com.pahimar.ee3.reference.Key;
import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.util.IKeyBound;
import com.pahimar.ee3.util.IOverlayItem;
import com.pahimar.ee3.util.NBTHelper;
import com.pahimar.ee3.util.TransmutationHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemMiniumStone extends ItemEE implements IKeyBound, IOverlayItem, ITransmutationStone
{
    public ItemMiniumStone()
    {
        super();
        this.setUnlocalizedName(Names.Items.MINIUM_STONE);
        this.setMaxDamage(1000); // TODO Get this from configs
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }

    @Override
    public boolean getShareTag()
    {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        ItemStack copiedStack = itemStack.copy();

        copiedStack.setItemDamage(copiedStack.getItemDamage() + 1);
        copiedStack.stackSize = 1;

        return copiedStack;
    }

    @Override
    public boolean hasContainerItem() {
        return true;
     }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack itemStack, int renderPass)
    {
        return NBTHelper.hasKey(itemStack, Names.NBT.CRAFTING_GUI_OPEN) || NBTHelper.hasKey(itemStack, Names.NBT.TRANSMUTATION_GUI_OPEN);
    }

    @Override
    public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, Key key)
    {
        if (key == Key.EXTRA) {
            if (!entityPlayer.isSneaking()) {
                openPortableCraftingGUI(entityPlayer, itemStack);
            } else {
                //this.openPortableTransmutationGUI(entityPlayer, itemStack);
            }
        } else if (key == Key.TOGGLE && TransmutationHelper.targetBlockStack != null) {
            if (!entityPlayer.isSneaking()) {
                TransmutationHelper.targetBlockStack = TransmutationHelper.getNextBlock(TransmutationHelper.targetBlockStack.getItem(), TransmutationHelper.targetBlockStack.getItemDamage());
            }
            else {
                TransmutationHelper.targetBlockStack = TransmutationHelper.getPreviousBlock(TransmutationHelper.targetBlockStack.getItem(), TransmutationHelper.targetBlockStack.getItemDamage());
            }
        }
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int sideHit, float hitVecX, float hitVecY, float hitVecZ) {
        if (world.isRemote) {
            this.transmuteBlock(itemStack, entityPlayer, world, x, y, z, sideHit);
        }
        return true;
    }

    @Override
    public void transmuteBlock(final ItemStack itemStack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int sideHit) {
        EquivalentExchange3.proxy.transmuteBlock(itemStack, player, world, x, y, z, ForgeDirection.getOrientation(sideHit));
    }

    @Override
    public void openPortableCraftingGUI(EntityPlayer thePlayer, ItemStack itemStack) {
        NBTHelper.setBoolean(itemStack, Names.NBT.CRAFTING_GUI_OPEN, true);
        thePlayer.openGui((Object)EquivalentExchange3.instance, GUIs.PORTABLE_CRAFTING.ordinal(), thePlayer.worldObj, (int)thePlayer.posX, (int)thePlayer.posY, (int)thePlayer.posZ);
    }

    @Override
    public void openPortableTransmutationGUI(EntityPlayer thePlayer, ItemStack itemStack) {
        NBTHelper.setBoolean(itemStack, Names.NBT.TRANSMUTATION_GUI_OPEN, true);
        //thePlayer.openGui((Object)EquivalentExchange3.instance, 1, thePlayer.worldObj, (int)thePlayer.posX, (int)thePlayer.posY, (int)thePlayer.posZ);
    }

}
