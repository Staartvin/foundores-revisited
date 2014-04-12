package me.staartvin.foundores.saves;

import java.util.ArrayList;
import java.util.UUID;

import me.staartvin.foundores.FoundOres;


/**
 * This class handles whether we should save to MYSQL if it's enabled. We always save local.
 * @author Staartvin
 *
 */
public class SaveHandler {

	private FoundOres plugin;

	public SaveHandler(FoundOres instance) {
		plugin = instance;
	}

	public void save(UUID uuid, String world, int blockID) {
		// Update local database
		plugin.getDatabaseConnector().incrementBlockCount(uuid, world, blockID);
		
		if (plugin.getConfig().getBoolean("MySQL.use")) {
			plugin.getMySQLHandler().incrementBlockCount(world, uuid, blockID);
		}
	}
	
	public void forceSave() {
		ArrayList<String> log = plugin.loggedActions;
		plugin.getLoggerClass().debug("Save is forced! (Shutdown)");
		for (int i = 0; i < log.size(); i++) {

			// WE NEED TO WORK WITHOUT A STOP!!!!
			String line = log.get(i);
			String[] temp;
			temp = line.split(":");

			// Temp = UUID:World:BlockID
			plugin.getDatabaseConnector().incrementBlockCount(UUID.fromString(temp[0]), temp[1],
					Integer.parseInt(temp[2]));
			
			// Don't use save() because MySQL takes too long for a shutdown
			// The data will be saved to the database once the server is started again.
		}
		log.clear();
		plugin.getInternalSaves().saveProgress = false;
	}
}
