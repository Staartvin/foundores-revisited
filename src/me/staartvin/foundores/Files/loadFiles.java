package me.staartvin.foundores.Files;

import java.util.Arrays;
import java.util.List;

import me.staartvin.foundores.FoundOres;

import org.bukkit.configuration.file.FileConfiguration;


public class loadFiles {

	FoundOres plugin;
	private String[] worlds = {};

	public loadFiles(FoundOres plugin) {
		this.plugin = plugin;
	}

	public void loadConfiguration() {
		FileConfiguration config = plugin.getConfig();
		config
				.options()
				.header("FoundOres Revisited v"
						+ plugin.getDescription().getVersion()
						+ "\nThis is the config of FoundOres Revisited"
						+ "\nWhen 'announceMode' is true, when a player mines a block which is in the 'NoticeableBlocks' list, all OP's and players with 'foundores.notice' will be notified"
						+ "\nWhen 'noticePlayerOnMine' is true, players will be notified that admins are watching them. This only scares players that they are watched. The message shown is 'noticeMessageToPlayer'"
						+ "\nSaveInterval is the interval in minutes. E.G: 'SaveInterval: 30' means that FO:RE will save every 30 minutes."
						+ "\nWhen 'allowLowLightMining' is true, players can mine in the dark. (Lightlevel = 4) This is to stop fullbrighters."
						+ "\nWhen 'checkCreativeMode' is true, players in creativemode will be logged as well."
						+ "\n'Sleeptime' is the amount of seconds between 2 save actions. By default this is set to 10 seconds. For most servers this doesn't have to be changed. If you feel like changing it, don't set it to more than 1 minute."
						+ "\n'LightLevelDenial' is the light level when a player can't break blocks anymore and gets the message: 'Too dark'!"
						+ "\nDisabledWorlds contains all disabled worlds. These worlds will not be logged. World names are case-sensitive."
						+ "\nAutoUpdateIntervalTimer is the interval in minutes that FoundOres will check for a new version."
						+ "\n\nThanks for using FO:RE! Questions? http://dev.bukkit.org/server-mods/foundores-revisited/");

		config.addDefault("announceMode", false);
		config.addDefault("noticePlayerOnMine", true);
		config
				.addDefault("noticeMessageToPlayer",
						"You are mining. Please note that you're being watched by the admins.");
		config.addDefault("SaveInterval", 5);
		config.addDefault("allowLowLightMining", false);
		config.addDefault("checkCreativeMode", false);
		config.addDefault("sleepTime", 10);
		config.addDefault("LightLevelDenial", 1);
		if (config.getList("NoticeableBlocks") == null) {
			config.set("NoticeableBlocks",
					Arrays.asList(worlds));
			List<Integer> blocks = config.getIntegerList(
					"NoticeableBlocks");
			blocks.add(56);
			blocks.add(73);
			config.set("NoticeableBlocks", blocks);
			plugin.saveConfig();
		}
		if (config.getList("DisabledWorlds") == null) {
			config.set("DisabledWorlds",
					Arrays.asList(worlds));
			List<String> worlds = config.getStringList(
					"DisabledWorlds");
			worlds.add("Herobrine_World");
			worlds.add("ExampleWorld_the_end");
			config.set("DisabledWorlds", worlds);
			plugin.saveConfig();
		}
		config.addDefault("Logger.useLogger", true);
		config.addDefault("Logger.logMine", false);
		config.addDefault("Logger.logReportCreation", true);
		config.addDefault("Logger.logCommands", true);
		config.addDefault("Logger.logMySQL", false);
		config.addDefault("verboseLogging", true);
		config.addDefault("debug", false);
		
		// Add MySQL data
		config.addDefault("MySQL.use", false);
		config.addDefault("MySQL.host", "localhost:3306");
		config.addDefault("MySQL.database", "minecraft");
		config.addDefault("MySQL.user", "admin");
		config.addDefault("MySQL.password", "1234");
		
		// Add Updater options
		config.addDefault("Updater.doCheckUpdate", true);
		config.addDefault("Updater.doAutoUpdate", false);
		config.addDefault("Updater.autoUpdateIntervalTimer", 60);


		plugin.getLoggerClass().logVerbose("Connecting to database...");

		config.options().copyDefaults(true);
		plugin.saveConfig();
	}
}
