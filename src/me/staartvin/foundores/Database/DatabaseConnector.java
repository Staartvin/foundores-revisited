package me.staartvin.foundores.Database;

import java.util.List;

import me.staartvin.foundores.FoundOres;


public class DatabaseConnector {

	FoundOres plugin;
	
	public DatabaseConnector(FoundOres plugin) {
		this.plugin = plugin;
	}
	
	public Integer getBrokenStone(String playerName, String world) {
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).ieq("world", world).findUnique();
		
		if (database == null) return null;
		
		return database.getBrokenStone();
	}
	
	public Integer getBrokenCoal(String playerName, String world) {
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).ieq("world", world).findUnique();
		
		if (database == null) return null;
		
		return database.getBrokenCoal();
	}
	
	public Integer getBrokenIron(String playerName, String world) {
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).ieq("world", world).findUnique();
		
		if (database == null) return null;
		
		return database.getBrokenIron();
	}
	
	public Integer getBrokenGold(String playerName, String world) {
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).ieq("world", world).findUnique();
		
		if (database == null) return null;
		
		return database.getBrokenGold();
	}
	
	public Integer getBrokenLapisLazuli(String playerName, String world) {
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).ieq("world", world).findUnique();
		
		if (database == null) return null;
		
		return database.getBrokenLapisLazuli();
	}
	
	public Integer getBrokenEmerald(String playerName, String world) {
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).ieq("world", world).findUnique();
		
		if (database == null) return null;
		
		return database.getBrokenEmerald();
	}
	
	public Integer getBrokenRedstone(String playerName, String world) {
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).ieq("world", world).findUnique();
		
		if (database == null) return null;
		
		return database.getBrokenRedstone();
	}
	
	public Integer getBrokenDiamond(String playerName, String world) {
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).ieq("world", world).findUnique();
		
		if (database == null) return null;
		
		return database.getBrokenDiamond();
	}
	
	public Integer getBrokenNetherQuartz(String playerName, String world) {
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).ieq("world", world).findUnique();
		
		if (database == null) return null;
		
		return database.getBrokenNetherQuartz();
	}
	
	public boolean clearEntry(String playerName, String world) {
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).ieq("world", world).findUnique();
		
		if (database == null) return false;
		
		database.setBrokenStone(0);
		database.setBrokenCoal(0);
		database.setBrokenIron(0);
		database.setBrokenGold(0);
		database.setBrokenRedstone(0);
		database.setBrokenDiamond(0);
		database.setBrokenEmerald(0);
		database.setBrokenNetherQuartz(0);
		database.setBrokenLapisLazuli(0);
		
		plugin.getDatabase().save(database);
		
		return true;
	}
	
	public Integer purgeDatabase() {
		List<Database> database = plugin.getDatabase().find(Database.class).findList();
		
		if (database == null || database.size() == 0) {
			return 0;
		}
		plugin.getDatabase().delete(database);
		
		return database.size();
	}
	
	public boolean createNewEntry(String playerName, String world) {
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).ieq("world", world).findUnique();
		
		if (database != null) return false;
		
		database = new Database();
		database.setBrokenStone(0);
		database.setBrokenCoal(0);
		database.setBrokenIron(0);
		database.setBrokenGold(0);
		database.setBrokenRedstone(0);
		database.setBrokenDiamond(0);
		database.setBrokenEmerald(0);
		database.setBrokenNetherQuartz(0);
		database.setBrokenLapisLazuli(0);
		
		database.setPlayerName(playerName);
		database.setWorld(world);
		
		plugin.getDatabase().save(database);
		
		return true;
	}
	
	public boolean incrementBlockCount(String playerName, String world, Integer blockID) {
		
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).ieq("world", world).findUnique();
		if (database == null) return false;
		
		if (blockID == 1) {
			database.setBrokenStone(database.getBrokenStone() + 1);
			plugin.getDatabase().save(database);
			return true;
		}
		else if (blockID == 14) {
			database.setBrokenGold(database.getBrokenGold() + 1);
			plugin.getDatabase().save(database);
			return true;
		}
		else if (blockID == 15) {
			database.setBrokenIron(database.getBrokenIron() + 1);
			plugin.getDatabase().save(database);
			return true;
		}
		else if (blockID == 16) {
			database.setBrokenCoal(database.getBrokenCoal() + 1);
			plugin.getDatabase().save(database);
			return true;
		}
		else if (blockID == 21) {
			database.setBrokenLapisLazuli(database.getBrokenLapisLazuli() + 1);
			plugin.getDatabase().save(database);
			return true;
		}
		else if (blockID == 56) {
			database.setBrokenDiamond(database.getBrokenDiamond() + 1);
			plugin.getDatabase().save(database);
			return true;
		}
		else if (blockID == 73) {
			database.setBrokenRedstone(database.getBrokenRedstone() + 1);
			plugin.getDatabase().save(database);
			return true;
		}
		else if (blockID == 129) {
			database.setBrokenEmerald(database.getBrokenEmerald() + 1);
			plugin.getDatabase().save(database);
			return true;
		}
		else if (blockID == 153) {
			database.setBrokenNetherQuartz(database.getBrokenNetherQuartz() + 1);
			plugin.getDatabase().save(database);
			return true;
		}
		return false;
	}
	
	public boolean isLogged(String playerName, String world) {
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).ieq("world", world).findUnique();
		
		if (database == null) return false;
		
		return true;
	}
}
