package com.pahimar.ee3.proxy;

import com.pahimar.ee3.client.handler.DrawBlockHighlightEventHandler;
import com.pahimar.ee3.client.handler.HUDTickHandler;
import com.pahimar.ee3.client.handler.ItemTooltipEventHandler;
import com.pahimar.ee3.client.handler.KeyInputEventHandler;
import com.pahimar.ee3.client.renderer.item.*;
import com.pahimar.ee3.client.renderer.tileentity.*;
import com.pahimar.ee3.client.settings.Keybindings;
import com.pahimar.ee3.client.util.ClientParticleHelper;
import com.pahimar.ee3.client.util.ClientSoundHelper;
import com.pahimar.ee3.init.ModBlocks;
import com.pahimar.ee3.network.PacketHandler;
import com.pahimar.ee3.network.message.MessageTransmutateEvent;
import com.pahimar.ee3.reference.RenderIds;
import com.pahimar.ee3.settings.ChalkSettings;
import com.pahimar.ee3.tileentity.*;
import com.pahimar.ee3.util.TransmutationHelper;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class ClientProxy extends CommonProxy {
    public ChalkSettings chalkSettings = new ChalkSettings();

    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();
        FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());
        FMLCommonHandler.instance().bus().register(new HUDTickHandler());
        MinecraftForge.EVENT_BUS.register(new ItemTooltipEventHandler(Loader.isModLoaded("ProjectE")));
        MinecraftForge.EVENT_BUS.register(new DrawBlockHighlightEventHandler());
    }

    @Override
    public void registerKeybindings() {
        ClientRegistry.registerKeyBinding(Keybindings.charge);
        ClientRegistry.registerKeyBinding(Keybindings.extra);
        ClientRegistry.registerKeyBinding(Keybindings.release);
        ClientRegistry.registerKeyBinding(Keybindings.toggle);
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
        ClientSoundHelper.playSound(soundName, xCoord, yCoord, zCoord, volume, pitch);
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
        ClientParticleHelper.spawnParticleAtLocation(
            particleName, xCoord, yCoord, zCoord, xVelocity, yVelocity, zVelocity
        );
    }

    @Override
    public ClientProxy getClientProxy() {
        return this;
    }

    @Override
    public void initRenderingAndTextures() {
        RenderIds.calcinator = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.aludel = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.alchemicalChest = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.glassBell = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.researchStation = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.augmentationTable = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.alchemyArray = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.dummyArray = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.tabletSlab = RenderingRegistry.getNextAvailableRenderId();

        MinecraftForgeClient.registerItemRenderer(
            Item.getItemFromBlock(ModBlocks.alchemicalChest),
            new ItemRendererAlchemicalChest()
        );
        MinecraftForgeClient.registerItemRenderer(
            Item.getItemFromBlock(ModBlocks.aludel), new ItemRendererAludel()
        );
        MinecraftForgeClient.registerItemRenderer(
            Item.getItemFromBlock(ModBlocks.calcinator), new ItemRendererCalcinator()
        );
        MinecraftForgeClient.registerItemRenderer(
            Item.getItemFromBlock(ModBlocks.glassBell), new ItemRendererGlassBell()
        );
        MinecraftForgeClient.registerItemRenderer(
            Item.getItemFromBlock(ModBlocks.researchStation),
            new ItemRendererResearchStation()
        );

        ClientRegistry.bindTileEntitySpecialRenderer(
            TileEntityAlchemicalChest.class, new TileEntityRendererAlchemicalChest()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TileEntityCalcinator.class, new TileEntityRendererCalcinator()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TileEntityAludel.class, new TileEntityRendererAludel()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TileEntityGlassBell.class, new TileEntityRendererGlassBell()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TileEntityResearchStation.class, new TileEntityRendererResearchStation()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TileEntityAlchemyArray.class, new TileEntityRendererAlchemyArray()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TileEntityTransmutationTablet.class,
            new TileEntityRendererTransmutationTablet()
        );
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
        if (TransmutationHelper.targetBlockStack != null && itemStack != null) {
            ItemStack stack = TransmutationHelper.targetBlockStack;
            Block targetBlock = Block.getBlockFromItem(stack.getItem());
            PacketHandler.INSTANCE.sendToServer(new MessageTransmutateEvent(
                x, y, z, sideHit, targetBlock, stack.getItemDamage()
            ));
        }
    }
}
