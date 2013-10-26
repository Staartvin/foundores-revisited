package me.staartvin.foundores.announcer;

import java.util.Set;

import me.staartvin.foundores.FoundOres;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AnnounceTask extends BukkitRunnable {

	private final FoundOres plugin;

	public AnnounceTask(FoundOres plugin) {
		this.plugin = plugin;
	}

	public void run() {

		Player[] players = plugin.getServer().getOnlinePlayers();

		for (Player player : players) {
			if (!player.hasPermission("foundores.notice")) {
				continue;
			}

			Set<String> playerKeys = plugin.getAnnounceHandler()
					.getRecordKeys();

			for (String playerKey : playerKeys) {
				
				String message = ChatColor.RED + "";
				
				for (int i=0; i<plugin.getAnnounceHandler().getBlockIdKeys(playerKey).size();i++) {
					
					int blockId = (Integer) plugin.getAnnounceHandler().getBlockIdKeys(playerKey).toArray()[i];
					
					@SuppressWarnings("deprecation")
					Material mat = Material.getMaterial(blockId);
					int amount = plugin.getAnnounceHandler().getBlockIdAmount(playerKey, blockId);

					// If it's the first one
					if (i == 0 ) {
						message = message.concat(playerKey
							+ " has mined " + amount
							+ " " + mat.name().replace("_", " ").toLowerCase());
					}
					// If it's the last one
					else if (i == (plugin.getAnnounceHandler().getBlockIdKeys(playerKey).size() - 1)) {
						message = message.concat( " and " + amount
								+ " " + mat.name().replace("_", " ").toLowerCase() + ".");
					} else {
						// Neither first nor last
						message = message.concat( ", " + amount
								+ " " + mat.name().replace("_", " ").toLowerCase());
					}
						
				}
				
				player.sendMessage(message);
			}
		}
		
		// Reset stats
		plugin.getAnnounceHandler().resetStats();
	}
}
