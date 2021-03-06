package k4unl.minecraft.Hydraulicraft.api;

import java.util.List;

import pneumaticCraft.api.tileentity.IAirHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;

public interface IHydraulicMachine {
	/**
	 * Will be used to calculate the pressure all over the network.
	 * @author Koen Beckers
	 * @date 15-12-2013
	 * @return How much liquid this block can store.
	 */
	public abstract int getMaxStorage();
	
	/**
	 * @author Koen Beckers
	 * @date 14-12-2013
	 * @param isOil Whether or not oil is stored.
	 * @return The max amount of pressure(bar) this machine can handle.
	 */
	public float getMaxPressure(boolean isOil);

	/**
	 * Called when the block has been broken.
	 * @author Koen Beckers
	 * @date 29-12-2013
	 */
	public void onBlockBreaks();

	public IBaseClass getHandler();
	
	/**
	 * Forward this function to the Base class
	 * @param tagCompound
	 */
	public void readFromNBT(NBTTagCompound tagCompound);
	
	/**
	 * Forward this function to the Base class
	 * @param tagCompound
	 */
	public void writeToNBT(NBTTagCompound tagCompound);
	
	/**
	 * Used to read NBT. Called from handler.
	 * @param tagCompound
	 */
	public void readNBT(NBTTagCompound tagCompound);
	
	/**
	 * Used to write NBT. Called from handler
	 * @param tagCompound
	 */
	public void writeNBT(NBTTagCompound tagCompound);
	
	
	
	/**
	 * Forward this function to the Base class
	 * @param net
	 * @param packet
	 */
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet);
	
	/**
	 * Forward this function to the Base class
	 * @return
	 */
	public Packet getDescriptionPacket();
	
	/**
	 * Forward this function to the base class.
	 */
	public void updateEntity();
}
