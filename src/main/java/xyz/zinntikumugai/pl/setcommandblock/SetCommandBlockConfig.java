package xyz.zinntikumugai.pl.setcommandblock;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

public class SetCommandBlockConfig {

	private Logger logs;
	private SetCommandBlock scb;

	private FileConfiguration config = null;

	private String logPrefix;
	private String logTitle;
	private String logString;

	public SetCommandBlockConfig(SetCommandBlock scb) {
		this.scb = scb;
		logs = scb.getLogger();
	}

	public boolean loadConfg() {

		if( !(new File(scb.getDataFolder() + File.separator + "config.yml").exists()) ) {
			scb.saveDefaultConfig();
			logs.info("default config.yml Copied");
		}

		if( config != null) {
			scb.reloadConfig();
		}

		config = scb.getConfig();
		logPrefix = config.getString("Log.Prefix", "");
		logTitle = config.getString("Log.Title", "[SCB]");
		logString = logPrefix + logTitle;

		return false;
	}

	public String getLogString() {
		return logString;
	}
}
