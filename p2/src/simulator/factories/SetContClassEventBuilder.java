package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event>{

	public SetContClassEventBuilder() {
		super("set_cont_class");

	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time=data.getInt("time");
		List<Pair<String,Integer>>p= new ArrayList<Pair<String, Integer>>();
		Pair<String, Integer> j;
		String r;
		int w;
		JSONObject aux;
		JSONArray array= data.getJSONArray("info");
		for(int i=0;i<array.length();i++) {
			aux=array.getJSONObject(i);
			r=aux.getString("vehicle");
			w=aux.getInt("class");
			j= new Pair<>(r,w);
			p.add(j);
		}
		
		return new NewSetContClassEvent(time, p);
	}

}
