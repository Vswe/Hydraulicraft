package k4unl.minecraft.Hydraulicraft.lib;


import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CrushingRecipes {

    public static class CrushingRecipe {
        public final String inputString;
        public final ItemStack output;
        public final float pressure;

        public CrushingRecipe(String inp, float press, ItemStack outp){
        	inputString = inp;
            output = outp;
            pressure = press;
        }

        
        /*public CrushingRecipe(ItemStack inp, float press, ItemStack outp){
        	inputString = "";
            input = inp;
            output = outp;
            pressure = press;
        }*/
    }

    public static LinkedList<CrushingRecipe> crushingRecipes = new
            LinkedList<CrushingRecipe>();

    public static void addCrushingRecipe(CrushingRecipe toAdd){
        crushingRecipes.add(toAdd);
    }
    
    public static ItemStack getCrushingRecipe(ItemStack itemStack){
    	String oreDictName = OreDictionary.getOreName(OreDictionary.getOreID(itemStack));
        return getCrushingRecipe(oreDictName);
    }
    
    public static ItemStack getCrushingRecipe(String oreDictName){
        for(CrushingRecipe rec : crushingRecipes){
            if(rec.inputString.equals(oreDictName)){
                return rec.output;
            }
        }
        return null;
    }

/*
    public ItemStack getCrushingRecipe(ItemStack itemStack){
        ItemStack ret = null;

        List<String> allowedList = new ArrayList<String>();
        allowedList.add("Gold");
        allowedList.add("Iron");
        allowedList.add("Copper");
        allowedList.add("Lead");
        allowedList.add("Quartz");

        //Get oreDictionaryName
        String oreName = itemStack.getUnlocalizedName();
        oreName = oreName.substring("tile.".length());
        String metalName = Functions.getMetalName(oreName);
        if(allowedList.contains(metalName)){
            for(int i = 0; i < chunks.size(); i++){
                String cName = chunks.get(i).getName();
                if(cName.equals(metalName)){
                    if(metalName.equals("Quartz")){
                        return new ItemStack(Item.netherQuartz,3 + ((new Random()).nextFloat() > 0.85F ? 1 : 0));
                    }else{
                        return new ItemStack(this.itemID, 2, i);
                    }
                }
            }
        }

        return ret;
    }*/

}
