package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.model.RoadMap;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ChangeCO2Dialog extends ChangeDialog<String, Integer>{

	private static final long serialVersionUID = 1L;
	private static final String MSG ="Schedule an event to change the CO2 of a vehicle after a given number of simulation ticks from now.";
	

	public ChangeCO2Dialog(Frame parent) {
		super(parent, MSG);
		initGUI();
		
	}
	
	private void initGUI() {
		setTitle("Change CO2 CLass");
		for(int i=0;i<10;i++)
			caja2.addElement(i);
		
		viewsPanel.add(new JLabel("Vehicle: "));
		viewsPanel.add(Caja1);
		viewsPanel.add(new JLabel("CO2 Class: "));
		viewsPanel.add(Caja2);
		viewsPanel.add(new JLabel("Ticks: "));
		viewsPanel.add(ticks);
	}

	@Override
	public int open(RoadMap map) {
		if(map!=null && !map.EmptyList()) {
			List<Vehicle> v = map.getVehicles();
			for(Vehicle v1:v)
				caja1.addElement(v1.getId());
			
			setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10); //ver que es esto
			setVisible(true);
			return _status;
		}
		else
			JOptionPane.showMessageDialog(this,"You can't change the CO2 of a"
					+ " Car without loading a file and running","Error",JOptionPane.WARNING_MESSAGE);	 
			return 0;
	}
	
	public Integer getCaja2() { return (int) caja2.getSelectedItem();}
}