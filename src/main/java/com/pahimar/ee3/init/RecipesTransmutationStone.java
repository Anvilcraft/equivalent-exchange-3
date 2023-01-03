package com.pahimar.ee3.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pahimar.ee3.handler.EquivalencyHandler;
import com.pahimar.ee3.util.ItemStackUtils;
import com.pahimar.ee3.util.RecipeHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class RecipesTransmutationStone {
    private static ItemStack philStone;
    private static ItemStack miniumStone;
    public static List<ItemStack> transmutationStones;
    private static ItemStack anyCoal;
    private static ItemStack anyWood;
    private static ItemStack anyPlank;
    private static ItemStack anySandStone;
    private static ItemStack dyeBoneMeal;

    public static void init() {
        initEquivalencyList();
        for (ItemStack stone : RecipesTransmutationStone.transmutationStones) {
            initTransmutationRecipes(stone);
            initEquivalenceRecipes(stone);
            initDestructorRecipes(stone);
            initPortableSmeltingRecipes(stone);
        }
    }

    public static void initTransmutationRecipes(ItemStack transmutationStone) {
        RecipeHelper.addRecipe(
            Items.flint,
            transmutationStone,
            Blocks.cobblestone,
            Blocks.cobblestone,
            Blocks.cobblestone,
            Blocks.cobblestone
        );
        RecipeHelper.addRecipe(
            new ItemStack(Blocks.cobblestone, 4), transmutationStone, Items.flint
        );
        RecipeHelper.addRecipe(
            Blocks.gravel,
            transmutationStone,
            Blocks.dirt,
            Blocks.dirt,
            Blocks.dirt,
            Blocks.dirt
        );
        RecipeHelper.addRecipe(
            new ItemStack(Blocks.dirt, 4), transmutationStone, Blocks.gravel
        );
        RecipeHelper.addRecipe(
            new ItemStack(Blocks.sand, 4),
            transmutationStone,
            RecipesTransmutationStone.anySandStone
        );
        RecipeHelper.addRecipe(
            Blocks.planks, transmutationStone, Items.stick, Items.stick
        );
        RecipeHelper.addRecipe(
            Blocks.log,
            transmutationStone,
            RecipesTransmutationStone.anyPlank,
            RecipesTransmutationStone.anyPlank,
            RecipesTransmutationStone.anyPlank,
            RecipesTransmutationStone.anyPlank
        );
        RecipeHelper.addRecipe(
            Items.clay_ball,
            transmutationStone,
            Blocks.gravel,
            Blocks.gravel,
            Blocks.gravel,
            Blocks.gravel
        );
        RecipeHelper.addRecipe(
            Items.clay_ball,
            transmutationStone,
            RecipesTransmutationStone.anySandStone,
            RecipesTransmutationStone.anySandStone,
            RecipesTransmutationStone.anySandStone,
            RecipesTransmutationStone.anySandStone
        );
        RecipeHelper.addRecipe(
            Items.clay_ball,
            transmutationStone,
            Items.flint,
            Items.flint,
            Items.flint,
            Items.flint
        );
        RecipeHelper.addRecipe(
            new ItemStack(Blocks.gravel, 4), transmutationStone, Items.clay_ball
        );
        RecipeHelper.addRecipe(
            Blocks.obsidian,
            transmutationStone,
            RecipesTransmutationStone.anyWood,
            RecipesTransmutationStone.anyWood
        );
        RecipeHelper.addRecipe(
            new ItemStack(Blocks.log, 2), transmutationStone, Blocks.obsidian
        );
        RecipeHelper.addRecipe(
            new ItemStack(Items.clay_ball, 4), transmutationStone, Blocks.clay
        );
        RecipeHelper.addRecipe(
            Items.iron_ingot,
            transmutationStone,
            Blocks.obsidian,
            Blocks.obsidian,
            Blocks.obsidian,
            Blocks.obsidian
        );
        RecipeHelper.addRecipe(
            Items.iron_ingot,
            transmutationStone,
            Blocks.clay,
            Blocks.clay,
            Blocks.clay,
            Blocks.clay
        );
        RecipeHelper.addRecipe(
            new ItemStack(Blocks.clay, 4), transmutationStone, Items.iron_ingot
        );
        RecipeHelper.addRecipe(
            Items.gold_ingot,
            transmutationStone,
            Items.iron_ingot,
            Items.iron_ingot,
            Items.iron_ingot,
            Items.iron_ingot,
            Items.iron_ingot,
            Items.iron_ingot,
            Items.iron_ingot,
            Items.iron_ingot
        );
        RecipeHelper.addRecipe(
            new ItemStack(Items.iron_ingot, 8), transmutationStone, Items.gold_ingot
        );
        RecipeHelper.addRecipe(
            Items.diamond,
            transmutationStone,
            Items.gold_ingot,
            Items.gold_ingot,
            Items.gold_ingot,
            Items.gold_ingot
        );
        RecipeHelper.addRecipe(
            new ItemStack(Items.gold_ingot, 4), transmutationStone, Items.diamond
        );
        RecipeHelper.addRecipe(
            Blocks.gold_block,
            transmutationStone,
            Blocks.iron_block,
            Blocks.iron_block,
            Blocks.iron_block,
            Blocks.iron_block,
            Blocks.iron_block,
            Blocks.iron_block,
            Blocks.iron_block,
            Blocks.iron_block
        );
        RecipeHelper.addRecipe(
            new ItemStack(Blocks.iron_block, 8), transmutationStone, Blocks.gold_block
        );
        RecipeHelper.addRecipe(
            Blocks.diamond_block,
            transmutationStone,
            Blocks.gold_block,
            Blocks.gold_block,
            Blocks.gold_block,
            Blocks.gold_block
        );
        RecipeHelper.addRecipe(
            new ItemStack(Blocks.gold_block, 4), transmutationStone, Blocks.diamond_block
        );
        RecipeHelper.addRecipe(
            Items.ender_pearl,
            transmutationStone,
            Items.iron_ingot,
            Items.iron_ingot,
            Items.iron_ingot,
            Items.iron_ingot
        );
        RecipeHelper.addRecipe(
            new ItemStack(Items.iron_ingot, 4), transmutationStone, Items.ender_pearl
        );
    }

    public static void initEquivalenceRecipes(ItemStack stone) {
        for (ArrayList<ItemStack> itemStackList :
             EquivalencyHandler.instance().getAllLists()) {
            ItemStack[] currentList = new ItemStack[itemStackList.size()];
            currentList = itemStackList.toArray(currentList);
            for (int i = 0; i < currentList.length; ++i) {
                int outputI = (i == currentList.length - 1) ? 0 : (i + 1);
                RecipeHelper.addRecipe(
                    currentList[outputI],
                    stone,
                    ItemStackUtils.convertSingleStackToPluralStacks(currentList[i])
                );
            }
        }
    }

    public static void initReconstructiveRecipes(ItemStack stone) {
        RecipeHelper.addRecipe(
            Items.bone,
            stone,
            RecipesTransmutationStone.dyeBoneMeal,
            RecipesTransmutationStone.dyeBoneMeal,
            RecipesTransmutationStone.dyeBoneMeal
        );
        RecipeHelper.addRecipe(
            Items.blaze_rod, stone, Items.blaze_powder, Items.blaze_powder
        );
    }

    public static void initDestructorRecipes(ItemStack stone) {
        RecipeHelper.addRecipe(Blocks.cobblestone, stone, Blocks.stone);
        RecipeHelper.addRecipe(Blocks.sand, stone, Blocks.glass);
        RecipeHelper.addRecipe(
            new ItemStack(Items.glowstone_dust, 4), stone, Blocks.glowstone
        );
        RecipeHelper.addRecipe(new ItemStack(Items.brick, 4), stone, Blocks.brick_block);
    }

    public static void initPortableSmeltingRecipes(ItemStack stone) {
        Map furnaceMap = FurnaceRecipes.smelting().getSmeltingList();
        Iterator<ItemStack> iterFurnaceKeyMap = furnaceMap.keySet().iterator();
        while (iterFurnaceKeyMap.hasNext()) {
            ItemStack furnaceMapKey = iterFurnaceKeyMap.next();
            RecipeHelper.addSmeltingRecipe(
                furnaceMapKey, stone, RecipesTransmutationStone.anyCoal
            );
        }
    }

    protected static void initEquivalencyList() {
        EquivalencyHandler.instance().addObjects(
            Blocks.sand, Blocks.dirt, Blocks.cobblestone, Blocks.grass
        );
        EquivalencyHandler.instance().addObjects(Blocks.yellow_flower, Blocks.red_flower);
        EquivalencyHandler.instance().addObjects(
            Blocks.red_mushroom, Blocks.brown_mushroom
        );
        EquivalencyHandler.instance().addObjects(Items.pumpkin_seeds, Items.melon_seeds);
        EquivalencyHandler.instance().addObjects(Blocks.pumpkin, Blocks.melon_block);
        EquivalencyHandler.instance().addObjects(
            Blocks.spruce_stairs, Blocks.birch_stairs, Blocks.jungle_stairs
        );
        EquivalencyHandler.instance().addObjects(
            new ItemStack(Items.paper, 3), new ItemStack(Items.reeds, 3)
        );
        EquivalencyHandler.instance().addObjects(
            new ItemStack(Items.flint, 2),
            new ItemStack(Blocks.gravel, 2),
            new ItemStack(Blocks.sandstone, 2, 0),
            new ItemStack(Blocks.sandstone, 2, 1),
            new ItemStack(Blocks.sandstone, 2, 2)
        );
        EquivalencyHandler.instance().addObjects(
            RecipeHelper.getMetaCycle(Blocks.planks, 4)
        );
        EquivalencyHandler.instance().addObjects(RecipeHelper.getMetaCycle(Blocks.log, 4)
        );
        EquivalencyHandler.instance().addObjects(
            RecipeHelper.getMetaCycle(Blocks.wooden_slab, 4)
        );
        EquivalencyHandler.instance().addObjects(
            RecipeHelper.getMetaCycle(Blocks.sapling, 4)
        );
        EquivalencyHandler.instance().addObjects(
            RecipeHelper.getMetaCycle(Blocks.leaves, 4)
        );
        EquivalencyHandler.instance().addObjects(
            RecipeHelper.getMetaCycle(Blocks.tallgrass, 3)
        );
        EquivalencyHandler.instance().addObjects(
            RecipeHelper.getMetaCycle(Blocks.wool, 16)
        );
        EquivalencyHandler.instance().addObjects(
            RecipeHelper.getMetaCycle(Blocks.stonebrick, 4)
        );
        EquivalencyHandler.instance().addObjects(
            RecipeHelper.getMetaCycle(Items.dye, 16, 3, 4, 15)
        );
    }

    static {
        RecipesTransmutationStone.philStone
            = new ItemStack(ModItems.stonePhilosophers, 1, 32767);
        RecipesTransmutationStone.miniumStone
            = new ItemStack(ModItems.stoneMinium, 1, 32767);
        RecipesTransmutationStone.transmutationStones = Arrays.asList(
            RecipesTransmutationStone.miniumStone, RecipesTransmutationStone.philStone
        );
        RecipesTransmutationStone.anyCoal = new ItemStack(Items.coal, 1, 32767);
        RecipesTransmutationStone.anyWood = new ItemStack(Blocks.log, 1, 32767);
        RecipesTransmutationStone.anyPlank = new ItemStack(Blocks.planks, 1, 32767);
        RecipesTransmutationStone.anySandStone
            = new ItemStack(Blocks.sandstone, 1, 32767);
        RecipesTransmutationStone.dyeBoneMeal = new ItemStack(Items.dye, 1, 15);
    }
}
