package me.staartvin.foundores.report;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import me.staartvin.foundores.FoundOres;
import me.staartvin.foundores.database.Database;
import me.staartvin.foundores.database.DatabaseConnector.blockTypes;
import me.staartvin.foundores.util.FileLogger.eventTypes;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Report {

	FoundOres plugin;
	protected FileConfiguration reportConfig;
	protected File reportConfigFile;
	java.util.Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	String[] playerarray = {};

	protected Report(FoundOres plugin) {
		this.plugin = plugin;
	}

	protected void createReport() {

		reloadReportConfig();
		saveReportConfig();
		loadReport();
		fillReport();
	}

	protected void reloadReportConfig() {
		if (reportConfigFile == null) {
			reportConfigFile = new File(plugin.getDataFolder() + "/reports",
					"Report " + format.format(date) + ".yml");
		}
		reportConfig = YamlConfiguration.loadConfiguration(reportConfigFile);

		// Look for defaults in the jar
		InputStream defConfigStream = plugin.getResource("Report.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			reportConfig.setDefaults(defConfig);
		}
	}

	protected FileConfiguration getReportConfig() {
		if (reportConfig == null) {
			this.reloadReportConfig();
		}
		return reportConfig;
	}

	protected void saveReportConfig() {
		if (reportConfig == null || reportConfigFile == null) {
			return;
		}
		try {
			getReportConfig().save(reportConfigFile);
		} catch (IOException ex) {
			plugin.getLogger().log(Level.SEVERE,
					"Could not save config to " + reportConfigFile, ex);
		}
	}

	protected void loadReport() {

		reportConfig.options().header("REPORT CREATED: " + date);
		reportConfig.set("PlayersRed", Arrays.asList(playerarray));
		reportConfig.set("PlayersOrange", Arrays.asList(playerarray));

		reportConfig.options().copyDefaults(true);
		saveReportConfig();
	}

	protected void fillReport() {
		plugin.getLoggerClass().logNormal(
				"Creating a report. This can take a while...");
		plugin.getLogClass().logToFile("Creating a report",
				eventTypes.REPORT_CREATION);
		List<Database> database = plugin.getDatabase().find(Database.class)
				.findList();

		// Don't do any extra work if that isn't needed.
		if (database.size() == 0) {
			return;
		}

		UUID uuid;

		for (int i = 0; i < database.size(); i++) {
			uuid = database.get(i).getRealUUID();

			String playerName = plugin.getUUIDManager().getPlayerFromUUID(uuid);

			double b1 = database.get(i).getBrokenCount(blockTypes.STONE);
			double b2 = database.get(i).getBrokenCount(blockTypes.COAL);
			double b3 = database.get(i).getBrokenCount(blockTypes.IRON);
			double b4 = database.get(i).getBrokenCount(blockTypes.REDSTONE);
			double b5 = database.get(i).getBrokenCount(blockTypes.GOLD);
			double b6 = database.get(i).getBrokenCount(blockTypes.LAPIS_LAZULI);
			double b7 = database.get(i).getBrokenCount(blockTypes.DIAMOND);
			double b8 = database.get(i).getBrokenCount(blockTypes.EMERALD);
			double b9 = database.get(i)
					.getBrokenCount(blockTypes.NETHER_QUARTZ);
			double ba = b1 + b2 + b3 + b4 + b5 + b6 + b7 + b8 + b9;

			double p2 = b2 / ba;
			double p3 = b3 / ba;
			double p4 = b4 / ba;
			double p5 = b5 / ba;
			double p6 = b6 / ba;
			double p7 = b7 / ba;
			double p8 = b8 / ba;
			double p9 = b9 / ba;

			if (p2 * 100.0D > plugin.getOreRatioStorage().getCoalHigh()) {

				List<String> players = reportConfig.getStringList("PlayersRed");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersRed", players);
				saveReportConfig();
			} else if (p2 * 100.0D > plugin.getOreRatioStorage().getCoalLow()) {

				List<String> players = reportConfig
						.getStringList("PlayersOrange");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersOrange", players);
				saveReportConfig();
			}

			if (p3 * 100.0D > plugin.getOreRatioStorage().getIronHigh()) {

				List<String> players = reportConfig.getStringList("PlayersRed");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersRed", players);
				saveReportConfig();
			} else if (p3 * 100.0D > plugin.getOreRatioStorage().getIronLow()) {

				List<String> players = reportConfig
						.getStringList("PlayersOrange");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersOrange", players);
				saveReportConfig();
			}
			if (p4 * 100.0D > plugin.getOreRatioStorage().getRedstoneHigh()) {

				List<String> players = reportConfig.getStringList("PlayersRed");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersRed", players);
				saveReportConfig();
			} else if (p4 * 100.0D > plugin.getOreRatioStorage()
					.getRedstoneLow()) {

				List<String> players = reportConfig
						.getStringList("PlayersOrange");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersOrange", players);
				saveReportConfig();
			}
			if (p5 * 100.0D > plugin.getOreRatioStorage().getGoldHigh()) {

				List<String> players = reportConfig.getStringList("PlayersRed");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersRed", players);
				saveReportConfig();
			} else if (p5 * 100.0D > plugin.getOreRatioStorage().getGoldLow()) {

				List<String> players = reportConfig
						.getStringList("PlayersOrange");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersOrange", players);
				saveReportConfig();
			}
			if (p6 * 100.0D > plugin.getOreRatioStorage().getLapisHigh()) {

				List<String> players = reportConfig.getStringList("PlayersRed");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersRed", players);
				saveReportConfig();
			} else if (p6 * 100.0D > plugin.getOreRatioStorage().getLapisLow()) {

				List<String> players = reportConfig
						.getStringList("PlayersOrange");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersOrange", players);
				saveReportConfig();
			}
			if (p7 * 100.0D > plugin.getOreRatioStorage().getDiamondHigh()) {

				List<String> players = reportConfig.getStringList("PlayersRed");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersRed", players);
				saveReportConfig();
			} else if (p7 * 100.0D > plugin.getOreRatioStorage()
					.getDiamondLow()) {

				List<String> players = reportConfig
						.getStringList("PlayersOrange");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersOrange", players);
				saveReportConfig();
			}
			if (p8 * 100.0D > plugin.getOreRatioStorage().getEmeraldHigh()) {

				List<String> players = reportConfig.getStringList("PlayersRed");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersRed", players);
				saveReportConfig();
			} else if (p8 * 100.0D > plugin.getOreRatioStorage()
					.getEmeraldLow()) {

				List<String> players = reportConfig
						.getStringList("PlayersOrange");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersOrange", players);
				saveReportConfig();
			}
			if (p9 * 100.0D > plugin.getOreRatioStorage().getNetherquartzHigh()) {

				List<String> players = reportConfig.getStringList("PlayersRed");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersRed", players);
				saveReportConfig();
			} else if (p9 * 100.0D > plugin.getOreRatioStorage()
					.getNetherquartzLow()) {

				List<String> players = reportConfig
						.getStringList("PlayersOrange");
				if (players.contains(playerName.toLowerCase())) {
					continue;
				}
				players.add(playerName.toLowerCase());
				reportConfig.set("PlayersOrange", players);
				saveReportConfig();
			}
		}

		plugin.getLoggerClass().debug("Report has been created!");
		plugin.getLogClass().logToFile("Report has been created",
				eventTypes.REPORT_CREATION);
	}
}
