package com.pahimar.ee3.util;

import java.util.List;

import com.pahimar.ee3.reference.ToolMode;
import net.minecraft.item.ItemStack;

public interface IModalTool {
    public abstract List<ToolMode> getAvailableToolModes();

    public abstract ToolMode getCurrentToolMode(ItemStack itemStack);

    public abstract void setToolMode(ItemStack itemStack, ToolMode toolMode);

    public abstract void changeToolMode(ItemStack itemStack);
}
