package Pendulum;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;

public class PendulumEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField angleField;
	private JTextField airResistenceField;
	private JTextField gravityField;
	private JTextField initialVelocityField;
	
	public PendulumEditor(PendulumVisual pendulumVisual) {
		setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout); 
		
		JLabel airResistanceLabel = new JLabel("Air Resistence");
		airResistanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_airResistanceLabel = new GridBagConstraints();
		gbc_airResistanceLabel.anchor = GridBagConstraints.EAST;
		gbc_airResistanceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_airResistanceLabel.gridx = 1;
		gbc_airResistanceLabel.gridy = 0;
		add(airResistanceLabel, gbc_airResistanceLabel);
		
		airResistenceField = new JTextField();
		airResistenceField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_airResistenceField = new GridBagConstraints();
		gbc_airResistenceField.insets = new Insets(0, 0, 5, 0);
		gbc_airResistenceField.fill = GridBagConstraints.HORIZONTAL;
		gbc_airResistenceField.gridx = 2;
		gbc_airResistenceField.gridy = 0;
		add(airResistenceField, gbc_airResistenceField);
		airResistenceField.setColumns(10);
		
		JLabel gravityLabel = new JLabel("Gravity");
		gravityLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_gravityLabel = new GridBagConstraints();
		gbc_gravityLabel.anchor = GridBagConstraints.WEST;
		gbc_gravityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_gravityLabel.gridx = 1;
		gbc_gravityLabel.gridy = 1;
		add(gravityLabel, gbc_gravityLabel);
		
		gravityField = new JTextField();
		gravityField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_gravityField = new GridBagConstraints();
		gbc_gravityField.insets = new Insets(0, 0, 5, 0);
		gbc_gravityField.fill = GridBagConstraints.HORIZONTAL;
		gbc_gravityField.gridx = 2;
		gbc_gravityField.gridy = 1;
		add(gravityField, gbc_gravityField);
		gravityField.setColumns(10);
		
		JLabel initialAngleLabel = new JLabel("Initial Angle");
		initialAngleLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_initialAngleLabel = new GridBagConstraints();
		gbc_initialAngleLabel.anchor = GridBagConstraints.WEST;
		gbc_initialAngleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_initialAngleLabel.gridx = 1;
		gbc_initialAngleLabel.gridy = 2;
		add(initialAngleLabel, gbc_initialAngleLabel);
		
		angleField = new JTextField();
		angleField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_angleField = new GridBagConstraints();
		gbc_angleField.insets = new Insets(0, 0, 5, 0);
		gbc_angleField.fill = GridBagConstraints.HORIZONTAL;
		gbc_angleField.gridx = 2;
		gbc_angleField.gridy = 2;
		add(angleField, gbc_angleField);
		angleField.setColumns(10);
		
		JLabel velocityLabel = new JLabel("Initial Velocity");
		velocityLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_velocityLabel = new GridBagConstraints();
		gbc_velocityLabel.anchor = GridBagConstraints.EAST;
		gbc_velocityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_velocityLabel.gridx = 1;
		gbc_velocityLabel.gridy = 3;
		add(velocityLabel, gbc_velocityLabel);
		
		initialVelocityField = new JTextField();
		GridBagConstraints gbc_initialVelocityField = new GridBagConstraints();
		gbc_initialVelocityField.insets = new Insets(0, 0, 5, 0);
		gbc_initialVelocityField.fill = GridBagConstraints.HORIZONTAL;
		gbc_initialVelocityField.gridx = 2;
		gbc_initialVelocityField.gridy = 3;
		add(initialVelocityField, gbc_initialVelocityField);
		initialVelocityField.setColumns(10);
		
		JButton resetButton = new JButton("Reset");
		resetButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.anchor = GridBagConstraints.WEST;
		gbc_btnReset.insets = new Insets(0, 0, 0, 5);
		gbc_btnReset.gridx = 1;
		gbc_btnReset.gridy = 4;
		add(resetButton, gbc_btnReset);
		
		resetButton.addActionListener(e -> {
			pendulumVisual.reset();
		});
	}

	public JTextField getInitialVelocityField() { return initialVelocityField; }
	public JTextField getAngleField() { return angleField; }
	public JTextField getAirResistenceField() { return airResistenceField; }
	public JTextField getGravityField() { return gravityField; }
}
