package simulator.model;

import Exceptions.ValoresException;

public class CityRoad extends Road{

	public CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather)throws ValoresException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	public void reduceTotalContamination() {
		int x;
		if(this.getWeather()==Weather.WINDY ||this.getWeather()==Weather.STORM) 
			x=10;
		else x=2;

		//set_total_cont((int) Math.ceil(this.getTotalCO2()-x));
		set_total_cont((int)this.getTotalCO2()-x);
		
	}

	@Override
	public void updateSpeedLimit() {
		this.set_lim_veloc(this.getMaxSpeed());
		
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		int s= this.getLimSpeed();
		int f=v.getContClass();
		//int x= (int) Math.ceil((((11.0-f)/11.0)*s)); //Parte 1 de la practica
		int x= (int) (((11.0-f)/11.0)*s); //Parte 2 de la practica
		return x;
	}

}
