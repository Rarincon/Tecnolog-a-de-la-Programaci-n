package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import extra.jtable.EventEx;
import simulator.control.Controller;
import simulator.misc.SortedArrayList;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.SimulatedObject;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends TableModel<Junction>{
	
	private static final long serialVersionUID = 1L;
	
	private static String[] _colNames= {"Id", "Green", "Queues"};
	
	public JunctionsTableModel(Controller c) {
		super(c, _colNames);
		_events= new ArrayList<Junction>();
	}

	public void setEventsList(List<Junction> events) {
		for(Junction j: events) 
			_events.add(j);
		update();
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = ((Junction) _events.get(rowIndex)).getId();
			break;
		case 1:										
			s = ((Junction) _events.get(rowIndex)).getLightIndex();
			break;
		case 2:
			s = ((Junction) _events.get(rowIndex)).getRoads();
			break;
		}
		return s;
	}


	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_events.clear(); //limpiamos lista por si contiene algo
		List<Junction> j=map.getJunctions();
		//_events=j;
		//update();
		setEventsList(j);
	}
	
}