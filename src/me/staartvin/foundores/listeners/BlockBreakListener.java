package me.staartvin.foundores.listeners;

import java.util.HashMap;

import me.staartvin.foundores.FileLogger.eventTypes;
import me.staartvin.foundores.FoundOres;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

	private FoundOres plugin;
	
	// Stores all blocks that should be logged
	private int[] loggableBlocks = { 1, 14, 15, 16, 21, 56, 73, 129, 153 };
	
	// Stores whether a player has received the mining message.
	public HashMap<String, Boolean> hasReceived = new HashMap<String, Boolean>();

	public BlockBreakListener(FoundOres plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	protected void onBlockBreak(final BlockBreakEvent event) {

		if (event.isCancelled())
			return;

		@SuppressWarnings("deprecation")
		int bid = event.getBlock().getTypeId();
		Player player = event.getPlayer();

		// Is this a disabled world?
		if (plugin.getConfig().getStringList("DisabledWorlds")
				.contains(player.getWorld().getName()))
			return;
		
		// Should we check creative?
		if (player.getGameMode().toString().equalsIgnoreCase("creative")
				&& !plugin.getConfig().getBoolean("checkCreativeMode"))
			return;
		
		if (bid == 74) {
			bid = 73;
		}

		// Check if we should log it.
		if (!isLoggable(event.getBlock()))
			return;

		// Check if a player can mine this block
		if (!canMine(event.getBlock(), player)) {
			event.setCancelled(true);
			return;
		}

		// Add this block to the logfile.yml
		plugin.getLogClass().logToFile(
				"[INFO] PLAYER " + player.getName() + " has broken "
						+ event.getBlock().getType() + " at "
						+ event.getBlock().getX() + ", "
						+ event.getBlock().getY() + ", "
						+ event.getBlock().getZ(), eventTypes.BLOCKBREAK);

		// Add a new block break action to the queue.
		// Use UUID
		plugin.loggedActions.add(player.getUniqueId().toString() + ":" + player.getWorld().getName()
				+ ":" + bid);

		// Notice player of mining
		if (shouldNotice(player.getName())) {
			player.sendMessage(plugin.getConfig().getString(
					"noticeMessageToPlayer"));
			
			// Player has received message
			hasReceived.put(player.getName(), true);
		}

		// Check if we have to save it for announcing later
		if (plugin.getAnnounceHandler().shouldRecord(player, bid)) {
			plugin.getAnnounceHandler().registerBlockBreak(player.getName(),
					bid);
		}
	}

	/**
	 * Checks whether a player is allowed to mine in the dark.
	 * 
	 * @param block Block to check for
	 * @param player Player to check for
	 * @return true if allowed, false otherwise.
	 */
	private boolean canMine(Block block, Player player) {
		if (!plugin.getConfig().getBoolean("allowLowLightMining")) {
			if (block.getY() <= 60) {
				if (plugin.getMethodsClass().getLightLevel(block) <= plugin
						.getConfig().getInt("LightLevelDenial")) {
					if (player.hasPermission("foundores.exempt.lightlevel")) {
						return true;
					}
					player.sendMessage(ChatColor.RED
							+ "You may not break this block! Light level is too low!");
					return false;
				}
			}
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	private boolean isLoggable(Block block) {
		int blockId = block.getTypeId();

		for (int logBlockId : loggableBlocks) {
			if (blockId == logBlockId)
				return true;
		}

		return false;
	}
	
	private boolean shouldNotice(String playerName) {
		if (plugin.getConfig().getBoolean("noticePlayerOnMine")) {
			
			return (!hasReceived.containsKey(playerName));
		}
		
		return false;
	}
}
