package com.pahimar.ee3.exchange;

import java.util.Set;

import com.pahimar.ee3.api.exchange.EnergyValue;
import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy.Phase;

import moze_intel.projecte.api.proxy.IEMCProxy;

import com.pahimar.ee3.api.exchange.IEnergyValueRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EMCRegistry implements IEnergyValueRegistry {

    public static EMCRegistry INSTANCE = null;
    private IEMCProxy emc;

    public EMCRegistry(IEMCProxy proxy) {
        emc = proxy;
    }

    @Override
    public boolean hasEnergyValue(Object object, boolean strict) {
        WrappedStack wrapped = WrappedStack.wrap(object);
        if (wrapped == null) return false;
        object = wrapped.getWrappedObject();
        if (object instanceof Item) {
            return emc.hasValue((Item) object);
        } else if (object instanceof ItemStack) {
            return emc.hasValue((ItemStack) object);
        } else if (object instanceof Block) {
            return emc.hasValue((Block) object);
        }
        return false;
    }

    @Override
    public EnergyValue getEnergyValue(Object object, boolean strict) {
        WrappedStack wrapped = WrappedStack.wrap(object);
        if (wrapped == null) return null;
        object = wrapped.getWrappedObject();
        if (object instanceof Item) {
            return new EnergyValue(emc.getValue((Item) object));
        } else if (object instanceof ItemStack) {
            return new EnergyValue(emc.getValue((ItemStack) object));
        } else if (object instanceof Block) {
            return new EnergyValue(emc.getValue((Block) object));
        }
        return null;
    }

    @Override
    public EnergyValue getEnergyValueForStack(Object object, boolean strict) {
        WrappedStack wrapped = WrappedStack.wrap(object);
        if (wrapped == null) return null;
        EnergyValue value = getEnergyValue(object, strict);
        if (value == null) return null;
        return new EnergyValue(wrapped.getStackSize() * value.getValue());
    }

    @Override
    public Set<ItemStack> getStacksInRange(EnergyValue start, EnergyValue finish) {
        return null;
    }

    @Override
    public void setEnergyValue(Object object, EnergyValue energyValue, Phase phase) {
        if (phase != Phase.PRE_CALCULATION) return;
        if (object instanceof Item) {
            emc.registerCustomEMC(new ItemStack((Item) object), (int) energyValue.getValue());
        } else if (object instanceof ItemStack) {
            emc.registerCustomEMC((ItemStack) object, (int) energyValue.getValue());
        } else if (object instanceof Block) {
            emc.registerCustomEMC(new ItemStack((Block) object), (int) energyValue.getValue());
        }
    }
    
}
