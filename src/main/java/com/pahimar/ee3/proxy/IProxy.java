package com.pahimar.ee3.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IProxy
{
    public abstract ClientProxy getClientProxy();

    public abstract void initRenderingAndTextures();

    public abstract void registerEventHandlers();

    public abstract void registerKeybindings();

    public abstract void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch);

    public abstract void spawnParticle(String particleName, double xCoord, double yCoord, double zCoord, double xVelocity, double yVelocity, double zVelocity);

    void transmuteBlock(final ItemStack itemStack, final EntityPlayer player, final World world, final int x, final int y, final int z, final ForgeDirection sideHit);
}
