package me.staartvin.foundores;

import java.util.logging.Level;

/**
 * This class outputs text in the console in real time.
 * @author Staartvin
 *
 */
public class Logger {

	FoundOres plugin;

	protected Logger(FoundOres plugin) {
		this.plugin = plugin;
	}
	
	public boolean logVerbose(String log) {
		if (plugin.getConfig().getBoolean("verboseLogging")) {
			plugin.getLogger().log(Level.INFO, log);
			return true;
		}
		return false;
	}
	
	public boolean debug(String log) {
		if (plugin.getConfig().getBoolean("debug")) {
			plugin.getLogger().log(Level.INFO, "DEBUG: " + log);
			return true;
		}
		return false;
	}
	public void logNormal(String log) {
		plugin.getLogger().info(log);
	}
}
