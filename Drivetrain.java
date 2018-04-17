package org.usfirst.frc.team1984.robot;

import org.usfirst.frc.team1984.lib.Dashboard;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain {
	private Victor leftM, rightM, inL, inR;
	private SpeedControllerGroup left, right;
	private DifferentialDrive drive;
	private Solenoid muhdick, muhdick2;
	int intakeMode;
	
	public Drivetrain() {
		leftM = new Victor(0);
		rightM = new Victor(2);
		inR = new Victor(3);
		inL = new Victor(1);
		
		left = new SpeedControllerGroup(leftM, inL);
		right = new SpeedControllerGroup(rightM, inR);
		
		drive = new DifferentialDrive(left, right);
		muhdick = new Solenoid(0);
		muhdick2 = new Solenoid(1);
	}
	
	public void run() {
		
/*		if(Robot.controller.toggleA() && !Robot.controller.getLBBtn() && !forced) {
			forced = false;
			clamp();
		} else if(!Robot.controller.toggleA() && !forced) {
			forced = false;
			opem();
		}
		if(Robot.controller.getLBBtn()) {
			forced = true;
			unClamp();*/
//		}
		
/*		if(Robot.controller.getLBBtn()) {
			intakeMode = 0; //closed
		}
		if(Robot.controller.getRBBtn()) {
			intakeMode = 1; //forced open
		}
		if(Robot.controller.getABtn()) {
			intakeMode = 2; //floppy
		}		
		
		switch(intakeMode) {
			case 0: 
				clamp();
				break;
			case 1:
				opem();
				break;
			case 2:
				flop();
				break;
			default:
				break;
		}*/
		
		if(Robot.controller.getYBtn()) {
			Robot.pixyClass.pidTurnCube(Robot.pixyClass.targetXTurn,-Robot.controller.getLSY());
		}else if(Robot.controller.getBBtn()) {
			Robot.pixyClass.pidTurnCubeTarget(Dashboard.getNumber("cubePixy Target X Target: ", Robot.pixyClass.targetXTarget), -Robot.controller.getLSY());
		} else {
		this.drive.arcadeDrive(-Robot.controller.getLSY(), Robot.controller.getRSX(), false);
		}
		
//		if(Robot.controller.getLT() > 0.1) {
/*			inR.set(Robot.controller.getLT());
			inL.set(Robot.controller.getLT());
		} else if(Robot.controller.getRT() > 0.1) {
			inR.set(-Robot.controller.getRT());
			inL.set(-Robot.controller.getRT());
		} else {
			inR.stopMotor();
			inL.stopMotor();
		}*/
		

		//drive.arcadeDrive(Robot.controller.getLSY(), Robot.controller.getRSX(), false);

	}

	public void turnCube(double speed, double forward) {
		System.out.println("turnCube called");
		drive.arcadeDrive(-speed, forward, false);
	}
	
/*	public void clamp() {
		muhdick.set(true);
		muhdick2.set(false);
	}
	
	public void flop() {
		muhdick.set(false);
		muhdick2.set(false);
	}
	
	public void opem() {
		muhdick.set(false);
		muhdick2.set(true);
	*/}

