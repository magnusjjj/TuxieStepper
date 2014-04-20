package se.tuxie.stepper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class StepCommand extends CommandBase {
	
	/*
	 * - Todo:
	 *   Search for placing of the player in the new dimension
	 *   Register an item
	 */
	
	
	private static StepCommand instance = null;
	
	public static StepCommand instance()
	{
		if (instance == null)
			instance = new StepCommand();

		return instance;
	}
	
    public int compareTo(ICommand par1ICommand)
    {
        return this.getCommandName().compareTo(par1ICommand.getCommandName());
    }

    public int compareTo(Object par1Obj)
    {
        return this.compareTo((ICommand)par1Obj);
    }

	@Override
	public String getCommandName() {
		return "step";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		// TODO Auto-generated method stub
		return "/step";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		// Command start
		
		String direction = "west";
		if(astring.length > 0)
		{
			if(astring[0].equals("east"))
			{
				direction = "east";
			}
		}
		
		if(icommandsender instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) icommandsender;
			StepperHelper.stepPlayer(player, direction);

		} else {
			ChatMessageComponent cc = new ChatMessageComponent();
			cc.addText("You are not a player");
			icommandsender.sendChatToPlayer(cc);
			
		}
		
	}
}
