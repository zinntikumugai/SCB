package xyz.zinntikumugai.pl.setcommandblock;

public class SetCommandBlockPermissins {
	private String use				= "use";
	private String put				= "put";
	private String commandSet		= "commandset";
	private String commandRemove	= "commandremove";

	private String base			= "setcommandblock";

	public String getPermissionUse() {
		return base + use;
	}

	public String getPermissionPut() {
		return base + put;
	}

	public String getPermissionCommandSet() {
		return base + commandSet;
	}

	public String getPermissionCommandRemove() {
		return base + commandRemove;
	}
}
