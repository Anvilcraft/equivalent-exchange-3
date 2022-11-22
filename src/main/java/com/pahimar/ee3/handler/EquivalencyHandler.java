package com.pahimar.ee3.handler;

import java.util.ArrayList;

import com.pahimar.ee3.util.ItemStackUtils;
import com.pahimar.ee3.util.LogHelper;

import org.apache.logging.log4j.Level;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EquivalencyHandler
{
    private static final EquivalencyHandler instance;
    private static ArrayList<ArrayList<ItemStack>> equivalencyList;
    
    public static EquivalencyHandler instance() {
        return EquivalencyHandler.instance;
    }
    
    public ArrayList<ArrayList<ItemStack>> getAllLists() {
        return EquivalencyHandler.equivalencyList;
    }
    
    public void addObjects(final Object obj1, final Object obj2) {
        final ItemStack stack1 = ItemStackUtils.convertObjectToItemStack(obj1);
        final ItemStack stack2 = ItemStackUtils.convertObjectToItemStack(obj2);
        ArrayList<ItemStack> currentList = new ArrayList();
        final Integer stack1Index = this.getIndexInList(stack1);
        final Integer stack2Index = this.getIndexInList(stack2);
        if (stack1Index != null && stack2Index != null) {
            return;
        }
        if (stack1Index != null && stack2Index == null) {
            currentList = EquivalencyHandler.equivalencyList.get(stack1Index);
            currentList.add(stack2);
            EquivalencyHandler.equivalencyList.set(stack1Index, currentList);
        }
        else if (stack1Index == null && stack2Index != null) {
            currentList = EquivalencyHandler.equivalencyList.get(stack2Index);
            currentList.add(stack1);
            EquivalencyHandler.equivalencyList.set(stack2Index, currentList);
        }
        else if (stack1Index == null && stack2Index == null) {
            currentList.add(stack1);
            currentList.add(stack2);
            EquivalencyHandler.equivalencyList.add(currentList);
        }
    }
    
    public void addObjects(final Object... objList) {
        if (objList.length < 2) {
            return;
        }
        for (int i = 0; i < objList.length - 1; ++i) {
            this.addObjects(objList[i], objList[i + 1]);
        }
    }
    
    public Integer getIndexInList(final Object obj) {
        final ItemStack checkStack = ItemStackUtils.convertObjectToItemStack(obj);
        for (int i = 0; i < EquivalencyHandler.equivalencyList.size(); ++i) {
            final ArrayList<ItemStack> currentList = EquivalencyHandler.equivalencyList.get(i);
            for (final ItemStack currentStack : currentList) {
                if (ItemStack.areItemStacksEqual(checkStack, currentStack)) {
                    return new Integer(i);
                }
            }
        }
        return null;
    }
    
    public Integer getIndexInList(final Item id, final int meta) {
        for (int i = 0; i < EquivalencyHandler.equivalencyList.size(); ++i) {
            final ArrayList<ItemStack> currentList = EquivalencyHandler.equivalencyList.get(i);
            for (final ItemStack currentStack : currentList) {
                if (id == currentStack.getItem() && meta == currentStack.getItemDamage()) {
                    return new Integer(i);
                }
            }
        }
        return null;
    }
    
    public ArrayList<ItemStack> getEquivalencyList(final Object obj) {
        final ItemStack checkStack = ItemStackUtils.convertObjectToItemStack(obj);
        if (checkStack == null) {
            return null;
        }
        for (final ArrayList<ItemStack> list : EquivalencyHandler.equivalencyList) {
            for (final ItemStack currentStack : list) {
                if (ItemStack.areItemStacksEqual(checkStack, currentStack)) {
                    return list;
                }
            }
        }
        return null;
    }
    
    public ArrayList<ItemStack> getEquivalencyList(final Item id, final int meta) {
        for (final ArrayList<ItemStack> list : EquivalencyHandler.equivalencyList) {
            for (final ItemStack currentStack : list) {
                if (id == currentStack.getItem() && meta == currentStack.getItemDamage()) {
                    return list;
                }
            }
        }
        return null;
    }
    
    public ItemStack getNextInList(final Object obj) {
        final ItemStack checkStack = ItemStackUtils.convertObjectToItemStack(obj);
        if (checkStack != null) {
            return this.getNextInList(checkStack.getItem(), checkStack.getItemDamage());
        }
        return null;
    }
    
    public ItemStack getNextInList(final Item id, final int meta) {
        final ArrayList<ItemStack> list = this.getEquivalencyList(id, meta);
        ItemStack returnStack = null;
        int i = 0;
        if (list != null) {
            if (list.size() == 1) {
                return list.get(i);
            }
            while (i < list.size()) {
                final ItemStack currentStack = list.get(i);
                if (id == currentStack.getItem() && meta == currentStack.getItemDamage()) {
                    returnStack = list.get((i + 1) % list.size());
                    break;
                }
                ++i;
            }
        }
        return returnStack;
    }
    
    public ItemStack getPrevInList(final Object obj) {
        final ItemStack checkStack = ItemStackUtils.convertObjectToItemStack(obj);
        if (checkStack != null) {
            return this.getPrevInList(checkStack.getItem(), checkStack.getItemDamage());
        }
        return null;
    }
    
    public ItemStack getPrevInList(final Item id, final int meta) {
        final ArrayList<ItemStack> list = this.getEquivalencyList(id, meta);
        ItemStack returnStack = null;
        int i = 0;
        if (list != null) {
            if (list.size() == 1) {
                return list.get(i);
            }
            while (i < list.size()) {
                final ItemStack currentStack = list.get(i);
                if (id == currentStack.getItem() && meta == currentStack.getItemDamage()) {
                    final int index = (i - 1 + list.size()) % list.size();
                    returnStack = list.get(index);
                    break;
                }
                ++i;
            }
        }
        return returnStack;
    }
    
    public boolean areEquivalent(final Object obj1, final Object obj2) {
        return this.getEquivalencyList(obj1) != null && this.getEquivalencyList(obj2) != null && ((ItemStackUtils.convertObjectToItemStack(obj1).getItem() == ItemStackUtils.convertObjectToItemStack(obj2).getItem() && ItemStackUtils.convertObjectToItemStack(obj1).getItemDamage() == ItemStackUtils.convertObjectToItemStack(obj2).getItemDamage()) || this.getEquivalencyList(obj1).equals(this.getEquivalencyList(obj2)));
    }
    
    public boolean areWorldEquivalent(final Object obj1, final Object obj2) {
        final ItemStack first = ItemStackUtils.convertObjectToItemStack(obj1);
        if (first == null) {
            return false;
        }
        final ItemStack second = ItemStackUtils.convertObjectToItemStack(obj2);
        return second != null && (this.getEquivalencyList(first.getItem(), first.getItemDamage()) != null && this.getEquivalencyList(second.getItem(), second.getItemDamage()) != null) && ((first.getItem() == second.getItem() && first.getItemDamage() == second.getItemDamage()) || this.getEquivalencyList(first.getItem(), first.getItemDamage()).equals(this.getEquivalencyList(second.getItem(), second.getItemDamage())));
    }
    
    public void debug() {
        int i = 0;
        for (final ArrayList list : EquivalencyHandler.equivalencyList) {
            LogHelper.log(Level.INFO, "equivalencyList[" + i + "]: " + list.toString());
            ++i;
        }
    }
    
    static {
        instance = new EquivalencyHandler();
        EquivalencyHandler.equivalencyList = new ArrayList();
    }
}
