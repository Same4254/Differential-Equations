package BezierCurve;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BezierTrack {
	private ArrayList<BezierCurve> curves;
	
	private Path2D.Double curve1, curve2;
	private double trackWidth;
	
	/*
	 * This is a race track of sorts that will have an equal width throughout the duration of the track.
	 */
	public BezierTrack(double trackWidth) {
		this.trackWidth = trackWidth;
		this.curves = new ArrayList<>();
		this.curve1 = new Path2D.Double();
		this.curve2 = new Path2D.Double();
	}
	
	public void addCurve(BezierCurve curve) {
		curves.add(curve);
	}
	
	public void calculateCurve() {
		curve1.reset();
		curve2.reset();
		
		double angle = 0;
		
		for(int i = 0; i < curves.size(); i++) {
			BezierCurve bezierCurve = curves.get(i);
			
			for(int j = 0; j < bezierCurve.getDrawPoints().size(); j++) {
				Point2D.Double point = bezierCurve.getDrawPoints().get(j);
				Point2D.Double point2 = null;
				
				if(j == bezierCurve.getDrawPoints().size() - 1 && i != curves.size() - 1)
					point2 = curves.get(i + 1).getDrawPoints().get(0);
				else if(j != bezierCurve.getDrawPoints().size() - 1)
					point2 = bezierCurve.getDrawPoints().get(j + 1);
				
				if(point2 != null)
					angle = Math.atan2(point2.getY() - point.getY(), point2.getX() - point.getX());
				
				addPoint(curve1, point.getX() - (trackWidth * Math.sin(angle)), point.getY() + (trackWidth * Math.cos(angle)));
				addPoint(curve2, point.getX() + (trackWidth * Math.sin(angle)), point.getY() - (trackWidth * Math.cos(angle)));
			}
		}
	}
	
	private void addPoint(Path2D.Double curve, double x, double y) {
		if(curve.getCurrentPoint() == null)
			curve.moveTo(x, y);
		else
			curve.lineTo(x, y);
	}
	
	public ArrayList<BezierCurve> getCurves() { return curves; }
	public Path2D.Double getTrack1() { return curve1; }
	public Path2D.Double getTrack2() { return curve2; }
}
