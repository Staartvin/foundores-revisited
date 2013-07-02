package Staartvin.FoundOres.Update;

import org.bukkit.scheduler.BukkitRunnable;

import Staartvin.FoundOres.FoundOres;

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
