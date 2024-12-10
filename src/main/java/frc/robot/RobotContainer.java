package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.SwerveConstants.OIConstants;
import frc.robot.commands.velocityshoot;
import frc.robot.commands.LimelightCommands.AutoAlign;
import frc.robot.subsystems.DriveSubsystem;

//8054 <3
public class RobotContainer {
        public final static DriveSubsystem DRIVE_SUBSYSTEM = new DriveSubsystem();
        public final static frc.robot.subsystems.Shooter Shooter = new frc.robot.subsystems.Shooter();
        public SendableChooser<Integer> VelocitySelect = new SendableChooser<Integer>();

        public static CommandXboxController m_DriverJoy = new CommandXboxController(OIConstants.kDriverControllerPort);
        public static CommandXboxController m_OperatorJoy = new CommandXboxController(
                        OIConstants.kOperatorControllerPort);

        public static SendableChooser<Command> auto_Chooser = new SendableChooser<>();

        boolean driverModeEnabled = false;


        public RobotContainer() {
                auto_Chooser = AutoBuilder.buildAutoChooser();
                SmartDashboard.putData(auto_Chooser);

                configureButtonBindings();

                DRIVE_SUBSYSTEM
                                .setDefaultCommand(
                                                new RunCommand(
                                                                () -> DRIVE_SUBSYSTEM
                                                                                .drive(
                                                                                                -MathUtil.applyDeadband(
                                                                                                                m_DriverJoy.getLeftY(),
                                                                                                                OIConstants.kDriveDeadband)
                                                                                                                / 1.00d,
                                                                                                -MathUtil.applyDeadband(
                                                                                                                m_DriverJoy.getLeftX(),
                                                                                                                OIConstants.kDriveDeadband)
                                                                                                                / 1.00d,
                                                                                                MathUtil.applyDeadband(
                                                                                                                m_DriverJoy.getRightX(),
                                                                                                                OIConstants.kDriveDeadband)
                                                                                                                / 1.00d,
                                                                                                true, true, true),
                                                                DRIVE_SUBSYSTEM));
        }

        private void configureButtonBindings() {

                // Shoot Ball, might need to change directions
                VelocitySelect.addOption("1.25% Speed", 75);
                VelocitySelect.addOption("2.5% Speed", 150);
                VelocitySelect.addOption("5% Speed", 300);
                VelocitySelect.addOption("8% Speed", 400);
                VelocitySelect.addOption("10% Speed", 600);
                VelocitySelect.addOption("15% Speed", 900);
                VelocitySelect.addOption("20% Speed", 1200);
                VelocitySelect.addOption("25% Speed", 1500);
                VelocitySelect.setDefaultOption("30% Speed", 1800);//larson 6_20_2024 changed addOption from setDefaultOption
                VelocitySelect.addOption("40% Speed", 2400);
                VelocitySelect.addOption("50% Speed", 3000);
                VelocitySelect.addOption("60% Speed", 3600);
                VelocitySelect.addOption("70% Speed", 4200);
                VelocitySelect.addOption("80% Speed", 4800);
                VelocitySelect.addOption("90% Speed", 5400);
                VelocitySelect.addOption("100% Speed", 6000); 

                m_DriverJoy.a().whileTrue(new velocityshoot(Shooter, () -> VelocitySelect.getSelected()));
                m_DriverJoy.start().whileTrue(new InstantCommand(() -> DriveSubsystem.resetGryo()));
                
              
                // Reset Odom, not working properly right now
                m_DriverJoy.start().whileTrue(new RunCommand(() -> DRIVE_SUBSYSTEM.zeroHeading(), DRIVE_SUBSYSTEM));
                SmartDashboard.putData("Select Speed", VelocitySelect);
        

        }

        private void setControllerRumbleOperator(double rumble) {
                m_OperatorJoy.getHID().setRumble(RumbleType.kBothRumble, rumble);
        }

        private void setControllerRumbleDriver(double rumble) {
                m_DriverJoy.getHID().setRumble(RumbleType.kBothRumble, rumble);
        }

        private Command generatePathOnFlyCommand() {
                return new AutoAlign(DRIVE_SUBSYSTEM, 0.175);
        }

        public Command getAutonomousCommand() {
                return auto_Chooser.getSelected();
        }
}
