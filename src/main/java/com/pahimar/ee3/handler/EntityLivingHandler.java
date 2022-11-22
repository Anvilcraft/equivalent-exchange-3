package com.pahimar.ee3.handler;

import com.pahimar.ee3.util.ItemUtil;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class EntityLivingHandler {
    
    @SubscribeEvent
    public void onEntityLivingDeath(LivingDeathEvent event) {
        if (event.source.getDamageType().equals("player")) {
            ItemUtil.dropMiniumShard((EntityPlayer)event.source.getSourceOfDamage(), event.entityLiving);
        } 
        if (event.source.getSourceOfDamage() instanceof EntityArrow && ((EntityArrow)event.source.getSourceOfDamage()).shootingEntity != null && ((EntityArrow)event.source.getSourceOfDamage()).shootingEntity instanceof EntityPlayer) {
            ItemUtil.dropMiniumShard((EntityPlayer)((EntityArrow)event.source.getSourceOfDamage()).shootingEntity, event.entityLiving);
        }
    } 

}
