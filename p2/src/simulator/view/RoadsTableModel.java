package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.omg.CosNaming._BindingIteratorImplBase;

import extra.jtable.EventEx;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SimulatedObject;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends TableModel<Road>{
	
	private static final long serialVersionUID = 1L;
	
	private static String[] _colNames = { "Id", "Lenght", "Weather", "Max. Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	
	public RoadsTableModel(Controller c) {
		super(c,_colNames);
		_events=new ArrayList<Road>();
	}
	
	public void setEventsList(List<Road> roads) {
		for(Road r: roads)
			_events.add(r);
		update();
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = ((Road) _events.get(rowIndex)).getId();
			break;
		case 1:
			s = ((Road) _events.get(rowIndex)).getLength();
			break;
		case 2:
			s = ((Road) _events.get(rowIndex)).getWeather();
			break;
		case 3:
			s = ((Road) _events.get(rowIndex)).getMaxSpeed();
			break;
		case 4:
			s = ((Road) _events.get(rowIndex)).getLimSpeed();
			break;
		case 5:
			s = ((Road) _events.get(rowIndex)).getTotalCO2();
			break;
		case 6:
			s = ((Road) _events.get(rowIndex)).getCO2Limit();
			break;
		}
		return s;
	}

	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_events.clear();
		List<Road> r= map.getRoads();
		//_events=r;
		//update();
		setEventsList(r);
	}
}