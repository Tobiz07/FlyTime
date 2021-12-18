package de.sh00ckbass.minecraft.flytime.util;

import de.sh00ckbass.minecraft.flytime.FlyTime;

import java.util.UUID;

/*******************************************************
 * Copyright (C) Sh00ckBass tobias@sh00ckbass.de
 *
 * This file is part of FlyTime and was created at the 18.12.2021
 *
 * FlyTime can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */

public class FlyTimeManager {

    private final FlyTime plugin;
    private final Data data;

    public FlyTimeManager(final FlyTime plugin) {
        this.plugin = plugin;
        this.data = plugin.getData();
    }

    public String getFormattedFlyTime(final UUID uuid) {
        final long playerFlyTime = this.data.getFlyTime(uuid);
        if (playerFlyTime == 0) {
            return "0 Seconds";
        }
        final int seconds = (int) (playerFlyTime % 60);
        int minutes = (int) (playerFlyTime / 60);
        int hours = (minutes / 60);
        final int days = hours / 24;

        hours = (hours % 24);
        minutes = (minutes % 60);

        String dayStr = null;
        String hourStr = null;
        String minStr = null;
        final String secStr;

        if (days != 0) {
            if (days != 1) {
                dayStr = days + " Days";
            } else {
                dayStr = days + " Day";
            }
        }
        if (hours != 0) {
            if (hours != 1) {
                hourStr = hours + " Hours";
            } else {
                hourStr = hours + " Hour";
            }
        }
        if (minutes != 0) {
            if (minutes != 1) {
                minStr = minutes + " Minutes";
            } else {
                minStr = minutes + " Minute";
            }
        }
        if (seconds != 1) {
            secStr = seconds + " Seconds";
        } else {
            secStr = seconds + " Second";
        }

        final StringBuilder formattedStringBuilder = new StringBuilder();
        if (dayStr != null) {
            formattedStringBuilder.append(dayStr).append(" ");
        }
        if (hourStr != null) {
            formattedStringBuilder.append(hourStr).append(" ");
        }
        if (minStr != null) {
            formattedStringBuilder.append(minStr).append(" ");
        }
        formattedStringBuilder.append(secStr);


        return formattedStringBuilder.toString();
    }

}
