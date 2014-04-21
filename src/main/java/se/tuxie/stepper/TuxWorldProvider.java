package se.tuxie.stepper;

import java.util.Random;

import org.bouncycastle.jcajce.provider.symmetric.SEED;

import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraftforge.common.DimensionManager;

public class TuxWorldProvider extends WorldProvider {
	
	
	
	@Override
	public String getDimensionName() 
	{
		return "Stepper " + this.dimensionId;
	}
	
	@Override
	
	public long getSeed()
	{
		Random r = new Random(DimensionManager.getProvider(0).getSeed() + dimensionId);
		return r.nextLong();
	}
	
	
	/*
    public IChunkProvider createChunkGenerator()
    {
    	long seed = worldObj.getSeed();
    	return new ChunkProviderGenerate(worldObj, seed, false);
    }*/
	
	@Override
	public String getSaveFolder()
	{
		return (dimensionId == 0 ? null : "Stepper/stepper" + dimensionId);
	}

}
