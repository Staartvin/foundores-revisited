package me.staartvin.foundores.mysql;

import java.util.UUID;

import me.staartvin.foundores.FileLogger.eventTypes;
import me.staartvin.foundores.FoundOres;
import me.staartvin.foundores.database.DatabaseConnector.blockTypes;

/**
 * @author Staartvin
 *
 */
public class MySQLHandler {

	FoundOres plugin;
	private String table = "foundores";
	private MySQL mysql;
	private String host;
	private String user;
	private String password;
	private String database;

	public MySQLHandler(FoundOres instance) {
		plugin = instance;
	}

	private void getConfigData() {
		host = plugin.getConfig().getString("MySQL.host");
		user = plugin.getConfig().getString("MySQL.user");
		password = plugin.getConfig().getString("MySQL.password");
		database = plugin.getConfig().getString("MySQL.database");
	}

	public void setupSQL() {

		getConfigData();

		mysql = new MySQL(host, user, password, database);

		if (mysql.connect()) {
			plugin.getLogger().info("MySQL database connected!");
			plugin.getLogClass().logToFile("Connected to database '" + database
					+ "' on host " + host, eventTypes.MYSQL_CONNECTION);
		} else {
			plugin.getLogger().info("Could not connect to MySQL database!");
		}
	}

	public void constructTables() {
		// Fixed MySQL connection not staying alive
		// If the MySQL Connection is somehow closed, open it again.
		if (mysql.isClosed()) {
			mysql.connect();
		}

		String statement = "CREATE TABLE IF NOT EXISTS " + table + " "
				+ "(id INT PRIMARY KEY AUTO_INCREMENT,"
				+ " uuid VARCHAR(255) not NULL UNIQUE,"
				+ " world VARCHAR(100) not NULL UNIQUE,"
				+ " stone BIGINT not NULL default '0',"
				+ " coal BIGINT not NULL default '0',"
				+ " iron BIGINT not NULL default '0',"
				+ " gold BIGINT not NULL default '0',"
				+ " redstone BIGINT not NULL default '0',"
				+ " lapis_lazuli BIGINT not NULL default '0',"
				+ " diamond BIGINT not NULL default '0',"
				+ " emerald BIGINT not NULL default '0',"
				+ " nether_quartz BIGINT not NULL default '0');";
		mysql.execute(statement);
	}

	// Add extra blocks if they aren't in the database yet.
	// If the database is from Minecraft Beta 1.8, it will not have emeralds as a column. We need to add that
	public void updateToLatestMinecraftVersion() {

		// If the MySQL Connection is somehow closed, open it again.
		if (mysql.isClosed()) {
			mysql.connect();
		}

		// Update for emerald
		String statement = " ALTER TABLE " + table
				+ " ADD emerald BIGINT NOT NULL default '0';";
		mysql.execute(statement);
		plugin.getLogger().info("Database check for emerald");

		// Update netherquartz
		statement = " ALTER TABLE " + table
				+ " ADD nether_quartz BIGINT NOT NULL default '0';";
		mysql.execute(statement);
		plugin.getLogger().info("Database check for netherquartz");

		plugin.getLogger().info("Database is up-to-date!");
	}

	public void incrementBlockCount(String world, UUID uuid, int blockID) {
		// If the MySQL Connection is somehow closed, open it again.
		if (mysql.isClosed()) {
			mysql.connect();
		}

		blockTypes type = null;

		if (blockID == 1) {
			type = blockTypes.STONE;
		} else if (blockID == 14) {
			type = blockTypes.GOLD;
		} else if (blockID == 15) {
			type = blockTypes.IRON;
		} else if (blockID == 16) {
			type = blockTypes.COAL;
		} else if (blockID == 21) {
			type = blockTypes.LAPIS_LAZULI;
		} else if (blockID == 56) {
			type = blockTypes.DIAMOND;
		} else if (blockID == 73) {
			type = blockTypes.REDSTONE;
		} else if (blockID == 129) {
			type = blockTypes.EMERALD;
		} else if (blockID == 153) {
			type = blockTypes.NETHER_QUARTZ;
		} else {
			type = blockTypes.STONE;
		}
		
		String blockName = type.toString().toLowerCase(); 
		int blockCount = plugin.getDatabaseConnector().getBrokenBlockCount(uuid, world, type);
		
		String statement = "" + "INSERT INTO " + table
				+ " (uuid, world, " + blockName + ")" + " VALUES ('" + uuid.toString()
				+ "', '" + world + "', " + blockCount + ")"
				+ " ON DUPLICATE KEY UPDATE " + blockName + "=" + blockCount;
		mysql.execute(statement);
	}
}
