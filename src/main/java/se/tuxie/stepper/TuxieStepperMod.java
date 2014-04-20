package se.tuxie.stepper;

import java.io.File;

import scala.reflect.io.Directory;
import scala.util.parsing.json.JSON;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = TuxieStepperMod.MODID, version = TuxieStepperMod.VERSION)
public class TuxieStepperMod
{
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";
    public static final int providerid = 2000;
    public static Item stepperItem;
    
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	if (!DimensionManager.registerProviderType(providerid, TuxWorldProvider.class, false))
			throw new IllegalStateException("Error stolen from DimensionalDoors!");
    	
    	stepperItem = new StepperItem(900);
    	LanguageRegistry.addName(stepperItem, "Stepper");

    }
    
    @EventHandler
    public void onInitialization(FMLInitializationEvent event)
    {

    	
    }
    

    
    
    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
    	event.registerServerCommand(StepCommand.instance());
    	try {
	    	// Time to register all the world that we *already* have generated
	    	File root = DimensionManager.getCurrentSaveRootDirectory();
	    	File stepperdir = new File(root.getPath() + "/Stepper/");
	    	
	    	File[] listOfFiles = stepperdir.listFiles();
	    	
	        for (int i = 0; i < listOfFiles.length; i++) {
	        	if (listOfFiles[i].isDirectory()) {
	        	  DimensionManager.registerDimension(Integer.parseInt(listOfFiles[i].getName().substring("stepper".length())),
	        			  TuxieStepperMod.providerid);
	              System.out.println("TUXIESTEPPER REGISTERING DIMENSION " + Integer.parseInt(listOfFiles[i].getName().substring("stepper".length())));
	            }
	        }
    	} catch(Exception e)
    	{
    		System.out.println("Error in registering dimensions. Probably just the first time you ran this, right? Right? <3");
    	}
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	
        
    }
}
