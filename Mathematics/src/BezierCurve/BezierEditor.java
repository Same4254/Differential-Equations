package BezierCurve;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BezierEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Rectangle rectangle1, rectangle2, rectangle3;
	private BezierCurve bezierCurve;
	
	public BezierEditor() {
		rectangle1 = new Rectangle(0, 0, 10, 10);
		rectangle2 = new Rectangle(50, 50, 10, 10);
		rectangle3 = new Rectangle(100, 100, 10, 10);
		
		bezierCurve = new BezierCurve(rectangle1.getCenterX(), rectangle1.getCenterY(), 
									  rectangle2.getCenterX(), rectangle2.getCenterY(),
									  rectangle3.getCenterX(), rectangle3.getCenterY(), 100);
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			private Rectangle dragRectangle;
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				if(rectangle1.contains(e.getPoint()))
					dragRectangle = rectangle1;
				else if(rectangle2.contains(e.getPoint()))
					dragRectangle = rectangle2;
				else if(rectangle3.contains(e.getPoint()))
					dragRectangle = rectangle3;
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				
				if(dragRectangle != null) {
					dragRectangle.setLocation(e.getPoint());
					
					if(dragRectangle == rectangle1)
						bezierCurve.setControlPoint1(dragRectangle.getCenterX(), dragRectangle.getCenterY());
					if(dragRectangle == rectangle2)
						bezierCurve.setControlPoint2(dragRectangle.getCenterX(), dragRectangle.getCenterY());
					if(dragRectangle == rectangle3)
						bezierCurve.setControlPoint3(dragRectangle.getCenterX(), dragRectangle.getCenterY());
					
					repaint();
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				dragRectangle = null;
			}
		};
		
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.fill(rectangle1);
		g2d.fill(rectangle2);
		g2d.fill(rectangle3);
		g2d.draw(bezierCurve.getCurve());
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new BezierEditor());
		
		frame.setVisible(true);
	}
}
