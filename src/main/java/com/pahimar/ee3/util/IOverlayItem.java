package com.pahimar.ee3.util;

import com.pahimar.ee3.client.renderer.IOverlayItemRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IOverlayItem {
    @SideOnly(Side.CLIENT)
    IOverlayItemRenderer getOverlayItemRenderer();
}
