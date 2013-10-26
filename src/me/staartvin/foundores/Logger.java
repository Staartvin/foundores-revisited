package me.staartvin.foundores;

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
			System.out.print("[FoundOres Revisited] " + log);
			return true;
		}
		return false;
	}
	
	public boolean debug(String log) {
		if (plugin.getConfig().getBoolean("debug")) {
			System.out.print("[FoundOres Revisited DEBUG] " + log);
			return true;
		}
		return false;
	}
	public void logNormal(String log) {
		System.out.print("[FoundOres Revisited] " + log);
	}
}
