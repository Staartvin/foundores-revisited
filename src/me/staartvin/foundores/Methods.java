package me.staartvin.foundores;

import java.text.DecimalFormat;
import java.util.List;

import me.staartvin.foundores.database.Database;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Methods {

	FoundOres plugin;
	public final BlockFace[] lightFaces = { BlockFace.EAST, BlockFace.WEST,
			BlockFace.SOUTH, BlockFace.NORTH, BlockFace.DOWN, BlockFace.UP };

	public Methods(FoundOres plugin) {
		this.plugin = plugin;
	}

	public void startTest() {
		plugin.firstTime = System.currentTimeMillis();
	}

	public int getLightLevel(Block block) {
		int highestLevel = 0;

		for (BlockFace y : this.lightFaces) {
			int lightLevel = block.getRelative(y).getLightLevel();
			if (lightLevel > highestLevel) {
				highestLevel = lightLevel;
			}
		}
		return highestLevel;
	}

	public void stopTest() {
		plugin.lastTime = System.currentTimeMillis();
		plugin.finalTime = plugin.lastTime - plugin.firstTime;
		plugin.getLoggerClass().logVerbose("Load Time: " + plugin.finalTime + "ms ");
	}

	public boolean hasPermission(String permission, CommandSender sender) {
		if (sender.hasPermission(permission)) {
			return true;
		} else {
			sender.sendMessage(ChatColor.RED
					+ "You don't have the correct permission: (" + permission
					+ ")");
			return false;
		}
	}

	public void clearBlockCount(String playerName, CommandSender sender,
			String world) {
		if (world == null) {
			List<World> worlds = plugin.getServer().getWorlds();
			world = worlds.get(0).getName();
		}

		if (plugin.getDatabaseConnector().clearEntry(playerName, world)) {
			sender.sendMessage(ChatColor.GREEN + "The blockcount of "
					+ playerName + " has been successfully cleared on world '"
					+ world + "'.");
		} else {
			sender.sendMessage(ChatColor.RED + "Player " + playerName
					+ " is not logged on world '" + world + "'.");
		}
	}

	public void calculateBlockCount(String playerName, CommandSender sender,
			String world) {

		if (world == null) {
			List<World> worlds = plugin.getServer().getWorlds();
			world = worlds.get(0).getName();
		}

		Database database = plugin.getDatabase().find(Database.class).where()
				.ieq("playerName", playerName).ieq("world", world).findUnique();

		if (database == null) {
			sender.sendMessage(ChatColor.RED + "Player " + playerName
					+ " is not logged on world '" + world + "'!");
			return;
		}

		playerName = database.getPlayerName();

		double b1 = database.getBrokenStone();
		double b2 = database.getBrokenCoal();
		double b3 = database.getBrokenIron();
		double b4 = database.getBrokenRedstone();
		double b5 = database.getBrokenGold();
		double b6 = database.getBrokenLapisLazuli();
		double b7 = database.getBrokenDiamond();
		double b8 = database.getBrokenEmerald();
		double b9 = database.getBrokenNetherQuartz();
		double ba = b1 + b2 + b3 + b4 + b5 + b6 + b7 + b8 + b9;

		double p1 = b1 / ba;
		double p2 = b2 / ba;
		double p3 = b3 / ba;
		double p4 = b4 / ba;
		double p5 = b5 / ba;
		double p6 = b6 / ba;
		double p7 = b7 / ba;
		double p8 = b8 / ba;
		double p9 = b9 / ba;

		sender.sendMessage(ChatColor.GREEN + "----[" + playerName + " on "
				+ world + "]----");
		if (ba == 0.0D) {
			sender.sendMessage(ChatColor.RED + playerName
					+ " hasn't broken any ore on world '" + world + "'!");
			return;
		}

		DecimalFormat df = new DecimalFormat("0.00%");
		String p1p = ChatColor.GREEN + df.format(p1);
		String p2p = df.format(p2);
		String p3p = df.format(p3);
		String p4p = df.format(p4);
		String p5p = df.format(p5);
		String p6p = df.format(p6);
		String p7p = df.format(p7);
		String p8p = df.format(p8);
		String p9p = df.format(p9);

		if (p2 * 100.0D > plugin.getOreRatioStorage().getCoalHigh())
			p2p = ChatColor.RED + p2p;
		else if (p2 * 100.0D > plugin.getOreRatioStorage().getCoalLow())
			p2p = ChatColor.GOLD + p2p;
		else {
			p2p = ChatColor.GREEN + p2p;
		}

		if (p3 * 100.0D > plugin.getOreRatioStorage().getIronHigh())
			p3p = ChatColor.RED + p3p;
		else if (p3 * 100.0D > plugin.getOreRatioStorage().getIronLow())
			p3p = ChatColor.GOLD + p3p;
		else {
			p3p = ChatColor.GREEN + p3p;
		}

		if (p4 * 100.0D > plugin.getOreRatioStorage().getRedstoneHigh())
			p4p = ChatColor.RED + p4p;
		else if (p4 * 100.0D > plugin.getOreRatioStorage().getRedstoneLow())
			p4p = ChatColor.GOLD + p4p;
		else {
			p4p = ChatColor.GREEN + p4p;
		}

		if (p5 * 100.0D > plugin.getOreRatioStorage().getGoldHigh())
			p5p = ChatColor.RED + p5p;
		else if (p5 * 100.0D > plugin.getOreRatioStorage().getGoldLow())
			p5p = ChatColor.GOLD + p5p;
		else {
			p5p = ChatColor.GREEN + p5p;
		}

		if (p6 * 100.0D > plugin.getOreRatioStorage().getLapisHigh())
			p6p = ChatColor.RED + p6p;
		else if (p6 * 100.0D > plugin.getOreRatioStorage().getLapisLow())
			p6p = ChatColor.GOLD + p6p;
		else {
			p6p = ChatColor.GREEN + p6p;
		}

		if (p7 * 100.0D > plugin.getOreRatioStorage().getDiamondHigh())
			p7p = ChatColor.RED + p7p;
		else if (p7 * 100.0D > plugin.getOreRatioStorage().getDiamondLow())
			p7p = ChatColor.GOLD + p7p;
		else {
			p7p = ChatColor.GREEN + p7p;
		}

		if (p8 * 100.0D > plugin.getOreRatioStorage().getEmeraldHigh())
			p8p = ChatColor.RED + p8p;
		else if (p8 * 100.0D > plugin.getOreRatioStorage().getEmeraldLow())
			p8p = ChatColor.GOLD + p8p;
		else {
			p8p = ChatColor.GREEN + p8p;
		}

		if (p9 * 100.0D > plugin.getOreRatioStorage().getNetherquartzHigh())
			p9p = ChatColor.RED + p9p;
		else if (p8 * 100.0D > plugin.getOreRatioStorage().getNetherquartzLow())
			p9p = ChatColor.GOLD + p9p;
		else {
			p9p = ChatColor.GREEN + p9p;
		}

		sender.sendMessage(ChatColor.DARK_GRAY + "Stone: " + p1p + " ("
				+ Math.round(b1) + ")");
		sender.sendMessage(ChatColor.DARK_AQUA + "Coal: " + p2p + " ("
				+ Math.round(b2) + ")");
		sender.sendMessage(ChatColor.GRAY + "Iron: " + p3p + " ("
				+ Math.round(b3) + ")");
		sender.sendMessage(ChatColor.RED + "Redstone: " + p4p + " ("
				+ Math.round(b4) + ")");
		sender.sendMessage(ChatColor.GOLD + "Gold: " + p5p + " ("
				+ Math.round(b5) + ")");
		sender.sendMessage(ChatColor.BLUE + "Lapis: " + p6p + " ("
				+ Math.round(b6) + ")");
		sender.sendMessage(ChatColor.AQUA + "Diamond: " + p7p + " ("
				+ Math.round(b7) + ")");
		sender.sendMessage(ChatColor.GREEN + "Emerald: " + p8p + " ("
				+ Math.round(b8) + ")");
		sender.sendMessage(ChatColor.WHITE + "Nether Quartz: " + p9p + " ("
				+ Math.round(b9) + ")");
	}

	public void disablePlugin() {
		if (plugin.getServer().getPluginManager().isPluginEnabled(plugin)) {
			plugin.getServer().getScheduler()
					.runTaskLater(plugin, new Runnable() {

						public void run() {
							plugin.getServer().getPluginManager()
									.disablePlugin(plugin);
						}
					}, 5L);
			return;
		} else {
			// Plugin is not loaded.
			return;
		}
	}

	public void noticePlayeronMine(Player player) {
		if (plugin.getConfig().getBoolean("noticePlayerOnMine")) {
			if (plugin.hasReceived.get(player.getName()) == null) {
				plugin.hasReceived.put(player.getName(), false);
			}
			if (plugin.hasReceived.get(player.getName())) {

				return;
			} else if (plugin.hasReceived.get(player.getName()) == false) {
				plugin.hasReceived.put(player.getName(), true);
			}

			player.sendMessage(plugin.getConfig().getString(
					"noticeMessageToPlayer"));
		}
	}

	public boolean checkLightLevel(Block block, Player player) {
		if (!plugin.getConfig().getBoolean("allowLowLightMining")) {
			if (block.getY() <= 60) {
				if (plugin.getMethodsClass().getLightLevel(block) <= plugin
						.getConfig().getInt("LightLevelDenial")) {
					if (player.hasPermission("foundores.exempt.lightlevel")) {
						return true;
					}
					player.sendMessage(ChatColor.RED
							+ "You may not break this block! Light level is too low!");
					return false;
				}
			}
		}
		return true;
	}
	
	public String matchWorlds(String worldGuess) {
		List<World> worlds = plugin.getServer().getWorlds();
		
		for (int i=0;i<worlds.size();i++) {
			String worldName = worlds.get(i).getName();
			if (worldName.equalsIgnoreCase(worldGuess)) return worldName;
			continue;
		}
		return null;
	}
}