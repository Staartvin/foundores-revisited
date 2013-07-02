package Staartvin.FoundOres.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import Staartvin.FoundOres.FoundOres;

public class PlayerQuitListener implements Listener {

	FoundOres plugin;

	public PlayerQuitListener(FoundOres plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	protected void onPlayerQuit(PlayerQuitEvent event) {
		if (plugin.getConfig().getBoolean("noticePlayerOnMine")) {
			if (!plugin.hasReceived.containsKey(event.getPlayer().getName())) {
				plugin.hasReceived.put(event.getPlayer().getName(), false);
				return;
			}
			if (plugin.hasReceived.get(event.getPlayer().getName())) {
				plugin.hasReceived.put(event.getPlayer().getName(), false);
				return;
			} else if (plugin.hasReceived.get(event.getPlayer().getName()) == false) {
				return;
			}
		}
	}
}
