package Staartvin.FoundOres.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import Staartvin.FoundOres.FoundOres;
import Staartvin.FoundOres.LogClass.eventTypes;

public class BlockBreakListener implements Listener{

	FoundOres plugin;

	public BlockBreakListener(FoundOres plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	protected void onBlockBreak(final BlockBreakEvent event) {
		
		if (event.isCancelled()) return;
		
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

			if (!plugin.methods.checkLightLevel(event, player))
				return;

			plugin.log.logToFile("[INFO] PLAYER " + player.getName()
					+ " has broken " + event.getBlock().getType() + " at "
					+ event.getBlock().getX() + ", " + event.getBlock().getY()
					+ ", " + event.getBlock().getZ(), eventTypes.BLOCKBREAK);
			plugin.loggedActions.add(playername + ":"
					+ player.getWorld().getName() + ":" + bid);

			plugin.methods.noticePlayeronMine(event.getPlayer());
			plugin.methods.checkAnnounceMode(playername, bid, player,
					event.getBlock());

		}
	}
}
