package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import Exceptions.ValoresException;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {
	
	private TrafficSimulator traf_sim;
	private Factory<Event> fact_events;
	
	private JSONObject jo;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		if(sim==null || eventsFactory==null)
			throw new IllegalArgumentException("valores nulos");
		this.traf_sim=sim;
		fact_events=eventsFactory;
	}
	

	public void loadEvents(InputStream in) {
		jo = new JSONObject(new JSONTokener(in));
		JSONArray events= jo.getJSONArray("events");
		Event e;
		for(int i=0;i<events.length();i++) {
			e= fact_events.createInstance(events.getJSONObject(i));
			if(e==null)
				throw new IllegalArgumentException("valor nulo del event");
			traf_sim.addEvent(e);
		}
	}
	
	public void run(int n, OutputStream out) {
		if(out== null) {
			out = new OutputStream() {
				@Override
				public void write(int b) throws IOException {
				}
			};
		}	
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println(" \"states\": [");
		int i=n;
		try {
			while(i>1) {
				traf_sim.advance();
				p.print(traf_sim.report());
				p.println(",");
				i--;
			}
			if(i>0) {
				traf_sim.advance();
				p.print(traf_sim.report());
			}
			 p.println("]");
			 p.println("}");
		}
		catch(ValoresException e) {
			System.out.println(e);
		}
	}
	
	public void run(int ticks) {
		for(int i=0; i<ticks; i++) {
			try {
				traf_sim.advance();
			} catch (ValoresException e) {
				System.out.println(e);
			}
		}
	}

	public void reset() {
		traf_sim.reset();
	}
	
	public void addObserver(TrafficSimObserver o) {
		traf_sim.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o) {
		traf_sim.removeObserver(o);
	}
	
	public void addEvent(Event e) {
		traf_sim.addEvent(e);
	}


	public boolean hasEvents() {
		if(traf_sim.hasEvents())return true;
		return false;
	}


	public List<Event> getEvents() {
		return traf_sim.getEvents();
	}
	
	public void loadEvents() {
		if(jo!=null) {
			JSONArray events= jo.getJSONArray("events");
			Event e;
			for(int i=0;i<events.length();i++) {
				e= fact_events.createInstance(events.getJSONObject(i));
				if(e==null)
					throw new IllegalArgumentException("valor nulo del event");
				traf_sim.addEvent(e);
			}
		}
	}
	
	public JSONObject saveSim() {
		return traf_sim.report();
	}
	
	public JSONObject saveEvents() {
		return traf_sim.reportEvents();
	}
}
