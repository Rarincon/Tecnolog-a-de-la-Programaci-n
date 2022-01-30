package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONObject;

import Exceptions.ValoresException;

public class Junction extends SimulatedObject{
	private List<Road> list_road_ent;
	private Map<Junction,Road> map_road_sal;
	private List<List<Vehicle >> list_colas;
	private Map<Road,List<Vehicle>> map_road_list;
	private int indice;
	private int last_change_traffic_light;
	private LightSwitchingStrategy traffic_light;
	private DequeuingStrategy extract_cars;
	private int x;
	private int y;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy
			dqStrategy, int xCoor, int yCoor)throws ValoresException {
		super(id);
		
		if(lsStrategy == null || dqStrategy ==null)
			throw new ValoresException("El valor de las estrategias no puede ser null");
		if(xCoor <0 || yCoor < 0)
			throw new ValoresException("El valor de las coordenadas no puede ser negativo");
		
		this.traffic_light=lsStrategy;
		this.extract_cars=dqStrategy;
		
		this.list_road_ent= new ArrayList<>();
		this.list_colas = new ArrayList<>();
		this.map_road_list= new HashMap<>();
		this.map_road_sal = new HashMap<>();
		this.indice=-1;
		this.last_change_traffic_light=0;
		this.x=xCoor;
		this.y=yCoor;
			
	}

	@Override
	void advance(int time) throws ValoresException { 
		if(indice!=-1 && !list_road_ent.isEmpty()) {
			List<Vehicle> v = list_colas.get(indice);
			if(v!=null && v.size()>0) {
				v= extract_cars.dequeue(Collections.unmodifiableList(list_colas.get(indice)));
				for(Vehicle v1 : v) {
						map_road_list.get(v1.getRoad()).remove(v1);
						v1.moveToNextRoad();			
				}
			}	
		}
		int semaforo;
		semaforo= traffic_light.chooseNextGreen(list_road_ent, list_colas, 
				indice, last_change_traffic_light, time);
		if(indice !=semaforo) {
			last_change_traffic_light=time;
			indice=semaforo;
		}
	}

	@Override
	public JSONObject report() {
		JSONObject j = new JSONObject();
		if(indice!=-1)
			j.put("green",list_road_ent.get(indice));
		else
			j.put("green", "none");
		JSONArray array= new JSONArray();
		for(Road r:list_road_ent) {
			List<Vehicle> v;
			v= map_road_list.get(r);
			JSONObject cola= new JSONObject();
			cola.put("road",r.getId());
			JSONArray vehiculos = new JSONArray();
			for(Vehicle veh: v)
				vehiculos.put(veh.getId());
			cola.put("vehicles", vehiculos);
			array.put(cola);
		}
		j.put("queues", array);
		j.put("id", _id);
		return j;
	}
	
	void addIncommingRoad(Road r) throws ValoresException {
		if(!this.equals(r.getDest()))
			throw new ValoresException("No coinciden los cruces de destino");
		List<Vehicle> v = new ArrayList<>();
		list_road_ent.add(r);
		list_colas.add(v);
		map_road_list.put(r, v);
	}
	

	void addOutGoingRoad(Road r) throws ValoresException{
		if(map_road_sal.containsKey(r.getDest())==true)
			throw new ValoresException("Mas de una carretera va desde el actual al cruce siguiente");
		Junction j=r.getDest();
		if(r.getSrc()!=this)
			throw new ValoresException("No coincide el cruce de origen");
		map_road_sal.put(j, r);
		
	}
	
	void enter(Vehicle v) throws ValoresException {
		Road r = v.getRoad();
		List<Vehicle> m= map_road_list.get(r);
		if(m==null)
			throw new ValoresException("No existe la carretera en ese cruce");
		m.add(v);
		
	}
	
	Road roadTo(Junction j) {
		return map_road_sal.get(j);
	}
	
	public int getX(){ return x; }
	public int getY(){ return y; }
	public int getGreenLightIndex(){ return indice; }
	public String getLightIndex() { if(indice==-1)return "NONE"; else return list_road_ent.get(indice).getId();}
	public List<Road>getInRoads(){ return Collections.unmodifiableList(new ArrayList<Road>(list_road_ent)); } //Revisar si la vale sin modificar
	public List<List<Vehicle >> getColas(){return Collections.unmodifiableList(list_colas);}
	
	public String getRoads() {
		String queue="";
		int i=0;
		for(Road r:list_road_ent) {
			queue+=r.getId();
			queue+=":[";
			Iterator<Vehicle> it= list_colas.get(i).iterator();
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
