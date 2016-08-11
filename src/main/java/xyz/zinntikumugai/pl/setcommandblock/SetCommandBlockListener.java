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
	private final String permissionUse;
	private Util util;

	public SetCommandBlockListener(SetCommandBlock scb) {
		this.logs = scb.getLogger();
		this.permissionUse = scb.getPermissins().getPermissionUse();
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
		if(!(util.hasPlayerPermission(p, permissionUse))) {
			return;
		}

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

		if( !isCanSetBlock(p, block)) {
			return ;
		}
		Block commandBlock = this.getCommandBlockPeel(e.getClickedBlock(), blockface, clickedBlockType);


		//
	}

	/**
	 * クリックされたブロックとクリックされた面の情報でブロックの方向を求め指定ブロックを取得
	 * @param clickedBlock	クリックされたブロック
	 * @param blockFace	クリックされたブロックの情報
	 * @param commandBlockMaterial	置くブロック
	 * @return 生成されたブロックデータ
	 */
	private Block getCommandBlockPeel(Block clickedBlock, BlockFace blockFace, Material commandBlockMaterial) {
		Block commandBlock = clickedBlock;
		int pitch, yaw;
		pitch = yaw = 0;

		commandBlock.getLocation().setX( commandBlock.getLocation().getX() + blockFace.getModX() );
		commandBlock.getLocation().setY( commandBlock.getLocation().getY() + blockFace.getModY() );
		commandBlock.getLocation().setZ( commandBlock.getLocation().getZ() + blockFace.getModZ() );

		if( commandBlock.getLocation().getY() == blockFace.getModY()) {

			if( blockFace.getModY() == 1)
				pitch = -90;
			else if ( blockFace.getModY() == -1)
				pitch = 90;
			else
				pitch = 0;	//
		}else {
			pitch = 0;
			if( blockFace.getModX() != 0) {

				if( blockFace.getModX() == 1)
					yaw = 90;
				else if ( blockFace.getModX() == -1)
					yaw = -90;
				else
					yaw = 0;	//
			}else if(blockFace.getModZ() != 1){

				if(blockFace.getModZ() == 1)
					yaw = -180;
				else if(blockFace.getModZ() == -1)
					yaw = 0;
				else
					yaw = 0;	//
			}else {
				yaw = 0;	//
			}
		}

		commandBlock.getLocation().setPitch( pitch );
		commandBlock.getLocation().setYaw( yaw );

		commandBlock.setType( commandBlockMaterial );
		return commandBlock;
	}

	/**
	 * プレイヤーの位置情報からブロックが置けるか
	 * @param p		プレイヤー
	 * @param setBlock	置くブロック
	 * @return	置けるならtrue, 置けないならfalse
	 */
	private boolean isCanSetBlock(Player p, Block setBlock) {
		double max = 0.3;

		double maxX = (double)setBlock.getX() + max;
		double minX = (double)setBlock.getX() - max;
		double maxZ = (double)setBlock.getZ() + max;
		double minZ = (double)setBlock.getZ() - max;

		double pX = p.getLocation().getX();
		double pZ = p.getLocation().getZ();

		if( (int)p.getLocation().getY() != setBlock.getY()) {
			return true;
		}

		if( pX > maxX && minX > pX ) {
			return true;
		}
		if( pZ > maxX && minZ > pZ) {
			return true;
		}
		return false;
	}
}
