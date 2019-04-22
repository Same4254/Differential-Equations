package Pendulum;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Util.Vector;

public class Pendulum {
	private Vector vector;
	private double gravity, airRessistence;
	private double angularAcceleration, angularVelocity;
	
	public Pendulum() {
		vector = new Vector(1, 0);
		gravity = 10;
		airRessistence = 1;
		
		angularVelocity = 20;
	}
	
	public void update(double timeStep) {
		double angle = ((3.0 * Math.PI) / 2.0) - vector.getAngle();
		
		angularAcceleration = -(airRessistence * angularVelocity) -(gravity * Math.sin(angle));
		angularVelocity += angularAcceleration * timeStep;
		vector.setAngle(vector.getAngle() - (angularVelocity * timeStep));
	}
	
	public double getGravity() { return gravity; }
	public void setGravity(double gravity) { this.gravity = gravity; }

	public Vector getVector() { return vector; }
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
				
				pendulum.getVector().getRenderer().setX(200);
				pendulum.getVector().getRenderer().setY(200);
				pendulum.getVector().getRenderer().render(g2d, 200, 1);
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