package com.pahimar.ee3.util;

import com.pahimar.ee3.init.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class ItemUtil {

    private static double rand;

    public static void dropMiniumShard(final EntityPlayer player, final EntityLivingBase entity) {
        if (EntityHelper.isHostileEntity(entity)) {
            ItemUtil.rand = Math.random();
            if (ItemUtil.rand < 0.15) {
                entity.dropItem(ModItems.shardMinium, 1);
                
            }
        }
    }

}
