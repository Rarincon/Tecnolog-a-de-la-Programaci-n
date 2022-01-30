package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Exceptions.ValoresException;
import simulator.misc.Pair;

public class NewSetContClassEvent extends Event{
	
	private List<Pair<String, Integer>> cs;

	public NewSetContClassEvent(int time,List<Pair<String, Integer>>cs) {
		super(time);
		if(cs==null) 
			throw new IllegalArgumentException("cs es null");
		this.cs=new ArrayList<>();
		for(Pair<String, Integer> c:cs)
			this.cs.add(c);

	}

	@Override
	public void execute(RoadMap map) throws ValoresException {
		for(Pair<String, Integer> c:cs) {
				map.getVehicle(c.getFirst()).setContaminationClass(c.getSecond());
		}
	}
	
	public String toString() {
		String p="[";
		for(Pair<String, Integer> s: cs) {
			p+="(";
			p+=s.getFirst();
			p+=",";
			p+=s.getSecond();
			p+=")";
			p+=" ";
		}
		p+="]";
		return "Change CO2 class: "+p;
	}
	
	public JSONObject reportE() {
		JSONObject o = new JSONObject();
		JSONArray a = new JSONArray();
		for(Pair<String, Integer> p : cs) {
			JSONObject aux = new JSONObject();
			aux.put("time",getTime());
			aux.put("id", p.getFirst());
			aux.put("value", p.getSecond());
			a.put(aux);
		}
		o.put("Change_CO2_class", a);
		return o;
	}
}