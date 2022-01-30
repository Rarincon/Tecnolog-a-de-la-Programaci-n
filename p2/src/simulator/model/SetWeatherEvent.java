package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Exceptions.ValoresException;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event{

	private List<Pair<String,Weather>> ws;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>>ws) {
		super(time);
		if(ws==null) 
			throw new IllegalArgumentException("cs es null");
		this.ws= new ArrayList<>();
		for(Pair<String, Weather> w:ws)
			this.ws.add(w);
	}

	@Override
	public void execute(RoadMap map) throws ValoresException{
		for(Pair<String, Weather> w:ws) {
			map.getRoad(w.getFirst()).setWeather(w.getSecond());
		}		
	}
	
	public String toString() {
		String p="[";
		for(Pair<String,Weather> s: ws) {
			p+="(";
			p+=s.getFirst();
			p+=",";
			p+=s.getSecond();
			p+=")";
			p+=" ";
		}
		p+="]";
		return "Change Weather: "+p;
	}
	
	public JSONObject reportE() {
		JSONObject o = new JSONObject();
		JSONArray a = new JSONArray();
		for(Pair<String, Weather> p : ws) {
			JSONObject aux = new JSONObject();
			aux.put("time",getTime());
			aux.put("id", p.getFirst());
			aux.put("weather", p.getSecond());
			a.put(aux);
		}
		o.put("Change_Weather", a);
		return o;
	}
}