package com.pahimar.ee3.handler;

import com.pahimar.ee3.api.event.WorldTransmutationEvent;
import com.pahimar.ee3.network.PacketHandler;
import com.pahimar.ee3.network.message.MessageSingleParticleEvent;
import com.pahimar.ee3.network.message.MessageSoundEvent;
import com.pahimar.ee3.reference.Particles;
import com.pahimar.ee3.reference.Sounds;
import com.pahimar.ee3.util.TransmutationHelper;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldTransmutationHandler {
    public static void handleWorldTransmutation(
        EntityPlayer thePlayer,
        int originX,
        int originY,
        int originZ,
        byte rangeX,
        byte rangeY,
        byte rangeZ,
        ForgeDirection sideHit,
        Block block,
        int metadata
    ) {
        //actionRequestEvent actionRequestEvent = null;
        WorldTransmutationEvent actionEvent = null;
        int lowerBoundX = -1 * rangeX / 2;
        int upperBoundX = -1 * lowerBoundX;
        int lowerBoundY = -1 * rangeY / 2;
        int upperBoundY = -1 * lowerBoundY;
        int lowerBoundZ = -1 * rangeZ / 2;
        int upperBoundZ = -1 * lowerBoundZ;
        boolean anySuccess = false;
        double xShift = 0.0;
        double yShift = 0.0;
        double zShift = 0.0;
        int xSign = 1;
        int ySign = 1;
        int zSign = 1;
        switch (sideHit) {
            case UP: {
                yShift = 1.5;
                break;
            }
            case DOWN: {
                yShift = 0.1;
                ySign = -1;
                break;
            }
            case NORTH: {
                zShift = 1.0;
                zSign = -1;
                break;
            }
            case SOUTH: {
                zShift = 1.0;
                break;
            }
            case EAST: {
                xShift = 1.0;
                break;
            }
            case WEST: {
                xShift = 1.0;
                xSign = -1;
            }
        }
        for (int x = lowerBoundX; x <= upperBoundX; ++x) {
            for (int y = lowerBoundY; y <= upperBoundY; ++y) {
                for (int z = lowerBoundZ; z <= upperBoundZ; ++z) {
                    actionEvent = new WorldTransmutationEvent(
                        originX + x,
                        originY + y,
                        originZ + z,
                        thePlayer.worldObj,
                        block,
                        metadata,
                        sideHit,
                        thePlayer
                    );
                    if (actionEvent != null) {
                        //actionRequestEvent = new ActionRequestEvent(thePlayer,
                        //actionEvent, originX + x, originY + y, originZ + z, sideHit);
                        //MinecraftForge.EVENT_BUS.post(actionRequestEvent);
                        //if (actionRequestEvent.allowEvent != Event.Result.DENY) {
                        MinecraftForge.EVENT_BUS.post(actionEvent);
                        //}
                        /*if (actionEvent.actionResult ==
                        ActionEvent.ActionResult.SUCCESS) { if (!anySuccess) { anySuccess
                        = true;
                            }
                            PacketDispatcher.sendPacketToAllAround((double)(originX + x),
                        (double)(originY + y), (double)(originZ + z), 64.0,
                        ((Entity)thePlayer).worldObj.provider.dimensionId,
                        PacketTypeHandler.populatePacket(new
                        PacketSpawnParticle("largesmoke", originX + x + xShift * xSign,
                        originY + y + yShift * ySign, originZ + z + zShift * zSign, 0.0 *
                        xSign, 0.05 * ySign, 0.0 * zSign)));
                            PacketDispatcher.sendPacketToAllAround((double)(originX + x),
                        (double)(originY + y), (double)(originZ + z), 64.0,
                        ((Entity)thePlayer).worldObj.provider.dimensionId,
                        PacketTypeHandler.populatePacket(new
                        PacketSpawnParticle("largeexplode", originX + x + xShift * xSign,
                        originY + y + yShift * ySign, originZ + z + zShift * zSign, 0.0 *
                        xSign, 0.15 * ySign, 0.0 * zSign)));
                        }
                        else if (actionEvent.actionResult ==
                        ActionEvent.ActionResult.FAILURE &&
                        actionEvent.world.func_72798_a(originX + x, originY + y, originZ +
                        z) != 0) { PacketDispatcher.sendPacketToAllAround((double)(originX
                        + x), (double)(originY + y), (double)(originZ + z), 64.0,
                        ((Entity)thePlayer).worldObj.provider.dimensionId,
                        PacketTypeHandler.populatePacket(new
                        PacketSpawnParticle("reddust", originX + x + xShift * xSign,
                        originY + y + yShift * ySign, originZ + z + zShift * zSign, 0.0 *
                        xSign, 0.05 * ySign, 0.0 * zSign)));
                            PacketDispatcher.sendPacketToAllAround((double)(originX + x),
                        (double)(originY + y), (double)(originZ + z), 64.0,
                        ((Entity)thePlayer).worldObj.provider.dimensionId,
                        PacketTypeHandler.populatePacket(new
                        PacketSpawnParticle("witchMagic", originX + x + xShift * xSign,
                        originY + y + yShift * ySign, originZ + z + zShift * zSign, 0.0 *
                        xSign, 0.05 * ySign, 0.0 * zSign)));
                        }*/
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldTransmutationEvent(WorldTransmutationEvent event) {
        Block block = event.world.getBlock(event.x, event.y, event.z);
        int meta = event.world.getBlockMetadata(event.x, event.y, event.z);
        boolean result = false;
        if (block != null) {
            meta = block.damageDropped(meta);
        }
        ItemStack worldStack = new ItemStack(block, 1, meta);
        ItemStack targetStack = new ItemStack(event.block, 1, event.blockMetadata);
        ItemStack handItem = event.player.getCurrentEquippedItem();
        if (!worldStack.isItemEqual(targetStack)
            && EquivalencyHandler.instance().areWorldEquivalent(worldStack, targetStack)
            && handItem != null && handItem.getItemDamage() <= handItem.getMaxDamage()) {
            result = TransmutationHelper.transmuteInWorld(
                event.world,
                event.player,
                handItem,
                event.x,
                event.y,
                event.z,
                event.block,
                event.blockMetadata
            );
        }
        if (result) {
            PacketHandler.INSTANCE.sendToAllAround(
                new MessageSoundEvent(
                    Sounds.TRANSMUTE, event.x, event.y, event.z, 0.5f, 1.0f
                ),
                new TargetPoint(
                    event.player.worldObj.provider.dimensionId,
                    event.x,
                    event.y,
                    event.z,
                    32.0
                )
            );
            PacketHandler.INSTANCE.sendToAllAround(
                new MessageSingleParticleEvent(
                    Particles.LARGE_EXPLODE,
                    event.x + 0.5,
                    event.y + 0.5,
                    event.z + 0.5,
                    0.0,
                    0.0,
                    0.0
                ),
                new TargetPoint(
                    event.player.worldObj.provider.dimensionId,
                    event.x,
                    event.y,
                    event.z,
                    32
                )
            );
            //event.actionResult = ActionEvent.ActionResult.SUCCESS;
            int currentSlot = event.player.inventory.currentItem;
            handItem.damageItem(1, event.player);
            if (handItem.stackSize < 1) {
                event.player.inventory.setInventorySlotContents(
                    currentSlot, (ItemStack) null
                );
                //PacketDispatcher.sendPacketToPlayer(PacketTypeHandler.populatePacket(new
                //PacketItemUpdate((byte)currentSlot, (byte)0)), (Player)event.player);
                event.player.worldObj.playSoundAtEntity(
                    event.player,
                    "random.break",
                    0.8f,
                    0.8f + event.player.worldObj.rand.nextFloat() * 0.4f
                );
            }
        } else {
            //event.actionResult = ActionEvent.ActionResult.FAILURE;
            PacketHandler.INSTANCE.sendToAllAround(
                new MessageSoundEvent(Sounds.FAIL, event.x, event.y, event.z, 0.5f, 1.0f),
                new TargetPoint(
                    event.player.worldObj.provider.dimensionId,
                    event.x,
                    event.y,
                    event.z,
                    32.0
                )
            );
        }
    }
}
