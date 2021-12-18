package de.sh00ckbass.minecraft.flytime.util;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

    private final List<Player> flyingPlayers = new LinkedList<>();
    private final Map<UUID, Long> flyTimeOfPlayers = new HashMap<>();

    public boolean isFlying(final Player player) {
        return this.flyingPlayers.contains(player);
    }

    public boolean toggleFlying(final Player player) {
        if (this.isFlying(player)) {
            this.flyingPlayers.remove(player);
            player.sendActionBar(Component.text());
            return false;
        }
        player.sendActionBar(Component.text("§7Fly-Mode toggled"));
        this.flyingPlayers.add(player);
        return true;
    }

    public long getFlyTime(final UUID uuid) {
        return this.flyTimeOfPlayers.get(uuid);
    }

    public long addFlyTime(final UUID uuid, final long flyTime) {
        return this.flyTimeOfPlayers.computeIfPresent(uuid, (uid, value) -> value + flyTime);
    }

    public void removeFlyTime(final UUID uuid, final long flyTime) {
        this.flyTimeOfPlayers.computeIfPresent(uuid, (uid, value) -> value - flyTime > 0 ? value - flyTime : 0);
    }

    public void registerPlayer(final UUID uuid) {
        if (this.isRegistered(uuid)) {
            return;
        }
        this.flyTimeOfPlayers.put(uuid, 86461L);
    }

    public boolean isRegistered(final UUID uuid) {
        return this.flyTimeOfPlayers.containsKey(uuid);
    }

    public void onTick() {
        this.flyTimeOfPlayers.forEach((uuid, flyTime) -> this.removeFlyTime(uuid, 1));
    }

}
