// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
 
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
 
public class velocityshoot extends Command {
  /** Creates a new RunIntake. */

  Shooter m_Shooter;
  Supplier<Integer> m_speed;

  public velocityshoot(Shooter Shooter, Supplier<Integer> Speed) {
    
    m_Shooter = Shooter;
    m_speed = Speed;

    addRequirements(Shooter);
     
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("/Shooter/ExpectedSpeed", m_speed.get());
 
    m_Shooter.RunShooter(m_speed.get());
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
 
    m_Shooter.RunShooter(0);
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return false;

  }
}

