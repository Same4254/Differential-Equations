package Util;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;

public class VectorRenderer {
	private Vector vector;
	
	private int x, y;
	private int endX, endY;
	
	public VectorRenderer(Vector vector) {
		this.vector = vector;
	}
	
	public void renderArrow(Graphics2D g2d, int maxLength, double maxMagnitude) {
		g2d.setStroke(new BasicStroke(2));
		
		maxMagnitude = Math.abs(maxMagnitude);
		
		calculateEndX(maxLength, maxMagnitude);
		calculateEndY(maxLength, maxMagnitude);
		
		int renderMagnitude = (int) Math.sqrt(Math.pow(endX - x, 2) + Math.pow(endY - y, 2));
		
		g2d.drawLine(x, y, endX, endY);
		g2d.drawLine(endX, endY, (int) (endX - ((renderMagnitude * .2) * Math.cos(vector.getAngle() - (Math.PI / 4)))), 
				 				 (int) (endY + ((renderMagnitude * .2) * Math.sin(vector.getAngle() - (Math.PI / 4)))));
		
		g2d.drawLine(endX, endY, (int) (endX - ((renderMagnitude * .2) * Math.cos(vector.getAngle() + (Math.PI / 4)))), 
								 (int) (endY + ((renderMagnitude * .2) * Math.sin(vector.getAngle() + (Math.PI / 4)))));
	}
	
	public void renderArrow(Graphics2D g2d, int startX, int startY, int endX, int endY) {
		g2d.setStroke(new BasicStroke(2));

		this.x = startX;
		this.y = startY;
		this.endX = endX;
		this.endY = endY;
		
		int renderMagnitude = (int) Math.sqrt(Math.pow(endX - x, 2) + Math.pow(endY - y, 2));
		
		g2d.drawLine(x, y, endX, endY);
		g2d.drawLine(endX, endY, (int) (endX - ((renderMagnitude * .2) * Math.cos(vector.getAngle() - (Math.PI / 4)))), 
				 				 (int) (endY + ((renderMagnitude * .2) * Math.sin(vector.getAngle() - (Math.PI / 4)))));
		
		g2d.drawLine(endX, endY, (int) (endX - ((renderMagnitude * .2) * Math.cos(vector.getAngle() + (Math.PI / 4)))), 
								 (int) (endY + ((renderMagnitude * .2) * Math.sin(vector.getAngle() + (Math.PI / 4)))));
	}
	
	public void renderLine(Graphics2D g2d, int maxLength, double maxMagnitude) {
		g2d.setStroke(new BasicStroke(2));
		
		calculateEndX(maxLength, maxMagnitude);
		calculateEndY(maxLength, maxMagnitude);
		
		g2d.drawLine(x, y, endX, endY);
	}

	private void calculateEndX(int maxLength, double maxMagnitude) { 
		this.endX = (int) (x + (Math.cos(vector.getAngle()) * (maxLength * Math.min((vector.getMagnitude() / maxMagnitude), 1)))); 
	}
	private void calculateEndY(int maxLength, double maxMagnitude) { 
		this.endY = (int) (y - (Math.sin(vector.getAngle()) * (maxLength * Math.min((vector.getMagnitude() / maxMagnitude), 1)))); 
	}
	
	public void setLocation(Point point) { setLocation(point.x, point.y); }
	
	public void setLocation(int x, int y) {
		setX(x);
		setY(y);
	}
	
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }

	public int getEndX() { return endX; }
	public int getEndY() { return endY; }
}
