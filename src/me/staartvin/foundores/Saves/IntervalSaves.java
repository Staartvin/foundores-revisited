package me.staartvin.foundores.Saves;

import me.staartvin.foundores.FoundOres;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class IntervalSaves {

	FoundOres plugin;
	int saveInterval;
	public boolean saveProgress = false;
	int count = 0;

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
				plugin.logger.logNormal("NOTICE: Interval time is set to "
						+ saveInterval
						+ " minutes. Save interval cannot be higher than 600!");
				plugin.methods.disablePlugin();
				return;
			} else if (saveInterval >= 300) {
				plugin.logger
						.logNormal("NOTICE: Interval time is set to "
								+ saveInterval
								+ " minutes. A large interval time is not recommended!");
			} else {
				plugin.logger.logNormal("Interval time is set to "
						+ plugin.getConfig().getInt("SaveInterval")
						+ " minutes");
			}
		}
		saveInterval = plugin.getConfig().getInt("SaveInterval") * 1200;

		plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {

			@Override
			public void run() {
				save(null, true, false, null);
			}
		}, 0L, saveInterval);
	}

	// Save ALL THE DATA!!!
	public void save(String playerName, boolean firstSave, boolean forceSave,
			Player player) {
		//plugin.reloadConfig();
		//plugin.loadFiles.reloadDataConfig();

		if (saveProgress)
			return;

		// Schedule a save.
		new SaveTask(plugin, this, playerName, firstSave, forceSave, player)
				.runTaskAsynchronously(plugin);

	}

	protected void saveComplete(Player player) {
		if (player != null && player.isOnline()) {
			player.sendMessage(ChatColor.GREEN + "All work saved!");
			plugin.logger.logVerbose("Save is complete.");
		} else {
			plugin.logger.logVerbose("Save is complete.");
		}
	}

	protected void letsWork(final String playername, final Player player) {
		plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
			public void run() {

				save(playername, false, false, player);
			}
		}, (plugin.getConfig().getInt("sleepTime") * 20));
	}
}
