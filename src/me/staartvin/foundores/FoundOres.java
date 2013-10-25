package me.staartvin.foundores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.staartvin.foundores.LogClass.eventTypes;
import me.staartvin.foundores.API.API;
import me.staartvin.foundores.Commands.FoundOresCommandExecutor;
import me.staartvin.foundores.Database.Database;
import me.staartvin.foundores.Database.DatabaseConnector;
import me.staartvin.foundores.Files.loadFiles;
import me.staartvin.foundores.Listeners.BlockBreakListener;
import me.staartvin.foundores.Listeners.PlayerJoinListener;
import me.staartvin.foundores.Listeners.PlayerQuitListener;
import me.staartvin.foundores.MySQL.MySQLHandler;
import me.staartvin.foundores.Saves.IntervalSaves;
import me.staartvin.foundores.Saves.SaveHandler;
import me.staartvin.foundores.Update.AutoUpdateTask;
import me.staartvin.foundores.Update.Updater;
import me.staartvin.foundores.Update.Updater.UpdateResult;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;


public class FoundOres extends JavaPlugin {

	// Push to GitHub
	public IntervalSaves saves = new IntervalSaves(this);
	public Logger logger = new Logger(this);
	public Methods methods = new Methods(this);
	public API api = new API(this);
	public DatabaseConnector dCon = new DatabaseConnector(this);
	public LogClass log = new LogClass(this);
	public OreRatios oreRatio = new OreRatios();
	public MySQLHandler mysqlHandler = new MySQLHandler(this);
	public SaveHandler saveHandler = new SaveHandler(this);
	public ArrayList<String> loggedActions = new ArrayList<String>();
	public Updater updater;
	public BukkitTask autoUpdateTask;

	public HashMap<String, Boolean> hasReceived = new HashMap<String, Boolean>();
	public HashMap<String, Boolean> mineVerify = new HashMap<String, Boolean>();
	public HashMap<String, Boolean> schedulerRun = new HashMap<String, Boolean>();
	public HashMap<String, Integer> oreID = new HashMap<String, Integer>();
	public HashMap<String, Integer> brokenCount = new HashMap<String, Integer>();
	public HashMap<String, Material> oreMaterial = new HashMap<String, Material>();

	long firstTime, lastTime, finalTime;

	public void onDisable() {
		//saves.save(null, true, true, null);

		saveHandler.forceSave();

		reloadConfig();
		saveConfig();

		getServer().getScheduler().cancelTasks(this);
		
		logger.logNormal("FoundOres Revisted v" + getDescription().getVersion()
				+ " has been disabled!");
		log.logToFile("[INFO] FOUNDORES v" + getDescription().getVersion()
				+ " has been disabled", eventTypes.DISABLE);
	}

	public void onEnable() {

		methods.startTest();

		getCommand("foundores").setExecutor(new FoundOresCommandExecutor(this)); // Register
		// commands
		new loadFiles(this).loadConfiguration();

		setupDatabase();

		saves.saveOnInterval();

		registerListeners();

		// Setup a MySQL connection
		if (getConfig().getBoolean("MySQL.use")) {

			// Setup connection
			mysqlHandler.setupSQL();

			// Construct table
			mysqlHandler.constructTables();
		}

		methods.stopTest();

		// Check for a newer version
		if (getConfig().getBoolean("Updater.doCheckUpdate")) {
			updater = new Updater(this, "foundores-revisited", this.getFile(),
					Updater.UpdateType.NO_DOWNLOAD, false);
		}
		
		// Schedule a check every x minutes
		if (getConfig().getBoolean("Updater.doAutoUpdate")) {
			autoUpdateTask = new AutoUpdateTask(this).runTaskTimerAsynchronously(this, 600L, getConfig().getInt("Updater.autoUpdateIntervalTimer") * 1200);
		}

		logger.logNormal("FoundOres Revisited v"
				+ getDescription().getVersion() + " has been enabled!");
		log.logToFile("[INFO] FOUNDORES v" + getDescription().getVersion()
				+ " has been enabled", eventTypes.ENABLE);
	}

	/**
	 * Gets an instance of FoundOres Revisited.
	 * This allows you to get all methods in API class.
	 * 
	 * @return API Class.
	 */
	public API getInstance() {
		return api;
	}

	private void setupDatabase() {
		try {
			getDatabase().find(Database.class).findRowCount();
		} catch (Exception e) {
			logger.logNormal("Installing Database for the first time!");
			installDDL();
		}
	}

	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(Database.class);
		return list;
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(
				new BlockBreakListener(this), this);
		getServer().getPluginManager().registerEvents(
				new PlayerJoinListener(this), this);
		getServer().getPluginManager().registerEvents(
				new PlayerQuitListener(this), this);
	}

	public boolean isUpdateAvailable() {
		if (!getConfig().getBoolean("Updater.doCheckUpdate")) {
			return false;
		}
		
		Updater updater = new Updater(this, "foundores-revisited", this.getFile(),
				Updater.UpdateType.NO_DOWNLOAD, false);
		Updater.UpdateResult result = updater.getResult();

		if (result.equals(Updater.UpdateResult.UPDATE_AVAILABLE)) {
			// There was an update found, but because you had the UpdateType set to NO_DOWNLOAD, it was not downloaded.
			return true;
		} else {
			return false;
		}
	}

	public boolean updatePlugin(CommandSender sender) {
		if (sender == null) {
			sender = getServer().getConsoleSender();
		}
		
		if (!isUpdateAvailable()) {
			sender.sendMessage(ChatColor.RED + "There is no update available.");
			return false;
		}
		
		sender.sendMessage(ChatColor.GOLD + "Starting update...");
		Updater updater = new Updater(this, "foundores-revisited", this.getFile(), Updater.UpdateType.NO_VERSION_CHECK, true);
		UpdateResult result = updater.getResult();
		
		switch(result) {
			case SUCCESS:
				sender.sendMessage(ChatColor.GREEN + "Successfully downloaded " + updater.getLatestVersionString());
				sender.sendMessage(ChatColor.GOLD + "Check your updates folder for the file.");
				return true;
			case FAIL_DOWNLOAD:
				sender.sendMessage(ChatColor.RED + "Newer version detected, but could not download!");
				return false;
			case FAIL_DBO:
				sender.sendMessage(ChatColor.RED + "Could not contact dev.bukkit.org. Try again later!");
				return false;
			case FAIL_NOVERSION:
				sender.sendMessage(ChatColor.RED + "Could not find any version. Nag author!");
				return false;
			case FAIL_BADSLUG:
				sender.sendMessage(ChatColor.RED + "Author did not set up updater correctly. Nag author!");
				return false;
			default:
				break;
		}
		return false;
	}
}
