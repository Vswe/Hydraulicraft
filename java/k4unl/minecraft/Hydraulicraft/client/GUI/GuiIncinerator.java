package k4unl.minecraft.Hydraulicraft.client.GUI;

import k4unl.minecraft.Hydraulicraft.TileEntities.TileHydraulicFrictionIncinerator;
import k4unl.minecraft.Hydraulicraft.baseClasses.MachineGUI;
import k4unl.minecraft.Hydraulicraft.containers.ContainerIncinerator;
import k4unl.minecraft.Hydraulicraft.lib.config.ModInfo;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiIncinerator extends MachineGUI {

	private static ResourceLocation resLoc = new ResourceLocation(ModInfo.LID,"textures/gui/incinerator.png");
	TileHydraulicFrictionIncinerator incinerator;
	
	
	public GuiIncinerator(InventoryPlayer invPlayer, TileHydraulicFrictionIncinerator _incinerator) {
		super(_incinerator, new ContainerIncinerator(invPlayer, _incinerator), resLoc);
		incinerator = _incinerator;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		fontRenderer.drawString(incinerator.getInvName(), 8, 6, 0xFFFFFF);

		drawFluidAndPressure();
		
		if(incinerator.isSmelting()){
			ItemStack smeltingItem = incinerator.getSmeltingItem();
			ItemStack targetItem = incinerator.getTargetItem();

			int done = incinerator.getSmeltingTicks();
			int startX = 40;
			int maxTicks = 200;
			int targetX = 118;
			int travelPath = targetX - startX;
			float percentage = (float)done / (float)maxTicks;
			int xPos = startX + (int) (travelPath * percentage);

            //TODO decide whether one should have a wobbling effect or not (the last parameter)
            IconRenderer.drawMergedIcon(xPos, 19, zLevel, smeltingItem, targetItem, percentage, smeltingItem.stackSize % 2 == 0);
		}
		
		
		checkTooltips(mouseX, mouseY);
		
	}





}
