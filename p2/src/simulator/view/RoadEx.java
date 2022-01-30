package simulator.view;

import simulator.model.Weather;

public class RoadEx {
	private String _id;
	private Weather _weather;
	private int _length, _maxSpeed, _limSpeed, _CO2Limit, _TotalCO2;
	
	public RoadEx(String id, Weather w, Integer length, Integer mspeed, Integer lspeed, Integer co2lim, Integer Tco2) {
		_id=id;
		_weather= w;
		_length=length;
		_maxSpeed=mspeed;
		_limSpeed=lspeed;
		_CO2Limit=co2lim;
		_TotalCO2=Tco2;
	}
	
	public String getId() { return _id;}
	public Weather getWeather() { return _weather;}
	public Integer getLength() { return _length;}
	public Integer getMaxSpeed() { return _maxSpeed;}
	public Integer getLimSpeed() { return _limSpeed;}
	public Integer getCO2Limit() { return _CO2Limit;}
	public Integer getTotalCO2() { return _TotalCO2;}
}