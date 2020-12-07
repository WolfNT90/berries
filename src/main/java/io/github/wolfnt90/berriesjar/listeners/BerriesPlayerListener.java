package io.github.wolfnt90.berriesjar.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import io.github.wolfnt90.berriesjar.Berry;
public class BerriesPlayerListener implements Listener {

	private Berry plugin = null;

	public BerriesPlayerListener(Berry plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerSleep(PlayerBedEnterEvent e) {
		if (!plugin.getConfig().getBoolean("gameplayTweaks.simplifySleep", true))
			return;

		e.setUseBed(Result.ALLOW);
		plugin.playersAsleep.add(e.getPlayer());
		plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				if (e.getPlayer() != null && e.getBed() != null && e.getPlayer().isSleeping()
						&& plugin.playersAsleep.size() >= plugin.getServer().getOnlinePlayers().size() / 2) {
					plugin.getServer().getWorld(e.getPlayer().getWorld().getUID())
							.setTime(23999 - plugin.getServer().getWorld(e.getPlayer().getWorld().getUID()).getTime());
					plugin.getServer().getWorld(e.getPlayer().getWorld().getUID()).setStorm(false);
					for (final Player players : plugin.getServer().getWorld(e.getPlayer().getWorld().getUID())
							.getPlayers()) {
						players.sendMessage(
								"The night has been skipped. (" + plugin.playersAsleep.size() + " were asleep)");

					}
					plugin.playersAsleep.clear();
				}
			}
		}, 20);
	}

	@EventHandler
	public void onPlayerSleep(PlayerBedLeaveEvent e) {
		plugin.playersAsleep.remove(e.getPlayer());
	}
}
