package me.staartvin.foundores.api;

import me.staartvin.foundores.FoundOres;
import me.staartvin.foundores.database.DatabaseConnector.blockTypes;

public class API {

	FoundOres plugin;
	
	public API(FoundOres plugin) {
		this.plugin = plugin;
	}
	
	public String getName() {
		return plugin.getName();
	}
	
	public String getVersion() {
		return plugin.getDescription().getVersion();
	}
	
	public int getBrokenCount(String playerName, String world, blockTypes blockType) {
		return plugin.getDatabaseConnector().getBrokenBlockCount(playerName, world, blockType);
	}
	
	/**
	 * Checks if a player is logged on a certain world.
	 * @param playerName
	 * @param world
	 * @return true if player is logged on given world
	 */
	public boolean isLogged(String playerName, String world) {
		return plugin.getDatabaseConnector().isLogged(playerName, world);
	}
	
	/**
	 * Clear the data of an entry.
	 * All data of the entry is set to 0. (Apart from playerName and world)
	 * @param playerName
	 * @param world
	 * @return false if no entry was found
	 */
	public boolean clearEntry(String playerName, String world) {
		return plugin.getDatabaseConnector().clearEntry(playerName, world);
	}
	
	/**
	 * Create a new entry in the database. (eg. a new player)
	 * Entry gets all default counts.
	 * @param playerName
	 * @param world
	 * @return false if entry already exists
	 */
	public boolean createNewEntry(String playerName, String world) {
		return plugin.getDatabaseConnector().createNewEntry(playerName, world);
	}
}
