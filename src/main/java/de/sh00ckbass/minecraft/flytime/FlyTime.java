package de.sh00ckbass.minecraft.flytime;

import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.CommandCompletions;
import co.aikar.commands.PaperCommandManager;
import de.sh00ckbass.minecraft.flytime.commands.FlyCommand;
import de.sh00ckbass.minecraft.flytime.listener.PlayerListener;
import de.sh00ckbass.minecraft.flytime.util.Data;
import de.sh00ckbass.minecraft.flytime.util.DataLoader;
import de.sh00ckbass.minecraft.flytime.util.FlyTimeManager;
import de.sh00ckbass.minecraft.flytime.util.LiteSQL;
import de.sh00ckbass.minecraft.flytime.util.ThreadManager;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*******************************************************
 * Copyright (C) Sh00ckBass tobias@sh00ckbass.de
 *
 * This file is part of FlyTime and was created at the 17.12.2021
 *
 * FlyTime can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */

public final class FlyTime extends JavaPlugin {

    @Getter
    private final Data data;
    @Getter
    private final ThreadManager threadManager;
    @Getter
    private final LiteSQL sql;
    @Getter
    private Economy economy;
    @Getter
    private final FlyTimeManager flyTimeManager;
    private PaperCommandManager commandManager;
    private final DataLoader loader;

    public FlyTime() {
        this.data = new Data();
        this.threadManager = new ThreadManager(this);
        this.sql = new LiteSQL(this);
        this.flyTimeManager = new FlyTimeManager(this);
        this.loader = new DataLoader(this);
    }

    @Override
    public void onEnable() {
        if (!this.setupEconomy()) {
            this.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", this.getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        this.commandManager = new PaperCommandManager(this);
        this.sql.connect();
        this.sql.onUpdate("CREATE TABLE IF NOT EXISTS flytime (uuid VARCHAR(50) PRIMARY KEY, flytime BIGINT(50))");
        this.threadManager.startActionBarThread();
        this.registerListener();
        this.registerCommandCompletion();
        this.loader.load();
        //Commands
        this.commandManager.registerCommand(new FlyCommand(this));
    }

    @Override
    public void onDisable() {
        this.threadManager.stopTasks();
        this.loader.save();
    }

    private void registerListener() {
        final PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerListener(this), this);
    }

    private void registerCommandCompletion() {
        final CommandCompletions<BukkitCommandCompletionContext> commandCompletions = this.commandManager.getCommandCompletions();
        commandCompletions.registerAsyncCompletion("onlineplayers", c -> {
            final List<String> playerNames = new ArrayList<>();
            for (final Player player : Bukkit.getOnlinePlayers()) {
                playerNames.add(player.getName());
            }
            return playerNames;
        });
        commandCompletions.registerAsyncCompletion("ints", c -> {
            return Arrays.asList("10", "100", "3600", "86400");
        });
    }

    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        final RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        this.economy = rsp.getProvider();
        return true;
    }

}
