package me.staartvin.foundores.leaderboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.staartvin.foundores.FoundOres;
import me.staartvin.foundores.database.Database;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class LeaderboardClass {

	FoundOres plugin;
	
	public LeaderboardClass(FoundOres plugin) {
		this.plugin = plugin;
	}
	// Lol you shouldn't see this yet. Oh well.
	public List<String> createLeaderboard(String block, String world) {
		List<Database> database = plugin.getDatabase().find(Database.class)
				.where().ieq("world", world).findList();
		// Don't do any extra work if that isn't needed.

		if (database == null)
			return null;
		if (database.size() == 0)
			return null;

		List<String> unsortedArray = new ArrayList<String>();

		if (block.equalsIgnoreCase("stone")) {
			for (int i = 0; i < database.size(); i++) {
				double b1 = database.get(i).getBrokenStone();
				double b2 = database.get(i).getBrokenCoal();
				double b3 = database.get(i).getBrokenIron();
				double b4 = database.get(i).getBrokenRedstone();
				double b5 = database.get(i).getBrokenGold();
				double b6 = database.get(i).getBrokenLapisLazuli();
				double b7 = database.get(i).getBrokenDiamond();
				double b8 = database.get(i).getBrokenEmerald();
				double b9 = database.get(i).getBrokenNetherQuartz();
				double ba = b1 + b2 + b3 + b4 + b5 + b6 + b7 + b8 + b9;

				double p1 = (b1 / ba) * 100D;
				String tempString = "";
				tempString = Math.round(p1) + ":"
						+ database.get(i).getPlayerName();
				unsortedArray.add(tempString);
			}
		} else if (block.equalsIgnoreCase("coal")) {
			for (int i = 0; i < database.size(); i++) {
				double b1 = database.get(i).getBrokenStone();
				double b2 = database.get(i).getBrokenCoal();
				double b3 = database.get(i).getBrokenIron();
				double b4 = database.get(i).getBrokenRedstone();
				double b5 = database.get(i).getBrokenGold();
				double b6 = database.get(i).getBrokenLapisLazuli();
				double b7 = database.get(i).getBrokenDiamond();
				double b8 = database.get(i).getBrokenEmerald();
				double b9 = database.get(i).getBrokenNetherQuartz();
				double ba = b1 + b2 + b3 + b4 + b5 + b6 + b7 + b8 + b9;

				double p2 = (b2 / ba) * 100D;
				String tempString = "";
				tempString = Math.round(p2) + ":"
						+ database.get(i).getPlayerName();
				unsortedArray.add(tempString);
			}
		} else if (block.equalsIgnoreCase("iron")) {
			for (int i = 0; i < database.size(); i++) {
				double b1 = database.get(i).getBrokenStone();
				double b2 = database.get(i).getBrokenCoal();
				double b3 = database.get(i).getBrokenIron();
				double b4 = database.get(i).getBrokenRedstone();
				double b5 = database.get(i).getBrokenGold();
				double b6 = database.get(i).getBrokenLapisLazuli();
				double b7 = database.get(i).getBrokenDiamond();
				double b8 = database.get(i).getBrokenEmerald();
				double b9 = database.get(i).getBrokenNetherQuartz();
				double ba = b1 + b2 + b3 + b4 + b5 + b6 + b7 + b8 + b9;

				double p3 = (b3 / ba) * 100D;
				String tempString = "";
				tempString = Math.round(p3) + ":"
						+ database.get(i).getPlayerName();
				unsortedArray.add(tempString);
			}
		} else if (block.equalsIgnoreCase("gold")) {
			for (int i = 0; i < database.size(); i++) {
				double b1 = database.get(i).getBrokenStone();
				double b2 = database.get(i).getBrokenCoal();
				double b3 = database.get(i).getBrokenIron();
				double b4 = database.get(i).getBrokenRedstone();
				double b5 = database.get(i).getBrokenGold();
				double b6 = database.get(i).getBrokenLapisLazuli();
				double b7 = database.get(i).getBrokenDiamond();
				double b8 = database.get(i).getBrokenEmerald();
				double b9 = database.get(i).getBrokenNetherQuartz();
				double ba = b1 + b2 + b3 + b4 + b5 + b6 + b7 + b8 + b9;

				double p5 = (b5 / ba) * 100D;
				String tempString = "";
				tempString = Math.round(p5) + ":"
						+ database.get(i).getPlayerName();
				unsortedArray.add(tempString);
			}
		} else if (block.equalsIgnoreCase("redstone")) {
			for (int i = 0; i < database.size(); i++) {
				double b1 = database.get(i).getBrokenStone();
				double b2 = database.get(i).getBrokenCoal();
				double b3 = database.get(i).getBrokenIron();
				double b4 = database.get(i).getBrokenRedstone();
				double b5 = database.get(i).getBrokenGold();
				double b6 = database.get(i).getBrokenLapisLazuli();
				double b7 = database.get(i).getBrokenDiamond();
				double b8 = database.get(i).getBrokenEmerald();
				double b9 = database.get(i).getBrokenNetherQuartz();
				double ba = b1 + b2 + b3 + b4 + b5 + b6 + b7 + b8 + b9;

				double p4 = (b4 / ba) * 100D;
				String tempString = "";
				tempString = Math.round(p4) + ":"
						+ database.get(i).getPlayerName();
				unsortedArray.add(tempString);
			}
		} else if (block.equalsIgnoreCase("lapislazuli")) {
			for (int i = 0; i < database.size(); i++) {
				double b1 = database.get(i).getBrokenStone();
				double b2 = database.get(i).getBrokenCoal();
				double b3 = database.get(i).getBrokenIron();
				double b4 = database.get(i).getBrokenRedstone();
				double b5 = database.get(i).getBrokenGold();
				double b6 = database.get(i).getBrokenLapisLazuli();
				double b7 = database.get(i).getBrokenDiamond();
				double b8 = database.get(i).getBrokenEmerald();
				double b9 = database.get(i).getBrokenNetherQuartz();
				double ba = b1 + b2 + b3 + b4 + b5 + b6 + b7 + b8 + b9;

				double p6 = (b6 / ba) * 100D;
				String tempString = "";
				tempString = Math.round(p6) + ":"
						+ database.get(i).getPlayerName();
				unsortedArray.add(tempString);
			}
		} else if (block.equalsIgnoreCase("diamond")) {
			for (int i = 0; i < database.size(); i++) {
				double b1 = database.get(i).getBrokenStone();
				double b2 = database.get(i).getBrokenCoal();
				double b3 = database.get(i).getBrokenIron();
				double b4 = database.get(i).getBrokenRedstone();
				double b5 = database.get(i).getBrokenGold();
				double b6 = database.get(i).getBrokenLapisLazuli();
				double b7 = database.get(i).getBrokenDiamond();
				double b8 = database.get(i).getBrokenEmerald();
				double b9 = database.get(i).getBrokenNetherQuartz();
				double ba = b1 + b2 + b3 + b4 + b5 + b6 + b7 + b8 + b9;

				double p7 = (b7 / ba) * 100D;
				String tempString = "";
				tempString = Math.round(p7) + ":"
						+ database.get(i).getPlayerName();
				unsortedArray.add(tempString);
			}
		} else if (block.equalsIgnoreCase("emerald")) {
			for (int i = 0; i < database.size(); i++) {
				double b1 = database.get(i).getBrokenStone();
				double b2 = database.get(i).getBrokenCoal();
				double b3 = database.get(i).getBrokenIron();
				double b4 = database.get(i).getBrokenRedstone();
				double b5 = database.get(i).getBrokenGold();
				double b6 = database.get(i).getBrokenLapisLazuli();
				double b7 = database.get(i).getBrokenDiamond();
				double b8 = database.get(i).getBrokenEmerald();
				double b9 = database.get(i).getBrokenNetherQuartz();
				double ba = b1 + b2 + b3 + b4 + b5 + b6 + b7 + b8 + b9;

				double p8 = (b8 / ba) * 100D;
				String tempString = "";
				tempString = Math.round(p8) + ":"
						+ database.get(i).getPlayerName();
				unsortedArray.add(tempString);
			}
		} else if (block.equalsIgnoreCase("netherquartz")) {
			for (int i = 0; i < database.size(); i++) {
				double b1 = database.get(i).getBrokenStone();
				double b2 = database.get(i).getBrokenCoal();
				double b3 = database.get(i).getBrokenIron();
				double b4 = database.get(i).getBrokenRedstone();
				double b5 = database.get(i).getBrokenGold();
				double b6 = database.get(i).getBrokenLapisLazuli();
				double b7 = database.get(i).getBrokenDiamond();
				double b8 = database.get(i).getBrokenEmerald();
				double b9 = database.get(i).getBrokenNetherQuartz();
				double ba = b1 + b2 + b3 + b4 + b5 + b6 + b7 + b8 + b9;

				double p9 = (b9 / ba) * 100D;
				String tempString = "";
				tempString = Math.round(p9) + ":"
						+ database.get(i).getPlayerName();
				unsortedArray.add(tempString);
			}
		}
		return unsortedArray;
	}

	public List<String> sortLeaderboard(List<String> unsortedList) {
		List<String> sortedList = new ArrayList<String>();
		List<Integer> Integernumbers = new ArrayList<Integer>();
		List<String> Stringnumbers = new ArrayList<String>();
		String[] tempArray = {};

		if (unsortedList == null) return null;
		for (String entry : unsortedList) {
			tempArray = entry.split(":");

			if (tempArray.length == 0 || tempArray.length == 1)
				return null;

			Integernumbers.add(Integer.parseInt(tempArray[0]));
		}

		Stringnumbers = convertToCorrectNumber(Integernumbers);

		for (int i = 0; i < unsortedList.size(); i++) {
			String entry = unsortedList.get(i);
			tempArray = entry.split(":");

			if (tempArray.length == 0 || tempArray.length == 1)
				return null;

			Stringnumbers.set(i, Stringnumbers.get(i) + ":" + tempArray[1]);
		}
		Collections.sort(Stringnumbers);

		sortedList = Stringnumbers;
		return sortedList;
	}

	protected int getIntegerLength(Integer maxNumber) {
		int numberCount;
		if (maxNumber < 10 && maxNumber >= 0)
			numberCount = 1;
		else if (maxNumber < 100 && maxNumber >= 10)
			numberCount = 2;
		else if (maxNumber < 1000 && maxNumber >= 100)
			numberCount = 3;
		else if (maxNumber < 10000 && maxNumber >= 1000)
			numberCount = 4;
		else if (maxNumber < 100000 && maxNumber >= 10000)
			numberCount = 5;
		else if (maxNumber < 1000000 && maxNumber >= 100000)
			numberCount = 6;
		else if (maxNumber < 10000000 && maxNumber >= 1000000)
			numberCount = 7;
		else if (maxNumber < 100000000 && maxNumber >= 10000000)
			numberCount = 8;
		else if (maxNumber < 1000000000 && maxNumber >= 100000000)
			numberCount = 9;
		else
			numberCount = 0;

		return numberCount;
	}

	protected List<String> convertToCorrectNumber(List<Integer> incorrectNumbers) {
		List<String> correctNumbersInString = new ArrayList<String>();
		int numberCount = getIntegerLength(Collections.max(incorrectNumbers));

		for (Integer incorrectNumber : incorrectNumbers) {
			int intCount = getIntegerLength(incorrectNumber);
			String correctNumber;

			if (numberCount - intCount == 8) {
				correctNumber = "00000000" + Integer.toString(incorrectNumber);
				correctNumbersInString.add(correctNumber);
				continue;
			} else if (numberCount - intCount == 7) {
				correctNumber = "0000000" + Integer.toString(incorrectNumber);
				correctNumbersInString.add(correctNumber);
				continue;
			} else if (numberCount - intCount == 6) {
				correctNumber = "000000" + Integer.toString(incorrectNumber);
				correctNumbersInString.add(correctNumber);
				continue;
			} else if (numberCount - intCount == 5) {
				correctNumber = "00000" + Integer.toString(incorrectNumber);
				correctNumbersInString.add(correctNumber);
				continue;
			} else if (numberCount - intCount == 4) {
				correctNumber = "0000" + Integer.toString(incorrectNumber);
				correctNumbersInString.add(correctNumber);
				continue;
			} else if (numberCount - intCount == 3) {
				correctNumber = "000" + Integer.toString(incorrectNumber);
				correctNumbersInString.add(correctNumber);
				continue;
			} else if (numberCount - intCount == 2) {
				correctNumber = "00" + Integer.toString(incorrectNumber);
				correctNumbersInString.add(correctNumber);
				continue;
			} else if (numberCount - intCount == 1) {
				correctNumber = "0" + Integer.toString(incorrectNumber);
				correctNumbersInString.add(correctNumber);
				continue;
			} else if (numberCount - intCount == 0) {
				correctNumbersInString.add(Integer.toString(incorrectNumber));
				continue;
			}
		}
		return correctNumbersInString;
	}
	
	public void showLeaderboard(List<String> leaderboard, CommandSender sender, String world, String block) {
		sender.sendMessage(ChatColor.BLUE + "---- [" + ChatColor.GOLD + "Leaderboard for " + block + " on "
				+ world + ChatColor.BLUE + "] ----");
		Integer rank = 1;
		for (int i=leaderboard.size() - 1;i>=0;i--) {
			if (rank == 11) return;
			String entry = leaderboard.get(i);
			String[] tempArray = entry.split(":");
			sender.sendMessage(ChatColor.GOLD + (rank + "") + ChatColor.BLUE + " -- " + ChatColor.GRAY + tempArray[1] + ChatColor.BLUE + " -- " + ChatColor.GRAY + Integer.parseInt(tempArray[0]) + "%");
			rank++;
		}
	}
}
