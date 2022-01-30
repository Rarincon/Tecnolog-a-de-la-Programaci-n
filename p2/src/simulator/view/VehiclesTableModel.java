package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import extra.jtable.EventEx;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.SimulatedObject;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends TableModel<Vehicle>{
	private static final long serialVersionUID = 1L;
	
	private static String[] _colNames = { "Id", "Location", "Itinerary", "CO2 Class", "Max Speed", "Speed", "Total CO2", "Distance"};
	
	public VehiclesTableModel(Controller c) {
		super(c, _colNames);
		_events=new ArrayList<Vehicle>();
	}
	
	public void setEventsList(List<Vehicle> vehicles) {
		for(Vehicle v:vehicles)
			_events.add(v);
		update();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = ((Vehicle) _events.get(rowIndex)).getId();
			break;
		case 1:
			s = ((Vehicle) _events.get(rowIndex)).getLocation();
			break;
		case 2:
			s = ((Vehicle) _events.get(rowIndex)).getItinerary();
			break;
		case 3:
			s = ((Vehicle) _events.get(rowIndex)).getContClass();
			break;
		case 4:
			s = ((Vehicle) _events.get(rowIndex)).getMaxSpeed();
			break;
		case 5:
			s = ((Vehicle) _events.get(rowIndex)).getSpeed();
			break;
		case 6:
			s = ((Vehicle) _events.get(rowIndex)).getTotalCO2();
			break;
		case 7:
			s = ((Vehicle) _events.get(rowIndex)).getDistance();
			break;
		}
		return s;
	}

	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_events.clear();
		List<Vehicle> v = map.getVehicles();
		//_events=v;
		//update();
		setEventsList(v);
	}

}