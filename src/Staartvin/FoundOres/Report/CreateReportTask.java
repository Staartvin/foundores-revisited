package Staartvin.FoundOres.Report;

import org.bukkit.scheduler.BukkitRunnable;

import Staartvin.FoundOres.FoundOres;

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
