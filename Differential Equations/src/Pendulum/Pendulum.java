package Pendulum;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Util.Vector;

public class Pendulum {
	private Vector wireVector, accelerationVector, velocityVector;
	private double gravity, airRessistence;
	private double angularAcceleration, angularVelocity;
	
	public Pendulum() {
		wireVector = new Vector(1, 0);
		accelerationVector = new Vector(0, 0);
		velocityVector = new Vector(0, 0);
		
		gravity = 2;
		airRessistence = 0;
		
		angularVelocity = 0;
	}
	
	public void update(double timeStep) {
		//Angle of wire relative to y-axis
		/*
		 *   \\\\\\\\\\\\\\\\
		 *         |\
		 *         | \
		 *         |  \
		 *         |   \
		 *         | O \
		 *         |      \
		 * 
		 * To the right is positive, and to the left is negative
		 * Unit: Radians
		 */
		double angle = wireVector.getAngle() - ((3.0 * Math.PI) / 2.0) ;
		
		//Update 
		accelerationVector.setMagnitude(gravity * Math.sin(angle));
		if(angle > 0)
			accelerationVector.setAngle(wireVector.getAngle() - (Math.PI / 2.0));
		else
			accelerationVector.setAngle(angle);
		
		angularAcceleration = -(airRessistence * angularVelocity) -(gravity * Math.sin(angle));
		angularVelocity += angularAcceleration * timeStep;
		wireVector.setAngle(wireVector.getAngle() + (angularVelocity * timeStep));
		
		velocityVector.setMagnitude(angularVelocity);
		if(angularVelocity < 0)
			velocityVector.setAngle(wireVector.getAngle() - (Math.PI / 2.0));
		else
			velocityVector.setAngle(angle);
	}
	
	public double getGravity() { return gravity; }
	public void setGravity(double gravity) { this.gravity = gravity; }

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
		
		class TestPanel extends JPanel {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				Graphics2D g2d = (Graphics2D) g;
				
				//Wire
				Vector wireVector = pendulum.getWireVector();
				
				wireVector.getRenderer().setX(220);
				wireVector.getRenderer().setY(200);
				wireVector.getRenderer().renderLine(g2d, 200, 1);
				
				//Mass
				g.setColor(Color.GREEN);
				int ovalLength = 40;
				g.fillOval(pendulum.getWireVector().getRenderer().getEndX() - (ovalLength / 2), 
						         pendulum.getWireVector().getRenderer().getEndY() - (ovalLength / 2), 
						         ovalLength, ovalLength);
				
				//Acceleration Vector
				g.setColor(Color.RED);
				Vector accelerationVector = pendulum.getAccelerationVector();
				accelerationVector.getRenderer().setX(wireVector.getRenderer().getEndX());
				accelerationVector.getRenderer().setY(wireVector.getRenderer().getEndY());
				accelerationVector.getRenderer().renderArrow(g2d, 50, pendulum.getGravity());
				
				//Velocity Vector
				g.setColor(Color.BLUE);
				Vector veclocityVector = pendulum.getVelocityVector();
				veclocityVector.getRenderer().setX(wireVector.getRenderer().getEndX());
				veclocityVector.getRenderer().setY(wireVector.getRenderer().getEndY());
				veclocityVector.getRenderer().renderArrow(g2d, 50, pendulum.getGravity());
			}
		}
		
		TestPanel testPanel = new TestPanel();
		frame.add(testPanel);
		
		frame.setVisible(true);
		
		long lastTime = System.currentTimeMillis();
		while(true) {
			if(System.currentTimeMillis() > lastTime + 16) {
				pendulum.update((System.currentTimeMillis() - lastTime) / 1000.0);
				testPanel.repaint();
				
				lastTime = System.currentTimeMillis();
			}
		}
	}
}