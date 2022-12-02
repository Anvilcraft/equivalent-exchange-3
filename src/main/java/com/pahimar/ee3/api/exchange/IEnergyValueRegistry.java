package com.pahimar.ee3.api.exchange;

import java.util.Set;

import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy.Phase;

import net.minecraft.item.ItemStack;

public interface IEnergyValueRegistry {
    
    boolean hasEnergyValue(Object object, boolean strict);

    EnergyValue getEnergyValue(Object object, boolean strict);

    EnergyValue getEnergyValueForStack(Object object, boolean strict);

    Set<ItemStack> getStacksInRange(EnergyValue start, EnergyValue finish);

    void setEnergyValue(Object object, EnergyValue energyValue, Phase phase);

}
