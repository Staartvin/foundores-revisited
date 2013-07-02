package Staartvin.FoundOres.API;

import Staartvin.FoundOres.FoundOres;

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
	
	public Integer getBrokenStone(String playerName, String world) {
		return plugin.dCon.getBrokenStone(playerName, world);
	}
	
	public Integer getBrokenCoal(String playerName, String world) {
		return plugin.dCon.getBrokenCoal(playerName, world);
	}
	
	public Integer getBrokenIron(String playerName, String world) {
		return plugin.dCon.getBrokenIron(playerName, world);
	}
	
	public Integer getBrokenGold(String playerName, String world) {
		return plugin.dCon.getBrokenGold(playerName, world);
	}
	
	public Integer getBrokenRedstone(String playerName, String world) {
		return plugin.dCon.getBrokenRedstone(playerName, world);
	}
	
	public Integer getBrokenLapisLazuli(String playerName, String world) {
		return plugin.dCon.getBrokenLapisLazuli(playerName, world);
	}
	
	public Integer getBrokenDiamond(String playerName, String world) {
		return plugin.dCon.getBrokenDiamond(playerName, world);
	}
	
	public Integer getBrokenEmerald(String playerName, String world) {
		return plugin.dCon.getBrokenEmerald(playerName, world);
	}
	
	public Integer getBrokenNetherQuartz(String playerName, String world) {
		return plugin.dCon.getBrokenNetherQuartz(playerName, world);
	}
	
	/**
	 * Checks if a player is logged on a certain world.
	 * @param playerName
	 * @param world
	 * @return true if player is logged on given world
	 */
	public boolean isLogged(String playerName, String world) {
		return plugin.dCon.isLogged(playerName, world);
	}
	
	/**
	 * Clear the data of an entry.
	 * All data of the entry is set to 0. (Apart from playerName and world)
	 * @param playerName
	 * @param world
	 * @return false if no entry was found
	 */
	public boolean clearEntry(String playerName, String world) {
		return plugin.dCon.clearEntry(playerName, world);
	}
	
	/**
	 * Create a new entry in the database. (eg. a new player)
	 * Entry gets all default counts.
	 * @param playerName
	 * @param world
	 * @return false if entry already exists
	 */
	public boolean createNewEntry(String playerName, String world) {
		return plugin.dCon.createNewEntry(playerName, world);
	}
}
