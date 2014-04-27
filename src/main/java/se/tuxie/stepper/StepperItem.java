package se.tuxie.stepper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
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
		this.setMaxDamage(500);
		this.setMaxStackSize(1);
		
		
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 0;
		

		
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5){
		this.setDamage(par1ItemStack, this.getDamage(par1ItemStack) - 1 != 0 ? this.getDamage(par1ItemStack) - 1 : 0);
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(TuxieStepperMod.MODID + ":" + this.getUnlocalizedName().replace("item.", ""));
		
		
	}
	
	@Override
	public boolean isDamageable()
	{
		return false;
	}
	
	
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack){
		if(entityLiving instanceof EntityPlayerMP)
		{
			if(stack.getItemDamage() == 0)
			{
				stack.setItemDamage(400);
				//stack.damageItem(400, entityLiving);
				EntityPlayerMP playermp = (EntityPlayerMP) entityLiving;

				StepperHelper.stepPlayer(playermp, "east");
			}
		}
		
		
		return true;
		
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		//par1ItemStack.setItemDamage(400);
		return false;
	}


	@Override
	public ItemStack onItemRightClick(ItemStack stack, World theworld, EntityPlayer player)
	{
		//stack = super.onItemRightClick(stack, theworld, player);
		if(player instanceof EntityPlayerMP)
		{
			if(stack.getItemDamage() == 0)
			{
				stack.setItemDamage(400);
				
				player.openContainer.detectAndSendChanges();
				//stack.damageItem(400, player);
				EntityPlayerMP playermp = (EntityPlayerMP) player;
				StepperHelper.stepPlayer(playermp, "west");
				//return null;
			}
			

		}
		
		return stack;
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
