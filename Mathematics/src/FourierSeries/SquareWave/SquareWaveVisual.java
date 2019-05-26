package FourierSeries.SquareWave;

import java.awt.Color;
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
import javax.swing.JSplitPane;

import Graph.FixedGraph;

public class SquareWaveVisual extends JSplitPane {
	private static final long serialVersionUID = 1L;
	private static final int n = 20;
	
	private FixedGraph graph;
	private SeriesVisual seriesVisual;
	private ResultVisual resultVisual;
	
	private double time = 0;
	
	private Color[] colors;
	
	public SquareWaveVisual() {
		double jump = 1.0 / (n * 1.0);
		colors = new Color[n];
		for (int i = 0; i < colors.length; i++) {
		    colors[i] = new Color(Color.HSBtoRGB((float) (jump * i), 1f, 1f));
		}
		
		setEnabled(false);
		setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		setResizeWeight(.5);
		setDividerSize(0);
		
		graph = new FixedGraph();
		graph.setConstraints(-4, 4, -4, 4);                        
		graph.setTickInterval(1);
		
		seriesVisual = new SeriesVisual();
		seriesVisual.setOpaque(false);
		
		resultVisual = new ResultVisual();
		
		JLayeredPane seriesPane = new JLayeredPane();
		seriesPane.add(graph, new Integer(0));
		seriesPane.add(seriesVisual, new Integer(1));
		
		seriesPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				
				graph.setSize(seriesPane.getSize());
				seriesVisual.setSize(seriesPane.getSize());
			}
		});
		
		setLeftComponent(seriesPane);
		setRightComponent(resultVisual);
	}
	
	public void update(double timeStep) {
		time += timeStep;
	}
	
	class SeriesVisual extends JPanel {
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			
			double lastX = 0, lastY = 0;
			for(int i = 0; i < n; i++) {
//				g2d.setColor(colors[i]);
				int oddIndex = (2 * i) + 1;
				
				double amplitude = 4.0 / (oddIndex * Math.PI);
				double theta = oddIndex * time;

				double x = amplitude * Math.cos(theta);
				double y = amplitude * Math.sin(theta);
				
				int pixelRadiusX = (int) (graph.getScaleX() * amplitude);
				int pixelRadiusY = (int) (graph.getScaleY() * amplitude);
				
				Point lastPixelPoint = graph.coordsToPixel(lastX, lastY);
				Point currentPixelPoint = graph.coordsToPixel(lastX + x, lastY + y);
				
				g2d.drawOval(lastPixelPoint.x - pixelRadiusX, lastPixelPoint.y - pixelRadiusY, 2 * pixelRadiusX, 2 * pixelRadiusY);
				g2d.drawLine(lastPixelPoint.x, lastPixelPoint.y, currentPixelPoint.x, currentPixelPoint.y);
				
				lastX += x;
				lastY += y;
			}
			
			g2d.setColor(Color.BLACK);
			Point lastPixelPoint = graph.coordsToPixel(lastX, lastY);
			g2d.drawLine(lastPixelPoint.x, lastPixelPoint.y, getWidth(), lastPixelPoint.y);
			
			resultVisual.getPoints().add(0, new Point2D.Double(0, lastY));
		}
	}
	
	class ResultVisual extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private ArrayList<Point2D.Double> points;
		
		public ResultVisual() {
			points = new ArrayList<>();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			
			if(points.size() < 2)
				return;
			
			for(int i = points.size() - 1; i >= 1; i--) {
				if(i > getWidth()) {
					points.remove(i);
					continue;
				}
				
				Point nextPixelPoint = graph.coordsToPixel(points.get(i - 1));
				Point currentPixelPoint = graph.coordsToPixel(points.get(i));
				
				g2d.drawLine(i - 1, nextPixelPoint.y, i, currentPixelPoint.y);
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
		
		SquareWaveVisual visual = new SquareWaveVisual();
		
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
