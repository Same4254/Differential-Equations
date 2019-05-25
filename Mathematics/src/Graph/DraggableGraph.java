package Graph;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class DraggableGraph extends FixedGraph {
	private static final long serialVersionUID = 1L;
	
	private boolean enabled;
	
	public DraggableGraph() {
		super();
		
		enabled = true;
		
		MouseAdapter adapter = new MouseAdapter() {
			Point lastPoint;

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				lastPoint = null;
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);

				if(enabled) {
					if(lastPoint == null) {
						lastPoint = e.getPoint();
						return;
					}
					
					int dx = lastPoint.x - e.getPoint().x;
					int dy = lastPoint.y - e.getPoint().y;
					
					maxX += dx * .025;
					minX += dx * .025;
					
					maxY -= dy * .025;
					minY -= dy * .025;
					
					lastPoint = e.getPoint();
					
					repaint();
				}
			}
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				super.mouseWheelMoved(e);
				
				if(enabled) {
					int dw = e.getWheelRotation();
					
					maxX += dw * .2;
					minX -= dw * .2;
					
					maxY += dw * .2;
					minY -= dw * .2;
					
					repaint();
				}
			}
		};
		
		addMouseListener(adapter);
		addMouseMotionListener(adapter);
		addMouseWheelListener(adapter);
	}
}