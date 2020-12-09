package io.github.wolfnt90.berriesjar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.wolfnt90.berriesjar.listeners.BerriesMonsterListener;
import io.github.wolfnt90.berriesjar.listeners.BerriesPlayerListener;

public class BerriesJarPlugin extends JavaPlugin {

	public List<Player> playersAsleep = new ArrayList<Player>();
	public final Random rng = new Random();

	@Override
	public void onEnable() {
		if (getDataFolder().mkdir()) {
			getLogger().info(ChatColor.YELLOW + "Thank you! This is your first time running BerriesJar.");
		}
		saveDefaultConfig();
		try {
			getConfig().load(getDataFolder() + "/config.yml");
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		getServer().getPluginManager().registerEvents(new BerriesPlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new BerriesMonsterListener(this), this);
	}

	@Override
	public void onDisable() {
		this.playersAsleep.clear();
	}

	public Random getRNGforWorld(long seed) {
		this.rng.setSeed(seed);
		return rng;
	}
}
