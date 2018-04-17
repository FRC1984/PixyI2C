package org.usfirst.frc.team1984.robot;

//TODO: Fix targetPixy stuff; At Regional: Test with 2 Cubes; Either: Set up controls

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1984.lib.*;
import org.usfirst.frc.team1984.robot.Robot;

public class CubePixy {
	
//	public PixyI2C cubePixy;
//	Port port = Port.kOnboard;
//	public PixyPacket[] packet1 = new PixyPacket[7];
//	String print;
	public int targetXTurn = 50; // TODO: Initialize here, once good base value has been found
	public int targetXTarget = 50;
	private int cubeCurX;
	public GenericPID cube;
	
	public CubePixy() {
		
		Dashboard.putNumber("cubePixy Target X Turn: ", targetXTurn);
		Dashboard.putNumber("cubePixy Target X Target: ", targetXTarget);
		cube = new GenericPID();
		cube.setPID(.001, 0, 0);
		cube.setMaxSpeed(.5);
		cube.setMinSpeed(-.5);
	}
	
	public void testCubePixy() {
		for (int i = 0; i < Robot.packet1.length; i++) {
			Robot.packet1[i] = null;
		}
		for (int i = 1; i < 8; i++) {
			try {
				Robot.packet1[i - 1] = Robot.cubePixy.readPacket(i);
			} catch (PixyException e) {
			}
			if (Robot.packet1[i - 1] == null) {
				continue;
				}
			SmartDashboard.putNumber("cubePixy X Value: " + i, Robot.packet1[i - 1].X);
			SmartDashboard.putNumber("cubePixy Y Value: " + i, Robot.packet1[i - 1].Y);
			SmartDashboard.putNumber("cubePixy Width Value: " + i, Robot.packet1[i - 1].Width);
			SmartDashboard.putNumber("cubePixy Height Value: " + i, Robot.packet1[i - 1].Height);
			SmartDashboard.putString("cubePixy Error: " + i, "False");
		}
	}
	public void turnCubeI2C(int destination, double forward) {
		pidTurnCube(Dashboard.getNumber("cubePixy Target X Turn: ", 20), -Robot.controller.getLSY());
	}
	
	public PixyPacket[] getTarget() {
		PixyPacket[] blocks = Robot.cubePixy.readBlocks();
		if (blocks == null) {
			return null;
		}
		return blocks;
	}
	
	public int getTargetCubeX() {
		int targetX;
		PixyPacket[] packets = getTarget();
		if(packets[0] == null || packets[1] == null) {
			return (Integer) null;
		}
		if(packets[0].Y > packets[1].Y) {
			targetX = packets[0].X;
			return targetX;
		} else if(packets[1].Y > packets[0].Y) {
			targetX = packets[1].X;
			return targetX;
		} else {
			return (Integer) null;
		}
	}
	
	// To be called during teleop to move to the cube///////////////////////////////////
	public void pidTurnCube(double destination, double forward)
	{
		for (int i = 1; i < 8; i++) {
			try {
				Robot.packet1[i - 1] = Robot.cubePixy.readPacket(i);
			} catch (PixyException e) {
			}
			if (Robot.packet1[i - 1] == null) {
				continue;
				}
			cubeCurX = Robot.packet1[i-1].X; 
			Robot.driveTrain.turnCube(cube.setPIDVal(destination, cubeCurX),forward);
		}
		Robot.driveTrain.turnCube(cube.setPIDVal(destination, cubeCurX),forward);
	}
	
	public void pidTurnCubeTarget(double destination, double forward) {
		Robot.driveTrain.turnCube(cube.setPIDVal(destination, getTargetCubeX()),forward);
	}
}