package me.staartvin.foundores.listeners;

import me.staartvin.foundores.FoundOres;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoinListener implements Listener {

	FoundOres plugin;

	public PlayerJoinListener(FoundOres plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	protected void onPlayerJoin(final PlayerJoinEvent event) {
		World world = event.getPlayer().getLocation().getWorld();
		if (!plugin.getDatabaseConnector().isLogged(event.getPlayer().getName(), world.getName())) {
			plugin.getLoggerClass().logVerbose("I don't recognise "
					+ event.getPlayer().getName() + "!");
			plugin.getLoggerClass().logVerbose("Creating info about "
					+ event.getPlayer().getName() + "...");
			for (World worlds : plugin.getServer().getWorlds()) {
				plugin.getDatabaseConnector().createNewEntry(event.getPlayer().getName(),
						worlds.getName());
			}
		}
		
		
		// Check for updates
		if (plugin.getConfig().getBoolean("Updater.doCheckUpdate")) {
			if (event.getPlayer().hasPermission("foundores.noticeonupdate")) {
				if (plugin.isUpdateAvailable()) {
					plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							event.getPlayer().sendMessage(ChatColor.GREEN + plugin.getUpdater().getLatestVersionString() + ChatColor.GOLD + " is now available for download!");
							event.getPlayer().sendMessage(ChatColor.GOLD + "Type " + ChatColor.GREEN + "'/fo update'" + ChatColor.GOLD + " to update FoundOres.");	
						}
						
					}, 10L);
					
				}
			}
		}
	}
}
