package io.github.wolfnt90.berriesjar.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.wolfnt90.berriesjar.Berry;
public class BerriesPlayerListener implements Listener {

	private Berry plugin = null;

	public BerriesPlayerListener(Berry plugin) {
		this.plugin = plugin;
	}

	// Functionality of simplifySleep

	@EventHandler
	public void onPlayerSleep(PlayerBedEnterEvent e) {
		if (e.getBedEnterResult() != BedEnterResult.OK)
			return;

		if (!plugin.getConfig().getBoolean("gameplayTweaks.simplifySleep", true))
			return;

		plugin.playersAsleep.add(e.getPlayer());
		plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				if (e.getPlayer() != null && e.getBed() != null && e.getPlayer().isSleeping()
						&& plugin.playersAsleep.size() >= plugin.getServer().getOnlinePlayers().size() / 2) {
					//TODO set world time
					plugin.getServer().getWorld(e.getPlayer().getWorld().getUID())
							.setTime(plugin.getServer().getWorld(e.getPlayer().getWorld().getUID()).getTime() + 1000);
					for (final Player players : plugin.getServer().getWorld(e.getPlayer().getWorld().getUID())
							.getPlayers()) {
						players.sendMessage(
								ChatColor.YELLOW + "The night has been skipped due to majority sleeping.");

					}
					plugin.playersAsleep.clear();
				}
			}
		}, 40);
	}

	@EventHandler
	public void onPlayerSleep(PlayerBedLeaveEvent e) {
		plugin.playersAsleep.remove(e.getPlayer());
	}

	// Functionality of sounds

	@EventHandler
	public void onPlayerCraft(CraftItemEvent e) {
		if (e.isCancelled())
			return;

		if (e.getInventory().getType() == InventoryType.CRAFTING
				&& plugin.getConfig().getBoolean("sounds.crafting-in-inventory", false)) {
			e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.ITEM_AXE_STRIP, 1f, 2f);
			e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_SCAFFOLDING_STEP, 1f,
					0.5f);
		}

		if (e.getInventory().getType() == InventoryType.WORKBENCH
				&& plugin.getConfig().getBoolean("sounds.crafting", true)) {
			e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_SCAFFOLDING_STEP, 1f,
					0.5f);
			e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_IRON_GOLEM_HURT, 0.5f,
					3f);
			e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.ITEM_AXE_STRIP, 1f, 1.5f);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getJoinMessage().isEmpty())
			return;

		if (plugin.getConfig().getBoolean("sounds.joinMessages", true)) {
			for (final Player player : plugin.getServer().getOnlinePlayers()) {
				player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.7f, 2f);
			}
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		if (e.getQuitMessage().isEmpty())
			return;

		if (plugin.getConfig().getBoolean("sounds.leaveMessages", false)) {
			for (final Player player : plugin.getServer().getOnlinePlayers()) {
				player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.7f, 0.7f);
			}
		}
	}

	@EventHandler
	public void onPlayerKicked(PlayerKickEvent e) {
		if (e.isCancelled())
			return;

		if (plugin.getConfig().getBoolean("gameplayTweaks.unique-kick-message", true)) {
			String reason = e.getReason();
			if (reason.isEmpty()) {
				reason = "unknown";
			}
			e.setLeaveMessage(
					ChatColor.YELLOW + e.getPlayer().getName() + " was removed from the game for reason " + reason);
		}

		if (plugin.getConfig().getBoolean("sounds.kickMessages", false)) {
			for (final Player player : plugin.getServer().getOnlinePlayers()) {
				player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.7f, 0.7f);
			}
		}
	}

	@EventHandler
	public void onChatMessage(AsyncPlayerChatEvent e) {
		if (e.isCancelled())
			return;

		if (plugin.getConfig().getBoolean("sounds.chatMessages", true)) {
			for (final Player player : plugin.getServer().getOnlinePlayers()) {
				player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 0.7f, 2f);
			}
		}
	}
}
