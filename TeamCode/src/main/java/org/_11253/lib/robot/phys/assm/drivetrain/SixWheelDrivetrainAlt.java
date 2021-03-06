/*
 * **
 *
 * Copyright (c) 2020
 * Copyright last updated on 6/10/20, 6:30 PM
 * Part of the _1125c library
 *
 * **
 *
 * Permission is granted, free of charge, to any person obtaining
 * a copy of this software and / or any of it's related source code or
 * documentation ("Software") to copy, merge, modify, publish,
 * distribute, sublicense, and / or sell copies of Software.
 *
 * All Software included is provided in an "as is" state, without any
 * type or form of warranty. The Authors and Copyright Holders of this
 * piece of software, documentation, or source code waive all
 * responsibility and shall not be liable for any claim, damages, or
 * other forms of liability, regardless of the form it may take.
 *
 * Any form of re-distribution of Software is required to have this same
 * copyright notice included in any source files or forms of documentation
 * which have stemmed or branched off of the original Software.
 *
 * **
 *
 */

package org._11253.lib.robot.phys.assm.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org._11253.lib.robot.phys.components.Motor;

/**
 * Alternative drivetrain if the six wheel drivetrain isn't yo thang.
 *
 * @author Colin Robertson
 */
public class SixWheelDrivetrainAlt extends SixWheelDrivetrain implements DrivetrainInterface {
    @Override
    public void init() {
        frontRight = new Motor(frontRightName);
        midRight = new Motor(midRightName);
        backRight = new Motor(backRightName);
        frontLeft = new Motor(frontLeftName);
        midLeft = new Motor(midLeftName);
        backLeft = new Motor(backLeftName);

        frontRight.isRound = isRound;
        midRight.isRound = isRound;
        backRight.isRound = isRound;
        frontLeft.isRound = isRound;
        backLeft.isRound = isRound;

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        midRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
