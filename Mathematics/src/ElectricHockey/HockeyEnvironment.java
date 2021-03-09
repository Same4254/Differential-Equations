package ElectricHockey;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import Util.Vector;
import VectorField.VectorField;

public class HockeyEnvironment extends JLayeredPane {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<StationaryCharge> stationaryCharges;
	private MovingCharge movingCharge;
	
	private VectorField electricField;
	private ChargePanel chargePanel;
	
	public HockeyEnvironment() {
		this.stationaryCharges = new ArrayList<StationaryCharge>();
		this.movingCharge = new MovingCharge(this);
		
		electricField = new VectorField(10, 0.01);
		chargePanel = new ChargePanel();
		
		electricField.setUpdateFunction(vector -> {
			Vector netElectric = new Vector();
			
			for(StationaryCharge c : stationaryCharges) {
				Vector directionVector = new Vector(c.getX(), vector.getRenderer().getY(), vector.getRenderer().getX(), c.getY());
				
				directionVector.setMagnitude(Constants.COULOMB_CONSTANT * c.getCharge() / (Math.pow(c.getX() - vector.getRenderer().getX(), 2) + Math.pow(c.getY() - vector.getRenderer().getY(), 2)));
				
				netElectric.mAdd(directionVector);
			}
			
			vector.setComponents(netElectric.getXComp(), netElectric.getYComp());
			
			return null;
		});
		
		chargePanel.setOpaque(false);
		
		add(chargePanel, new Integer(0));
		add(electricField);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				
				chargePanel.setSize(getSize());
				electricField.setSize(getSize());
			}
		});
	}
	
	public void update(double delta) {
		movingCharge.update(delta);
	}
	
	public ArrayList<StationaryCharge> getStationaryCharges() { return stationaryCharges; }
	public MovingCharge getMovingCharge() { return movingCharge; }
	
	private class ChargePanel extends JPanel {
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			
			for(StationaryCharge c : stationaryCharges)
				c.render(g2d);
			
			movingCharge.render(g2d);
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		
		HockeyEnvironment h = new HockeyEnvironment();
		
		h.stationaryCharges.add(new StationaryCharge(200, 200, -100));
		h.stationaryCharges.add(new StationaryCharge(200, 300, -100));
		
		h.movingCharge = new MovingCharge(h);
		h.movingCharge.setCharge(1000);
		h.movingCharge.setPosition(150, 250);
		h.movingCharge.setVelocity(20, 0);
		
		frame.add(h);
		
		frame.setVisible(true);
		
		long lastTime = System.currentTimeMillis();
		while(true) {
			if(System.currentTimeMillis() > lastTime + 16) {
				h.update((System.currentTimeMillis() - lastTime) / 1000.0);
				h.repaint();
				
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
