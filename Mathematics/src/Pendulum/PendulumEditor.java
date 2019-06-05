package Pendulum;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JSlider;
import java.awt.Insets;

public class PendulumEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public PendulumEditor(PendulumVisual pendulumVisual) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{2, 19, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout); 
		
		JLabel airResistanceLabel = new JLabel("Air Resistence");
		GridBagConstraints gbc_airResistanceLabel = new GridBagConstraints();
		gbc_airResistanceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_airResistanceLabel.gridx = 1;
		gbc_airResistanceLabel.gridy = 0;
		add(airResistanceLabel, gbc_airResistanceLabel);
		
		JSlider airResistanceSlider = new JSlider();
		airResistanceSlider.setMinimum(0);
		airResistanceSlider.setMaximum(20);
		airResistanceSlider.setMajorTickSpacing(2);
		airResistanceSlider.setMinorTickSpacing(1);
		airResistanceSlider.setPaintTicks(true);
		
		GridBagConstraints gbc_airResistanceSlider = new GridBagConstraints();
		gbc_airResistanceSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_airResistanceSlider.insets = new Insets(0, 0, 5, 0);
		gbc_airResistanceSlider.gridx = 2;
		gbc_airResistanceSlider.gridy = 0;
		add(airResistanceSlider, gbc_airResistanceSlider);
		
		JLabel gravityLabel = new JLabel("Gravity");
		GridBagConstraints gbc_gravityLabel = new GridBagConstraints();
		gbc_gravityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_gravityLabel.gridx = 1;
		gbc_gravityLabel.gridy = 1;
		add(gravityLabel, gbc_gravityLabel);
		
		JSlider gravitySlider = new JSlider();
		gravitySlider.setMinimum(0);
		gravitySlider.setMaximum(20);
		gravitySlider.setMajorTickSpacing(2);
		gravitySlider.setMinorTickSpacing(1);
		gravitySlider.setPaintTicks(true);
		
		GridBagConstraints gbc_gravitySlider = new GridBagConstraints();
		gbc_gravitySlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_gravitySlider.insets = new Insets(0, 0, 5, 0);
		gbc_gravitySlider.gridx = 2;
		gbc_gravitySlider.gridy = 1;
		add(gravitySlider, gbc_gravitySlider);
		
		JLabel initialAngleLabel = new JLabel("Initial Angle");
		GridBagConstraints gbc_initialAngleLabel = new GridBagConstraints();
		gbc_initialAngleLabel.insets = new Insets(0, 0, 0, 5);
		gbc_initialAngleLabel.gridx = 1;
		gbc_initialAngleLabel.gridy = 2;
		add(initialAngleLabel, gbc_initialAngleLabel);
		
		JSlider initialAngleSlider = new JSlider();
		initialAngleSlider.setMinimum(0);
		initialAngleSlider.setMaximum(360);
		initialAngleSlider.setMajorTickSpacing(45);
		initialAngleSlider.setMinorTickSpacing(15);
		initialAngleSlider.setPaintTicks(true);
		initialAngleSlider.setPaintLabels(true);
		
		GridBagConstraints gbc_initialAngleSlider = new GridBagConstraints();
		gbc_initialAngleSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_initialAngleSlider.gridx = 2;
		gbc_initialAngleSlider.gridy = 2;
		add(initialAngleSlider, gbc_initialAngleSlider);
		
	}
}
