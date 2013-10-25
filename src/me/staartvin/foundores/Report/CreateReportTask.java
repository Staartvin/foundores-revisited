package me.staartvin.foundores.Report;

import me.staartvin.foundores.FoundOres;

import org.bukkit.scheduler.BukkitRunnable;


public class CreateReportTask extends BukkitRunnable {

	private final FoundOres plugin;
	private Report report;
	
	public CreateReportTask(FoundOres instance) {
		plugin = instance;
		report = new Report(plugin);
	}
	
	public void run() {
		report.createReport();
	}
}
