package simulator.model;

import java.util.ArrayList;
import java.util.List;

import Exceptions.ValoresException;

public class NewVehicleEvent extends Event{
	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> itinerary;
	

	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		this.id=id;
		this.maxSpeed=maxSpeed;
		this.contClass=contClass;
		this.itinerary=new ArrayList<>();
		
		for(String j:itinerary)
			this.itinerary.add(j);
	}

	@Override 
	public void execute(RoadMap map) throws ValoresException {
		List<Junction> l= new ArrayList<>();
		Junction j;
		for(int i=0;i<this.itinerary.size();i++) {
			j=map.getJunction(itinerary.get(i));
			l.add(j);
		}
		Vehicle v=new Vehicle(id,maxSpeed,contClass,l);
		map.addVehicle(v);
		v.moveToNextRoad(); 
	}
	
	public String toString() {
		return "New Vehicle '"+id+"'";
	}
}