package me.staartvin.foundores.database;

import java.util.List;
import java.util.UUID;

import me.staartvin.foundores.FoundOres;

import org.bukkit.entity.Player;

public class DatabaseConnector {

	FoundOres plugin;

	public DatabaseConnector(FoundOres plugin) {
		this.plugin = plugin;
	}

	public enum blockTypes {
		STONE, COAL, IRON, GOLD, LAPIS_LAZULI, EMERALD, REDSTONE, DIAMOND, NETHER_QUARTZ
	};

	public int getBrokenBlockCount(Player player, String worldName,
			blockTypes blockType) {
		return getBrokenBlockCount(player.getUniqueId(), worldName, blockType);
	}

	public int getBrokenBlockCount(String playerName, String worldName,
			blockTypes blockType) {
		return getBrokenBlockCount(
				plugin.getUUIDManager().getUUIDFromPlayer(playerName),
				worldName, blockType);
	}

	public int getBrokenBlockCount(UUID uuid, String worldName,
			blockTypes blockType) {
		if (uuid == null) return -1;
		Database database = plugin.getDatabase().find(Database.class).where()
				.ieq("uuid", uuid.toString()).ieq("world", worldName)
				.findUnique();

		if (database == null)
			return -1;

		return database.getBrokenCount(blockType);
	}

	public boolean clearEntry(Player player, String world) {
		return clearEntry(player.getUniqueId(), world);
	}

	public boolean clearEntry(String playerName, String world) {
		return clearEntry(
				plugin.getUUIDManager().getUUIDFromPlayer(playerName), world);
	}

	public boolean clearEntry(UUID uuid, String world) {
		if (uuid == null) return false;
		
		Database database = plugin.getDatabase().find(Database.class).where()
				.ieq("uuid", uuid.toString()).ieq("world", world).findUnique();

		if (database == null)
			return false;

		for (blockTypes blockType : blockTypes.values()) {
			database.setBrokenCount(blockType, 0);
		}

		plugin.getDatabase().save(database);

		return true;
	}

	public int purgeDatabase() {
		List<Database> database = plugin.getDatabase().find(Database.class)
				.findList();

		if (database == null || database.size() == 0) {
			return 0;
		}
		plugin.getDatabase().delete(database);

		return database.size();
	}

	public boolean createNewEntry(Player player, String worldName) {
		return createNewEntry(player.getUniqueId(), worldName);
	}

	public boolean createNewEntry(String playerName, String worldName) {
		return createNewEntry(
				plugin.getUUIDManager().getUUIDFromPlayer(playerName),
				worldName);
	}

	public boolean createNewEntry(UUID uuid, String worldName) {
		if (uuid == null) return false;
		
		Database database = plugin.getDatabase().find(Database.class).where()
				.ieq("uuid", uuid.toString()).ieq("world", worldName)
				.findUnique();

		if (database != null)
			return false;

		database = new Database();

		for (blockTypes blockType : blockTypes.values()) {
			database.setBrokenCount(blockType, 0);
		}

		database.setUUID(uuid.toString());

		database.setWorld(worldName);

		plugin.getDatabase().save(database);

		return true;
	}

	public boolean incrementBlockCount(Player player, String world, int blockID) {
		return incrementBlockCount(player.getUniqueId(), world, blockID);
	}

	public boolean incrementBlockCount(UUID uuid, String world, int blockID) {
		if (uuid == null) return false;
		
		Database database = plugin.getDatabase().find(Database.class).where()
				.ieq("uuid", uuid.toString()).ieq("world", world).findUnique();

		if (database == null)
			return false;

		blockTypes blockType = null;

		if (blockID == 1) {
			blockType = blockTypes.STONE;
		} else if (blockID == 14) {
			blockType = blockTypes.GOLD;
		} else if (blockID == 15) {
			blockType = blockTypes.IRON;
		} else if (blockID == 16) {
			blockType = blockTypes.COAL;
		} else if (blockID == 21) {
			blockType = blockTypes.LAPIS_LAZULI;
		} else if (blockID == 56) {
			blockType = blockTypes.DIAMOND;
		} else if (blockID == 73) {
			blockType = blockTypes.REDSTONE;
		} else if (blockID == 129) {
			blockType = blockTypes.EMERALD;
		} else if (blockID == 153) {
			blockType = blockTypes.NETHER_QUARTZ;
		}

		if (blockType == null)
			return false;

		database.setBrokenCount(blockType,
				database.getBrokenCount(blockType) + 1);

		plugin.getDatabase().save(database);
		return true;
	}

	public boolean isLogged(Player player, String worldName) {
		return isLogged(player.getUniqueId(), worldName);
	}

	public boolean isLogged(String playerName, String worldName) {
		return isLogged(plugin.getUUIDManager().getUUIDFromPlayer(playerName),
				worldName);
	}

	public boolean isLogged(UUID uuid, String worldName) {
		if (uuid == null) return false;
		
		Database database = plugin.getDatabase().find(Database.class).where()
				.ieq("uuid", uuid.toString()).ieq("world", worldName)
				.findUnique();

		return (database != null);
	}
}
