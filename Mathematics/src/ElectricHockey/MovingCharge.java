package ElectricHockey;

import java.awt.Color;
import java.awt.Graphics2D;

import Util.Vector;

public class MovingCharge {
	private double x, y;
	private double charge;
	
	private Vector velocity, acceleration;
	
	private HockeyEnvironment environment;
	
	public MovingCharge(HockeyEnvironment environment) {
		this.environment = environment;
		
		velocity = new Vector();
		acceleration = new Vector();
	}
	
	public void update(double delta) {
		Vector forceVector = new Vector();
		
		for(StationaryCharge c : environment.getStationaryCharges()) {
			Vector tempForce = new Vector((int) x, (int) y, c.getX(), c.getY());
			
			double distanceSquared = Math.pow(c.getX() - x, 2) + Math.pow(c.getY() - y, 2);
			
			tempForce.setMagnitude((-1 * Math.signum(charge) * Math.signum(c.getCharge()) * Constants.COULOMB_CONSTANT * Math.abs(charge * c.getCharge())) / distanceSquared);
			
			forceVector.mAdd(tempForce);
		}
		
		forceVector.mScale(delta / Constants.CHARGE_MASS);
		
		acceleration.mAdd(forceVector);
		
		velocity.mAdd(acceleration.scale(delta));
		
		x += velocity.getXComp() * delta;
		y += velocity.getYComp() * delta;
	}
	
	public void render(Graphics2D g2d) {
		if(charge == 0)
			g2d.setColor(Color.GRAY);
		else if(charge > 0)
			g2d.setColor(Color.BLUE);
		else 
			g2d.setColor(Color.RED);
		
		g2d.fillOval((int) x, (int) y, Constants.CHARGE_RADIUS, Constants.CHARGE_RADIUS);
	}
	
	public void setCharge(double charge) { this.charge = charge; }
	public double getCharge() { return charge; }
	
	public void setVelocity(double xComp, double yComp) { this.velocity.setComponents(xComp, yComp); }
	
	public void setPosition(int x, int y) { this.x = x; this.y = y; }
}
