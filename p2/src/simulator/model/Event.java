package simulator.model;

import org.json.JSONObject;

import Exceptions.ValoresException;

public abstract class Event implements Comparable<Event> {

	protected int _time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			_time = time;
	}

	public int getTime() { //esto era protected
		return _time;
	}

	@Override
	public int compareTo(Event o) { 
		if(this._time < o._time) return -1;
		else if(this._time==o._time) return 0;
		else return 1;
	}


	abstract void execute(RoadMap map) throws ValoresException;
	
	//Añadido para el save
	public JSONObject reportE() {
		return null;
	}
}
