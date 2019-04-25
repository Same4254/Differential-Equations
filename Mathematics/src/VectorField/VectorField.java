package VectorField;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.function.Function;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Util.Vector;

public class VectorField extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Vector[][] vectors;
	
	private int maxPixelMagnitude;
	private double maxMagnitude;
	
	/**
	 * The Vector Field is exactly what it sounds like: A Field of Vectors. 
	 * 
	 * The idea for this class is to make a JPanel that will handle the dynamic resize of the screen and render all of the vectors. 
	 * This is a general-use class, adn therefore the evaluation of how the angle and magnitude changes is up to the 
	 * 		user to pass in as a lambda expression. This allows for different uses of this class without the alteration of the source. 
	 * 
	 * In addition, this class has two functions Update and Render, these two functions should be called inside of a loop 
	 * 		to continuously update the vectors and render the screen accordingly. 
	 * 
	 * The method of use is to fit as many vectors on the screen as possible, thus a different amount of vectors will be present 
	 * 		for different screen resolutions: A higher resolution screen will have more pixels and therefore allow more vectors to be drawn
	 * 
	 * @param maxPixelMagnitude -> Max pixel length for every vector in the field
	 * @param maxMagnitude -> Max size for the actual magnitude of each vector 
	 * 							(this is to set up proportion between the two that corresponds to the actual size of the visual vector)
	 */
	public VectorField(int maxPixelMagnitude, double maxMagnitude) {
		this.maxPixelMagnitude = maxPixelMagnitude;
		this.maxMagnitude = maxMagnitude;
		
		updateVectorArray();
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				
				updateVectorArray();
			}
		});
	}
	
	/**
	 * This method will look at the screen size and see how many vectors should be put onto the screen. 
	 * This will recreate the array to allocate for the change in window resolution.
	 */
	private void updateVectorArray() {
		int numRows = (int) (getHeight() / (2.0 * maxPixelMagnitude)) + 1;
		int numCols = (int) (getWidth()  / (2.0 * maxPixelMagnitude)) + 1;
		
		vectors = new Vector[numRows][numCols];
		
		for(int i = 0; i < numRows; i++) {
		for(int j = 0; j < numCols; j++) {
			Vector v = new Vector(0, 0);
			
			v.getRenderer().setX(maxPixelMagnitude + (2 * maxPixelMagnitude * j));
			v.getRenderer().setY(maxPixelMagnitude + (2 * maxPixelMagnitude * i));
			
			vectors[i][j] = v;
		}}
	}
	
	/**
	 * This update function will update all the vectors on the screen and apply the evaluation function to all of them
	 * 
	 * @param evaluation -> Function to evaluate each vector upon
	 */
	public void update(Function<Vector, Void> evaluation) {
		for(Vector[] vectors : this.vectors)
		for(Vector vector : vectors)
			evaluation.apply(vector);
	}
	
	public void render(Graphics2D g2d) {
		for(Vector[] vectors : this.vectors)
		for(Vector vector : vectors)
			vector.getRenderer().renderArrow(g2d, maxPixelMagnitude, maxMagnitude);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		render(g2d);
	}
	
	public int getMaxPixelMagnitude() { return maxPixelMagnitude; }
	public void setMaxPixelMagnitude(int maxPixelMagnitude) { this.maxPixelMagnitude = maxPixelMagnitude; updateVectorArray(); }
	
	public double getMaxMagnitude() { return maxMagnitude; }
	public void setMaxMagnitude(double maxMagnitude) { this.maxMagnitude = maxMagnitude; updateVectorArray(); }
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		VectorField field = new VectorField(20, 1);
		
		frame.add(field);
		frame.setVisible(true);
		
		Thread thread = new Thread(() -> {
			long start = System.currentTimeMillis();
			double theta = 0;
			while(true) {
				if(System.currentTimeMillis() - start >= 16) {
//					vector.setComponents(Math.cos(theta), Math.sin(theta));
					final double temp = theta;
					
					field.update(v -> {
						v.setComponents(1, 0);
						v.setAngle(temp);
						
						return null;
					});
					field.repaint();
					
					theta += .01;
					
					start = System.currentTimeMillis();
				}
			}
		});
		
		thread.start();
	}
}
