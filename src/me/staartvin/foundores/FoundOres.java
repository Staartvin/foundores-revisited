package me.staartvin.foundores;

import java.util.ArrayList;
import java.util.List;

import me.staartvin.foundores.announcer.AnnounceHandler;
import me.staartvin.foundores.announcer.AnnounceTask;
import me.staartvin.foundores.api.API;
import me.staartvin.foundores.commands.FoundOresCommandExecutor;
import me.staartvin.foundores.database.Database;
import me.staartvin.foundores.database.DatabaseConnector;
import me.staartvin.foundores.files.loadFiles;
import me.staartvin.foundores.listeners.BlockBreakListener;
import me.staartvin.foundores.listeners.PlayerJoinListener;
import me.staartvin.foundores.listeners.PlayerQuitListener;
import me.staartvin.foundores.mysql.MySQLHandler;
import me.staartvin.foundores.saves.IntervalSaves;
import me.staartvin.foundores.saves.SaveHandler;
import me.staartvin.foundores.update.AutoUpdateTask;
import me.staartvin.foundores.update.Updater;
import me.staartvin.foundores.update.Updater.UpdateResult;
import me.staartvin.foundores.util.FileLogger;
import me.staartvin.foundores.util.Logger;
import me.staartvin.foundores.util.Methods;
import me.staartvin.foundores.util.OreRatios;
import me.staartvin.foundores.util.FileLogger.eventTypes;
import me.staartvin.foundores.util.uuid.UUIDManager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class FoundOres extends JavaPlugin {

	// Push to GitHub
	private IntervalSaves saves = new IntervalSaves(this);
	private Logger logger = new Logger(this);
	private Methods methods = new Methods(this);
	private API api = new API(this);
	private DatabaseConnector dCon = new DatabaseConnector(this);
	private FileLogger fileLog = new FileLogger(this);
	private OreRatios oreRatio = new OreRatios();
	private MySQLHandler mysqlHandler = new MySQLHandler(this);
	private SaveHandler saveHandler = new SaveHandler(this);
	private AnnounceHandler announceHandler = new AnnounceHandler(this);
	private BlockBreakListener blockBreakListener;
	private UUIDManager uuidManager = new UUIDManager();
	
	public ArrayList<String> loggedActions = new ArrayList<String>();
	private Updater updater;
	//private BukkitTask autoUpdateTask;

	public long firstTime, lastTime, finalTime;

	public void onDisable() {
		//saves.save(null, true, true, null);

		saveHandler.forceSave();

		reloadConfig();
		saveConfig();

		getServer().getScheduler().cancelTasks(this);
		
		logger.logNormal("FoundOres Revisted v" + getDescription().getVersion()
				+ " has been disabled!");
		fileLog.logToFile("[INFO] FOUNDORES v" + getDescription().getVersion()
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
			new AutoUpdateTask(this).runTaskTimerAsynchronously(this, 600L, getConfig().getInt("Updater.autoUpdateIntervalTimer") * 1200);
		}

		// Initiate the announce task
		new AnnounceTask(this).runTaskTimerAsynchronously(this, 100L, 1200L);
		
		logger.logNormal("FoundOres Revisited v"
				+ getDescription().getVersion() + " has been enabled!");
		fileLog.logToFile("[INFO] FOUNDORES v" + getDescription().getVersion()
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
		blockBreakListener = new BlockBreakListener(this);
		getServer().getPluginManager().registerEvents(
				blockBreakListener, this);
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
	
	public Logger getLoggerClass() {
		return logger;
	}
	
	public DatabaseConnector getDatabaseConnector() {
		return dCon;
	}
	
	public OreRatios getOreRatioStorage() {
		return oreRatio;
	}
	
	public Methods getMethodsClass() {
		return methods;
	}
	
	public MySQLHandler getMySQLHandler() {
		return mysqlHandler;
	}
	
	public FileLogger getLogClass() {
		return fileLog;
	}
	
	public IntervalSaves getInternalSaves() {
		return saves;
	}
	
	public Updater getUpdater() {
		return updater;
	}
	
	public SaveHandler getSaveHandler() {
		return saveHandler;
	}
	
	public AnnounceHandler getAnnounceHandler() {
		return announceHandler;
	}
	
	public void hasReceived(boolean status) {
		
	}

	public UUIDManager getUUIDManager() {
		return uuidManager;
	}

	public void setUUIDManager(UUIDManager uuidManager) {
		this.uuidManager = uuidManager;
	}
}
