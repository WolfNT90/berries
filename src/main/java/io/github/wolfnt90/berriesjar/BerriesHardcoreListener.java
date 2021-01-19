package io.github.wolfnt90.berriesjar;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BerriesHardcoreListener implements Listener {

	private BerriesJarPlugin plugin;

	public BerriesHardcoreListener(BerriesJarPlugin plugin) {
		this.plugin = plugin;
		plugin.getLogger().info("Server is hardcore! Plugin will register special hardcore behaviors.");
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		plugin.getLogger().info("Placeholder"); //TODO implement hardcore behavior changes.
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player)
		{
			var player = (Player) e.getEntity();
			var reason = e.getCause();
			var damage = e.getFinalDamage();
			if (reason == DamageCause.FALL && damage > player.getHealth() / 2)
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0), false);
		}
	}

	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player)
		{
			var player = (Player) e.getEntity();
			var attacker = e.getDamager();
			var damage = e.getFinalDamage();
			if (attacker.getType() == EntityType.PHANTOM) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 0), true);
				((Phantom) attacker).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 0), true);
			}
		}
	}

}
