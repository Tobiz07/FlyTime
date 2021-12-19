package de.sh00ckbass.minecraft.flytime.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import de.sh00ckbass.minecraft.flytime.FlyTime;
import de.sh00ckbass.minecraft.flytime.util.Data;
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

    public FlyCommand(final FlyTime plugin) {
        this.plugin = plugin;
        this.data = plugin.getData();
    }

    @Default
    public void fly(final Player player) {
        if (this.data.getFlyTime(player.getUniqueId()) == 0) {
            player.sendMessage("§7You don't have any Fly time.");
            return;
        }
        if (this.data.toggleFlying(player)) {
            player.sendMessage("§7Fly activated.");
            player.setAllowFlight(true);
        } else {
            player.sendMessage("§7Fly disabled.");
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
            case "sec":
                break;
        }
        this.data.removeFlyTime(target.getUniqueId(), time);
        final String formattedTime = this.plugin.getFlyTimeManager().getFormattedTime(time, "§e", "§7", false);
        player.sendMessage("§7You removed " + formattedTime + "Fly time from §e" + target.getName() + "§7.");
        target.sendMessage("§7From your Fly time were " + formattedTime + "removed.");
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
            case "sec":
                break;
        }
        this.data.addFlyTime(target.getUniqueId(), time);
        final String formattedTime = this.plugin.getFlyTimeManager().getFormattedTime(time, "§e", "§7", false);
        player.sendMessage("§7You added " + formattedTime + "Fly time to §e" + target.getName() + "§7.");
        target.sendMessage("§7You received " + formattedTime + "Fly time.");
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
            case "sec":
                break;
        }
        final String formattedTime = this.plugin.getFlyTimeManager().getFormattedTime(time, "§e", "§7", false);
        if (!this.plugin.getEconomy().has(player, 20 * time)) {
            player.sendMessage("§7You don't have enough Money to buy " + formattedTime + "Fly time. You need $§e" + 20 * time + "§7");
            return;
        }
        this.data.addFlyTime(player.getUniqueId(), time);
        this.plugin.getEconomy().withdrawPlayer(player, 20 * time);
        player.sendMessage("§7You bought " + formattedTime + "Fly time for $§e" + 20 * time);
    }

    @Subcommand("gettime")
    public void getTime(final Player player) {
        final long flyTime = this.plugin.getData().getFlyTime(player.getUniqueId());
        final String formattedTime = this.plugin.getFlyTimeManager().getFormattedTime(flyTime, "§e", "§7", true);
        player.sendMessage("§7You have " + formattedTime + "Fly time left.");
    }

}
