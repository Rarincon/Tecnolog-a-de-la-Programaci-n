package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import Exceptions.ValoresException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle>{

	private List<Junction> itinerary;
	private int maximum_speed;
	private int current_speed;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int contamination_class;
	private int total_contamination;
	private int total_travelled_distance;
	
	private int index;
	
	Vehicle(String id,int maxSpeed, int contClass,
			List<Junction> itinerary) throws ValoresException {
		super(id);
		
		if(maxSpeed<0 || contClass<0 || contClass>10 || itinerary.size()<2)
			throw new ValoresException("Los datos para la creacion del vehiculo no son validos");
		
		this.itinerary=Collections.unmodifiableList(new ArrayList<>(itinerary));		
		this.maximum_speed=maxSpeed;
		this.contamination_class=contClass;
		this.current_speed=0;
		this.total_contamination=0;
		this.total_travelled_distance=0;
		index=0;
		this.status=VehicleStatus.PENDING;

	}
	

	@Override
	void advance(int time) throws ValoresException {
		if(status == VehicleStatus.TRAVELING) {
			int locAux=location;
			if((location+current_speed)<road.getLength()) 
				location+=current_speed;
			else location=road.getLength();	
			
			int l=location-locAux;

			int cont=(this.contamination_class*l);
			this.total_contamination+=cont;
	
			total_travelled_distance+=l;
			
			this.road.addContamination(cont);

			if(location==road.getLength()) {
				status=VehicleStatus.WAITING;
				current_speed=0;
				this.itinerary.get(index).enter(this);

			}
		}
	}

	@Override
	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("speed", current_speed);
		j.put("distance", total_travelled_distance);
		j.put("co2", total_contamination);
		j.put("class",contamination_class);
		j.put("status", status);
		if(this.status!=VehicleStatus.ARRIVED) {
			j.put("road", road);
			j.put("location", location);
		}
		j.put("id", _id);
		return j;
	}
	
	void setSpeed(int s) throws ValoresException {
		if(s<0) throw new ValoresException("La velocidad es negativa");
		else if(status==VehicleStatus.TRAVELING) {
			if(s<maximum_speed) current_speed=s;
			else current_speed=maximum_speed;
		}
	}
	
	void setContaminationClass(int c) throws ValoresException {
		if(c<0 || c>10) 
			throw new ValoresException("Valor de contaminacion fuera de los limites");
		else contamination_class=c;
	}
	
	void moveToNextRoad() throws ValoresException {
		if(status==VehicleStatus.PENDING) {
			this.road=this.itinerary.get(index).roadTo(itinerary.get(index+1));
			this.current_speed=0;
			this.location=0;
			this.road.enter(this); 
			this.status=VehicleStatus.TRAVELING;
			index++;
		}
		else if(status==VehicleStatus.WAITING) {
			if(index+1>=itinerary.size()) {
				road.exit(this);
				this.status=VehicleStatus.ARRIVED;
				this.location=0;
				this.road=null;
				this.current_speed=0;
			}
			else {
				road.exit(this);
				this.road=this.itinerary.get(index).roadTo(itinerary.get(index+1));
				this.status=VehicleStatus.TRAVELING;
				this.location=0;
				this.current_speed=0; 
				this.road.enter(this); 
				index++;
			}
		}
		else throw new ValoresException("El estado del vehiculo no es ni PENDING, ni WAITING");
		
	}
	
	//GETS
	public int getContClass() { return this.contamination_class; }
	public Road getRoad() {return this.road;}
	public int getSpeed() {return this.current_speed;}
	public int getMaxSpeed() {return this.maximum_speed;}
	public int getLocation() {return this.location;}
	public Junction getJunct(int index) {return this.itinerary.get(index);}
	public int getLenghtIt() {return this.itinerary.size();}
	public VehicleStatus getStatus() { return status; }
	public int getTotalCO2() {return total_contamination;}
	public int getDistance() {return total_travelled_distance;}
	
	public List<Junction> getItinerary(){return itinerary;}
	
	public int compareTo(Vehicle arg0) {
		if(this.location < arg0.location) return 1;
		else if(this.location==arg0.location) return 0;
		else return -1;
	}
}
