package de.sh00ckbass.minecraft.flytime.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import de.sh00ckbass.minecraft.flytime.FlyTime;
import de.sh00ckbass.minecraft.flytime.util.Data;
import net.kyori.adventure.text.Component;
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
        if (this.data.toggleFlying(player)) {
            player.sendMessage(Component.text("§7Fly activated."));
        } else {
            player.sendMessage(Component.text("§7Fly disabled."));
        }
    }

}
