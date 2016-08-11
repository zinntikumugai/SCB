package xyz.zinntikumugai.pl.setcommandblock;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SetCommandBlockListener implements Listener {

	private final Logger logs;

	public SetCommandBlockListener(SetCommandBlock scb) {
		this.logs = scb.getLogger();
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(!(
				e.getAction().equals(Action.LEFT_CLICK_BLOCK) ||
				e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
				)) {
			return ;
		}

		Player p = e.getPlayer();
		ItemStack is = p.getItemOnCursor();	//クリックしたほうのアイテムスタックを取得
		if( !(
				is.equals(Material.COMMAND) ||
				is.equals(Material.COMMAND_CHAIN) ||
				is.equals(Material.COMMAND_REPEATING)
				)) {
			return ;
		}

		Material clickedBlockType = e.getClickedBlock().getType();
		if( (
				clickedBlockType.equals(Material.COMMAND) ||
				clickedBlockType.equals(Material.COMMAND_CHAIN) ||
				clickedBlockType.equals( Material.COMMAND_REPEATING)
				) && !(p.isSneaking()) ) {
			return ;
		}

		Location location = e.getClickedBlock().getLocation();
		BlockFace blockface = e.getBlockFace();

		location.setX( location.getX() + blockface.getModX() );
		location.setY( location.getY() + blockface.getModY() );
		location.setZ( location.getZ() + blockface.getModZ() );
		Block block = location.getBlock();
		//air, lava, water
		if(!(
				block.getType().equals(Material.AIR) ||
				block.getType().equals(Material.LAVA) ||
				block.getType().equals(Material.WATER)
				)) {
			return ;
		}

	}

	/**
	 * クリックされたブロックとクリック時のブロック関係の情報からコマンドブロックの向きを求める
	 * @param clickedblock
	 * @param commandblock
	 * @return
	 */
	private Block getCommandBlockPeel(Block clickedblock, BlockFace blockface) {
		Block commandblock = clickedblock;
		int pitch, yaw;
		pitch = yaw = 0;

		commandblock.getLocation().setX( commandblock.getLocation().getX() + blockface.getModX() );
		commandblock.getLocation().setY( commandblock.getLocation().getY() + blockface.getModY() );
		commandblock.getLocation().setZ( commandblock.getLocation().getZ() + blockface.getModZ() );

		if( commandblock.getLocation().getY() == blockface.getModY()) {

			if( blockface.getModY() == 1)
				pitch = -90;
			else if ( blockface.getModY() == -1)
				pitch = 90;
			else
				pitch = 0;	//
		}else {
			pitch = 0;
			if( blockface.getModX() != 0) {

				if( blockface.getModX() == 1)
					yaw = 90;
				else if ( blockface.getModX() == -1)
					yaw = -90;
				else
					yaw = 0;	//
			}else if(blockface.getModZ() != 1){

				if(blockface.getModZ() == 1)
					yaw = -180;
				else if(blockface.getModZ() == -1)
					yaw = 0;
				else
					yaw = 0;	//
			}else {
				yaw = 0;	//
			}
		}

		commandblock.getLocation().setPitch( pitch );
		commandblock.getLocation().setYaw( yaw );

		commandblock.setType( Material.COMMAND );
		return commandblock;
	}
}
