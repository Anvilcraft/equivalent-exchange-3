package com.pahimar.ee3.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.pahimar.ee3.exchange.OreStack;
import com.pahimar.ee3.exchange.WrappedStack;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Equivalent-Exchange-3
 * <p/>
 * RecipeHelper
 *
 * @author pahimar
 */
public class RecipeHelper {
    /**
     * Returns a list of elements that constitute the input in a crafting recipe
     *
     * @param recipe The IRecipe being examined
     * @return List of elements that constitute the input of the given IRecipe. Could be
     *     an ItemStack or an Arraylist
     */
    public static List<WrappedStack> getRecipeInputs(IRecipe recipe) {
        ArrayList<WrappedStack> recipeInputs = new ArrayList<WrappedStack>();

        if (recipe instanceof ShapedRecipes) {
            ShapedRecipes shapedRecipe = (ShapedRecipes) recipe;

            for (int i = 0; i < shapedRecipe.recipeItems.length; i++) {
                if (shapedRecipe.recipeItems[i] instanceof ItemStack) {
                    ItemStack itemStack = shapedRecipe.recipeItems[i].copy();

                    if (itemStack.stackSize > 1) {
                        itemStack.stackSize = 1;
                    }

                    recipeInputs.add(WrappedStack.wrap(itemStack));
                }
            }
        } else if (recipe instanceof ShapelessRecipes) {
            ShapelessRecipes shapelessRecipe = (ShapelessRecipes) recipe;

            for (Object object : shapelessRecipe.recipeItems) {
                if (object instanceof ItemStack) {
                    ItemStack itemStack = ((ItemStack) object).copy();

                    if (itemStack.stackSize > 1) {
                        itemStack.stackSize = 1;
                    }

                    recipeInputs.add(WrappedStack.wrap(itemStack));
                }
            }
        } else if (recipe instanceof ShapedOreRecipe) {
            ShapedOreRecipe shapedOreRecipe = (ShapedOreRecipe) recipe;

            if (validateOreDictionaryRecipe(Arrays.asList(shapedOreRecipe.getInput()))) {
                for (int i = 0; i < shapedOreRecipe.getInput().length; i++) {
                    /*
                     * If the element is a list, then it is an OreStack
                     */
                    if (shapedOreRecipe.getInput()[i] instanceof ArrayList) {
                        WrappedStack oreStack
                            = WrappedStack.wrap(shapedOreRecipe.getInput()[i]);

                        if (oreStack != null
                            && oreStack.getWrappedObject() instanceof OreStack) {
                            recipeInputs.add(oreStack);
                        }
                    } else if (shapedOreRecipe.getInput()[i] instanceof ItemStack) {
                        ItemStack itemStack
                            = ((ItemStack) shapedOreRecipe.getInput()[i]).copy();

                        if (itemStack.stackSize > 1) {
                            itemStack.stackSize = 1;
                        }

                        recipeInputs.add(WrappedStack.wrap(itemStack));
                    }
                }
            }
        } else if (recipe instanceof ShapelessOreRecipe) {
            ShapelessOreRecipe shapelessOreRecipe = (ShapelessOreRecipe) recipe;

            if (validateOreDictionaryRecipe(shapelessOreRecipe.getInput())) {
                for (Object object : shapelessOreRecipe.getInput()) {
                    if (object instanceof ArrayList) {
                        recipeInputs.add(WrappedStack.wrap(object));
                    } else if (object instanceof ItemStack) {
                        ItemStack itemStack = ((ItemStack) object).copy();

                        if (itemStack.stackSize > 1) {
                            itemStack.stackSize = 1;
                        }

                        recipeInputs.add(WrappedStack.wrap(itemStack));
                    }
                }
            }
        }

        return collateInputStacks(recipeInputs);
    }

