package com.pahimar.ee3.network.message;

import com.pahimar.ee3.handler.WorldTransmutationHandler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

public class MessageTransmutateEvent implements IMessage, IMessageHandler<MessageTransmutateEvent, IMessage> {

    public int originX;
    public int originY;
    public int originZ;
    public ForgeDirection sideHit;
    public byte rangeX = 0;
    public byte rangeY = 0;
    public byte rangeZ = 0;
    public Block block;
    public int metadata;

    public MessageTransmutateEvent() {}

    public MessageTransmutateEvent(int originX, int originY, int originZ, ForgeDirection sideHit, Block block, int metadata) {
        this.originX = originX;
        this.originY = originY;
        this.originZ = originZ;
        this.sideHit = sideHit;
        this.block = block;
        this.metadata = metadata;
    }

    @Override
    public IMessage onMessage(MessageTransmutateEvent message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        WorldTransmutationHandler.handleWorldTransmutation(player, message.originX, message.originY, message.originZ, message.rangeX, message.rangeY, message.rangeZ, message.sideHit, message.block, message.metadata);
        return null;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        originX = buf.readInt();
        originY = buf.readInt();
        originZ = buf.readInt();
        sideHit = ForgeDirection.getOrientation(buf.readInt());
        rangeX = buf.readByte();
        rangeY = buf.readByte();
        rangeZ = buf.readByte();
        block = Block.getBlockById(buf.readInt());
        metadata = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(originX);
        buf.writeInt(originY);
        buf.writeInt(originZ);
        buf.writeInt(sideHit.ordinal());
        buf.writeByte(rangeX);
        buf.writeByte(rangeY);
        buf.writeByte(rangeZ);
        buf.writeInt(Block.getIdFromBlock(block));
        buf.writeInt(metadata);
    }
    
}
