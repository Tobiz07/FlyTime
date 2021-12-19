package de.sh00ckbass.minecraft.flytime.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import de.sh00ckbass.minecraft.flytime.FlyTime;
import de.sh00ckbass.minecraft.flytime.config.Config;
import de.sh00ckbass.minecraft.flytime.data.Data;
import org.bukkit.entity.Player;

/*******************************************************
 * Copyright (C) Sh00ckBass tobias@sh00ckbass.de
 *
 * This file is part of FlyTime and was created at the 18.12.2021
 *
 * FlyTime can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */

@CommandAlias("fly")
@CommandPermission("flytime.fly")
public class FlyCommand extends BaseCommand {

    private final FlyTime plugin;
    private final Data data;
    private final Config config;

    public FlyCommand(final FlyTime plugin) {
        this.plugin = plugin;
        this.data = plugin.getData();
        this.config = plugin.getPluginConfig();
    }

    @Default
    public void fly(final Player player) {
        if (this.data.getFlyTime(player.getUniqueId()) == 0) {
            player.sendMessage(this.config.getNoFlyTime());
            return;
        }
        if (this.data.toggleFly(player)) {
            player.sendMessage(this.config.getFlyActivated());
            player.setAllowFlight(true);
        } else {
            player.sendMessage(this.config.getFlyDisabled());
            player.setFlying(false);
            player.setAllowFlight(false);
        }
    }

    @Subcommand("removeTime")
    @CommandPermission("flytime.admin.removeTime")
    @CommandCompletion("@time @timeunits @onlineplayers")
    public void removeTime(final Player player, int time, final String timeUnit, final Player target) {
        switch (timeUnit) {
            case "day":
                time = time * 60 * 60 * 24;
                break;
            case "hour":
                time = time * 60 * 60;
                break;
            case "min":
                time = time * 60;
                break;
        }
        this.data.removeFlyTime(target.getUniqueId(), time);
        final String formattedTime = this.plugin.getFlyTimeManager().getFormattedTime(time, "§e", "§7", false);
        player.sendMessage(this.config.getRemovedTimePlayer().replace("{time}", formattedTime).replace("{name}", target.getName()));
        target.sendMessage(this.config.getRemovedTimeTarget().replace("{time}", formattedTime));
    }

    @Subcommand("addTime")
    @CommandPermission("flytime.admin.removeTime")
    @CommandCompletion("@time @timeunits @onlineplayers")
    public void addTime(final Player player, int time, final String timeUnit, final Player target) {
        switch (timeUnit) {
            case "day":
                time = time * 60 * 60 * 24;
                break;
            case "hour":
                time = time * 60 * 60;
                break;
            case "min":
                time = time * 60;
                break;
        }
        this.data.addFlyTime(target.getUniqueId(), time);
        final String formattedTime = this.plugin.getFlyTimeManager().getFormattedTime(time, "§e", "§7", false);
        player.sendMessage(this.config.getAddedTimePlayer().replace("{time}", formattedTime).replace("{name}", target.getName()));
        target.sendMessage(this.config.getAddedTimeTarget().replace("{time}", formattedTime));
    }

    @Subcommand("buy")
    @CommandCompletion("@time @timeunits")
    public void buyTime(final Player player, int time, final String timeUnit) {
        switch (timeUnit) {
            case "day":
                time = time * 60 * 60 * 24;
                break;
            case "hour":
                time = time * 60 * 60;
                break;
            case "min":
                time = time * 60;
                break;
        }
        final String formattedTime = this.plugin.getFlyTimeManager().getFormattedTime(time, "§e", "§7", false);
        final int moneyNeeded = 20 * time;
        if (!this.plugin.getEconomy().has(player, moneyNeeded)) {
            player.sendMessage(this.config.getNotEnoughMoney().replace("{time}", formattedTime).replace("{money}", String.valueOf(moneyNeeded)));
            return;
        }
        this.data.addFlyTime(player.getUniqueId(), time);
        this.plugin.getEconomy().withdrawPlayer(player, moneyNeeded);
        player.sendMessage(this.config.getSuccessfullyBought().replace("{time}", formattedTime).replace("{money}", String.valueOf(moneyNeeded)));
    }

    @Subcommand("gettime")
    public void getTime(final Player player) {
        final long flyTime = this.plugin.getData().getFlyTime(player.getUniqueId());
        final String formattedTime = this.plugin.getFlyTimeManager().getFormattedTime(flyTime, "§e", "§7", true);
        player.sendMessage(this.config.getTimeLeft().replace("{time}", formattedTime));
    }

}
