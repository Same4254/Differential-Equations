package Pendulum;

import Util.Vector;

public class DoublePendulum {
	private double angle1, angle2;
	private double angularVelocity1, angularVelocity2;
	private double angularAcceleration1, angularAcceleration2;
	private double mass1, mass2;
	private double length1, length2;
	
	private double gravity, airResistance;
	
	private Vector wireVector1, wireVector2;
//	private Vector velocityVector1, velocityVector2;
//	private Vector accelerationVector1, accelerationVector2;
	
	public DoublePendulum() {
		wireVector1 = new Vector(1, 0);
//		wireVector1.setAngle(Math.toRadians(300));
		
		wireVector2 = new Vector(1, 0);
//		wireVector2.setAngle(Math.toRadians(200));
		
//		velocityVector1 = new Vector(0, 0);
//		velocityVector2 = new Vector(0, 0);
//		
//		accelerationVector1 = new Vector(0, 0);
//		accelerationVector2 = new Vector(0, 0);
		
		length1 = wireVector1.getMagnitude();
		length2 = wireVector2.getMagnitude();
		
		mass1 = 2;
		mass2 = 2;
		
		gravity = 9.8;
		
		airResistance = .1;
	}
	
	public void update(double timeStep) {
		double wireAngle1 = wireVector1.getAngle() % (2.0 * Math.PI);
		if(wireAngle1 < 0)
			wireAngle1 += 2.0 * Math.PI;
		
		double wireAngle2 = wireVector2.getAngle() % (2.0 * Math.PI);
		if(wireAngle2 < 0)
			wireAngle2 += 2.0 * Math.PI;
		
		angle1 = wireAngle1 - ((3.0 * Math.PI) / 2.0);
		angle2 = wireAngle2 - ((3.0 * Math.PI) / 2.0);
		
		double num1 = -gravity * (2.0 * mass1 + mass2) * Math.sin(angle1);
		double num2 = -mass2 * gravity * Math.sin(angle1 - (2 * angle2));
		double num3 = -2 * Math.sin(angle1 - angle2) * mass2;
		double num4 = (angularVelocity2 * angularVelocity2 * length2) + (angularVelocity1 * angularVelocity1 * length1 * Math.cos(angle1 - angle2));
		double den = length1 * ((2 * mass1) + mass2 - (mass2 * Math.cos((2 * angle1) - (2 * angle2))));
		angularAcceleration1 = ((num1 + num2 + (num3 * num4)) / den) - (airResistance * angularVelocity1);
		
		num1 = 2.0 * Math.sin(angle1 - angle2);
		num2 = (angularVelocity1 * angularVelocity1 * length1 * (mass1 + mass2));
		num3 = gravity * (mass1 + mass2) * Math.cos(angle1);
		num4 = angularVelocity2 * angularVelocity2 * length2 * mass2 * Math.cos(angle1 - angle2);
		den = length2 * ((2 * mass1) + mass2 - (mass2 * Math.cos((2 * angle1) - (2 * angle2))));
		angularAcceleration2 = ((num1 * (num2 + num3 + num4)) / den) + (airResistance * angularVelocity2);
		
		angularVelocity1 += angularAcceleration1 * timeStep;
		angularVelocity2 -= angularAcceleration2 * timeStep;
		
		wireVector1.setAngle(wireVector1.getAngle() + ((angularVelocity1 * timeStep) + (.5 * angularAcceleration1 * Math.pow(timeStep, 2))));
		wireVector2.setAngle(wireVector2.getAngle() - ((angularVelocity2 * timeStep) + (.5 * angularAcceleration2 * Math.pow(timeStep, 2))));
	}

	public double getAngle1() { return angle1; }
	public double getAngle2() { return angle2; }
	public double getAngularVelocity1() { return angularVelocity1; }
	public double getAngularVelocity2() { return angularVelocity2; }
	public double getAngularAcceleration1() { return angularAcceleration1; }
	public double getAngularAcceleration2() { return angularAcceleration2; }

	public double getMass1() { return mass1; }
	public double getMass2() { return mass2; }
	public double getLength1() { return length1; }
	public double getLength2() { return length2; }
	public double getGravity() { return gravity; }
	public double getAirResistence() { return airResistance; }
	
	public void setMass1(double mass1) { this.mass1 = mass1; }
	public void setMass2(double mass2) { this.mass2 = mass2; }

	public void setGravity(double gravity) { this.gravity = gravity; }
	public void setAirResistance(double airResistance) { this.airResistance = airResistance; }

	public void setInitialAngle1(double angle1) { wireVector1.setAngle(((3.0 * Math.PI) / 2.0) + angle1); }
	public void setInitialAngle2(double angle2) { wireVector2.setAngle(((3.0 * Math.PI) / 2.0) + angle2); }
	
	public void setInitialVelocity1(double initialAngularVelocity1) { angularVelocity1 = initialAngularVelocity1; }
	public void setInitialVelocity2(double initialAngularVelocity2) { angularVelocity2 = initialAngularVelocity2; }
	
	public Vector getWireVector1() { return wireVector1; }
	public Vector getWireVector2() { return wireVector2; }
} 
