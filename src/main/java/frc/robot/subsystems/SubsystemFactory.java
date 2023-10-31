package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotId;

public class SubsystemFactory {

    public static <T> T get(Class<T> clazz, Object... args) {
        RobotId robotId = Robot.getRobotId();
        try {
            if (robotId.getSubsystems().contains(clazz) && RobotBase.isReal()) {
                Class<? extends SubsystemBase> impl = clazz.getAnnotation(SubsystemImpl.class).value();
                return (T) impl.getDeclaredConstructor().newInstance(args);
            }
            return clazz.getDeclaredConstructor().newInstance(args);
        } catch (Exception e) {
            DriverStation.reportError("Could not create subsystem " + clazz.getSimpleName(), e.getStackTrace());
        }
        return null;
    }

}
