package pet.minecraft.Hydraulicraft.client.GUI;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import pet.minecraft.Hydraulicraft.TileEntities.TileHydraulicMixer;
import pet.minecraft.Hydraulicraft.client.containers.ContainerMixer;
import pet.minecraft.Hydraulicraft.lib.config.ModInfo;
import pet.minecraft.Hydraulicraft.lib.config.Names;

public class GuiMixer extends GuiContainer {
	private ResourceLocation resLoc = new ResourceLocation(ModInfo.LID,"textures/gui/mixer.png");
	
	
	public GuiMixer(InventoryPlayer invPlayer, TileHydraulicMixer mixer) {
		super(new ContainerMixer(invPlayer, mixer));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p1, int p2){
		//TODO: Change that color
		fontRenderer.drawString(Names.blockHydraulicMixer.localized, 8, 6, 0xFFFFFF);
		
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize-96 + 2, 0xFFFFFF);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(resLoc);
		
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
