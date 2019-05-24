package Graph;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FixedGraph extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final double dx = 0.001;
	
	private ArrayList<GraphFunction> functions;
	
	private double minX, minY;
	private double maxX, maxY;
	
	//Pixel per Coordinate
	private double scaleX, scaleY;
	
	private int originX, originY;
	
	public FixedGraph() {
		functions = new ArrayList<>(); 
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				System.out.println("---------");
				
				Point2D.Double coords = pixelToCoords(e.getPoint());
				System.out.println("Coordinates: " + coords);
				System.out.println("Pixels: " + coordsToPixel(coords));
				System.out.println("Mouse: " + e.getPoint());
			}
		});
	}

	private void calculateScale() { 
		scaleX = ((double) getWidth()) / (Math.abs(maxX - minX));
		scaleY = ((double) getHeight()) / (Math.abs(maxY - minY));
		
		originX = (int) -(minX * scaleX);
		originY = (int) (maxY * scaleY);
	}
	
	private void drawGrid(Graphics2D g2d) {
		g2d.drawLine(originX, 0, originX, getHeight());
		g2d.drawLine(0, originY, getWidth(), originY);
	}
	
	private void drawFunctions(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(2));
		
		for(int i = 0; i < functions.size(); i++) {
			GraphFunction function = functions.get(i);
			Point2D.Double lastPoint = null;
			
			double startX = 0;
			for(double x = minX; x <= maxX; x += dx) {
				try {
					double y = function.getFunction().apply(x);
					lastPoint = new Point2D.Double(x, y);
					startX = x;
					break;
				} catch(Exception e) {
					
				}
			}

			if(lastPoint == null) break;
			
			for(double x = startX + dx; x <= maxX; x += dx) {
				double y = function.getFunction().apply(x);
				Point2D.Double currentPoint = new Point2D.Double(x, y);

				Point lastPixelLocation = coordsToPixel(lastPoint);
				Point currentPixelLocation = coordsToPixel(currentPoint);
				
				g2d.drawLine(lastPixelLocation.x, lastPixelLocation.y, currentPixelLocation.x, currentPixelLocation.y);
				
				lastPoint = currentPoint;
			}
		}
	}
	
	private void calculateFunctions() {
		for(int i = 0; i < functions.size(); i++) {
			GraphFunction function = functions.get(i);
			function.getShape().reset();
			
			for(double x = minX; x <= maxX; x += dx) {
				double y = function.getFunction().apply(x);
				function.getShape().lineTo(x, y);
			}
		}
	}

	//************ Coordinate Conversions
	public Point2D.Double pixelToCoords(int x, int y) {
		return new Point2D.Double(minX + (x / scaleX), maxY - (y / scaleY));
	}
	
	public Point2D.Double pixelToCoords(Point pixelLocation) { return pixelToCoords(pixelLocation.x, pixelLocation.y); }
	
	public Point coordsToPixel(double x, double y) {
		int distanceX = (int) (x * scaleX);
		int distanceY = (int) (y * scaleY);
		
		return new Point(originX + distanceX, originY - distanceY);
	}

	public Point coordsToPixel(Point2D.Double coordinates) { return coordsToPixel(coordinates.x, coordinates.y); }
	//************
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		calculateScale();
		drawGrid(g2d);
		drawFunctions(g2d);
	}
	
	public ArrayList<GraphFunction> getFunctions() { return functions; }
	
	public void setConstraints(double minX, double maxX, double minY, double maxY) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		
		repaint();
	}
	
	public void setHorizontalConstraints(double minX, double maxX) {
		this.minX = minX;
		this.maxX = maxX;
		
		repaint();
	}
	
	public void setVerticalConstraints(double minY, double maxY) {
		this.minY = minY;
		this.maxY = maxY;
		
		repaint();
	}
	
	public double getMinX() { return minX; }
	public void setMinX(double minX) { 
		this.minX = minX; 
		repaint();
	}

	public double getMinY() { return minY; }
	public void setMinY(double minY) { 
		this.minY = minY; 
		repaint();
	}

	public double getMaxX() { return maxX; }
	public void setMaxX(double maxX) { 
		this.maxX = maxX; 
		repaint();
	}

	public double getMaxY() { return maxY; }
	public void setMaxY(double maxY) { 
		this.maxY = maxY;
		repaint();
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		
		FixedGraph graph = new FixedGraph();
		graph.minX = -5;
		graph.maxX =  5;
		graph.minY = -5;
		graph.maxY =  5;
		
		graph.functions.add(new GraphFunction(x -> { return Math.sin(4 * x) * Math.cos(.5 * x); }));
		
		frame.add(graph);
		
		frame.setVisible(true);
	} 
}
