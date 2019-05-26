package Util;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Vector {
	private VectorRenderer renderer;
	
	private double xComp, yComp;
	private double magnitude, angle;
	
	public  Vector() {
		this.renderer = new VectorRenderer(this);
	}
	
	public Vector(double xComp, double yComp) {
		this();
		this.xComp = xComp;
		this.yComp = yComp;
		calculateMagnitude();
		calculateAngle();
	}
	
	public void setComponents(double xComp, double yComp) {
		this.xComp = xComp;
		this.yComp = yComp;
		
		calculateMagnitude();
		calculateAngle();
	}
	
	public double getXComp() { return xComp; }
	public void setXComp(double xComp) {
		this.xComp = xComp;
		
		calculateMagnitude();
		calculateAngle();
	}
	
	public double getYComp() { return yComp; }
	public void setYComp(double yComp) {
		this.yComp = yComp;
		
		calculateMagnitude();
		calculateAngle();
	}
	
	public Vector add(Vector other) {
		return new Vector(this.xComp + other.xComp,
						  this.yComp + other.yComp);
	}
	
	public void setAngle(double angle) {
//		if(angle < 0) 
//			angle += 2.0 * Math.PI;
//		if(angle > 2.0 * Math.PI)
//			angle -= 2.0 * Math.PI;
		
		this.angle = angle;
		
		this.xComp = magnitude * Math.cos(angle);
		this.yComp = magnitude * Math.sin(angle);
	}
	
	public void setMagnitude(double magnitude) {
		this.magnitude = Math.abs(magnitude);
		this.xComp = magnitude * Math.cos(angle);
		this.yComp = magnitude * Math.sin(angle);
		
		if(magnitude < 0)
			setAngle(angle - Math.PI);
	}
	
	public double getMagnitude() { return magnitude; }
	private void calculateMagnitude() { this.magnitude = Math.sqrt(Math.pow(xComp, 2) + Math.pow(yComp, 2)); }
	
	public double getAngle() { return angle; }
	private void calculateAngle() { 
		this.angle = Math.atan2(yComp, xComp);
		
		if(this.angle < 0) 
			this.angle += 2.0 * Math.PI;
	}
	
	public VectorRenderer getRenderer() { return renderer; }
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Vector vector = new Vector(0, 0);
		
		class TestPanel extends JPanel {
			private static final long serialVersionUID = 1L;

			public TestPanel() {
				vector.getRenderer().setX(400);
				vector.getRenderer().setY(400);
			}
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				
//				g2d.fillRect(400, 400, 5, 5);
				
				vector.getRenderer().renderArrow(g2d, 100, 1);
			}
		}
		
		TestPanel testPanel = new TestPanel();
		
//		Thread thread = new Thread(() -> {
//			long start = System.currentTimeMillis();
//			double theta = 0;
//			vector.setMagnitude(2);
//			while(true) {
//				if(System.currentTimeMillis() - start >= 16) {
//					vector.setComponents(Math.cos(theta), Math.sin(theta));
					vector.setAngle(2);
					vector.setMagnitude(0.4161468365471424);
					testPanel.repaint();
//					theta += .01;
//					
//					start = System.currentTimeMillis();
//				}
//			}
//		});
		
//		thread.start();
		
		frame.add(testPanel);
		
		frame.setVisible(true);
	}
}
