package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.SortedArrayList;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends TableModel<Event>{

	private static final long serialVersionUID = 1L;
	
	private static String[] _colNames= {"Time", "Descr."};
	
	public EventsTableModel(Controller c) {
		super(c, _colNames);
		_events= new SortedArrayList<Event>();
		if(_ctrl.hasEvents())
			setEventsList(_ctrl.getEvents());
	}

	public void setEventsList(List<Event> events) {
		//_events=events;
		for(Event e: events) //{
			//String f= e.
			_events.add(e);
		//}
		update();
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = ((Event) _events.get(rowIndex)).getTime();
			break;
		case 1:										
			s = ((Event) _events.get(rowIndex)).toString();
			break;
		}
		return s;
	}

	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_events.clear();
		//_events=events;
		//update();
		setEventsList(events);
	}

	@Override
	public void onEventAdded(RoadMap map, List events, Event e, int time) {
		_events.add(e);
		update();
	}

	@Override
	public void onReset(RoadMap map, List events, int time) {
		_events.clear();
		//_events=events;
		 setEventsList(events);
		 update();
	}
	
}