package Pendulum;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Util.Vector;

public class PendulumRenderer extends JPanel {
	private static final long serialVersionUID = 1L;

	private Pendulum pendulum;
	
	public PendulumRenderer(Pendulum pendulum) {
		this.pendulum = pendulum;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		//Wire
		Vector wireVector = pendulum.getWireVector();
		
		wireVector.getRenderer().setX(getWidth() / 2);
		wireVector.getRenderer().setY(getHeight() / 2);
		wireVector.getRenderer().renderLine(g2d, Math.min((getWidth() / 2) - 10, (getHeight() / 2) - 10), 1);
		
		//Mass
		g.setColor(Color.GREEN);
		int ovalLength = 20;
		g.fillOval(pendulum.getWireVector().getRenderer().getEndX() - (ovalLength / 2), 
				         pendulum.getWireVector().getRenderer().getEndY() - (ovalLength / 2), 
				         ovalLength, ovalLength);
		
		//Acceleration Vector
		g.setColor(Color.RED);
		Vector accelerationVector = pendulum.getAccelerationVector();
		accelerationVector.getRenderer().setX(wireVector.getRenderer().getEndX());
		accelerationVector.getRenderer().setY(wireVector.getRenderer().getEndY());
		accelerationVector.getRenderer().renderArrow(g2d, 2 * ovalLength, pendulum.getGravity());
		
		//Velocity Vector
		g.setColor(Color.BLUE);
		Vector veclocityVector = pendulum.getVelocityVector();
		veclocityVector.getRenderer().setX(wireVector.getRenderer().getEndX());
		veclocityVector.getRenderer().setY(wireVector.getRenderer().getEndY());
		veclocityVector.getRenderer().renderArrow(g2d, 2 * ovalLength, pendulum.getGravity());
		
		g.drawString("\u03BC = " + pendulum.getAirResistence(), 10, 15);
		g.drawString("g = " + pendulum.getGravity(), 10, 30);
		g.drawString("\u03B8 = " + pendulum.getAngle(), 10, 45);
	}
}
