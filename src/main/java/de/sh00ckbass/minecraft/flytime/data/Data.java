package de.sh00ckbass.minecraft.flytime.data;

import de.sh00ckbass.minecraft.flytime.FlyTime;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*******************************************************
 * Copyright (C) Sh00ckBass tobias@sh00ckbass.de
 *
 * This file is part of FlyTime and was created at the 17.12.2021
 *
 * FlyTime can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */

public class Data {

    private final FlyTime plugin;
    private final Map<UUID, FlyPlayer> flyPlayers = new HashMap<>();

    public Data(final FlyTime plugin) {
        this.plugin = plugin;
    }

    public boolean isFlying(final Player player) {
        return this.getFlyPlayer(player.getUniqueId()).isFlying();
    }

    public boolean toggleFly(final Player player) {
        if (this.isFlying(player)) {
            this.getFlyPlayer(player.getUniqueId()).setFlying(false);
            player.sendActionBar(Component.text());
            return false;
        }
        this.getFlyPlayer(player.getUniqueId()).setFlying(true);
        return true;
    }

    public long getFlyTime(final UUID uuid) {
        return this.getFlyPlayer(uuid).getFlyTime();
    }

    public void addFlyTime(final UUID uuid, final long flyTime) {
        this.getFlyPlayer(uuid).addFlyTime(flyTime);
    }

    public void removeFlyTime(final UUID uuid, final long flyTime) {
        this.getFlyPlayer(uuid).removeFlyTime(flyTime);
    }

    public void registerPlayer(final UUID uuid) {
        if (this.isRegistered(uuid)) {
            return;
        }
        this.loadPlayer(uuid, 0L);
    }

    public boolean isRegistered(final UUID uuid) {
        return this.flyPlayers.containsKey(uuid);
    }

    public void onTick() {
        this.flyPlayers.forEach((uuid, flyPlayer) -> {
            flyPlayer.onTick();
        });
    }

    public HashMap<UUID, FlyPlayer> getFlyTimeOfPlayers() {
        return (HashMap<UUID, FlyPlayer>) this.flyPlayers;
    }

    public void loadPlayer(final UUID uuid, final long flyTime) {
        this.flyPlayers.put(uuid, new FlyPlayer(uuid, flyTime, this.plugin.getPluginConfig()));
    }

    private FlyPlayer getFlyPlayer(final UUID uuid) {
        return this.flyPlayers.get(uuid);
    }

}
