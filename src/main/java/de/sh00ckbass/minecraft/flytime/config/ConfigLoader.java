package de.sh00ckbass.minecraft.flytime.config;

import de.sh00ckbass.minecraft.flytime.FlyTime;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/*******************************************************
 * Copyright (C) Sh00ckBass tobias@sh00ckbass.de
 *
 * This file is part of FlyTime and was created at the 19.12.2021
 *
 * FlyTime can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */

public class ConfigLoader {

    private final FlyTime plugin;
    private final File configFile;
    private final YamlConfiguration yamlConfiguration;

    public ConfigLoader(final FlyTime plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!this.configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        this.yamlConfiguration = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public void load() {
        final int price = this.yamlConfiguration.getInt("price");
        final boolean onlyInAir = this.yamlConfiguration.getBoolean("onlyInAir");
        final String noFlyTime = this.yamlConfiguration.getString("noFlyTime").replace('&', '§');
        final String flyActivated = this.yamlConfiguration.getString("flyActivated").replace('&', '§');
        final String flyDisabled = this.yamlConfiguration.getString("flyDisabled").replace('&', '§');
        final String removedTimePlayer = this.yamlConfiguration.getString("removedTimePlayer").replace('&', '§');
        final String removedTimeTarget = this.yamlConfiguration.getString("removedTimeTarget").replace('&', '§');
        final String addedTimePlayer = this.yamlConfiguration.getString("addedTimePlayer").replace('&', '§');
        final String addedTimeTarget = this.yamlConfiguration.getString("addedTimeTarget").replace('&', '§');
        final String notEnoughMoney = this.yamlConfiguration.getString("notEnoughMoney").replace('&', '§');
        final String successfullyBought = this.yamlConfiguration.getString("successfullyBought").replace('&', '§');
        final String timeLeft = this.yamlConfiguration.getString("timeLeft").replace('&', '§');
        final String flyDisabledNoMoreTime = this.yamlConfiguration.getString("flyDisabledNoMoreTime").replace('&', '§');
        final String actionBar = this.yamlConfiguration.getString("actionBar").replace('&', '§');
        final Config config = new Config(price, onlyInAir, noFlyTime, flyActivated, flyDisabled, removedTimePlayer,
                removedTimeTarget, addedTimePlayer, addedTimeTarget,
                notEnoughMoney, successfullyBought, timeLeft, flyDisabledNoMoreTime, actionBar);

        this.plugin.setPluginConfig(config);
    }

}
