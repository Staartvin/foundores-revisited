package Staartvin.FoundOres;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogClass {

	FoundOres plugin;
	
	protected LogClass(FoundOres plugin) {
		this.plugin = plugin;
	}
	Calendar calendar = Calendar.getInstance();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public enum eventTypes {BLOCKBREAK, COMMAND, REPORT_CREATION, ENABLE, DISABLE, MYSQL_CONNECTION};
	
	public void logToFile(String message, eventTypes eventType)
    {
		if (!plugin.getConfig().getBoolean("Logger.useLogger")) return;
		
		if (eventType.equals(eventTypes.BLOCKBREAK)) {
			if (!plugin.getConfig().getBoolean("Logger.logMine")) return;
		}
		else if (eventType.equals(eventTypes.COMMAND)) {
			if (!plugin.getConfig().getBoolean("Logger.logCommands")) return;
		}
		else if (eventType.equals(eventTypes.REPORT_CREATION)) {
			if (!plugin.getConfig().getBoolean("Logger.logReportCreation")) return;
		}
		else if (eventType.equals(eventTypes.MYSQL_CONNECTION)) {
			if (!plugin.getConfig().getBoolean("Logger.logMySQL")) return;
		}
        try
        {
            File dataFolder = plugin.getDataFolder();
            if(!dataFolder.exists())
            {
                dataFolder.mkdir();
            }
 
            File logFile = new File(plugin.getDataFolder(), "logfile.txt");
            if (!logFile.exists())
            {
                logFile.createNewFile();
            }
 
            FileWriter fw = new FileWriter(logFile, true);
 
            PrintWriter pw = new PrintWriter(fw);
            
            pw.println(format.format(new Date()) + " " + message);
            pw.flush();
            pw.close();
 
        } catch (IOException e)
        {
            e.printStackTrace();
            plugin.logger.logNormal("Could not log to the logfile!");
        }
    }
}
