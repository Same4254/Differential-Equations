package Graph;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class FixedGraph extends JPanel {
	private static final long serialVersionUID = 1L;

	private ArrayList<GraphFunction> functions;
	
	private double minX, minY;
	private double maxX, maxY;
	
	private int originX, originY;
	private int numPoints;
	
	public FixedGraph() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				
//				repaint();
			}
		});
	}
	
	private void drawGrid(Graphics2D g2d) {
		
	}
	
	private void drawFunctions(Graphics2D g2d) {
		
	}
	
	//TODO
	public Point2D.Double pixelToCoords(Point pixelPoint) {
		return new Point2D.Double();
	}
	
	//TODO
	public Point coordsToPixel(Point2D.Double coordinates) {
		return new Point();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
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
}
