package pet.minecraft.Hydraulicraft.baseClasses;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import pet.minecraft.Hydraulicraft.lib.CustomTabs;
import pet.minecraft.Hydraulicraft.lib.config.ModInfo;
import pet.minecraft.Hydraulicraft.lib.helperClasses.Id;
import pet.minecraft.Hydraulicraft.lib.helperClasses.Name;

public abstract class MachineBlock extends BlockContainer {
	private Icon blockIcon;
	private Icon topIcon;
	private Icon bottomIcon;
	private Icon frontIcon;
	
	private Id tBlockId;
	private Name mName;
	
	protected boolean hasBottomIcon = false;
	protected boolean hasTopIcon = false;
	protected boolean hasFrontIcon = false;
	
	@Override
	public abstract TileEntity createNewTileEntity(World world);
	
	protected MachineBlock(Id blockId, Name machineName) {
		super(blockId.act, Material.rock);
		
		tBlockId = blockId;
		mName = machineName;
		
		setUnlocalizedName(mName.unlocalized);
		setStepSound(Block.soundStoneFootstep);
		setHardness(3.5F);
		
		setCreativeTab(CustomTabs.tabHydraulicraft);
	}
	
	
	private String getTextureName(String side){
		if(side != null){
			return ModInfo.LID + ":" + mName.unlocalized + "_" + side;
		}else{
			return ModInfo.LID + ":" + mName.unlocalized;
		}
	}
	
	
	@Override
	public void registerIcons(IconRegister iconRegistry){
		if(hasTopIcon || hasBottomIcon || hasFrontIcon){
			blockIcon = iconRegistry.registerIcon(getTextureName("sides"));
			if(hasTopIcon){
				topIcon = iconRegistry.registerIcon(getTextureName("top"));
			}else{
				topIcon = blockIcon;
			}
			if(hasBottomIcon){
				bottomIcon = iconRegistry.registerIcon(getTextureName("bottom"));
			}else{
				bottomIcon = blockIcon;
			}
			if(hasFrontIcon){
				frontIcon = iconRegistry.registerIcon(getTextureName("front"));
			}else{
				frontIcon = blockIcon;
			}
		}else{
			blockIcon = iconRegistry.registerIcon(getTextureName(null));
			bottomIcon = blockIcon;
			topIcon = blockIcon;
			frontIcon = blockIcon;
		}
	}
	
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z){
		super.onBlockAdded(world, x, y, z);
		if(hasFrontIcon){
			setDefaultDirection(world, x, y, z);
		}
		checkSideBlocks(world, x, y, z);
	}
	
	private void setDefaultDirection(World world, int x, int y, int z){
		if(!world.isRemote){ //Client, not server
			int zm1 = world.getBlockId(x, y, z - 1);
			int zp1 = world.getBlockId(x, y, z + 1);
			int xm1 = world.getBlockId(x - 1, y, z);
			int xp1 = world.getBlockId(x + 1, y, z);
			
			int metaDataToSet = 3;
			
			if(Block.opaqueCubeLookup[zm1] && !Block.opaqueCubeLookup[zp1]){
				metaDataToSet = 3;
			}
			
			if(!Block.opaqueCubeLookup[zm1] && Block.opaqueCubeLookup[zp1]){
				metaDataToSet = 2;
			}
			
			if(Block.opaqueCubeLookup[xm1] && !Block.opaqueCubeLookup[xp1]){
				metaDataToSet = 5;
			}
			
			if(!Block.opaqueCubeLookup[xm1] && Block.opaqueCubeLookup[xp1]){
				metaDataToSet = 4;
			}
			
			world.setBlockMetadataWithNotify(x, y, z, metaDataToSet, 2);
		}
	}
	
	
	
	@Override
	public Icon getIcon(int side, int metadata){
		ForgeDirection s = ForgeDirection.getOrientation(side);
		if(s.equals(ForgeDirection.UP)){
			return topIcon;
		}else if(s.equals(ForgeDirection.DOWN)){
			return bottomIcon;
		}
		if(hasFrontIcon){
			if(metadata > 0){
				if(side == metadata){
					return frontIcon; 
				}
			}else{
				if(side == 3){
					return frontIcon;
				}
			}
		}
		return blockIcon;
	}
	
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack iStack){
		if(hasFrontIcon){
			int sideToPlace = MathHelper.floor_double((double)(player.rotationYaw / 90F) + 0.5D) & 3;
			
			int metaDataToSet = 0;
			switch(sideToPlace){
			case 0:
				metaDataToSet = 2;
				break;
			case 1:
				metaDataToSet = 5;
				break;
			case 2:
				metaDataToSet = 3;
				break;
			case 3:
				metaDataToSet = 4;
				break;
			}
			
			world.setBlockMetadataWithNotify(x, y, z, metaDataToSet, 2);
		}
		
		checkSideBlocks(world, x, y, z);
	}
	
	private List<MachineEntity> getMachine(List<MachineEntity> list, World w, int x, int y, int z){
		TileEntity t = w.getBlockTileEntity(x, y, z);
		if(t instanceof MachineEntity){
			list.add((MachineEntity)t);
		}
		return list;
	}
	
	private void checkSideBlocks(World w, int x, int y, int z){
		//It should check the connecting blocks
		//And check how much liquid they have
		//Get liquid from them
		List<MachineEntity> machines = new ArrayList<MachineEntity>();
		machines = getMachine(machines, w, x-1, y, z);
		machines = getMachine(machines, w, x+1, y, z); 
		machines = getMachine(machines, w, x, y-1, z);
		machines = getMachine(machines, w, x, y+1, z);
		machines = getMachine(machines, w, x, y, z-1);
		machines = getMachine(machines, w, x, y, z+1);
		
		for (MachineEntity machineEntity : machines) {
			
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
		return false;
	}
	
	
}
