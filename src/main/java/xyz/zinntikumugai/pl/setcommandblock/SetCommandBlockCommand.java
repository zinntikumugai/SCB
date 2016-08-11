package xyz.zinntikumugai.pl.setcommandblock;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommandBlockCommand implements CommandExecutor {

	private final Logger logs;
	private final String permissionUse;
	private Util pluginutil;

	public SetCommandBlockCommand(SetCommandBlock scb) {
		this.logs = scb.getLogger();
		permissionUse = scb.getPermissins().getPermissionUse();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Con Use This Command");
			return false;
		}
		Player p = null;

		if( args.length > 0) {
			return false;
		}

		if(! pluginutil.hasPlayerPermission(p, permissionUse)) {
			return false;
		}else {
			switch( args[0]) {
				case "set":
					break;
				case "remove":
					break;
				default:
					return false;
			}
		}

		return false;
	}
}
