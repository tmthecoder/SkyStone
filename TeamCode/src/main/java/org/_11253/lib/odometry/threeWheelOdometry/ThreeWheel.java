//package org._11253.lib.odometry.threeWheelOdometry;
//
//import com.acmerobotics.roadrunner.geometry.Pose2d;
//import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
//import org._11253.lib.odometry.Odometry;
//import org._11253.lib.robot.phys.components.Motor;
//import org._11253.lib.utils.math.Math;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Objects;
//
///**
// * (Hopefully) functional odometry which does cool stuff, like
// * odometry things and stuff.
// * <p>
// * <a href="https://drive.google.com/file/d/1G6-VJjLn1lfbt6sMBetEOPQ2eBSudIti/view"></a>
// * Last timestamp: 51:18
// * </p>
// */
//public class ThreeWheel extends ThreeTrackingWheelLocalizer implements Odometry {
//    private static ThreeWheel internalInstance;
//
//    /**
//     * A HashMap of all of our motors. You can access these by using
//     * the getMotor() function and one of the enumerated wheels.
//     */
//    final HashMap<ThreeWheels.wheels, Motor> motors;
//
//    /**
//     * Another HashMap, also of our motors.
//     * <p>
//     * Rather than using our cool (very cool, indeed, the coolest, even)
//     * and swaggy custom motors, these are FTC's default motors. In
//     * addition to being very lame, uncool, and not very swaggy (sadly),
//     * these motors give you a much more direct access to the position
//     * of the motor's encoder. You don't need it, but RoadRunner
//     * (apparently) does, and I don't feel like re-writing an entire 2d
//     * motion planning library.
//     * </p>
//     */
//    final HashMap<ThreeWheels.wheels, DcMotor> dcMotors;
//
//    /**
//     * The radius of the odometry wheels.
//     * <p>
//     * All three wheels are the same size, so this should
//     * remain fairly consistent.
//     * Holy fucking shit, my balls itch.
//     * </p>
//     */
//    final double wheelRadius = 1.25;
//
//    /**
//     * CPR stands for "Counts Per Rotation."
//     * <p>
//     * Yep. Swag. That's all you need to know.
//     * </p>
//     */
//    final double cpr = 360;
//
//    /**
//     * A measurement used to convert "ticks" (whatever those are)
//     * into inches (a much more common measurement).
//     * <p>
//     * I'm assuming that, like any reasonable person, you'll
//     * probably prefer to use the function literally RIGHT
//     * BELOW this, but I wouldn't really know. You do you
//     * lovie.
//     * </p>
//     */
//    final double ticksToInch = 2 * Math.PI * wheelRadius / cpr;
//    public Pose2d currentPose = new Pose2d();
//    private ThreeWheels wheels;
//
//    /**
//     * Constructor! Yay! These are the positions of each of the three wheels used here.
//     */
//    public ThreeWheel(ThreeWheels wheels) {
//        super(wheels.getMotorArray());
//        motors = wheels.getMotorMap();
//        dcMotors = wheels.getDcMotorMap();
//        this.wheels = wheels;
//    }
//
//    public static ThreeWheel getInstance() {
//        if (internalInstance == null) {
//            ThreeWheels wheels = new ThreeWheels();
//            wheels.init();
//            internalInstance = new ThreeWheel(wheels);
//        }
//        return internalInstance;
//    }
//
//    private DcMotor getMotor(ThreeWheels.wheels wheel) {
//        return dcMotors.get(wheel);
//    }
//
//    private double getPositionTicks(ThreeWheels.wheels wheel) {
//        return Objects.requireNonNull(getMotor(wheel)).getCurrentPosition();
//    }
//
//    private double getPositionInches(ThreeWheels.wheels wheel) {
//        return getInches(getPositionTicks(wheel));
//    }
//
//    /**
//     * Get a measurement (in inches) from a measurement in ticks.
//     *
//     * @param ticks a tick count
//     * @return inches
//     */
//    public double getInches(double ticks) {
//        return ticks * ticksToInch;
//    }
//
//    public ThreeWheelPositions getOdometryWheelPositions() {
//        return new ThreeWheelPositions(getWheelPositions());
//    }
//
//    @NotNull
//    @Override
//    public List<Double> getWheelPositions() {
//        return Arrays.asList(
//                getPositionInches(ThreeWheels.wheels.LEFT),
//                getPositionInches(ThreeWheels.wheels.RIGHT),
//                getPositionInches(ThreeWheels.wheels.BACK)
//        );
//    }
//
//    public void updateOdometry() {
//        this.update();
//        currentPose = this.getPoseEstimate();
//    }
//
//    @Override
//    public Pose2d getPose() {
//        return getPoseEstimate();
//    }
//
//    @Override
//    public HeadingCoordinate<Double> getPosition() {
//        return new HeadingCoordinate<Double>(
//                getPose().getX(),
//                getPose().getY(),
//                getPose().getHeading()
//        );
//    }
//}
