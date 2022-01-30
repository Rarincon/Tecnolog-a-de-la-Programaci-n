package simulator.view;

public class EventEx implements Comparable<EventEx> {
	private Integer _time;
	private String _descr;
	
	public EventEx(Integer time, String descr) {
		_time = time;
		_descr = descr;
	}
	
	public int getTime() {return _time;}
	public String getDescr() {return _descr;}
	
	@Override
	public int compareTo(EventEx e) {
		if(this._time < e._time) return -1; 
		else if(this._time==e._time) return 0; 
		else return 1; 
	}
}