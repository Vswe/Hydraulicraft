package k4unl.minecraft.Hydraulicraft.blocks;

import java.util.List;
import java.util.Map;

import k4unl.minecraft.Hydraulicraft.TileEntities.TileHydraulicHose;
import k4unl.minecraft.Hydraulicraft.baseClasses.MachineTieredBlock;
import k4unl.minecraft.Hydraulicraft.lib.config.Ids;
import k4unl.minecraft.Hydraulicraft.lib.config.Names;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.util.vector.Vector3f;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class BlockHydraulicHose extends MachineTieredBlock{
	@SideOnly(Side.CLIENT)
    public Vector3f minVector = new Vector3f(0.35f, 0.35f, 0.35f);
	@SideOnly(Side.CLIENT)
    public Vector3f maxVector = new Vector3f(0.65f, 0.65f, 0.65f);

    protected BlockHydraulicHose(){
        super(Ids.blockHydraulicHose, Names.blockHydraulicHose);
    }

    @Override
    public TileEntity createNewTileEntity(World world){
        return new TileHydraulicHose();
    }

    @Override
    public int getRenderType(){
        return -1;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    private boolean isDir(Map<ForgeDirection, TileEntity> connectedSides, ForgeDirection dir){
        return connectedSides.containsKey(dir);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId){
        super.onNeighborBlockChange(world, x, y, z, blockId);
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if(tileEntity instanceof TileHydraulicHose) {
            ((TileHydraulicHose)tileEntity).checkConnectedSides();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z){
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if(tileEntity instanceof TileHydraulicHose) {
            Map<ForgeDirection, TileEntity> connectedSides = ((TileHydraulicHose)tileEntity).getConnectedSides();

            if(connectedSides != null) {
                float minX = minVector.x;
                float minY = minVector.y;
                float minZ = minVector.z;
                float maxX = maxVector.x;
                float maxY = maxVector.y;
                float maxZ = maxVector.z;

                if(isDir(connectedSides, ForgeDirection.UP)) maxY = 1.0F;
                if(isDir(connectedSides, ForgeDirection.DOWN)) minY = 0.0F;

                if(isDir(connectedSides, ForgeDirection.EAST)) maxX = 1.0F;
                if(isDir(connectedSides, ForgeDirection.WEST)) minX = 0.0F;

                if(isDir(connectedSides, ForgeDirection.SOUTH)) maxZ = 1.0F;
                if(isDir(connectedSides, ForgeDirection.NORTH)) minZ = 0.0F;

                setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity){
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if(tileEntity instanceof TileHydraulicHose) {
            Map<ForgeDirection, TileEntity> connectedSides = ((TileHydraulicHose)tileEntity).getConnectedSides();

            if(connectedSides != null) {
                float minX = minVector.x;
                float minY = minVector.y;
                float minZ = minVector.z;
                float maxX = maxVector.x;
                float maxY = maxVector.y;
                float maxZ = maxVector.z;

                setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
                super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, par7Entity);

                if(isDir(connectedSides, ForgeDirection.WEST)) {
                    setBlockBounds(0.0F, minY, minZ, maxX, maxY, maxZ);
                    super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, par7Entity);
                }

                if(isDir(connectedSides, ForgeDirection.EAST)) {
                    setBlockBounds(minX, minY, minZ, 1.0F, maxY, maxZ);
                    super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, par7Entity);
                }

                if(isDir(connectedSides, ForgeDirection.NORTH)) {
                    setBlockBounds(minX, minY, 0.0F, maxX, maxY, maxZ);
                    super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, par7Entity);
                }
                if(isDir(connectedSides, ForgeDirection.SOUTH)) {
                    setBlockBounds(minX, minY, minZ, maxX, maxY, 1.0F);
                    super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, par7Entity);
                }
                if(isDir(connectedSides, ForgeDirection.UP)) {
                    setBlockBounds(minX, minY, minZ, maxX, 1.0F, maxZ);
                    super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, par7Entity);
                }
                if(isDir(connectedSides, ForgeDirection.DOWN)) {
                    setBlockBounds(minX, 0.0F, minZ, maxX, maxY, maxZ);
                    super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, par7Entity);
                }
                setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack iStack){
        super.onBlockPlacedBy(world, x, y, z, player, iStack);
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if(tileEntity instanceof TileHydraulicHose) {
            ((TileHydraulicHose)tileEntity).checkConnectedSides();
        }
    }

}
