package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Exceptions.ValoresException;
import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver> {

	private RoadMap road_map;
	private SortedArrayList<Event> event_list;
	private int time;
	private List<TrafficSimObserver> observer;
	
	public TrafficSimulator() {
		road_map= new RoadMap();
		event_list=new SortedArrayList<>();
		time=0;
		
		observer=new ArrayList<TrafficSimObserver>();
	}
	
	public void addEvent(Event e) {
		event_list.add(e);
		notifyOnEventAdded(e); 
	}
	
	public void advance()throws ValoresException { 
		time++;
		
		notifyOnAdvanceRoad();
		try{
			while(!event_list.isEmpty() && event_list.get(0).getTime()==time) {
				event_list.get(0).execute(road_map);
				event_list.remove(0);
			}

			for(Junction j : road_map.getJunctions())
				j.advance(time);
			
			for(Road r: road_map.getRoads())
				r.advance(time);
		}
		catch(Exception e) {
			notifyOnError(e.getMessage());
			throw e;
		}
		notifyOnAdvanceEnd();
	}
	
	public void reset() {
		time=0;
		event_list.clear();
		road_map.reset();
		
		notifyOnReset();
	}
	
	public JSONObject report() {
		JSONObject j =new JSONObject();
		j.put("time", time);
		j.put("state", road_map.report());
		return j;
	}
	
	@Override
	public void addObserver(TrafficSimObserver o) {
		if(!observer.contains(o)) {
			observer.add(o);
			o.onRegister(road_map, event_list, time);
		}
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		observer.remove(o);
	}
	
	private void notifyOnAdvanceRoad() {
		for(TrafficSimObserver o: observer)
			o.onAdvanceStart(road_map, event_list, time);
	}
	
	private void notifyOnAdvanceEnd() {
		for(TrafficSimObserver o: observer)
			o.onAdvanceEnd(road_map, event_list, time);
	}
	
	private void notifyOnEventAdded(Event e) {
		for(TrafficSimObserver o: observer)
			o.onEventAdded(road_map, event_list, e, time);
	}
	
	private void notifyOnReset() {
		for(TrafficSimObserver o: observer)
			o.onReset(road_map, event_list, time);
	}
	
	private void notifyOnRegister() {
		for(TrafficSimObserver o: observer)
			o.onRegister(road_map, event_list, time);
	}
	
	private void notifyOnError(String e) {
		for(TrafficSimObserver o: observer)
			o.onError(e);
	}
	
	public Boolean hasEvents() {
		if(event_list.size()>0)return true;
		else return false;
	}
	
	public List<Event> getEvents(){ return Collections.unmodifiableList(event_list);}
	
	public JSONObject reportEvents() {
		JSONObject j =new JSONObject();
		JSONArray J= new JSONArray();
		for(Event e : event_list) {
			J.put(e.reportE());
		}
		j.put("events", J);
		return j;
	}
}