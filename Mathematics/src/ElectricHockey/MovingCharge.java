package ElectricHockey;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import Util.Vector;

public class MovingCharge {
	private double x, y;
	private double charge;
	
	private Vector velocity, acceleration;
	
	private HockeyEnvironment environment;
	
	private ArrayList<Point2D> debugPoints;
	
	public MovingCharge(HockeyEnvironment environment) {
		this.environment = environment;
		
		velocity = new Vector();
		acceleration = new Vector();
		
		debugPoints = new ArrayList<>();
	}
	
	private void updateMovement(double delta) {
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
	
	/**
	 *	This very cursed function will manually check which side of the wall the charge is on and then check if it is colliding with that face.
	 *	If it is, simply negate the corresponding component of velocity to get ping-pong like collision response.
	 *
	 *	Problems:
	 *		- The code is awful. Lots of ifs. Lacks generality
	 *		- When the acceleration begins to outcompete the negation of the component, the charge will begin to collide with the wall
	 *			over and over, resulting in the component's sign flipping over and over, and it will slowly phase through the wall...
	 */
	private void naiveCollisionWall(Wall wall) {
		if(y > wall.getY() && y < wall.getY() + wall.getHeight()) {
			if(x + Constants.CHARGE_RADIUS > wall.getX() && x + Constants.CHARGE_RADIUS < wall.getX() + wall.getWidth()) {
				velocity.setXComp(-velocity.getXComp());
				return;
			}
			
			if(x - Constants.CHARGE_RADIUS < wall.getX() + wall.getWidth() && x - Constants.CHARGE_RADIUS > wall.getX()) {
				velocity.setXComp(-velocity.getXComp());
				return;
			}
		}
		
		if(x > wall.getX() && x < wall.getX() + wall.getWidth()) {
			if(y + Constants.CHARGE_RADIUS > wall.getY() && y + Constants.CHARGE_RADIUS < wall.getY() + wall.getHeight()) {
				velocity.setYComp(-velocity.getYComp());
				return;
			}
			
			if(y - Constants.CHARGE_RADIUS < wall.getY() + wall.getHeight() && y - Constants.CHARGE_RADIUS > wall.getY()) {
				velocity.setYComp(-velocity.getYComp());
				return;
			}
		}
	}
	
	/**
	 *	The idea of this algorithm is that it will calculate the closest point on the rectangle's perimeter.
	 *	Using that known point we can calculate if it is inside the circle to see if there is a collision.
	 *	In addition, knowing the center of the circle, the closest point on the rectangle, and if there is a collision
	 *		we can generate a response easily without checking the geometry!
	 *
	 *	Problem: Still phases through the wall
	 */
	private boolean closestPointCollisionDetection(Wall wall) {
		debugPoints.clear();
		
		// Take the x-coordinate and put it in the range of the x region of the rectangle
		double closestX = Math.min(Math.max(x, wall.getX()), wall.getX() + wall.getWidth());
		// Take the y-coordinate and put it in the range of the y region of the rectangle
		double closestY = Math.min(Math.max(y, wall.getY()), wall.getY() + wall.getHeight());
		
		debugPoints.add(new Point2D.Double(closestX, closestY));
		
		boolean collision = Math.sqrt(Math.pow((closestX - x), 2) + Math.pow((closestY - y), 2)) < Constants.CHARGE_RADIUS;
		if(!collision)
			return false;
		
		//Direction vector from the center to the closest point
		Vector v = new Vector(x, y, closestX, closestY);
		
		//Flip the direction to get the response direction
		v.mScale(-1);
		
		velocity.setComponents(Math.copySign(velocity.getXComp(), v.getXComp()), Math.copySign(velocity.getYComp(), v.getYComp()));
		
		return true;
	}
	
	public void update(double delta) {
		updateMovement(delta);
		
		for(Wall w : environment.getWalls())
			closestPointCollisionDetection(w);
//			naiveCollisionWall(w);
	}
	
	public void render(Graphics2D g2d) {
		if(charge == 0)
			g2d.setColor(Color.GRAY);
		else if(charge > 0)
			g2d.setColor(Color.BLUE);
		else 
			g2d.setColor(Color.RED);
		
		g2d.fillOval((int) x - Constants.CHARGE_RADIUS, (int) y - Constants.CHARGE_RADIUS,  2 * Constants.CHARGE_RADIUS, 2 * Constants.CHARGE_RADIUS);
		
		g2d.setColor(Color.GREEN);
		for(Point2D p : debugPoints) {
			g2d.fillOval((int) p.getX() - 4, (int) p.getY() - 4, 8, 8);
		}
	}
	
	public void setCharge(double charge) { this.charge = charge; }
	public double getCharge() { return charge; }
	
	public void setVelocity(double xComp, double yComp) { this.velocity.setComponents(xComp, yComp); }
	
	public void setPosition(int x, int y) { this.x = x; this.y = y; }
}
