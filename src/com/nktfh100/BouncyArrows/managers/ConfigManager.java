package com.nktfh100.BouncyArrows.managers;

import org.bukkit.configuration.file.FileConfiguration;

import com.nktfh100.BouncyArrows.main.BouncyArrows;

public class ConfigManager {

	private BouncyArrows plugin;

	private double bounciness;
	private int maxBounces;
	private double arrowMinSpeed;

	public ConfigManager(BouncyArrows instance) {
		this.plugin = instance;
	}

	public void loadConfig() {
		this.plugin.saveDefaultConfig();
		this.loadConfigVars();
	}

	public void loadConfigVars() {

		this.plugin.reloadConfig();
		FileConfiguration config = this.plugin.getConfig();

		this.bounciness = config.getDouble("bounciness", 0.85);
		this.maxBounces = config.getInt("max-bounces", 5);
		this.arrowMinSpeed = config.getDouble("arrow-min-speed", 0.7);
	}

	public double getBounciness() {
		return bounciness;
	}

	public int getMaxBounces() {
		return maxBounces;
	}

	public double getArrowMinSpeed() {
		return arrowMinSpeed;
	}
}
