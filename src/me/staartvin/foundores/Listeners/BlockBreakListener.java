package me.staartvin.foundores.Listeners;

import me.staartvin.foundores.FoundOres;
import me.staartvin.foundores.LogClass.eventTypes;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class BlockBreakListener implements Listener {

	FoundOres plugin;

	public BlockBreakListener(FoundOres plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	protected void onBlockBreak(final BlockBreakEvent event) {

		if (event.isCancelled())
			return;

		@SuppressWarnings("deprecation")
		int bid = event.getBlock().getTypeId();
		final String playername = event.getPlayer().getName();
		final Player player = event.getPlayer();

		if (plugin.getConfig().getStringList("DisabledWorlds")
				.contains(player.getWorld().getName()))
			return;
		if (player.getGameMode().toString().equalsIgnoreCase("creative")
				&& !plugin.getConfig().getBoolean("checkCreativeMode"))
			return;
		if (bid == 74) {
			bid = 73;
		}
		if (bid == 1 || bid == 14 || bid == 15 || bid == 16 || bid == 21
				|| bid == 56 || bid == 73 || bid == 129 || bid == 153) {

			if (!plugin.getMethodsClass().checkLightLevel(event, player))
				return;

			// Log block
			plugin.getLogClass().logToFile("[INFO] PLAYER " + player.getName()
					+ " has broken " + event.getBlock().getType() + " at "
					+ event.getBlock().getX() + ", " + event.getBlock().getY()
					+ ", " + event.getBlock().getZ(), eventTypes.BLOCKBREAK);

			// Add a new block break action to the queue.
			plugin.loggedActions.add(playername + ":"
					+ player.getWorld().getName() + ":" + bid);

			// Notice player of mining
			plugin.getMethodsClass().noticePlayeronMine(event.getPlayer());

			plugin.getMethodsClass().checkAnnounceMode(playername, bid, player,
					event.getBlock());

		}
	}
}
