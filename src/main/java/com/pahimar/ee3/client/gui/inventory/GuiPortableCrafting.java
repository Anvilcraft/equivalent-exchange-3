package com.pahimar.ee3.client.gui.inventory;

import com.pahimar.ee3.inventory.ContainerPortableCrafting;
import com.pahimar.ee3.reference.Names;
import com.pahimar.ee3.util.NBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiPortableCrafting extends GuiContainer {
    public GuiPortableCrafting(EntityPlayer player, World world, int x, int y, int z) {
        super(new ContainerPortableCrafting(player.inventory, world, x, y, z));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float arg0, int arg1, int arg2) {
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        this.mc.renderEngine.bindTexture(
            new ResourceLocation("textures/gui/container/crafting_table.png")
        );
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(
            StatCollector.translateToLocal((String) "container.crafting"), 28, 6, 0x404040
        );
        this.fontRendererObj.drawString(
            StatCollector.translateToLocal((String) "container.inventory"),
            8,
            this.ySize - 96 + 2,
            0x404040
        );
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if (this.mc.thePlayer != null) {
            for (ItemStack itemStack : this.mc.thePlayer.inventory.mainInventory) {
                if (itemStack == null
                    || !NBTHelper.hasKey(itemStack, Names.NBT.CRAFTING_GUI_OPEN))
                    continue;
                NBTHelper.removeTag(itemStack, Names.NBT.CRAFTING_GUI_OPEN);
            }
        }
    }
}
