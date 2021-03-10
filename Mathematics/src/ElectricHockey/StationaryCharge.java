package ElectricHockey;

import java.awt.Color;
import java.awt.Graphics2D;

public class StationaryCharge {
	private int x, y;
	private double charge;
	
	public StationaryCharge() { }
	
	public StationaryCharge(int x, int y, double charge) {
		this.x = x;
		this.y = y;
		this.charge = charge;
	}
	
	public void render(Graphics2D g2d) {
		if(charge == 0)
			g2d.setColor(Color.GRAY);
		else if(charge > 0)
			g2d.setColor(Color.BLUE);
		else 
			g2d.setColor(Color.RED);
		
		g2d.fillOval(x - Constants.CHARGE_RADIUS, y - Constants.CHARGE_RADIUS, 2 * Constants.CHARGE_RADIUS, 2 * Constants.CHARGE_RADIUS);
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	
	public double getCharge() { return charge; }
	public void setCharge(double charge) { this.charge = charge; }
}
