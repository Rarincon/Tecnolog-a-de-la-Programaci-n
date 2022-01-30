package simulator.model;

import Exceptions.ValoresException;

public class InterCityRoad extends Road{

	public InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather) throws ValoresException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);

	}

	@Override
	public void reduceTotalContamination() {
		int x;
		if(this.getWeather()==Weather.SUNNY)
			x=2;
		else if(this.getWeather()==Weather.CLOUDY)
			x=3;
		else if(this.getWeather()==Weather.RAINY)
			x=10;
		else if(this.getWeather()==Weather.WINDY)
			x=15;
		else
			x=20;	
		
		int u=(int)(((100.0-x)/100.0)*this.getTotalCO2());
		set_total_cont(u);
	}

	@Override
	void updateSpeedLimit() {
		if(this.getTotalCO2()>this.getCO2Limit())
			this.set_lim_veloc(this.getMaxSpeed()*0.5);
		else
			set_lim_veloc(this.getMaxSpeed());
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) { 
		if(this.getWeather()==Weather.STORM)
			return (int) (this.getLimSpeed()*0.8);
		else
			return this.getLimSpeed();
	}

}
