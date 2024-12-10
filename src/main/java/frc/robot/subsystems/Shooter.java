// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
 
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  
  TalonFX TopShooterMotor;
  TalonFX BottomShootMotor;

  VelocityVoltage VelocityVolts;

  public DigitalInput input;

  public Shooter() {

    TopShooterMotor = new TalonFX(Constants.TopFrontID);
    BottomShootMotor = new TalonFX(Constants.TopBottomID);

    TopShooterMotor.setInverted(true);
    BottomShootMotor.setInverted(true);

    VelocityVolts = new VelocityVoltage(0);

    input = new DigitalInput(0);

    var Velocityconfig = new Slot0Configs();
    Velocityconfig.kV = 0.12;
    Velocityconfig.kP = 0.11;
    Velocityconfig.kI = 0.48;
    Velocityconfig.kD = 0.01;

    TopShooterMotor.getConfigurator().apply(Velocityconfig, 0.050);
    BottomShootMotor.getConfigurator().apply(Velocityconfig, 0.050);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void RunShooter(double Velocity) {
 
    // Inside
    TopShooterMotor.setControl(VelocityVolts.withVelocity(Velocity/60));
    BottomShootMotor.setControl(VelocityVolts.withVelocity(Velocity/60));
  }
}
