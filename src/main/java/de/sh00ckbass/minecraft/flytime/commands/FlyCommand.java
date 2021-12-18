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
    @CommandCompletion("@ints @onlineplayers")
    public void removeTime(final Player player, final int time, final Player target) {
        this.data.removeFlyTime(target.getUniqueId(), time);
        player.sendMessage("§7You removed §e" + time + "§7 Seconds Fly time from §e" + target.getName() + "§7.");
        target.sendMessage("§7From your Fly time were §e" + time + "§7 Seconds removed.");
    }

    @Subcommand("addTime")
    @CommandPermission("flytime.admin.removeTime")
    @CommandCompletion("@ints @onlineplayers")
    public void addTime(final Player player, final int time, final Player target) {
        this.data.addFlyTime(target.getUniqueId(), time);
        player.sendMessage("§7You added §e" + time + "§7 Seconds Fly time to §e" + target.getName() + "§7.");
        target.sendMessage("§7You received §e" + time + "§7 Seconds Fly time.");
    }

    @Subcommand("buy")
    @CommandCompletion("@ints")
    public void buyTime(final Player player, final int time) {
        if (!this.plugin.getEconomy().has(player, 20 * time)) {
            player.sendMessage("§7You don't have enough Money to buy §e" + time + "§7 Seconds Fly time. You need $§e" + 20 * time + "§7");
            return;
        }
        this.data.addFlyTime(player.getUniqueId(), time);
        this.plugin.getEconomy().withdrawPlayer(player, 20 * time);
        player.sendMessage("§7You bought §e" + time + "§7 Seconds Fly time for $§e" + 20 * time);
    }

}
