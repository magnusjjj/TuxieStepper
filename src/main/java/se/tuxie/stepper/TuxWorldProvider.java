package se.tuxie.stepper;

import java.util.Random;

import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderGenerate;

public class TuxWorldProvider extends WorldProvider {
	
	
	
	@Override
	public String getDimensionName() 
	{
		return "Stepper " + this.dimensionId;

	}
	
    public IChunkProvider createChunkGenerator()
    {
    	Random r = new Random();
    	return new ChunkProviderGenerate(worldObj, r.nextLong(), false);
    }
	
	@Override
	public String getSaveFolder()
	{
		return (dimensionId == 0 ? null : "Stepper/stepper" + dimensionId);
	}

}
