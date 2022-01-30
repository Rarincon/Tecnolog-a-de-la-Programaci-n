package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import Exceptions.ValoresException;

public class RoadMap {
	private List<Junction> list_cruces;
	private List<Road> list_roads;
	private List<Vehicle> list_cars;
	private Map<String,Junction> map_cruces;
	private Map<String,Road> map_roads;
	private Map<String,Vehicle> map_cars;
	
	RoadMap(){
		this.list_cruces=new ArrayList<>();
		this.list_roads=new ArrayList<>();
		this.list_cars=new ArrayList<>();
		
		this.map_cruces= new TreeMap<>();
		this.map_roads= new TreeMap<>();
		this.map_cars= new TreeMap<>();
	}
	
	
	void addJunction(Junction j) throws ValoresException {
		if(!map_cruces.containsKey(j.getId())) {
			list_cruces.add(list_cruces.size(), j);
			map_cruces.put(j.getId(), j);
		}
		else throw new ValoresException("Existe otro cruce con el mismo id");
	}
	
	void addRoad(Road r) throws ValoresException {
		if(!map_roads.containsKey(r.getId()) && map_cruces.containsKey(r.getDest().getId())) {
			list_roads.add(list_roads.size(), r);
			map_roads.put(r.getId(), r);
		}
		else throw new ValoresException("Existe alguna carretera con el mismo id o los cruces que"
				+ " conectan con la carretera no existen");
	}
	
	void addVehicle(Vehicle v) throws ValoresException {
		if(!map_cars.containsKey(v.getId()) && itok(v) ) {
			list_cars.add(list_cars.size(), v);
			map_cars.put(v.getId(), v);
		}
		else throw new ValoresException("Error a la hora de añadir el vehiculo: "+v.getId());
		
	}
	
	private boolean itok(Vehicle v) {
		boolean ok=true;
		int i=0;
		while(ok && i< v.getLenghtIt()-1) {
			if(v.getJunct(i).roadTo(
					v.getJunct(i+1))==null)
				ok=false;
			i++;
			
		}
		return ok;
	}
	
	public Junction getJunction(String id) throws ValoresException {
		if(!map_cruces.containsKey(id)) {
			throw new ValoresException("El cruce no esta en el mapa");
		}
		else return map_cruces.get(id);
	}
	
	public Road getRoad(String id) throws ValoresException {
		if(!map_roads.containsKey(id)) {
			throw new ValoresException("La carretera no esta en el mapa");
		}
		else return map_roads.get(id);
	}
	
	public Vehicle getVehicle(String id) throws ValoresException {
		if(!map_cars.containsKey(id)) {
			throw new ValoresException("El vehiculo no esta en el mapa");
		}
		else return map_cars.get(id);
		
	}
	
	public List<Junction>getJunctions(){
		return Collections.unmodifiableList(new ArrayList<>(this.list_cruces));
	}
	
	public List<Road>getRoads(){
		return Collections.unmodifiableList(new ArrayList<>(this.list_roads));
		
	}
	
	public List<Vehicle>getVehicles(){
		return Collections.unmodifiableList(new ArrayList<>(this.list_cars));
	}
	
	void reset() {
		this.list_cars.clear();
		this.list_cruces.clear();
		this.list_roads.clear();
		this.map_cars.clear();
		this.map_cruces.clear();
		this.map_roads.clear();
	}
	
	public JSONObject report() {
		JSONObject j = new JSONObject();
		JSONArray a = new JSONArray();
		
		for(Road r: list_roads)
			a.put(r.report());
		j.put("roads", a);
		
		a= new JSONArray();
		for(Vehicle v: list_cars)
			a.put(v.report());
		j.put("vehicles", a);
		
		a= new JSONArray();
		for(Junction c: list_cruces)
			a.put(c.report());
		j.put("junctions", a);
		
		return j;
	}	
	
	public  Boolean EmptyList() {
		return list_cars.isEmpty() || list_cruces.isEmpty() || list_roads.isEmpty();
	}
}