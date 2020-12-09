package io.github.wolfnt90.berriesjar.listeners;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
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
		this.plugin = berry;
	}

	@EventHandler
	public void onMonsterSpawn(EntitySpawnEvent e) {
		if (!(e.getEntity() instanceof LivingEntity))
			return;
		
		final Random localRNGInstance = plugin.getRNGforWorld(e.getLocation().getWorld().getSeed());
		if (e.getEntityType() == EntityType.ZOMBIE) {
			final Zombie zombie = (Zombie)e.getEntity();
			if (plugin.getConfig().getBoolean("gameplayTweaks.no-zombie-babies", true)) {
				zombie.setBaby(false);
			}
			if (plugin.getConfig().getBoolean("gameplayTweaks.faster-zombies", false)) {
				switch (localRNGInstance.nextInt(7)) {
				case 5:
					zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1), true);
					break;
				case 6:
					zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 2), true);
					break;
				default:
					break;
				}
			}
		}
		if (e.getEntityType() == EntityType.SKELETON
				&& plugin.getConfig().getBoolean("gameplayTweaks.melee-skeletons", true)) {
			final Skeleton skeleton = (Skeleton) e.getEntity();
			if (skeleton.getEquipment().getItemInMainHand() == null
					|| skeleton.getEquipment().getItemInMainHand().getType() != Material.BOW)
				return;
			switch (localRNGInstance.nextInt(9)) {
			case 6:
				if (plugin.getConfig().getBoolean("gameplayTweaks.melee-skeletons-axe", false)
						&& localRNGInstance.nextBoolean()) {
					skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_AXE));
				} else {
					skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
				}
				break;
			case 7:
				if (plugin.getConfig().getBoolean("gameplayTweaks.melee-skeletons-axe", false)
						&& localRNGInstance.nextBoolean()) {
					skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_AXE));
				} else {
					skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_PICKAXE));
				}
				break;
			case 8:
				skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_PICKAXE));
				break;
			default:
				break;
			}
		}
	}
}
