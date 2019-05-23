package BezierCurve;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.function.Function;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BezierTrackEditor extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 10, HEIGHT = 10;
	
	private BezierTrack track;
	private ArrayList<ControlPoint> controlPoints;
	
	public BezierTrackEditor(BezierTrack track) {
		this.track = track;
		
		controlPoints = new ArrayList<>();
		int controlPointAMT = 3 + ((track.getCurves().size() - 1) * 2);
		
		for(int i = 0; i < controlPointAMT; i++) {
			final int tempControlIndex = i;
			
			ControlPoint controlPoint = new ControlPoint(point -> {
				if(tempControlIndex % 2 == 0) {
					int tempCurveIndex = tempControlIndex / 2;
					
					track.getCurves().get(tempCurveIndex - 1).setControlPoint1(point);
					if(tempCurveIndex != 0)
						track.getCurves().get(tempCurveIndex - 1).setControlPoint3(point);
				} else {
					track.getCurves().get(tempControlIndex / 2).setControlPoint2(point);
				}
				
				return null;
			});
			
//			controlPoint.rectangle.setRect(track.get, y, w, h);
			
			controlPoints.add(controlPoint);
		}
	}

	class ControlPoint {
		private Rectangle2D rectangle;
		private Function<Point2D.Double, Void> controlFunction;
		
		public ControlPoint(Function<Point2D.Double, Void> controlFunction) {
			this.controlFunction = controlFunction;
			rectangle = new Rectangle2D.Double();
		}
		
		public Rectangle2D getRectangle() { return rectangle; }
		public Function<Point2D.Double, Void> getControlFunction() { return controlFunction; }
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		ArrayList<BezierCurve> middleCurves = track.getCurves();
		Path2D.Double curve1 = track.getTrack1();
		Path2D.Double curve2 = track.getTrack2();
		
		if(curve1 == null || curve1.getCurrentPoint() == null || curve2 == null || curve2.getCurrentPoint() == null || middleCurves.size() == 0)
			return;
		
		for(int i = 0; i < middleCurves.size(); i++)
			g2d.draw(middleCurves.get(i).getCurve());
		
		g2d.setColor(Color.GREEN);
		g2d.draw(curve1);
		
		g2d.setColor(Color.RED);
		g2d.draw(curve2);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		
		BezierTrack track = new BezierTrack(50);
		track.addCurve(new BezierCurve(100, 200, 150, 100, 200, 100, 100));
		track.addCurve(new BezierCurve(200, 100, 250, 300, 200, 500, 100));
		
		track.calculateCurve();
		
		frame.add(new BezierTrackEditor(track));
		
		frame.setVisible(true);
	}
}
