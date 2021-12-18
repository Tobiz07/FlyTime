package de.sh00ckbass.minecraft.flytime;

import co.aikar.commands.PaperCommandManager;
import de.sh00ckbass.minecraft.flytime.commands.FlyCommand;
import de.sh00ckbass.minecraft.flytime.listener.PlayerListener;
import de.sh00ckbass.minecraft.flytime.util.Data;
import de.sh00ckbass.minecraft.flytime.util.FlyTimeManager;
import de.sh00ckbass.minecraft.flytime.util.LiteSQL;
import de.sh00ckbass.minecraft.flytime.util.ThreadManager;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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
    private final FlyTimeManager flyTimeManager;
    private PaperCommandManager commandManager;

    public FlyTime() {
        this.data = new Data();
        this.threadManager = new ThreadManager(this);
        this.sql = new LiteSQL(this);
        this.flyTimeManager = new FlyTimeManager(this);
    }

    @Override
    public void onEnable() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        this.commandManager = new PaperCommandManager(this);
        this.sql.connect();
        this.threadManager.startActionBarThread();
        this.registerListener();

        //Commands
        this.commandManager.registerCommand(new FlyCommand(this));
    }

    @Override
    public void onDisable() {
        this.threadManager.stopTasks();
    }

    private void registerListener() {
        final PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerListener(this), this);
    }

}
