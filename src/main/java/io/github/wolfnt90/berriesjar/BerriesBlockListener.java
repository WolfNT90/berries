package io.github.wolfnt90.berriesjar;

import org.bukkit.Material;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BerriesBlockListener implements Listener {

	private BerriesJarPlugin plugin;

	public BerriesBlockListener(BerriesJarPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onItemUse(PlayerInteractEvent e) {
		var block = e.getClickedBlock();
		var who = e.getPlayer();
		var item = who.getInventory().getItemInMainHand();
		var itemOffhand = who.getInventory().getItemInOffHand();
		if (block.getType() == Material.IRON_DOOR)
			if ((item != null && item.getType() == Material.REDSTONE_TORCH)
					| (itemOffhand != null && itemOffhand.getType() == Material.REDSTONE_TORCH))
				((Door) block).setOpen(!((Door) block).isOpen());
	}

}
