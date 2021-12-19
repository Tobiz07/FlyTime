package de.sh00ckbass.minecraft.flytime.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*******************************************************
 * Copyright (C) Sh00ckBass tobias@sh00ckbass.de
 *
 * This file is part of FlyTime and was created at the 19.12.2021
 *
 * FlyTime can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */

@AllArgsConstructor
@Getter
@Setter
public class Config {

    private final int price;
    private final boolean onlyInAir;
    private final String noFlyTime;
    private final String flyActivated;
    private final String flyDisabled;
    private final String removedTimePlayer;
    private final String removedTimeTarget;
    private final String addedTimePlayer;
    private final String addedTimeTarget;
    private final String notEnoughMoney;
    private final String successfullyBought;
    private final String timeLeft;
    private final String flyDisabledNoMoreTime;
    private final String actionBar;

}
