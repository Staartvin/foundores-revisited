package me.staartvin.foundores.saves;

import me.staartvin.foundores.FoundOres;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class IntervalSaves {

	FoundOres plugin;
	int saveInterval;
	public boolean saveProgress = false;
	int count = 0;
	private boolean firstSaveDone = false;

	public IntervalSaves(FoundOres plugin) {
		this.plugin = plugin;
	}

	public void saveOnInterval() {
		saveInterval = plugin.getConfig().getInt("SaveInterval");
		if (saveInterval <= 0) {
			plugin.getServer()
					.getLogger()
					.severe("[FoundOres Revisited] Could not start interval timer. Interval time is incorrect!");
			return;
		} else {
			if (saveInterval > 600) {
				plugin.getLoggerClass().logNormal("NOTICE: Interval time is set to "
						+ saveInterval
						+ " minutes. Save interval cannot be higher than 600!");
				plugin.getMethodsClass().disablePlugin();
				return;
			} else if (saveInterval >= 300) {
				plugin.getLoggerClass()
						.logNormal("NOTICE: Interval time is set to "
								+ saveInterval
								+ " minutes. A large interval time is not recommended!");
			} else {
				plugin.getLoggerClass().logNormal("Interval time is set to "
						+ plugin.getConfig().getInt("SaveInterval")
						+ " minutes");
			}
		}
		saveInterval = plugin.getConfig().getInt("SaveInterval") * 1200;

		plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {

			@Override
			public void run() {
				save(null, false, null);
			}
		}, 0L, saveInterval);
	}

	// Save ALL THE DATA!!!
	public void save(String playerName, boolean forceSave,
			Player player) {

		if (saveProgress)
			return;

		// Schedule a save.
		new SaveTask(plugin, this, playerName, forceSave, player)
				.runTaskAsynchronously(plugin);

	}

	protected void saveComplete(Player player) {
		if (player != null && player.isOnline()) {
			player.sendMessage(ChatColor.GREEN + "All work saved!");
		} 
		plugin.getLoggerClass().logVerbose("Save is complete.");
	}

	protected void letsWork(final String playername, final Player player) {
		plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
			public void run() {

				save(playername, false, player);
			}
		}, (plugin.getConfig().getInt("sleepTime") * 20));
	}

	public boolean isFirstSaveDone() {
		return firstSaveDone;
	}

	public void setFirstSaveDone(boolean firstSaveDone) {
		this.firstSaveDone = firstSaveDone;
	}
}
