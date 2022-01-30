package simulator.view;

import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import simulator.model.Road;
import simulator.model.Vehicle;

public class JunctionEx {
	private String _id;
	private String _lightIndex;
	private List<List<Vehicle >> _colas;
	private List<Road> _roads;
	
	public JunctionEx(String id, String indice,List<Road> r, List<List<Vehicle >> q) {
		_id=id;
		_lightIndex=indice;
		_roads =r;
		_colas=q;
	}
	
	public String getId() { return _id;};
	public String getGreenLightIndex() { return _lightIndex;}
	
	public String getInRoads() {
		String queue="";
		int i=0;
		for(Road r:_roads) {
			queue+=r.getId();
			queue+=":[";
			Iterator<Vehicle> it= _colas.get(i).iterator();
			while(it.hasNext()) {
				queue+=it.next().getId();
				if(it.hasNext())queue+=",";
			}
			queue+="] ";
			i++;
		}
		return queue;
	}
}