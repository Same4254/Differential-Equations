package Polar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.function.Function;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import Graph.FixedGraph;
import Graph.GraphFunction;
import Util.Vector;

public class PolarVisual extends JPanel {
	private static final long serialVersionUID = 1L;

	private FixedGraph euclideanGraph, polarGraph;
	private EuclideanTrace euclideanTrace;
	private PolarTrace polarTrace;
	
	private JLayeredPane euclideanLayer, polarLayer;
	
	private Function<Double, Double> euclideanFunction;
	private double x;
	
	public PolarVisual() {
		euclideanFunction = x -> { return Math.cos(2.0 * x); };
		
		euclideanGraph = new FixedGraph();
		euclideanGraph.setConstraints(-1, 7, -1.5, 1.5);
		euclideanGraph.setTickInterval(1);
		euclideanGraph.getFunctions().add(new GraphFunction(euclideanFunction));
		
		polarGraph = new FixedGraph();
		polarGraph.setConstraints(-1, 7, -1.5, 1.5);
		polarGraph.setTickInterval(1);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setResizeWeight(.5);
		
		//****** Euclidean
		euclideanLayer = new JLayeredPane();
		euclideanTrace = new EuclideanTrace();
		euclideanTrace.setOpaque(false);

		euclideanLayer.add(euclideanGraph, new Integer(0));
		euclideanLayer.add(euclideanTrace, new Integer(1));
		
		euclideanLayer.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				
				euclideanGraph.setSize(euclideanLayer.getSize());
				euclideanTrace.setSize(euclideanLayer.getSize());
			}
		});
		
		//****** Polar
		polarLayer = new JLayeredPane();
		polarTrace = new PolarTrace();
		polarTrace.setOpaque(false);

		polarLayer.add(polarGraph, new Integer(0));
		polarLayer.add(polarTrace, new Integer(1));
		
		polarLayer.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				
				polarGraph.setSize(polarLayer.getSize());
				polarTrace.setSize(polarLayer.getSize());
			}
		});
		
		splitPane.setTopComponent(euclideanLayer);
		splitPane.setBottomComponent(polarLayer);
		
		setLayout(new BorderLayout());
		add(splitPane, BorderLayout.CENTER);
	}
	
	public void update(double timeStep) {
		x += timeStep;
	}
	
	class EuclideanTrace extends JPanel {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.setColor(Color.RED);
			
			Point top = euclideanGraph.coordsToPixel(x, 0);
			Point bottom = euclideanGraph.coordsToPixel(x, euclideanFunction.apply(x));
			
			g2d.drawLine(bottom.x, bottom.y, top.x, top.y);
		}
	}
	
	class PolarTrace extends JPanel {
		private static final long serialVersionUID = 1L;

		private ArrayList<Point2D.Double> points;
		private Vector vector;
		
		public PolarTrace() {
			vector = new Vector(0, 0);
			points = new ArrayList<>();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			
			//****** Vector
			vector.getRenderer().setLocation(polarGraph.coordsToPixel(0, 0));
			
			double radius = euclideanFunction.apply(x);
			vector.setAngle(x);
			vector.setMagnitude(radius);
			
			points.add(new Point2D.Double(vector.getXComp(), vector.getYComp()));
			
			//****** Shape
			g2d.setColor(Color.RED);
			if(points.size() >= 2) {
				for(int i = 1; i < points.size(); i++) {
					Point lastPixelPoint = polarGraph.coordsToPixel(points.get(i - 1));
					Point currentPixelPoint = polarGraph.coordsToPixel(points.get(i));
					
					g2d.drawLine(lastPixelPoint.x, lastPixelPoint.y, currentPixelPoint.x, currentPixelPoint.y);
				}
			}

			g2d.setColor(Color.BLACK);
			Point lastPixelLocation = polarGraph.coordsToPixel(points.get(points.size() - 1));
			vector.getRenderer().renderArrow(g2d, vector.getRenderer().getX(), vector.getRenderer().getY(), lastPixelLocation.x, lastPixelLocation.y);
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PolarVisual visual = new PolarVisual();
		frame.add(visual);
		
		frame.setVisible(true);
		
		long lastTime = System.currentTimeMillis();
		while(true) {
			if(System.currentTimeMillis() > lastTime + 16) {
				visual.update((System.currentTimeMillis() - lastTime) / 1000.0);
				visual.repaint();
				
				lastTime = System.currentTimeMillis();
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
