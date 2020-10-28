package org.firstinspires.ftc.teamcode.testCode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org._11253.lib.drives.ShifterMeccanum;
import org._11253.lib.odometry.threeWheelOdometry.ThreeWheel;
import org._11253.lib.odometry.threeWheelOdometry.ThreeWheels;
import org._11253.lib.utils.telem.Telemetry;

@TeleOp(name = "Odometry Test", group = "default")
public class OdometryTest extends ShifterMeccanum {
    ThreeWheels odometryWheels;
    ThreeWheel threeWheel;

    public OdometryTest() {
        beforeStart.add(
                // Set up all of the odometry. This is at least a little bit important, especially given
                // the name of this op-mode is "Odometry Test." But oh well - what do I know?
                // Steps...
                // 1. Set "odometryWheels" to be an actual instance of odometryWheels. I didn't
                //    initialize it earlier on in the program because it would look more ugly up
                //    there at the top.
                // 2. Actually initialize the motors. As consistent with... well, every other op-mode
                //    in this entire codebase, physical components are initialized right before start.
                // 3. Initialize the odometry. There are no physical components in the odometry class,
                //    so we don't have to call any init function for this.
                // 4. Do cool stuff with the odometry!
                new Runnable() {
                    @Override
                    public void run() {
                        odometryWheels = new ThreeWheels();
                        odometryWheels.init();
                        threeWheel = new ThreeWheel(odometryWheels);
                    }
                }
        );
        onStart.add();
        onStartRun.add();
        run.add(
                new Runnable() {
                    @Override
                    public void run() {
                        Telemetry.addData(
                                "C_POSE",
                                "Odometry Pose",
                                ": ",
                                threeWheel.currentPose.toString()
                        );
                    }
                }
        );
        onFinishRun.add();
        onFinish.add();
    }

    public void resetPosition() {
        threeWheel.currentPose = new Pose2d(0, 0, 0);
    }

    public Pose2d getPosition() {
        return threeWheel.getPoseEstimate();
    }
}
