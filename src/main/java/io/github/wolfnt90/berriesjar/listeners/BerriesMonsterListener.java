package io.github.wolfnt90.berriesjar.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.wolfnt90.berriesjar.BerriesJarPlugin;

public class BerriesMonsterListener implements Listener {

	private final BerriesJarPlugin plugin;

	public BerriesMonsterListener(BerriesJarPlugin berry) {
		plugin = berry;
	}

	@EventHandler
	public void onMonsterSpawn(EntitySpawnEvent e) {
		if (e.getEntity() instanceof Animals)
			return;

		var var0 = plugin.getRNGforWorld(e.getLocation().getWorld().getSeed()).nextInt(10);
		if (e.getEntityType() == EntityType.ZOMBIE) {
			final var zombie = (Zombie) e.getEntity();
			if (plugin.getConfig().getBoolean("gameplayTweaks.no-zombie-babies", true) && zombie.isBaby())
				e.setCancelled(true);
			if (plugin.getConfig().getBoolean("gameplayTweaks.faster-zombies", false))
				switch (var0) {
				case 8:
					zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1), true);
					break;
				case 9:
					zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 2), true);
					break;
				default:
					break;
				}
		}
		if (e.getEntityType() == EntityType.SKELETON
				&& plugin.getConfig().getBoolean("gameplayTweaks.melee-skeletons", true)) {
			final var skeleton = (Skeleton) e.getEntity();
			switch (var0) {
			case 4:
				skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_HOE));
				break;
			case 5:
				skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_PICKAXE));
				break;
			case 6:
				skeleton.getEquipment().setItemInMainHand(
						new ItemStack(plugin.getConfig().getBoolean("gameplayTweaks.melee-skeletons-with-axes", false)
								? Material.STONE_AXE
										: Material.WOODEN_HOE));
				break;
			case 7:
				skeleton.getEquipment().setItemInMainHand(
						new ItemStack(plugin.getConfig().getBoolean("gameplayTweaks.melee-skeletons-with-axes", false)
								? Material.STONE_AXE
										: Material.WOODEN_PICKAXE));
				break;
			case 8:
				skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
				break;
			case 9:
				skeleton.getEquipment().setItemInMainHand(
						new ItemStack(plugin.getConfig().getBoolean("gameplayTweaks.melee-skeletons-with-axes", false)
								? Material.STONE_AXE
										: Material.STONE_SWORD));
				break;
			default:
				break;
			}
		}
	}
}
