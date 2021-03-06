package k4unl.minecraft.Hydraulicraft.thirdParty.nei;

import org.lwjgl.opengl.GL11;

import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ShapedRecipeHandler;
import k4unl.minecraft.Hydraulicraft.client.GUI.GuiCrusher;
import k4unl.minecraft.Hydraulicraft.lib.CrushingRecipes;
import k4unl.minecraft.Hydraulicraft.lib.config.ModInfo;
import k4unl.minecraft.Hydraulicraft.lib.config.Names;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class NEICrusherRecipeManager extends ShapedRecipeHandler{
    private ShapedRecipeHandler.CachedShapedRecipe getShape(CrushingRecipes.CrushingRecipe recipe) {
        ShapedRecipeHandler.CachedShapedRecipe shape = new
                ShapedRecipeHandler.CachedShapedRecipe(0, 0, null, recipe.output);


        PositionedStack stack = new PositionedStack(OreDictionary.getOres(recipe.inputString), 42, 22);
        
        //stack.setMaxSize(2);
        shape.ingredients.add(stack);
        shape.result.relx = 116;
        shape.result.rely = 22;
        return shape;
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for(CrushingRecipes.CrushingRecipe recipe: CrushingRecipes.crushingRecipes) {
            if(NEIClientUtils.areStacksSameTypeCrafting(recipe.output, result)) {
                this.arecipes.add(getShape(recipe));
            }
        }
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass(){
        return GuiCrusher.class;
    }

    @Override
    public String getRecipeName(){
        return Names.blockHydraulicCrusher.localized;
    }

    @Override
    public String getGuiTexture(){
        return ModInfo.LID + ":textures/gui/crusher.png";
    }


    @Override
    public boolean hasOverlay(GuiContainer gui, Container container, int recipe)
    {
        return false;
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {    	
        for(CrushingRecipes.CrushingRecipe recipe: CrushingRecipes.crushingRecipes) {
        	String oreName = OreDictionary.getOreName(OreDictionary.getOreID(ingredient));
        	if(recipe.inputString == oreName){
                this.arecipes.add(getShape(recipe));
                break;
            }
        }
    }



    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if(outputId.equals("crafting") && getClass() ==
                NEICrusherRecipeManager.class) {
            for(CrushingRecipes.CrushingRecipe recipe: CrushingRecipes.crushingRecipes) {
                this.arecipes.add(getShape(recipe));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }
    
    @Override
	public void drawExtras(int recipe){
		drawProgressBar(80, 21, 207, 0, 34, 19, 48, 2 | (1 << 3));
    }
    
}
