package me.staartvin.foundores.listeners;

import me.staartvin.foundores.FoundOres;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

	FoundOres plugin;

	public PlayerQuitListener(FoundOres plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	protected void onPlayerQuit(PlayerQuitEvent event) {
		if (plugin.getConfig().getBoolean("noticePlayerOnMine")) {
			
			// Player logs out so we reset the status
			plugin.hasReceived(false);
		}
	}
}
