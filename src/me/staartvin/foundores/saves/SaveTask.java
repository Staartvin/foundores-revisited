package me.staartvin.foundores.saves;

import java.util.ArrayList;

import me.staartvin.foundores.FoundOres;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class SaveTask extends BukkitRunnable {

	private final FoundOres plugin;
	private final IntervalSaves saves;
	private String playerName = "";
	private boolean forceSave = false;
	private Player player;

	public SaveTask(FoundOres plugin, IntervalSaves saves, String playerName,
			boolean forceSave, Player player) {
		this.plugin = plugin;
		this.saves = saves;
		this.playerName = playerName;
		this.forceSave = forceSave;
		this.player = player;
	}

	public void run() {
		// Is there already a save running?
		if (saves.saveProgress)
			return;

		int workCounter = 0;

		ArrayList<String> log = plugin.loggedActions;
		ArrayList<String> stbr = new ArrayList<String>(); // Soon to be removed strings

		// Just a message setup
		if (!plugin.getInternalSaves().isFirstSaveDone()) {
			plugin.getInternalSaves().setFirstSaveDone(true);
			if (playerName == null) {
				plugin.getLoggerClass().logVerbose("Saving all data...");
			} else {
				plugin.getLoggerClass().logVerbose(playerName + " has forced a save!");
			}
		}

		// Empty log, nothing to do!
		if (log.size() == 0) {
			saves.saveComplete(player);
			saves.saveProgress = false;
			return;
		}
		saves.saveProgress = true;
		plugin.getLoggerClass().debug("Saving " + log.size() + " items.");
		
		// Force save routine (On shutdown)
		if (forceSave) {
			plugin.getLoggerClass().debug("Save is forced! (Shutdown)");
			for (int i = 0; i < log.size(); i++) {

				// WE NEED TO WORK WITHOUT A STOP!!!!
				String line = log.get(i);
				String[] temp;
				temp = line.split(":");

				// Temp = playerName:World:BlockID
				plugin.getSaveHandler().save(temp[0], temp[1],
						Integer.parseInt(temp[2]));
				//plugin.loadFiles.getDataConfig().set(temp[1] + "." + temp[0] + "." + temp[2], Integer.valueOf(plugin.loadFiles.getDataConfig().getInt(temp[1] + "." + temp[0] + "." + temp[2]) + 1));
				//plugin.loadFiles.saveDataConfig();
			}
			log.clear();
			saves.saveComplete(player);
			saves.saveProgress = false;
			return;
		}

		// 'Quick saving to the end' routine
		if (log.size() <= 300) {
			for (int i = 0; i < log.size(); i++) {
				String line = log.get(i);
				String[] temp;
				temp = line.split(":");
				// Temp = playerName:World:BlockID
				plugin.getSaveHandler().save(temp[0], temp[1],
						Integer.parseInt(temp[2]));
				//plugin.loadFiles.getDataConfig().set(temp[1] + "." + temp[0] + "." + temp[2], Integer.valueOf(plugin.loadFiles.getDataConfig().getInt(temp[1] + "." + temp[0] + "." + temp[2]) + 1));
				//plugin.loadFiles.saveDataConfig();
			}
			plugin.getLoggerClass().debug("Quick saving to the end..");
			log.clear();
			saves.saveComplete(player);
			saves.saveProgress = false;
			return;
		}

		// Normal routine
		for (int i = 0; i < log.size(); i++) {

			// We have done enough work for this time! Let's take a short break.
			if (workCounter > Math.ceil(log.size() / 4)) {
				plugin.getLoggerClass().debug("Saved " + stbr.size()
						+ " items this round!");

				for (int j = 0; j < stbr.size(); j++) {
					plugin.loggedActions.remove(stbr.get(j));
				}
				saves.letsWork(playerName, player);
				plugin.getLoggerClass().debug("Total size of logged actions: "
						+ plugin.loggedActions.size());
				return;
			}
			// Work to do!
			String line = log.get(i);
			String[] temp;
			temp = line.split(":");
			stbr.add(line);

			// We have done more work! We're getting lazier...
			workCounter++;

			// Temp = playerName:World:BlockID
			plugin.getSaveHandler().save(temp[0], temp[1],
					Integer.parseInt(temp[2]));
			//plugin.loadFiles.getDataConfig().set(temp[1] + "." + temp[0] + "." + temp[2], Integer.valueOf(plugin.loadFiles.getDataConfig().getInt(temp[1] + "." + temp[0] + "." + temp[2]) + 1));
			//plugin.loadFiles.saveDataConfig();
		}
	}
}
