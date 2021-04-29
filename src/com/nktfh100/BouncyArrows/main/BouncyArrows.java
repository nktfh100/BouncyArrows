package com.nktfh100.BouncyArrows.main;

import org.bukkit.plugin.java.JavaPlugin;

import com.nktfh100.BouncyArrows.events.ProjectileHit;
import com.nktfh100.BouncyArrows.managers.ConfigManager;

public class BouncyArrows extends JavaPlugin {

	private static BouncyArrows instance;

	private ConfigManager configManager;

	public BouncyArrows() {
		instance = this;
	}

	@Override
	public void onEnable() {
		configManager = new ConfigManager(this);
		configManager.loadConfig();
		getServer().getPluginManager().registerEvents(new ProjectileHit(this), this);
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public static BouncyArrows getInstance() {
		return instance;
	}

}
