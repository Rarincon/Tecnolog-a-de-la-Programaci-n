package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

//import extra.jtable.EventsTableModel;
import simulator.control.Controller;
import simulator.view.MapComponent;

public class MainWindow extends JFrame{
	private Controller _ctrl;
	private Border _defaultBorder = BorderFactory.createLineBorder(Color.black, 2);
	
	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		this.setPreferredSize(new Dimension(1000,1000));
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout()); //Panel General
		this.setContentPane(mainPanel);
		ControlPanel control= new ControlPanel(_ctrl);
		mainPanel.add(control, BorderLayout.PAGE_START);  //Panel de Botones/menu
		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END); //Barra de abajo
		
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2)); //Los 4 event, vehicle, roads,junction
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		JPanel tablesPanel = new JPanel(); 	//Tablas de los 4 izq
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		
		JPanel mapsPanel = new JPanel();	//mapsPanel panel de mapas
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		
		// tables
		JPanel eventsView =
			createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events"); //Panel de eventos
			eventsView.setPreferredSize(new Dimension(500, 200));
			tablesPanel.add(eventsView);
		
		JPanel vehiclesView=
			createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles"); //Panel de vehiculos
			vehiclesView.setPreferredSize(new Dimension(500,200));
			tablesPanel.add(vehiclesView);
			
		JPanel roadsView=
				createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Vehicles"); //Panel de carreteras
				roadsView.setPreferredSize(new Dimension(500,200));
				tablesPanel.add(roadsView);
		
		JPanel junctionsView=
				createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions"); //Panel de cruces
				junctionsView.setPreferredSize(new Dimension(500,200));
				tablesPanel.add(junctionsView);

				
		eventsView.setBorder(BorderFactory.createTitledBorder
				(_defaultBorder, "Events", TitledBorder.LEFT, TitledBorder.TOP)); //Para poner el borde negro de los paneles
		vehiclesView.setBorder(BorderFactory.createTitledBorder
				(_defaultBorder, "Vehicles", TitledBorder.LEFT, TitledBorder.TOP));
		roadsView.setBorder(BorderFactory.createTitledBorder
				(_defaultBorder, "Roads", TitledBorder.LEFT, TitledBorder.TOP));
		junctionsView.setBorder(BorderFactory.createTitledBorder
				(_defaultBorder, "Junctions", TitledBorder.LEFT, TitledBorder.TOP));
			
				
		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapsPanel.add(mapView);
		JPanel mapRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Roads Map");
		mapsPanel.add(mapRoadView);
		
		mapView.setBorder(BorderFactory.createTitledBorder
				(_defaultBorder, "Map", TitledBorder.LEFT, TitledBorder.TOP));
		mapRoadView.setBorder(BorderFactory.createTitledBorder
				(_defaultBorder, "Map by Road", TitledBorder.LEFT, TitledBorder.TOP));

		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e){control.quit();}

			//No se usan pero hacen falta
			public void windowOpened(WindowEvent e){}
			public void windowClosed(WindowEvent e){}
			public void windowIconified(WindowEvent e){}
			public void windowDeiconified(WindowEvent e){}
			public void windowActivated(WindowEvent e){}
			public void windowDeactivated(WindowEvent e){}
		});
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout() );
		p.setPreferredSize(new Dimension(500, 400));
		p.add(new JScrollPane(c));
		return p;
	}
}