package com.pahimar.ee3.test;

import com.pahimar.ee3.init.ModBlocks;
import com.pahimar.ee3.init.ModItems;
import com.pahimar.ee3.reference.Files;
import net.minecraft.item.ItemStack;

import java.io.File;

public class EETestSuite extends EnergyValueTestSuite {

    public EETestSuite() {
        super();
    }

    public EETestSuite build() {

        add(ModBlocks.calcinator, 772);
        add(ModBlocks.aludel, 1794);
        add(ModBlocks.glassBell, 7);
        add(ModBlocks.researchStation, 520);
        add(new ItemStack(ModBlocks.alchemicalChest, 1, 0), 576);
        add(new ItemStack(ModBlocks.alchemicalChest, 1, 1), 16448);
        add(new ItemStack(ModBlocks.alchemicalChest, 1, 2), 65600);
        add(ModBlocks.chalkBlock, 320);
        add(new ItemStack(ModBlocks.alchemicalFuelBlock, 1, 0), 18720);
        add(new ItemStack(ModBlocks.alchemicalFuelBlock, 1, 1), 147744);
        add(new ItemStack(ModBlocks.alchemicalFuelBlock, 1, 2), 1179936);
        add(ModBlocks.ashInfusedStone, 2);
        add(ModBlocks.ashInfusedStoneSlab, 1);
        add(new ItemStack(ModItems.alchemicalBag, 1, 0), 560);
        add(new ItemStack(ModItems.alchemicalBag, 1, 1), 16432);
        add(new ItemStack(ModItems.alchemicalBag, 1, 2), 65584);
        add(new ItemStack(ModItems.alchemicalDust, 1, 0), 1);
        add(new ItemStack(ModItems.alchemicalDust, 1, 1), 64);
        add(new ItemStack(ModItems.alchemicalDust, 1, 2), 2048);
        add(new ItemStack(ModItems.alchemicalDust, 1, 3), 8192);
        add(new ItemStack(ModItems.alchemicalFuel, 1, 0), 2080);
        add(new ItemStack(ModItems.alchemicalFuel, 1, 1), 16416);
        add(new ItemStack(ModItems.alchemicalFuel, 1, 2), 131104);
        add(ModItems.stoneInert, 3076);
        add(ModItems.shardMinium, 8192);
        add(ModItems.stoneMinium, 68612);
        add(ModItems.stonePhilosophers, null);
        add(ModItems.chalk, 80);
        add(new ItemStack(ModItems.alchemicalUpgrade, 1, 0), null);
        add(new ItemStack(ModItems.alchemicalUpgrade, 1, 1), null);
        add(new ItemStack(ModItems.alchemicalUpgrade, 1, 2), null);
        add(ModItems.alchenomicon, 8352);

        return this;
    }

    public void save() {
        this.save(new File(Files.globalTestDirectory, "ee3-v1710-test-suite.json"));
    }
}
