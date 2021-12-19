package de.sh00ckbass.minecraft.flytime.data;

import de.sh00ckbass.minecraft.flytime.config.Config;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/*******************************************************
 * Copyright (C) Sh00ckBass tobias@sh00ckbass.de
 *
 * This file is part of FlyTime and was created at the 19.12.2021
 *
 * FlyTime can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */

public class FlyPlayer {

    @Getter
    private final UUID uuid;
    @Getter
    @Setter
    private boolean isFlying = false;
    @Getter
    @Setter
    private long flyTime;
    @Getter
    private final Config config;

    public FlyPlayer(final UUID uuid, final long flyTime, final Config config) {
        this.uuid = uuid;
        this.flyTime = flyTime;
        this.config = config;
    }

    public void addFlyTime(final long seconds) {
        this.setFlyTime(this.getFlyTime() + seconds);
    }

    public void removeFlyTime(final long seconds) {
        if ((this.getFlyTime() - seconds) < 0) {
            this.setFlyTime(0);
        } else {
            this.setFlyTime(this.getFlyTime() - seconds);
        }
    }

    public void onTick() {
        if (this.config.isOnlyInAir()) {
            final Player player = Bukkit.getPlayer(this.uuid);
            if (player == null) {
                return;
            }
            if (player.isFlying()) {
                this.removeFlyTime(1);
            }
        } else {
            this.removeFlyTime(1);
        }
        if (this.getFlyTime() == 0) {
            final Player player = Bukkit.getPlayer(this.uuid);
            if (player == null) {
                return;
            }
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage(this.getConfig().getFlyDisabledNoMoreTime());
            this.setFlying(false);
        }
    }

}
