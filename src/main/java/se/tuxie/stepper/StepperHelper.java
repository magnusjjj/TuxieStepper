package se.tuxie.stepper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class StepperHelper {
	public static void stepPlayer(EntityPlayerMP player, String direction)
	{

		World currentDimension = DimensionManager.getWorld(player.dimension);
		
		String savefolder =  DimensionManager.getProvider(player.dimension).getProviderForDimension(player.dimension).getSaveFolder();
		
		if(savefolder == null) // If the savefolder is null, then its the overworld
		{
			savefolder = "";
		}
		
		String fullpath = DimensionManager.getCurrentSaveRootDirectory() + "/" + savefolder + "/";
		
		
		String jumptopath = "";
		
		if(direction == "west")
		{
			jumptopath = fullpath + direction + "Dimension";
		} else {
			jumptopath = fullpath + direction + "Dimension";
		}
		
		
		
		
		File jumpToFile = new File(jumptopath);
		
		int jumptodimension = 0;
		try {
			if(jumpToFile.exists()) // Do we know where to jump?
			{
				// Read which is the 'next' dimension
				BufferedReader br = new BufferedReader(new FileReader(jumptopath));
				jumptodimension = Integer.parseInt(br.readLine());    
		        br.close();
			} else {
				// Make a new dimension
				jumptodimension = DimensionManager.getNextFreeDimId();
				DimensionManager.registerDimension(jumptodimension, TuxieStepperMod.providerid);
				// Store where we are going, in the current world
				BufferedWriter bw = new BufferedWriter(new FileWriter(fullpath + direction + "Dimension"));
				
				bw.write(Integer.toString(jumptodimension));
		        bw.close();
		        
		        // See to it that we init the dimension
		        
				World dim = DimensionManager.getWorld(jumptodimension);
				if(dim == null)
				{
					DimensionManager.initDimension(jumptodimension);
					dim = DimensionManager.getWorld(jumptodimension);
				}
		        
		        
				// Write where we are coming from, in the dimension we are going to
		        
				String savefolder_last =  DimensionManager.getProvider(jumptodimension).getProviderForDimension(jumptodimension).getSaveFolder();
				
				
				if(savefolder_last == null) // If the savefolder is null, then its the overworld
				{
					savefolder_last = "";
				}
				
				String fullpath_last = DimensionManager.getCurrentSaveRootDirectory() + "/" +  savefolder_last + "/";
				
				
				
				BufferedWriter bw2 = new BufferedWriter(new FileWriter(fullpath_last + (direction == "east" ? "west" : "east") +"Dimension"));
				bw2.write(Integer.toString(player.dimension));
		        bw2.close();
			}
			

		} catch (Exception e) {
			ChatMessageComponent cc2 = new ChatMessageComponent();
			cc2.addText("Error in stepping + " + e.toString());
			player.sendChatToPlayer(cc2);
			return;
		}
		
		World dim = DimensionManager.getWorld(jumptodimension);
		

		Point4D coord = new Point4D();
		coord.X = Math.floor(player.posX);
		coord.Y = bestheight(player.posX, player.posZ, player.posY, jumptodimension, player);
		coord.Z = Math.floor(player.posZ);
		coord.Dimension = jumptodimension;
		
		
		SenseiKiwisSuperAwesomeTeleporter.teleportPlayerTo(player, coord);				
	}
	
	public static int bestheight(double X, double Z, double currentheight, int dimension, EntityPlayerMP player) {
		// player.height
		
		World dim = DimensionManager.getWorld(dimension);
		
		int offset = 0;

		while(offset < dim.getActualHeight()) // Wheee, DANGER WILL ROBINSON
		{
			if(checkifvalidtp((int)X, (int)Z, (int)currentheight + offset, dimension, player))
			{
				return (int)currentheight + offset;
			}
			
			if(checkifvalidtp((int)X, (int)Z, (int)currentheight - offset, dimension, player))
			{
				return (int)currentheight - offset;
			}
			
			offset++;
		}
		
		return 128;
	}
	
	public static boolean checkifvalidtp(int X, int Z, int currentheight, int dimension, EntityPlayerMP player)
	{
		World dim = DimensionManager.getWorld(dimension);
		// Width
		for(int XT = X - (int)Math.ceil(player.width); XT < X + (int)Math.ceil(player.width) ; XT++)
		{
			// Length
			for(int ZT = Z - (int)Math.ceil(player.width); ZT < Z + (int) Math.ceil(player.width); ZT++)
			{
				// Height
				for(int YT = currentheight; YT < currentheight + (int) Math.ceil(player.height); YT++)
				{
					if(dim.getBlockMaterial(XT, YT, ZT).isSolid())
					{
						return false;
					}
				}
			}
		}

		return true;
	}

}
