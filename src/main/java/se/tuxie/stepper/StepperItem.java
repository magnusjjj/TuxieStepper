package se.tuxie.stepper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class StepperItem extends Item {

	public StepperItem(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabTools);
		//this.setTextureName(TuxieStepperMod.MODID + ":" + "stepper");
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(TuxieStepperMod.MODID + ":" + this.getUnlocalizedName().replace("item.", ""));
	}
	
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack){
		if(entityLiving instanceof EntityPlayerMP)
		{
			EntityPlayerMP playermp = (EntityPlayerMP) entityLiving;
			StepperHelper.stepPlayer(playermp, "east");
		}
		return true;
	}
	
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World theworld, EntityPlayer player)
	{
		if(player instanceof EntityPlayerMP)
		{
			EntityPlayerMP playermp = (EntityPlayerMP) player;
			StepperHelper.stepPlayer(playermp, "west");
		}
		return item;
	}
	
	

	@Override
	public boolean hasEffect(ItemStack par1ItemStack){
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack){
		return EnumRarity.epic;
	}
	
}
