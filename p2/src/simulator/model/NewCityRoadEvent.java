package simulator.model;

import Exceptions.ValoresException;

public class NewCityRoadEvent extends NewRoadEvent{
	
	public NewCityRoadEvent(int time, String id, String srcJun, String destJunc,
			int lenght, int co2Limit, int maxSpeed, Weather weather) {
		super(time, id, srcJun, destJunc, lenght, co2Limit, maxSpeed, weather);		
	}

	public void execute(RoadMap map) throws ValoresException {
		Junction orig= map.getJunction(srcJun);
		Junction dest=map.getJunction(destJunc);
		CityRoad r= new CityRoad(id, orig, dest, maxSpeed, co2Limit, lenght, weather);
		map.addRoad(r);
	}
	
	public String toString() {
		return "New City Road '"+id+"'";
	}
}