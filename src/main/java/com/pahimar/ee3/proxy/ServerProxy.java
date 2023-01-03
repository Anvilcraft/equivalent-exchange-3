package com.pahimar.ee3.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ServerProxy extends CommonProxy {
    @Override
    public ClientProxy getClientProxy() {
        return null;
    }

    @Override
    public void initRenderingAndTextures() {
        // NOOP
    }

    @Override
    public void registerKeybindings() {
        // NOOP
    }

    @Override
    public void playSound(
        String soundName,
        float xCoord,
        float yCoord,
        float zCoord,
        float volume,
        float pitch
    ) {
        // NOOP
    }

    @Override
    public void spawnParticle(
        String particleName,
        double xCoord,
        double yCoord,
        double zCoord,
        double xVelocity,
        double yVelocity,
        double zVelocity
    ) {
        // NOOP
    }

    @Override
    public void transmuteBlock(
        ItemStack itemStack,
        EntityPlayer player,
        World world,
        int x,
        int y,
        int z,
        ForgeDirection sideHit
    ) {
        // NOOP
    }
}
