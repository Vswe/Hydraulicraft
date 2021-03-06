package k4unl.minecraft.Hydraulicraft.blocks;

import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import k4unl.minecraft.Hydraulicraft.TileEntities.TileHydraulicHose;
import k4unl.minecraft.Hydraulicraft.TileEntities.TileHydraulicPiston;
import k4unl.minecraft.Hydraulicraft.baseClasses.MachineBlockContainer;
import k4unl.minecraft.Hydraulicraft.client.renderers.Renderers;
import k4unl.minecraft.Hydraulicraft.lib.config.Ids;
import k4unl.minecraft.Hydraulicraft.lib.config.Names;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockHydraulicPiston extends MachineBlockContainer {

	protected BlockHydraulicPiston() {
		super(Ids.blockHydraulicPiston, Names.blockHydraulicPiston);
		this.hasTopIcon = true;
		this.hasBottomIcon = true;
		this.hasFrontIcon = true;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileHydraulicPiston();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int par6, float par7, float par8, float par9) {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType(){
        return -1;
    }
	
	@Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return true;
    }
	
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z){
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if(tileEntity instanceof TileHydraulicPiston) {
            float extendedLength = ((TileHydraulicPiston)tileEntity).getExtendedLength();

            //Get rotation:
            int metadata = tileEntity.getBlockMetadata();
            float minX = 0.0F;
            float minY = 0.0F;
            float minZ = 0.0F;
            float maxX = 1.0F;
            float maxY = 1.0F;
            float maxZ = 1.0F;
            
            ForgeDirection dir = ForgeDirection.getOrientation(metadata);
            minX += extendedLength * (dir.offsetX < 0 ? dir.offsetX : 0);
            minY += extendedLength * (dir.offsetY < 0 ? dir.offsetY : 0);
            minZ += extendedLength * (dir.offsetZ < 0 ? dir.offsetZ : 0);
            
            maxX += extendedLength * (dir.offsetX > 0 ? dir.offsetX : 0);
            maxY += extendedLength * (dir.offsetY > 0 ? dir.offsetY : 0);
            maxZ += extendedLength * (dir.offsetZ > 0 ? dir.offsetZ : 0);
            
            
            setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
        }
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity){
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

    	if(tileEntity instanceof TileHydraulicPiston) {
            float extendedLength = ((TileHydraulicPiston)tileEntity).getExtendedLength();

            //Get rotation:
            int metadata = tileEntity.getBlockMetadata();
            float minX = 0.0F;
            float minY = 0.0F;
            float minZ = 0.0F;
            float maxX = 1.0F;
            float maxY = 1.0F;
            float maxZ = 1.0F;
            
            ForgeDirection dir = ForgeDirection.getOrientation(metadata);
            minX -= extendedLength * (dir.offsetX < 0 ? dir.offsetX : 0);
            minY -= extendedLength * (dir.offsetY < 0 ? dir.offsetY : 0);
            minZ -= extendedLength * (dir.offsetZ < 0 ? dir.offsetZ : 0);
            
            maxX += extendedLength * (dir.offsetX > 0 ? dir.offsetX : 0);
            maxY += extendedLength * (dir.offsetY > 0 ? dir.offsetY : 0);
            maxZ += extendedLength * (dir.offsetZ > 0 ? dir.offsetZ : 0);
            
            setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
            super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, par7Entity);
        }
    }
    
}
