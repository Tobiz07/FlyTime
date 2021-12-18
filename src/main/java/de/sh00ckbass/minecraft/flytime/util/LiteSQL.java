package de.sh00ckbass.minecraft.flytime.util;

import de.sh00ckbass.minecraft.flytime.FlyTime;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/*******************************************************
 * Copyright (C) Sh00ckBass tobias@sh00ckbass.de
 *
 * This file is part of FlyTime and was created at the 17.12.2021
 *
 * FlyTime can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */

public class LiteSQL {

    private String url;
    private final FlyTime plugin;

    public LiteSQL(final FlyTime plugin) {
        this.plugin = plugin;
    }

    public void connect() {
        try {
            final File file = new File(this.plugin.getDataFolder(), "datenbank.db");
            if (!file.exists()) {
                file.createNewFile();
            }

            this.url = "jdbc:sqlite:" + file.getPath();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.url);
    }

    public void onUpdate(final String sql) {
        try {
            this.getConnection().createStatement().execute(sql);
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateRAW(final String sql) throws SQLException {
        this.getConnection().createStatement().execute(sql);
    }

    public ResultSet executeQuery(final String sql) {

        try {
            return this.getConnection().createStatement().executeQuery(sql);
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}