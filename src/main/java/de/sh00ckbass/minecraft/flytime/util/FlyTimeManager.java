package de.sh00ckbass.minecraft.flytime.util;

import de.sh00ckbass.minecraft.flytime.FlyTime;
import de.sh00ckbass.minecraft.flytime.data.Data;

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

    private final Data data;

    public FlyTimeManager(final FlyTime plugin) {
        this.data = plugin.getData();
    }

    public String getFormattedFlyTime(final UUID uuid) {
        final long playerFlyTime = this.data.getFlyTime(uuid);
        if (playerFlyTime == 0) {
            return "0 Seconds";
        }
        return this.getFormattedTime(playerFlyTime, "§e", "§7", true);
    }

    public String getFormattedTime(long seconds, final String numberColor, final String timeUnitColor, final boolean showZeroSeconds) {
        int minutes = (int) (seconds / 60);
        int hours = (minutes / 60);
        final int days = hours / 24;

        hours = (hours % 24);
        minutes = (minutes % 60);
        seconds = (seconds % 60);

        String dayStr = null;
        String hourStr = null;
        String minStr = null;
        String secStr = null;

        if (days != 0) {
            if (days != 1) {
                dayStr = numberColor + days + timeUnitColor + " Days";
            } else {
                dayStr = numberColor + days + timeUnitColor + " Day";
            }
        }
        if (hours != 0) {
            if (hours != 1) {
                hourStr = numberColor + hours + timeUnitColor + " Hours";
            } else {
                hourStr = numberColor + hours + timeUnitColor + " Hour";
            }
        }
        if (minutes != 0) {
            if (minutes != 1) {
                minStr = numberColor + minutes + timeUnitColor + " Minutes";
            } else {
                minStr = numberColor + minutes + timeUnitColor + " Minute";
            }
        }
        if (showZeroSeconds) {
            if (seconds != 1) {
                secStr = numberColor + seconds + timeUnitColor + " Seconds";
            } else {
                secStr = numberColor + seconds + timeUnitColor + " Second";
            }
        } else {
            if (seconds != 0) {
                if (seconds != 1) {
                    secStr = numberColor + seconds + timeUnitColor + " Seconds";
                } else {
                    secStr = numberColor + seconds + timeUnitColor + " Second";
                }
            }
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
        if (secStr != null) {
            formattedStringBuilder.append(secStr).append(" ");
        }


        return formattedStringBuilder.toString();
    }

}
