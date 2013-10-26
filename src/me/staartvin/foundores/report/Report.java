package me.staartvin.foundores.report;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import me.staartvin.foundores.FoundOres;
import me.staartvin.foundores.FileLogger.eventTypes;
import me.staartvin.foundores.database.Database;

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
		plugin.getLoggerClass().logNormal("Creating a report. This can take a while...");
		plugin.getLogClass().logToFile("Creating a report", eventTypes.REPORT_CREATION);
		List<Database> database = plugin.getDatabase().find(Database.class)
				.findList();

		// Don't do any extra work if that isn't needed.
		if (database.size() == 0) {
			return;
		}

		String playerName;

		for (int i = 0; i < database.size(); i++) {
			playerName = database.get(i).getPlayerName();

			String world = database.get(i).getWorld();

			double b1 = plugin.getDatabaseConnector().getBrokenStone(playerName, world);
			double b2 = plugin.getDatabaseConnector().getBrokenCoal(playerName, world);
			double b3 = plugin.getDatabaseConnector().getBrokenIron(playerName, world);
			double b4 = plugin.getDatabaseConnector().getBrokenRedstone(playerName, world);
			double b5 = plugin.getDatabaseConnector().getBrokenGold(playerName, world);
			double b6 = plugin.getDatabaseConnector().getBrokenLapisLazuli(playerName, world);
			double b7 = plugin.getDatabaseConnector().getBrokenDiamond(playerName, world);
			double b8 = plugin.getDatabaseConnector().getBrokenEmerald(playerName, world);
			double b9 = plugin.getDatabaseConnector().getBrokenNetherQuartz(playerName, world);
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
			} else if (p4 * 100.0D > plugin.getOreRatioStorage().getRedstoneLow()) {

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
			} else if (p7 * 100.0D > plugin.getOreRatioStorage().getDiamondLow()) {

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
			} else if (p8 * 100.0D > plugin.getOreRatioStorage().getEmeraldLow()) {

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
			} else if (p9 * 100.0D > plugin.getOreRatioStorage().getNetherquartzLow()) {

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
		plugin.getLogClass().logToFile("Report has been created", eventTypes.REPORT_CREATION);
	}
}
