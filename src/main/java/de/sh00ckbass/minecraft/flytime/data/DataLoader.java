package de.sh00ckbass.minecraft.flytime.data;

import de.sh00ckbass.minecraft.flytime.FlyTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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

public class DataLoader {

    private final FlyTime plugin;

    public DataLoader(final FlyTime plugin) {
        this.plugin = plugin;
    }

    public void load() {
        final ResultSet rs = this.plugin.getSql().executeQuery("SELECT * FROM flytime");
        try {
            while (rs.next()) {
                final UUID uuid = UUID.fromString(rs.getString("uuid"));
                final long flyTime = rs.getLong("flytime");
                this.plugin.getData().loadPlayer(uuid, flyTime);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        final HashMap<UUID, FlyPlayer> flyTime = this.plugin.getData().getFlyTimeOfPlayers();

        for (final UUID uuid : flyTime.keySet()) {
            final String queryString = "INSERT OR REPLACE INTO flytime (uuid, flytime) VALUES (\"" + uuid.toString() + "\", " + flyTime.get(uuid).getFlyTime() + ")";
            this.plugin.getSql().onUpdate(queryString);
        }
    }

}
