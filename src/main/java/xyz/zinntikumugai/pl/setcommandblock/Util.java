package xyz.zinntikumugai.pl.setcommandblock;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Util {
	public boolean hasPlayerPermission( Player p, String permission, String message) {
		if( !p.hasPermission(permission)) {
			p.sendMessage(message);
			return false;
		}
		return true;
	}

	public boolean hasPlayerPermission( Player p, String permission) {
		if( !p.hasPermission(permission)) {
			p.sendMessage(ChatColor.RED + "You Don't Hav Permission!!");
			return false;
		}
		return true;
	}
}
