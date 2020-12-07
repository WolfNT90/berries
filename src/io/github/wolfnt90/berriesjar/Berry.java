package io.github.wolfnt90.berriesjar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.wolfnt90.berriesjar.listeners.BerriesPlayerListener;

public class Berry extends JavaPlugin {

	public List<Player> playersAsleep = new ArrayList<Player>();

	@Override
	public void onEnable() {
		if (getDataFolder().mkdir()) {
			getLogger().info(ChatColor.GOLD + "Thank you! This is your first time running BerriesJar.");
		}
		saveDefaultConfig();
		try {
			getConfig().load(getDataFolder() + "/config.yml");
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		getServer().getPluginManager().registerEvents(new BerriesPlayerListener(this), this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player)
			return execCommandPlayerContext(sender, label, args);
		else
			return execCommand(sender, label, args);
	}

	protected boolean execCommand(CommandSender sender, String label, String[] args) {
		if (label.equalsIgnoreCase("berry") || label.equalsIgnoreCase("b")) {
			if (args.length >= 0 && args[0].equalsIgnoreCase("stop")) {
				getServer().getPluginManager().disablePlugin(this);
				return true;
			}
			if (args.length >= 0 && args[0].equalsIgnoreCase("cfg")) {
				if (!sender.isOp()) {
					sender.sendMessage(
							"You cannot modify the configuration of Berry. If you believe this is in error, please contact an administrator.");
					return false;
				}
				if (args.length == 2) {
					sender.sendMessage(
							"According to the config, " + args[1] + ":" + getConfig().getString(args[1], "(null)"));
					sender.sendMessage(ChatColor.RED + "To modify:");
					sender.sendMessage(ChatColor.YELLOW+"/berry cfg <entry> <value>");
				}
				if (args.length == 3) {
					final String entry = args[1];
					final String value = args[2];
					try {
					getConfig().set(entry, value);
					saveConfig();
					sender.sendMessage(ChatColor.WHITE + "/berry cfg " + entry + " " + value + " should've worked.");
				} catch (final Exception exception) {
					sender.sendMessage(ChatColor.RED + "/berry cfg " + entry + " " + value
							+ " errored out.. check console for " + exception.getMessage());
					exception.printStackTrace();
				}
				}
				return true;
			}
		}
		return false;
	}

	protected boolean execCommandPlayerContext(CommandSender sender, String label, String[] args) {
		return false;
	}
}
