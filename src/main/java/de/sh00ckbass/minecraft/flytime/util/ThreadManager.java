package de.sh00ckbass.minecraft.flytime.util;

import de.sh00ckbass.minecraft.flytime.FlyTime;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/*******************************************************
 * Copyright (C) Sh00ckBass tobias@sh00ckbass.de
 *
 * This file is part of FlyTime and was created at the 17.12.2021
 *
 * FlyTime can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */

public class ThreadManager {

    private final FlyTime plugin;
    private final String actionBar;

    public ThreadManager(final FlyTime plugin) {
        this.plugin = plugin;
        this.actionBar = plugin.getPluginConfig().getActionBar();
    }

    public void startActionBarThread() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
            for (final Player player : Bukkit.getOnlinePlayers()) {
                if (this.plugin.getData().isFlying(player)) {
                    player.sendActionBar(Component.text(this.actionBar.replace("{time}",
                            this.plugin.getFlyTimeManager().getFormattedFlyTime(player.getUniqueId()))));
                    this.plugin.getData().onTick();
                }
            }
        }, 0, 20);
    }

    public void stopTasks() {
        Bukkit.getScheduler().cancelTasks(this.plugin);
    }

}
