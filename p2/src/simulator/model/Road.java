package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Exceptions.ValoresException;
import simulator.misc.SortedArrayList;

public abstract class Road extends SimulatedObject { 
	
	private Junction cruce_orig;
	private Junction cruce_dest;
	private int length;
	private int veloc_max;
	private int lim_veloc;
	private int alamr_contam;
	private Weather cond_ambient;
	private int total_contam;
	private List<Vehicle> vehiculos;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather)throws ValoresException {
		super(id);
		init(srcJunc,destJunc,maxSpeed,contLimit,length,weather);
		
	}

	@Override
	void advance(int time) throws ValoresException {
		reduceTotalContamination();	
		updateSpeedLimit();
		int speed;
		for(Vehicle v: vehiculos) {
			speed=calculateVehicleSpeed(v);
			v.setSpeed(speed);
			v.advance(time);
		}
		Collections.sort(vehiculos);
	}

	@Override
	public JSONObject report() {
		JSONObject j = new JSONObject();
		
		j.put("speedlimit",lim_veloc);
		j.put("weather", cond_ambient);
		j.put("co2", total_contam);
		JSONArray m= new JSONArray();
		for(Vehicle v: vehiculos)
			m.put(v.getId());
		j.put("vehicles", m);
		j.put("id", _id);
		return j;
	}
	
	private void init (Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather) throws ValoresException {
		if(maxSpeed<0 || contLimit<0 || length<0 || srcJunc==null ||
				destJunc==null || weather==null)
			throw new ValoresException("Uno de los parametros dela carretera no es valido");
		else {
			cruce_dest=destJunc;
			cruce_orig=srcJunc;
			destJunc.addIncommingRoad(this);
			srcJunc.addOutGoingRoad(this);
	
			this.length= length;
			veloc_max=maxSpeed;
			lim_veloc=maxSpeed;
			alamr_contam=contLimit;
			cond_ambient=weather;
			this.total_contam=0;
			this.vehiculos=new SortedArrayList<>();
		}
	}

	void enter(Vehicle v) throws ValoresException {
		if(v.getSpeed()!=0 || v.getLocation()!=0)
			throw new ValoresException("No se cumplen los requisitos para mover el vehiculo");
		else
			vehiculos.add(v);
	}
	
	void exit(Vehicle v) {
		vehiculos.remove(v);
	}
	
	void setWeather(Weather w) throws ValoresException {
		if(w==null)
			throw new ValoresException("El valor de weather no puede ser null");
		else this.cond_ambient=w;
	}
	
	void addContamination(int c)throws ValoresException {
		if(c<0)
			throw new ValoresException("El valor de la contaminacion no puede ser negativo");
		else total_contam+=c;
	}
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	
	public int getLength() {
		return this.length;
	}
	
	//GETS
	
	public Weather getWeather() { return this.cond_ambient; }
	
	public int getTotalCO2() { return this.total_contam; }
	public Junction getDest() {return this.cruce_dest;}
	public Junction getSrc() {return this.cruce_orig;}
	public int getCO2Limit() { return this.alamr_contam; }
	
	public int getMaxSpeed() { return this.veloc_max; }
	public int getLimSpeed() { return this.lim_veloc; }
	
	//SETS
	
	public void set_total_cont(int cont){ 
		if(cont<0)this.total_contam=0;
		else this.total_contam=cont; }
	
	public void set_lim_veloc(double d) { this.lim_veloc=(int) d; }
	
	public void set_cruze_dest(Vehicle v) throws ValoresException {
			this.cruce_dest.enter(v);
	}
}