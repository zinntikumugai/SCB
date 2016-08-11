package xyz.zinntikumugai.pl.setcommandblock;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class SetCommandBlock extends JavaPlugin {

	private SetCommandBlockCommand scbCommand;
	private SetCommandBlockConfig scbConfig;
	private SetCommandBlockPermissins scbPermissin;

	private boolean isCanCommandBlockCommand = false;

	public void onEnable() {
		scbCommand = new SetCommandBlockCommand(this);
		scbConfig = new SetCommandBlockConfig(this);
		scbPermissin = new SetCommandBlockPermissins();

		getCommand("SetCommandBlock").setExecutor(scbCommand);

		getServer().getPluginManager().registerEvents(new SetCommandBlockListener(this), this);

	}

	public void onDisable() {

	}

	public Logger getLogHandler() {
		return getLogger();
	}

	public boolean getIsCanCommandBlockCOmmand() {
		return isCanCommandBlockCommand;
	}

	public SetCommandBlockConfig getConfigHandler() {
		return scbConfig;
	}

	public SetCommandBlockPermissins getPermissins() {
		return scbPermissin;
	}
}
