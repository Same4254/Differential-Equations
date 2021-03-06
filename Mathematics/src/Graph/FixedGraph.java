package Graph;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FixedGraph extends JPanel {
	private static final long serialVersionUID = 1L;
	protected static final double dx = 0.001;
	
	protected ArrayList<GraphFunction> functions;
	
	protected double minX, minY;
	protected double maxX, maxY;
	
	//Pixel per Coordinate
	private double scaleX, scaleY;
	
	private int originX, originY;
	
	protected double tickInterval;
	
	public FixedGraph() {
		functions = new ArrayList<>(); 
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
		
		for(double i = (int) minX; i <= maxX; i += tickInterval) {
			if(i == 0)
				continue;
			
			Point tickPoint = coordsToPixel(i, 0);
			
			g2d.drawLine(tickPoint.x, tickPoint.y - 10, tickPoint.x, tickPoint.y + 10);
			g2d.drawString("" + i, tickPoint.x - 2, tickPoint.y + 25);
		}
		
		for(double i = (int) minY; i <= maxY; i += tickInterval) {
			if(i == 0)
				continue;
			
			Point tickPoint = coordsToPixel(0, i);
			
			g2d.drawLine(tickPoint.x - 10, tickPoint.y, tickPoint.x + 10, tickPoint.y);
			g2d.drawString("" + i, tickPoint.x + 15, tickPoint.y + 4);
		}
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
	
	public double getScaleX() { return scaleX; }
	public double getScaleY() { return scaleY; }

	public void setTickInterval(double tickInterval) { this.tickInterval = tickInterval; }

	public static void main(String[] args) {
//		double multiple = Math.PI / 4.0;
//		
//		double num = Math.PI;
//		
//		if (num % multiple == 0)
//			System.out.println("OK");
//		num = num + (multiple - num % multiple);
//		System.out.println(num / Math.PI);
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		
		DraggableGraph graph = new DraggableGraph();
		graph.minX = -5;
		graph.maxX =  5;
		graph.minY = -5;
		graph.maxY =  5;
		graph.tickInterval = 1;
		
		graph.functions.add(new GraphFunction(x -> { return 1.0 / x; }));
		
		frame.add(graph);
		
		frame.setVisible(true);
	} 
}
