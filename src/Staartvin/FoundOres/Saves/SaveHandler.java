package Staartvin.FoundOres.Saves;

import java.util.ArrayList;

import Staartvin.FoundOres.FoundOres;

public class SaveHandler {

	private FoundOres plugin;

	public SaveHandler(FoundOres instance) {
		plugin = instance;
	}

	public void save(String player, String world, int blockID) {
		// Update local database
		plugin.dCon.incrementBlockCount(player, world, blockID);
		
		if (plugin.getConfig().getBoolean("MySQL.use")) {
			plugin.mysqlHandler.incrementBlockCount(world, player, blockID);
		}
	}
	
	public void forceSave() {
		ArrayList<String> log = plugin.loggedActions;
		plugin.logger.debug("Save is forced! (Shutdown)");
		for (int i = 0; i < log.size(); i++) {

			// WE NEED TO WORK WITHOUT A STOP!!!!
			String line = log.get(i);
			String[] temp;
			temp = line.split(":");

			// Temp = playerName:World:BlockID
			plugin.dCon.incrementBlockCount(temp[0], temp[1],
					Integer.parseInt(temp[2]));
			
			// Don't use save() because MySQL takes too long for a shutdown
			// The data will be saved to the database once the server is started again.
		}
		log.clear();
		plugin.saves.saveProgress = false;
	}
}
