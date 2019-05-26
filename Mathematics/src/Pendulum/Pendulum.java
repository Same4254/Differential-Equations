package Pendulum;

import javax.swing.JFrame;

import Util.Vector;

public class Pendulum {
	private Vector wireVector, accelerationVector, velocityVector;
	private double gravity, airResistance;
	private double angularAcceleration, angularVelocity;
	private double angle;
	
	private double initialAngle, initialVelocity;
	
	public Pendulum() {
		wireVector = new Vector(1, 0);
//		wireVector.setAngle(2 * Math.PI);
		
		accelerationVector = new Vector(0, 0);
		velocityVector = new Vector(0, 0);
	}
	
	public void update(double timeStep) {
		//Angle of wire relative to y-axis
		/*
		 *   \\\\\\\\\\\\\\\\
		 *         |\
		 *         | \
		 *         |  \
		 *         |   \
		 *         | O  \
		 *         |     \
		 * 
		 * To the right is positive, and to the left is negative
		 * Unit: Radians
		 */
		
		angle = wireVector.getAngle() - ((3.0 * Math.PI) / 2.0);

//		System.out.println(((wireVector.getAngle() % (2 * Math.PI)) * (2.0 * Math.PI)));
//		System.out.println(((wireVector.getAngle() % (2 * Math.PI))));
		
		//Update 
//		accelerationVector.setMagnitude(gravity * Math.sin(angle));
//		if((wireVector.getAngle() % (2 * Math.PI)) - (((3.0 * Math.PI) / 2.0)) > 0)
//			accelerationVector.setAngle(wireVector.getAngle() - (Math.PI / 2.0));
//		else
//			accelerationVector.setAngle(angle);
		
		
		angularAcceleration = -(airResistance * angularVelocity) -(gravity * Math.sin(angle));
		accelerationVector.setMagnitude(angularAcceleration);
		if(angularAcceleration < 0)
			accelerationVector.setAngle(wireVector.getAngle() - (Math.PI / 2.0));
		else
			accelerationVector.setAngle(angle);
		
		angularVelocity += angularAcceleration * timeStep;
		wireVector.setAngle(wireVector.getAngle() + (angularVelocity * timeStep));
		
		velocityVector.setMagnitude(angularVelocity);
		if(angularVelocity < 0)
			velocityVector.setAngle(wireVector.getAngle() - (Math.PI / 2.0));
		else
			velocityVector.setAngle(angle);
		
//		System.out.println("------");
//		System.out.println(angularAcceleration);
//		System.out.println(angularVelocity);
//		System.out.println(angle);
	}
	
	public void reset() {
		wireVector.setAngle(((3.0 * Math.PI) / 2.0) + initialAngle);
		angularVelocity = initialVelocity;
	}
	
	public double getInitialAngle() { return initialAngle; }
	public void setInitialAngle(double initialAngle) { this.initialAngle = initialAngle; }

	public double getInitialVelocity() { return initialVelocity; }
	public void setInitialVelocity(double initialVelocity) { this.initialVelocity = initialVelocity; }

	public double getAngle() { return angle; }
	
	public double getGravity() { return gravity; }
	public void setGravity(double gravity) { this.gravity = gravity; }

	public double getAirResistence() { return airResistance; }
	public void setAirResistance(double airResistence) { this.airResistance = airResistence; }
	
	public Vector getWireVector() { return wireVector; }
	public Vector getAccelerationVector() { return accelerationVector; }
	public Vector getVelocityVector() { return velocityVector; }
	
	public double getAngularAcceleration() { return angularAcceleration; }
	public double getAngularVelocity() { return angularVelocity; }
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Pendulum pendulum = new Pendulum();
		pendulum.setGravity(5);
		pendulum.setInitialVelocity(-.5);
		pendulum.setAirResistance(0);
		pendulum.setInitialAngle(Math.PI - .1);
		pendulum.reset();
		
		PendulumRenderer renderer = new PendulumRenderer(pendulum);
				
		frame.add(renderer);
		
		frame.setVisible(true);
		
		long lastTime = System.currentTimeMillis();
		while(true) {
			if(System.currentTimeMillis() > lastTime + 16) {
				pendulum.update((System.currentTimeMillis() - lastTime) / 1000.0);
				renderer.repaint();
				
				lastTime = System.currentTimeMillis();
			}
		}
	}
}