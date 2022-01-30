package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.SortedArrayList;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public abstract class TableModel <T extends Object> extends AbstractTableModel implements TrafficSimObserver{

	private static final long serialVersionUID = 1L;
	
	protected Controller _ctrl;
	protected List<T> _events;
	private String[] _colNames;

	public TableModel(Controller c, String[] array) {
		_ctrl=c;
		_colNames=array;
		_ctrl.addObserver(this);
	}

	public void update() {
		// observar que si no refresco la tabla no se carga
		// La tabla es la represantación visual de una estructura de datos,
		// en este caso de un ArrayList, hay que notificar los cambios.
		
		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();;		
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	//si no pongo esto no coge el nombre de las columnas
	//
	//this is for the column header
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	// método obligatorio, probad a quitarlo, no compila
	//
	// this is for the number of columns
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	// método obligatorio
	//
	// the number of row, like those in the events list
	public int getRowCount() {
		return _events == null ? 0 : _events.size();
	}
	
	@Override
	public abstract Object getValueAt(int rowIndex, int columnIndex);

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

	}

	public abstract void onAdvanceEnd(RoadMap map, List<Event> events, int time);
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time){
		
	}
	
	public void onReset(RoadMap map, List<Event> events, int time) {
		_events.clear();
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {

	}

	@Override
	public void onError(String err) {

	}
}