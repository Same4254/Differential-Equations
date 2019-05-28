package Pendulum;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import Graph.FixedGraph;
import VectorField.VectorField;

public class PendulumVisual extends JLayeredPane {
	private static final long serialVersionUID = 1L;

	private Pendulum pendulum;
	
	private FixedGraph graph;
	private VectorField vectorField;
	private PendulumRenderer pendulumRenderer;
	private TracePanel tracePanel;
	
	public PendulumVisual() {
		pendulum = new Pendulum();
		pendulum.setGravity(5);
		
		pendulum.setInitialAngle(Math.PI - .02);
		pendulum.setInitialVelocity(5);
		pendulum.setAirResistance(0.5);
		pendulum.reset();
		
		pendulumRenderer = new PendulumRenderer(pendulum);
		pendulumRenderer.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		
		graph = new FixedGraph();
		graph.setConstraints(-10, 10, -10, 10);
		graph.setTickInterval(1);
		
		tracePanel = new TracePanel();
		tracePanel.setOpaque(false);
		
		vectorField = new VectorField(20, 6);
		vectorField.setUpdateFunction(vector -> {
			Point2D.Double info = graph.pixelToCoords(vector.getRenderer().getX(), vector.getRenderer().getY());
			
			double theta = info.getX();
			double velocity = info.getY();
			
			vector.setComponents(velocity, -(pendulum.getAirResistence() * velocity) -(pendulum.getGravity() * Math.sin(theta)));
			
			return null;
		});
		
		vectorField.setOpaque(false);
		
		add(graph, new Integer(0));
		add(vectorField, new Integer(1));
		add(tracePanel, new Integer(2));
		add(pendulumRenderer, new Integer(3));
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				
				graph.setSize(getSize());
				tracePanel.setSize(getSize());
				vectorField.setSize(getSize());
				pendulumRenderer.setSize(new Dimension(getWidth() / 4, getHeight() / 4));
			}
		});
	}
	
	public void update(double timeStep) {
		pendulum.update(timeStep);
		
		tracePanel.getPoints().add(new Point2D.Double(pendulum.getAngle(), pendulum.getAngularVelocity()));
	}
	
	class TracePanel extends JPanel {
		private static final long serialVersionUID = 1L;
	
		private ArrayList<Point2D.Double> points;
		
		public TracePanel() {
			points = new ArrayList<>();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.blue);
			
			if(points.size() < 2)
				return;
			
			for(int i = 1; i < points.size(); i++) {
				Point lastPixelPoint = graph.coordsToPixel(points.get(i - 1));
				Point currentPixelPoint = graph.coordsToPixel(points.get(i));
				
				g2d.drawLine(lastPixelPoint.x, lastPixelPoint.y, currentPixelPoint.x, currentPixelPoint.y);
			}
			
			Point lastPixelPoint = graph.coordsToPixel(points.get(points.size() - 1));
			int boundX = (int) (getWidth() * 0.05);
			int boundY = (int) (getHeight() * 0.05);
			
			if(lastPixelPoint.x < boundX) {
				double shift = (boundX - lastPixelPoint.x) / graph.getScaleX();
				graph.setHorizontalConstraints(graph.getMinX() - shift, graph.getMaxX() - shift);
				
				vectorField.update();
			} else if(lastPixelPoint.x > getWidth() - boundX) {
				double shift = (lastPixelPoint.x - (getWidth() - boundX)) / graph.getScaleX();
				graph.setHorizontalConstraints(graph.getMinX() + shift, graph.getMaxX() + shift);
				
				vectorField.update();
			}
			
			if(lastPixelPoint.y < boundY) {
				double shift = (boundY - lastPixelPoint.y) / graph.getScaleY();
				graph.setVerticalConstraints(graph.getMinY() + shift, graph.getMaxY() + shift);
				
				vectorField.update();
			} else if(lastPixelPoint.y > getHeight() - boundY) {
				double shift = (lastPixelPoint.y - (getHeight() - boundY)) / graph.getScaleY();
				graph.setVerticalConstraints(graph.getMinY() - shift, graph.getMaxY() - shift);
				
				vectorField.update();
			}
		}

		public ArrayList<Point2D.Double> getPoints() { return points; }
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(-1920, 0);
		frame.setSize(1920, 1080);
//		frame.setSize(500, 500);
		
		PendulumVisual visual = new PendulumVisual();
		
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
