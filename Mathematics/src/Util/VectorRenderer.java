package Util;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class VectorRenderer {
	private Vector vector;
	
	private int x, y;
	private int endX, endY;
	
	public VectorRenderer(Vector vector) {
		this.vector = vector;
	}
	
	public void renderArrow(Graphics2D g2d, int maxLength, double maxMagnitude) {
		g2d.setStroke(new BasicStroke(2));
		
		calculateEndX(maxLength, maxMagnitude);
		calculateEndY(maxLength, maxMagnitude);
		
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
		this.endX = (int) (x + (Math.cos(vector.getAngle()) * (maxLength * (vector.getMagnitude() / maxMagnitude)))); 
	}
	private void calculateEndY(int maxLength, double maxMagnitude) { 
		this.endY = (int) (y - (Math.sin(vector.getAngle()) * (maxLength * (vector.getMagnitude() / maxMagnitude)))); 
	}
	
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }

	public int getEndX() { return endX; }
	public int getEndY() { return endY; }
}
