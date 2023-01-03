package com.pahimar.ee3.inventory;

import com.pahimar.ee3.inventory.element.IElementButtonHandler;
import com.pahimar.ee3.inventory.element.IElementSliderHandler;
import com.pahimar.ee3.inventory.element.IElementTextFieldHandler;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerPortableTransmutation extends ContainerEE
    implements IElementTextFieldHandler, IElementSliderHandler, IElementButtonHandler {
    @Override
    public boolean canInteractWith(EntityPlayer arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void handleElementButtonClick(String elementName, int mouseButton) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleElementSliderUpdate(String elementName, int elementValue) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleElementTextFieldUpdate(String elementName, String updatedText) {
        // TODO Auto-generated method stub
    }
}
