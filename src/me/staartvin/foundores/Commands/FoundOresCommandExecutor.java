package me.staartvin.foundores.Commands;

import java.util.List;

import me.staartvin.foundores.FoundOres;
import me.staartvin.foundores.LogClass.eventTypes;
import me.staartvin.foundores.Leaderboard.LeaderboardClass;
import me.staartvin.foundores.Report.CreateReportTask;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FoundOresCommandExecutor implements CommandExecutor {
	FoundOres plugin;
	private LeaderboardClass leaderBoard;

	public FoundOresCommandExecutor(FoundOres plugin) {
		this.plugin = plugin;
		this.leaderBoard = new LeaderboardClass(plugin);
	}

	private String[] materials = { "stone", "coal", "iron", "gold", "redstone",
			"lapislazuli", "diamond", "emerald", "netherquartz" };

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandlabel, String[] args) {

		String command = "";

		for (int i = 0; i < args.length; i++) {
			String argument = args[i];
			if (i == args.length - 1) {
				command = command.concat(argument);
			} else {
				command = command.concat(argument + " ");
			}
		}

		logCommand(command, sender);

		if (args.length == 3) {
			if (args[0].equalsIgnoreCase("clear")) {
				if (!plugin.getMethodsClass().hasPermission("foundores.clear", sender)) {
					return true;
				}
				if (plugin.getServer().getWorld(args[2]) == null) {
					sender.sendMessage(ChatColor.RED
							+ "World '"
							+ args[2]
							+ "' could not be found! Worlds are case sensitive!");
					return true;
				}
				plugin.getMethodsClass().clearBlockCount(args[1], sender, args[2]);
				return true;
			} else if (args[0].equalsIgnoreCase("check")) {
				if (!plugin.getMethodsClass().hasPermission("foundores.check", sender)) {
					return true;
				}
				if (plugin.getServer().getWorld(args[2]) == null) {
					sender.sendMessage(ChatColor.RED
							+ "World '"
							+ args[2]
							+ "' could not be found! Worlds are case sensitive!");
					return true;
				}
				plugin.getMethodsClass().calculateBlockCount(args[1], sender, plugin
						.getServer().getWorld(args[2]).getName());
				return true;
			} else if (args[0].equalsIgnoreCase("top")) {
				if (!plugin.getMethodsClass().hasPermission("foundores.top", sender)) {
					return true;
				}

				boolean isValid = false;
				for (String material : materials) {
					if (material.equalsIgnoreCase(args[1])) {
						isValid = true;
						break;
					}
					continue;
				}

				if (!isValid) {
					sender.sendMessage(ChatColor.RED + "'" + args[1]
							+ "' is not a valid material!");
					sender.sendMessage(ChatColor.YELLOW
							+ "Do '/fo materials' to get a list of materials!");
					return true;
				}
				String worldName = plugin.getMethodsClass().matchWorlds(args[2]);
				if (worldName == null) {
					sender.sendMessage(ChatColor.RED + "World '" + args[2]
							+ "' could not be found!");
					return true;
				}
				List<String> leaderboard = leaderBoard
						.sortLeaderboard(leaderBoard.createLeaderboard(args[1],
								worldName));

				if (leaderboard == null || leaderboard.size() <= 0) {
					sender.sendMessage(ChatColor.RED
							+ "This is not a valid leaderboard. Is the world logged?");
					return true;
				}
				leaderBoard.showLeaderboard(leaderboard, sender, worldName,
						args[1]);
				return true;
			}
		} // args.length = 3
		else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("check")) {
				if (!plugin.getMethodsClass().hasPermission("foundores.check", sender)) {
					return true;
				}
				Player player;
				try {
					player = (Player) sender;
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED
							+ "ERROR: Could not find world location!");
					return true;
				}

				if (player.getWorld() == null) {
					sender.sendMessage(ChatColor.RED
							+ "ERROR: Could not find world location!");
					return true;
				}
				plugin.getMethodsClass().calculateBlockCount(args[1], sender, player
						.getWorld().getName());
				return true;
			} else if (args[0].equalsIgnoreCase("remove")) {
				if (!plugin.getMethodsClass().hasPermission("foundores.remove", sender)) {
					return true;
				}
				sender.sendMessage(ChatColor.RED
						+ "This command is not stable yet. Use of it is denied.");
				return true;
			} else if (args[0].equalsIgnoreCase("clear")) {
				if (!plugin.getMethodsClass().hasPermission("foundores.clear", sender)) {
					return true;
				}
				Player player;
				try {
					player = (Player) sender;
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED
							+ "ERROR: Could not find world location!");
					return true;
				}
				if (player.getWorld() == null) {
					sender.sendMessage(ChatColor.RED
							+ "ERROR: Could not find world location!");
					return true;
				}
				plugin.getMethodsClass().clearBlockCount(args[1], sender, player
						.getWorld().getName());
				return true;
			} else if (args[0].equalsIgnoreCase("help")) {
				int page;

				try {
					page = Integer.parseInt(args[1]);
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + args[1]
							+ " is not a number!");
					return true;
				}
				showHelpPages(sender, page);
				return true;
			} else if (args[0].equalsIgnoreCase("top")) {
				if (!plugin.getMethodsClass().hasPermission("foundores.top", sender)) {
					return true;
				}
				boolean isValid = false;
				for (String material : materials) {
					if (material.equalsIgnoreCase(args[1])) {
						isValid = true;
						break;
					}
					continue;
				}

				if (!isValid) {
					sender.sendMessage(ChatColor.RED + "'" + args[1]
							+ "' is not a valid material!");
					sender.sendMessage(ChatColor.YELLOW
							+ "Do '/fo materials' to get a list of materials!");
					return true;
				}

				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED
							+ "Worlds is required for console!");
					return true;
				}
				Player player = (Player) sender;
				if (player.getWorld().getName() == null) {
					sender.sendMessage(ChatColor.RED + "World '" + args[2]
							+ "' could not be found!");
					return true;
				}
				List<String> leaderboard = leaderBoard
						.sortLeaderboard(leaderBoard.createLeaderboard(args[1],
								player.getWorld().getName()));

				if (leaderboard == null || leaderboard.size() <= 0) {
					sender.sendMessage(ChatColor.RED
							+ "This is not a valid leaderboard. Is the world logged?");
					return true;
				}
				leaderBoard.showLeaderboard(leaderboard, sender, player
						.getWorld().getName(), args[1]);
				return true;
			}
		} // args.length = 2
		else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("help")) {
				showHelpPages(sender, 1);
				return true;
			} else if (args[0].equalsIgnoreCase("remove")) {
				sender.sendMessage(ChatColor.RED + "Incorrect Command Usage."
						+ ChatColor.YELLOW + " /fo remove <player>");
				return true;
			} else if (args[0].equalsIgnoreCase("clear")) {
				sender.sendMessage(ChatColor.RED + "Incorrect Command Usage."
						+ ChatColor.YELLOW + " /fo clear <player> <world>");
				return true;
			} else if (args[0].equalsIgnoreCase("donators")
					|| args[0].equalsIgnoreCase("donator")) {
				sender.sendMessage(ChatColor.BLUE
						+ "-----------------------------------------------------");
				sender.sendMessage(ChatColor.YELLOW
						+ "Thanks to all the donators!");
				sender.sendMessage(ChatColor.GOLD
						+ "- Rodier for donating 5 dollars");
				sender.sendMessage(ChatColor.GOLD
						+ "- RilestheGiles for donating 1 dollar");
				sender.sendMessage(ChatColor.GOLD
						+ "- FaxionMC for donating 40 dollars!");
				sender.sendMessage(ChatColor.GOLD
						+ "- Timo Triisa for donating 30 dollars!");
				return true;
			} else if (args[0].equalsIgnoreCase("save")
					|| args[0].equalsIgnoreCase("forcesave")) {
				if (!plugin.getMethodsClass()
						.hasPermission("foundores.forcesave", sender)) {
					return true;
				}
				if (plugin.getInternalSaves().saveProgress) {
					sender.sendMessage(ChatColor.RED
							+ "Cannot start a save, there is already a save running.");
					return true;
				}
				sender.sendMessage(ChatColor.GREEN + "Starting save...");
				plugin.getInternalSaves().save(sender.getName(), true, false, plugin
						.getServer().getPlayer(sender.getName()));
				return true;
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (!plugin.getMethodsClass().hasPermission("foundores.reload", sender)) {
					return true;
				}
				plugin.reloadConfig();
				plugin.saveConfig();

				//plugin.getServer().getPluginManager().disablePlugin(plugin);
				//plugin.getServer().getPluginManager().enablePlugin(plugin);

				sender.sendMessage(ChatColor.GREEN
						+ "You successfully reloaded FO:RE.");
				return true;
			} else if (args[0].equalsIgnoreCase("list")) {
				if (!plugin.getMethodsClass().hasPermission("foundores.list", sender)) {
					return true;
				}
				sender.sendMessage(ChatColor.RED
						+ "This command is not useful. It serves no purpose yet.");
				//sender.sendMessage(ChatColor.BLUE
				//+ "-----------------------------------------------------");
				return true;
			} else if (args[0].equalsIgnoreCase("report")) {
				if (!plugin.getMethodsClass().hasPermission("foundores.report", sender)) {
					return true;
				}
				// Run creating of a report async.
				new CreateReportTask(plugin).runTaskAsynchronously(plugin);

				sender.sendMessage(ChatColor.BLUE
						+ "-----------------------------------------------------");
				sender.sendMessage(ChatColor.GREEN + "Creating new report!");
				sender.sendMessage(ChatColor.GREEN
						+ "This can take up a few minutes depending on your database size.");
				sender.sendMessage(ChatColor.YELLOW
						+ "Creating a report may put some heavy load on server for a couple of seconds.");
				return true;
			} else if (args[0].equalsIgnoreCase("check")) {
				if (!plugin.getMethodsClass().hasPermission("foundores.check", sender)) {
					return true;
				}
				Player player;
				try {
					player = (Player) sender;
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED
							+ "ERROR: Could not find world location!");
					return true;
				}

				if (player.getWorld() == null) {
					sender.sendMessage(ChatColor.RED
							+ "ERROR: Could not find world location!");
					return true;
				}
				plugin.getMethodsClass().calculateBlockCount(sender.getName(), sender,
						player.getWorld().getName());
				return true;
			} else if (args[0].equalsIgnoreCase("purge")) {
				if (!plugin.getMethodsClass().hasPermission("foundores.purge", sender)) {
					return true;
				}
				if (plugin.getServer().getOnlinePlayers().length >= 1) {
					sender.sendMessage(ChatColor.RED
							+ "Could not perform a purge! There are players online!");
					return true;
				}
				sender.sendMessage(ChatColor.GREEN
						+ "Database of FoundOres has been successfully purged with "
						+ plugin.getDatabaseConnector().purgeDatabase() + " items!");
				plugin.getLoggerClass().logNormal(sender.getName()
						+ " has purged the database!");
				return true;
			} else if (args[0].equalsIgnoreCase("materials")) {
				if (!plugin.getMethodsClass()
						.hasPermission("foundores.materials", sender)) {
					return true;
				}
				sender.sendMessage(ChatColor.BLUE + "Materials:");
				String result = "";
				for (int i = 0; i < materials.length; i++) {
					String material = materials[i];
					if (i == materials.length - 1) {
						result = result.concat(material);
						break;
					}
					result = result.concat(material + ", ");
				}
				sender.sendMessage(ChatColor.GOLD + result);
				return true;
			} else if (args[0].equalsIgnoreCase("updateMySQL")) {
				if (!plugin.getMethodsClass().hasPermission("foundores.updateMySQL",
						sender)) {
					return true;
				}
				sender.sendMessage(ChatColor.RED + "Checking database...");
				plugin.getMySQLHandler().updateToLatestMinecraftVersion();
				sender.sendMessage(ChatColor.GREEN + "Database is up-to-date!");
				return true;
			} else if (args[0].equalsIgnoreCase("update")) {
				if (!plugin.getMethodsClass().hasPermission("foundores.update", sender)) {
					return true;
				}

				// Update plugin
				plugin.updatePlugin(sender);
				return true;
			}
		} // args.length = 1
		else if (args.length == 0) {
			sender.sendMessage(ChatColor.BLUE
					+ "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Developed by: "
					+ ChatColor.GRAY + "Staartvin");
			sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.GRAY
					+ plugin.getDescription().getVersion());
			sender.sendMessage(ChatColor.YELLOW
					+ "Type /fo help for a list of commands.");
			return true;
		}
		sender.sendMessage(ChatColor.RED + "Command not recognised!");
		sender.sendMessage(ChatColor.YELLOW
				+ "Type '/fo help' for a list of commands");
		return true;
	}

	protected void showHelpPages(CommandSender sender, int page) {
		int maxpages = 3;

		if (page == 3) {
			sender.sendMessage(ChatColor.BLUE + "----------------["
					+ ChatColor.GRAY + "FoundOres Revisited" + ChatColor.BLUE
					+ "]------------------");
			sender.sendMessage(ChatColor.GOLD + "/fo updateMySQL"
					+ ChatColor.BLUE
					+ " --- Updates MySQL database to latest version");
			sender.sendMessage(ChatColor.GOLD + "/fo update" + ChatColor.BLUE
					+ " --- Updates FoundOres to the latest version.");
			sender.sendMessage(ChatColor.GRAY + "Page 3 of " + maxpages);
		} else if (page == 2) {
			sender.sendMessage(ChatColor.BLUE + "----------------["
					+ ChatColor.GRAY + "FoundOres Revisited" + ChatColor.BLUE
					+ "]------------------");
			sender.sendMessage(ChatColor.GOLD + "/fo clear <player> <world>"
					+ ChatColor.BLUE + " --- Resets blockcount of a player");
			sender.sendMessage(ChatColor.GOLD + "/fo remove <player>"
					+ ChatColor.BLUE + " --- Removes player from all lists");
			sender.sendMessage(ChatColor.GOLD
					+ "/fo purge"
					+ ChatColor.BLUE
					+ " --- Purge database. Do not use this when players are online.");
			sender.sendMessage(ChatColor.GOLD + "/fo donators" + ChatColor.BLUE
					+ " --- Shows all my lovely donators!");
			sender.sendMessage(ChatColor.GOLD + "/fo materials"
					+ ChatColor.BLUE + " --- Shows a list of materials");
			sender.sendMessage(ChatColor.GOLD
					+ "/fo top <material> <world>"
					+ ChatColor.BLUE
					+ " --- Shows a leaderboard of the highest percentages of players");
			sender.sendMessage(ChatColor.GRAY + "Page 2 of " + maxpages);
		} else {
			sender.sendMessage(ChatColor.BLUE + "----------------["
					+ ChatColor.GRAY + "FoundOres Revisited" + ChatColor.BLUE
					+ "]------------------");
			sender.sendMessage(ChatColor.GOLD + "/fo" + ChatColor.BLUE
					+ " --- Shows info about FoundOres Revisited");
			sender.sendMessage(ChatColor.GOLD + "/fo help <page>"
					+ ChatColor.BLUE + " --- Shows a list of commands");
			sender.sendMessage(ChatColor.GOLD + "/fo list" + ChatColor.BLUE
					+ " --- Shows a list of checked players");
			sender.sendMessage(ChatColor.GOLD + "/fo save" + ChatColor.BLUE
					+ " --- Forces a save");
			sender.sendMessage(ChatColor.GOLD + "/fo reload" + ChatColor.BLUE
					+ " --- Forces a reload");
			sender.sendMessage(ChatColor.GOLD
					+ "/fo report"
					+ ChatColor.BLUE
					+ " --- Creates a report based off the blockcount of players");
			sender.sendMessage(ChatColor.GOLD + "/fo check <player> <world>"
					+ ChatColor.BLUE + " --- Shows blockcount of a player");
			sender.sendMessage(ChatColor.GRAY + "Page 1 of " + maxpages);
		}
	}

	private void logCommand(String command, CommandSender sender) {
		plugin.getLogClass().logToFile(sender.getName() + " performed '/fo " + command
				+ "'", eventTypes.COMMAND);
	}
}