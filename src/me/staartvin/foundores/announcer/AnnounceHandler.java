package me.staartvin.foundores.announcer;

import java.util.HashMap;
import java.util.Set;

import me.staartvin.foundores.FoundOres;

import org.bukkit.entity.Player;

/**
 * Every minute an announcer task announces what blocks have been broken by what
 * people.
 * 
 * <p>
 * The {@link #records} hashmap contains a list of strings that identify what has
 * been broken by what player.
 * 
 * <p>
 * After a minute all entries will be counted and an announcement will print
 * what people broke.
 * 
 * @author Staartvin
 * 
 */
public class AnnounceHandler {

	private FoundOres plugin;

	public AnnounceHandler(FoundOres instance) {
		plugin = instance;
	}

	// String is the playername.
	// First integer is the block id
	// Second integer is amount
	private HashMap<String, HashMap<Integer, Integer>> records = new HashMap<String, HashMap<Integer, Integer>>();

	public void registerBlockBreak(String playerName, int blockId) {
		int amount = 0;
				
		if (records.containsKey(playerName)) {
			if (records.get(playerName).containsKey(blockId)) {
				amount = records.get(playerName).get(blockId);
			}
		}
		
		HashMap<Integer, Integer> newRecord;
		
		if (records.containsKey(playerName)) {
			newRecord = records.get(playerName);
		} else {
			HashMap<Integer, Integer> tempHash = new HashMap<Integer, Integer>();
			tempHash.put(blockId, 0);
			
			newRecord = tempHash;
		}
		
		
		// Update newRecord
		newRecord.put(blockId, amount + 1);
		
		records.put(playerName, newRecord);
	}

	public Set<String> getRecordKeys() {
		return records.keySet();
	}
	
	public Set<Integer> getBlockIdKeys(String playerName) {
		return records.get(playerName).keySet();
	}
	
	public int getBlockIdAmount(String playerName, int blockId) {
		return records.get(playerName).get(blockId);
	}

	public boolean shouldRecord(Player player, int blockId) {
		if (!plugin.getConfig().getBoolean("announceMode")) {
			return false;
		}
		if (!plugin.getConfig().getList("NoticeableBlocks").contains(blockId)) {
			return false;
		}
		if (player.hasPermission("foundores.exempt.noticeable")) {
			return false;
		}

		return true;
	}
	
	public void resetStats() {
		records.clear();
	}
}
