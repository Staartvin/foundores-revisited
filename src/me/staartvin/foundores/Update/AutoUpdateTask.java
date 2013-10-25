package me.staartvin.foundores.Update;

import me.staartvin.foundores.FoundOres;

import org.bukkit.scheduler.BukkitRunnable;


public class AutoUpdateTask extends BukkitRunnable {
	
	private final FoundOres plugin;
	
	public AutoUpdateTask(FoundOres instance) {
		plugin = instance;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (plugin.isUpdateAvailable()) {
			plugin.updatePlugin(null);
		}
	}
	
	

}
