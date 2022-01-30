package simulator.model;

import Exceptions.ValoresException;

public class NewInterCityRoadEvent extends NewRoadEvent {
	
	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc,
			int lenght, int co2Limit, int maxSpeed, Weather weather) {
		super(time, id, srcJun, destJunc, lenght, co2Limit, maxSpeed, weather);		
	}

	@Override
	public void execute(RoadMap map) throws ValoresException {
		Junction orig = map.getJunction(srcJun);
		Junction dest = map.getJunction(destJunc);
		InterCityRoad r= new InterCityRoad(id, orig, dest, maxSpeed, co2Limit, lenght, weather);
		map.addRoad(r);
	}
	
	public String toString() {
		return "New Inter City Road '"+id+"'";
	}
}