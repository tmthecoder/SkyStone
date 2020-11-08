/****
 * Made by Tejas Mehta
 * Made on Wednesday, November 04, 2020
 * File Name: LocalCoordinate
 * Package: com.tejasmehta.OdometryCore.com.tejasmehta.OdometryCore.math*/
package org.firstinspires.ftc.teamcode.testCode.odometryCoreTest.math;

public class CartesianCoordinate {
    private final double x;
    private final double y;

    public static CartesianCoordinate fromPolar(PolarCoordinate polar) {
        double r = polar.getR();
        double theta = polar.getTheta();
        double x = r * Math.cos(theta);
        double y = r * Math.sin(theta);
        return new CartesianCoordinate(x, y);
    }

    public CartesianCoordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
