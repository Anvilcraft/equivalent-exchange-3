package com.pahimar.ee3.api.exchange;

import java.util.Set;

import com.pahimar.ee3.knowledge.PlayerKnowledge;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface ITransmutationContainer extends IInventory {
    int getXCoord();

    int getYCoord();

    int getZCoord();

    Set<ItemStack> getPlayerKnowledge();

    void consumeInventoryForEnergyValue(ItemStack outputItemStack);

    EnergyValue getAvailableEnergy();

    void updateEnergyValueFromInventory();

    void handlePlayerKnowledgeUpdate(PlayerKnowledge playerKnowledge);
}
