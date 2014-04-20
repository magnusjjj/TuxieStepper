package se.tuxie.stepper;
import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet41EntityEffect;
import net.minecraft.network.packet.Packet9Respawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.registry.GameRegistry;

public class SenseiKiwisSuperAwesomeTeleporter {
	public static void teleportPlayerTo(EntityPlayerMP player, Point4D coord)
	{
		MinecraftServer server = MinecraftServer.getServer();
		if(player.dimension != coord.Dimension)
		{
			int id = player.dimension;
			WorldServer oldWorld = server.worldServerForDimension(player.dimension);
			player.dimension = coord.Dimension;
			WorldServer newWorld = server.worldServerForDimension(player.dimension);
			player.playerNetServerHandler.sendPacketToPlayer(new Packet9Respawn(player.dimension, (byte)player.worldObj.difficultySetting, newWorld.getWorldInfo().getTerrainType(), newWorld.getHeight(), player.theItemInWorldManager.getGameType()));
			oldWorld.removePlayerEntityDangerously(player);
			player.isDead = false;

			if(player.isEntityAlive())
			{
				newWorld.spawnEntityInWorld(player);
				player.setLocationAndAngles(coord.X+0.5, coord.Y+1, coord.Z+0.5, player.rotationYaw, player.rotationPitch);
				newWorld.updateEntityWithOptionalForce(player, false);
				player.setWorld(newWorld);
			}

			server.getConfigurationManager().func_72375_a(player, oldWorld);
			player.playerNetServerHandler.setPlayerLocation(coord.X+0.5, coord.Y+1, coord.Z+0.5, player.rotationYaw, player.rotationPitch);
			player.theItemInWorldManager.setWorld(newWorld);
			server.getConfigurationManager().updateTimeAndWeatherForPlayer(player, newWorld);
			server.getConfigurationManager().syncPlayerInventory(player);
			Iterator iterator = player.getActivePotionEffects().iterator();

			while(iterator.hasNext())
			{
				PotionEffect potioneffect = (PotionEffect)iterator.next();
				player.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(player.entityId, potioneffect));
			}

			GameRegistry.onPlayerChangedDimension(player);
		}
		else {
			player.playerNetServerHandler.setPlayerLocation(coord.X+0.5, coord.Y+1, coord.Z+0.5, player.rotationYaw, player.rotationPitch);
		}
	}
}