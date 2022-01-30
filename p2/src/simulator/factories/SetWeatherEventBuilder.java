package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	public SetWeatherEventBuilder() {
		super("set_weather");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time=data.getInt("time");
		List<Pair<String,Weather>>p= new ArrayList<Pair<String, Weather>>();
		Pair<String,Weather> j;
		String r;
		Weather w;
		JSONObject aux;
		JSONArray array= data.getJSONArray("info");
		for(int i=0;i<array.length();i++) {
			aux=array.getJSONObject(i);
			r=aux.getString("road");
			w=Weather.valueOf(aux.getString("weather").toUpperCase());
			j= new Pair<>(r,w);
			p.add(j);
		}
		
		return new SetWeatherEvent(time,p);
	}
}
