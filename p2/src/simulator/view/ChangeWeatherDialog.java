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

import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ChangeWeatherDialog extends ChangeDialog<String, Weather>{

	private static final long serialVersionUID = 1L;
	private static final String MSG ="Schedule an event to change the weather of a road after a given number of simulation ticks from now.";
	private String[] weathers= {"SUNNY", "CLOUDY", "RAINY","WINDY", "STORM"};
	private Weather weather;
	
	public ChangeWeatherDialog(Frame parent) {
		super(parent, MSG);
		initGUI();
		
	}
	
	private void initGUI() {
		setTitle("Change Road Weather");
		/*for(int i=0;i<Weathers.length;i++)
			caja2.addElement(Weathers[i]);*/
		for(int i=0;i <  weathers.length ;i++) {
			caja2.addElement(Weather.valueOf(weathers[i]));//addElement(Weather[i]);
		}
		
		viewsPanel.add(new JLabel("Roads: "));
		viewsPanel.add(Caja1);
		viewsPanel.add(new JLabel("Weather: "));
		viewsPanel.add(Caja2);
		viewsPanel.add(new JLabel("Ticks: "));
		viewsPanel.add(ticks);
	}


	@Override
	public int open(RoadMap map) {
		if(map!=null && !map.EmptyList()) {
			List<Road> r = map.getRoads();
			for(Road r1:r)
				caja1.addElement(r1.getId());
			
			setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10); //ver que es esto
			setVisible(true);
			return _status;
		}
		else 
			JOptionPane.showMessageDialog(this,"You can't change the Weather of a"
					+ " Road without loading a file and running","Error",JOptionPane.WARNING_MESSAGE);	
			return 0;
	}
	
	//public Weather getCaja2() { return Weather.valueOf((String) caja2.getSelectedItem()); }
	public Weather getCaja2() { return  (Weather) caja2.getSelectedItem(); }
}