    /**
     * Collates an uncollated, unsorted List of Objects into a sorted, collated List of
     * WrappedStacks
     *
     * @param uncollatedStacks List of objects for collating
     * @return A sorted, collated List of WrappedStacks
     */
    public static List<WrappedStack> collateInputStacks(List<?> uncollatedStacks) {
        List<WrappedStack> collatedStacks = new ArrayList<WrappedStack>();

        WrappedStack stack;
        boolean found;

        for (Object object : uncollatedStacks) {
            found = false;

            if (WrappedStack.canBeWrapped(object)) {
                stack = WrappedStack.wrap(object);

                if (collatedStacks.isEmpty()) {
                    collatedStacks.add(stack);
                } else {
                    for (WrappedStack collatedStack : collatedStacks) {
                        if (collatedStack.getWrappedObject() != null) {
                            if (stack.getWrappedObject() instanceof ItemStack
                                && collatedStack.getWrappedObject()
                                        instanceof ItemStack) {
                                if (ItemStackUtils.equals(
                                        (ItemStack) stack.getWrappedObject(),
                                        (ItemStack) collatedStack.getWrappedObject()
                                    )) {
                                    collatedStack.setStackSize(
                                        collatedStack.getStackSize()
                                        + stack.getStackSize()
                                    );
                                    found = true;
                                }
                            }
                            else if (stack.getWrappedObject() instanceof OreStack && collatedStack.getWrappedObject() instanceof OreStack)
                            {
                                if (OreStack.compareOreNames(
                                        (OreStack) stack.getWrappedObject(),
                                        (OreStack) collatedStack.getWrappedObject()
                                    )) {
                                    collatedStack.setStackSize(
                                        collatedStack.getStackSize()
                                        + stack.getStackSize()
                                    );
                                    found = true;
                                }
                            }
                        }
                    }

                    if (!found) {
                        collatedStacks.add(stack);
                    }
                }
            }
        }
        Collections.sort(collatedStacks);
        return collatedStacks;
    }

    /**
     * Validates the list of recipe inputs for an OreDictionary recipe to ensure that if
     * there is an OreDictionary entry found in the recipe (a list) that it is not empty
     * (that there are ItemStacks associated with that particular OreDictionary entry)
     *
     * @param objects a list of recipe inputs for an OreDictionary recipe
     * @return true if there exists no "invalid" (empty) OreDictionary entries in the
     *     provided list of recipe inputs
     */
    // FIXME This is not working as intended
    private static boolean validateOreDictionaryRecipe(List objects) {
        for (Object object : objects) {
            if (object != null) {
                if (!WrappedStack.canBeWrapped(object)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void addRecipe(final ItemStack output, final Object... input) {
        GameRegistry.addShapelessRecipe(output, input);
    }

    public static void addRecipe(
        final ItemStack output, final ItemStack transmutationStone, final Object... input
    ) {
        final Object[] inputs = new Object[input.length + 1];
        System.arraycopy(input, 0, inputs, 0, input.length);
        inputs[input.length] = transmutationStone;
        addRecipe(output, inputs);
    }

    public static void addRecipe(final Block output, final Object... input) {
        addRecipe(new ItemStack(output), input);
    }

    public static void
    addRecipe(final Block output, final int count, final Object... input) {
        addRecipe(new ItemStack(output, count), input);
    }

    public static void addRecipe(final Item output, final Object... input) {
        addRecipe(new ItemStack(output), input);
    }

    public static void
    addRecipe(final Item output, final int count, final Object... input) {
        addRecipe(new ItemStack(output, count), input);
    }

    public static Object[] getMetaCycle(final Object input, final int n) {
        final ArrayList<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            final ItemStack stack = ItemStackUtils.convertObjectToItemStack(input);
            stack.setItemDamage(i);
            list.add(stack);
        }
        return list.toArray();
    }

    public static Object[] getMetaCycle(
        final Object input, final int n, final int... excludedMeta
    ) {
        final ArrayList<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            for (final int j : excludedMeta) {
                if (i == j) {
                    ++i;
                }
            }
            if (i >= n) {
                break;
            }
            final ItemStack stack = ItemStackUtils.convertObjectToItemStack(input);
            stack.setItemDamage(i);
            list.add(stack);
        }
        return list.toArray();
    }

    public static void addSmeltingRecipe(
        final ItemStack input, final ItemStack stone, final ItemStack fuel
    ) {
        final ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(input);
        if (input == null || input.getItem() == null || result == null) {
            return;
        }
        final Object[] list = new Object[9];
        list[0] = stone;
        list[1] = fuel;
        for (int i = 2; i < 9; ++i) {
            list[i] = new ItemStack(input.getItem(), 1, input.getItemDamage());
        }
        if (result.stackSize * 7 <= result.getItem().getItemStackLimit()) {
            GameRegistry.addShapelessRecipe(
                new ItemStack(
                    result.getItem(), result.stackSize * 7, result.getItemDamage()
                ),
                list
            );
        } else {
            GameRegistry.addShapelessRecipe(
                new ItemStack(
                    result.getItem(),
                    result.getItem().getItemStackLimit(),
                    result.getItemDamage()
                ),
                list
            );
        }
    }
}
