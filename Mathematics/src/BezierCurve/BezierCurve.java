package BezierCurve;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BezierCurve {
	private Point2D controlPoint1, controlPoint2, controlPoint3;
	private ArrayList<Point2D> drawPoints;
	private double arcLength;
	private int n;
	
	/*
	 * This class will calculate a bezier curve. The accuracy of which will corespond to the value of n.
	 * The higher of an n value will mean a more accurate and smooth curve.
	 */
	public BezierCurve(int n) {
		this.n = n;
		drawPoints = new ArrayList<>();
	}
	
	public BezierCurve(Point2D controlPoint1, Point2D controlPoint2, Point2D controlPoint3, int n) {
		this(n);
		
		setControlPoints(controlPoint1, controlPoint2, controlPoint3);
	}

	private void calculateCurve() {
		if(controlPoint1 == null)
			System.err.println("Control Point 1 is Null");
		if(controlPoint2 == null)
			System.err.println("Control Point 2 is Null");
		if(controlPoint3 == null)
			System.err.println("Control Point 3 is Null");
		drawPoints.clear();
		arcLength = 0;
		
		for(double t = 0.0; t <= 1; t += 1.0 / n) {
			double x = (Math.pow(1.0 - t, 2) * controlPoint1.getX()) +
						(2.0 * t * controlPoint2.getX() * (1 - t)) + 
						(Math.pow(t, 2) * controlPoint3.getX());
			
			double y = (Math.pow(1.0 - t, 2) * controlPoint1.getY()) +
					(2.0 * t * controlPoint2.getY() * (1 - t)) + 
					(Math.pow(t, 2) * controlPoint3.getY());

			drawPoints.add(new Point2D.Double(x, y));
			
			if(drawPoints.size() > 1) {
				Point2D lastPoint = drawPoints.get(drawPoints.size() - 2);
				arcLength += Math.sqrt(Math.pow(x - lastPoint.getX(), 2) + Math.pow(y - lastPoint.getY(), 2));
			}
		}
	}
	
	public void setControlPoints(Point2D controlPoint1, Point2D controlPoint2, Point2D controlPoint3) {
		this.controlPoint1 = controlPoint1;
		this.controlPoint2 = controlPoint2;
		this.controlPoint3 = controlPoint3;
		
		calculateCurve();
	}
	
	public void setControlPoint1(Point2D controlPoint1) {
		this.controlPoint1 = controlPoint1;
		calculateCurve();
	}
	
	public void setControlPoint2(Point2D controlPoint2) {
		this.controlPoint2 = controlPoint2;
		calculateCurve();
	}
	
	public void setControlPoint3(Point2D controlPoint3) {
		this.controlPoint3 = controlPoint3;
		calculateCurve();
	}
	
	public Point2D getControlPoint1() { return controlPoint1; }
	public Point2D getControlPoint2() { return controlPoint2; }
	public Point2D getControlPoint3() { return controlPoint3; }

	public double getArcLength() { return arcLength; }
	public int getN() { return n; }

	public ArrayList<Point2D> getDrawPoints() { return drawPoints; }
}
