package se.tuxie.stepper.packetstuff;

import java.io.File;

import se.tuxie.stepper.TuxieStepperMod;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.network.ForgePacket;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraftforge.common.network.packet.DimensionRegisterPacket;

public class ConnectionHandler implements IConnectionHandler{

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler,
			INetworkManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler,
			INetworkManager manager) {
    	try {
	    	// Time to register all the world that we *already* have generated
	    	File root = DimensionManager.getCurrentSaveRootDirectory();
	    	File stepperdir = new File(root.getPath() + "/Stepper/");
	    	
	    	File[] listOfFiles = stepperdir.listFiles();
	    	
	        for (int i = 0; i < listOfFiles.length; i++) {
	        	if (listOfFiles[i].isDirectory()) {
					Packet250CustomPayload[] pkt = ForgePacket.makePacketSet(
														new DimensionRegisterPacket(Integer.parseInt(listOfFiles[i].getName().substring("stepper".length())), TuxieStepperMod.providerid)
												);
					manager.addToSendQueue(pkt[0]);
	            }
	        }
    	} catch(Exception e)
    	{
    		System.out.println("Error in registering dimensions. Probably just the first time you ran this, right? Right? <3");
    	}
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server,
			int port, INetworkManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler,
			MinecraftServer server, INetworkManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionClosed(INetworkManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler,
			INetworkManager manager, Packet1Login login) {
		// TODO Auto-generated method stub
		
	}

}
