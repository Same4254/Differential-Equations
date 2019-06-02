package Pendulum;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Util.Vector;

public class DoublePendulumRenderer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private DoublePendulum doublePendulum;
	
	public DoublePendulumRenderer(DoublePendulum doublePendulum) {
		this.doublePendulum = doublePendulum; 
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		//Wire
		Vector wireVector1 = doublePendulum.getWireVector1();
		
		wireVector1.getRenderer().setX(getWidth() / 2);
		wireVector1.getRenderer().setY(getHeight() / 2);
		wireVector1.getRenderer().renderLine(g2d, Math.min((getWidth() / 4) - 10, (getHeight() / 4) - 10), 1);
		
		Vector wireVector2 = doublePendulum.getWireVector2();
		
		wireVector2.getRenderer().setX(wireVector1.getRenderer().getEndX());
		wireVector2.getRenderer().setY(wireVector1.getRenderer().getEndY());
		wireVector2.getRenderer().renderLine(g2d, Math.min((getWidth() / 4) - 10, (getHeight() / 4) - 10), 1);
		
		//Mass
		g.setColor(Color.GREEN);
		int ovalLength = 20;
		g.fillOval(doublePendulum.getWireVector1().getRenderer().getEndX() - (ovalLength / 2), 
					doublePendulum.getWireVector1().getRenderer().getEndY() - (ovalLength / 2), 
				         ovalLength, ovalLength);
		
		g.setColor(Color.RED);
		g.fillOval(doublePendulum.getWireVector2().getRenderer().getEndX() - (ovalLength / 2), 
					doublePendulum.getWireVector2().getRenderer().getEndY() - (ovalLength / 2), 
				         ovalLength, ovalLength);
		
//		
//		//Acceleration Vector
//		g.setColor(Color.RED);
//		Vector accelerationVector = pendulum.getAccelerationVector();
//		accelerationVector.getRenderer().setX(wireVector.getRenderer().getEndX());
//		accelerationVector.getRenderer().setY(wireVector.getRenderer().getEndY());
//		accelerationVector.getRenderer().renderArrow(g2d, 2 * ovalLength, pendulum.getGravity());
//		
//		//Velocity Vector
//		g.setColor(Color.BLUE);
//		Vector veclocityVector = pendulum.getVelocityVector();
//		veclocityVector.getRenderer().setX(wireVector.getRenderer().getEndX());
//		veclocityVector.getRenderer().setY(wireVector.getRenderer().getEndY());
//		veclocityVector.getRenderer().renderArrow(g2d, 2 * ovalLength, pendulum.getGravity());
//		
//		g.drawString("\u03BC = " + pendulum.getAirResistence(), 10, 15);
//		g.drawString("g = " + pendulum.getGravity(), 10, 30);
//		g.drawString("\u03B8 = " + pendulum.getAngle(), 10, 45);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DoublePendulum pendulum = new DoublePendulum();
		DoublePendulumRenderer renderer = new DoublePendulumRenderer(pendulum);
				
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
