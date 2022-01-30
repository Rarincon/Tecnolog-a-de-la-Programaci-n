package simulator.view;

import java.util.List;

import simulator.model.Junction;

public class VehicleEx {
	private String _id;
	private int _location;
	private List<Junction> _itinerary;
	private int _CO2Class;
	private int _maxSpeed;
	private int _speed;
	private int _totalCO2;
	private int _distance;
	
	
	public VehicleEx(String id, int location, List<Junction> list,
			int co2Class, int maxSpeed, int speed, int totalCo2, int distance) {
		_id = id;
		_location = location;
		_itinerary=list;
		_CO2Class=co2Class;
		_maxSpeed=maxSpeed;
		_speed=speed;
		_totalCO2=totalCo2;
		_distance=distance;	
	}
	
	public String getId() { return _id;}
	public int getLocation() { return _location;}
	public List<Junction> getItinerary() { return _itinerary;}
	public int getCO2Class() { return _CO2Class;}
	public int getMaxSpeed() { return _maxSpeed;}
	public int getSpeed() {	return _speed;}
	public int getTotalCO2() { return _totalCO2;}
	public int getDistance() { return _distance;}

}