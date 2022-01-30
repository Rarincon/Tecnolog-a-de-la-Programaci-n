package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;
import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver{
	
	private static final long serialVersionUID = 1L;

	private static final int _JRADIUS = 10;
	
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	private static final int[] YCoor= {50,100,150,200,250};
	private static final int Lenght=350, INI=50;
	
	private RoadMap _map;

	private Image _car;
	
	public MapByRoadComponent(Controller ctrl) {
		ctrl.addObserver(this);
		setPreferredSize (new Dimension (300, 200));
		initGUI();
	}
	
	private void initGUI() {
		_car = loadImage("car.png");
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No Roadmap yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}
	
	
	private void drawMap(Graphics g) {
		drawRoads(g);
		drawVehicles(g);
	}
	
	private void drawRoads(Graphics g) {
		int i =0;
		int x1=50;
		int x2=getWidth()-100;
		for (Road r : _map.getRoads()) {
			int y=(i+1)*50;
			Image status,weather;

			// choose a color for the arrow depending on the traffic light of the road
			Color semaforo = _RED_LIGHT_COLOR;
			int idx = r.getDest().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
				semaforo = _GREEN_LIGHT_COLOR;
			}
			
			int C = (int) Math.floor(Math.min((double) r.getTotalCO2()/(1.0 + (double) r.getCO2Limit()),1.0) / 0.19);
			if(C==0) status= loadImage("cont_0.png");
			else if(C==1) status = loadImage("cont_1.png");
			else if(C==2) status = loadImage("cont_2.png");
			else if(C==3) status = loadImage("cont_3.png");
			else if(C==4) status = loadImage("cont_4.png");
			else status = loadImage("cont_5.png");
			
			Weather w= r.getWeather();
			if(w == Weather.CLOUDY) weather = loadImage("cloud.png");
			else if(w == Weather.RAINY) weather = loadImage("rain.png");
			else if(w == Weather.STORM) weather = loadImage("storm.png");
			else if(w == Weather.SUNNY) weather = loadImage("sun.png");
			else weather = loadImage("wind.png");
			
			g.setColor(Color.black);
			g.drawString(r.getId(),20 ,y);
			
			drawSrcJunctions(g,i,r.getSrc());
			drawDestJunctions(g, i, r.getDest(), semaforo);

			drawLine(g, x1, x2, y, i, Color.BLACK, semaforo, status, weather);
			i++;
		}
	}

	private void drawVehicles(Graphics g) {
		int x1=50, x2=getWidth()-100;
		int i;
		Boolean encontrada;
		for (Vehicle v : _map.getVehicles()) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {
				
				encontrada=false;
				i=0;
				Road r = v.getRoad();
				List<Road> roads= _map.getRoads();
				
				while(i<roads.size() && !encontrada) {
					if(Objects.equals(roads.get(i).getId(),r.getId())) encontrada=true;
					else i++;
				}
				
				int y=(i+1)*50;				
				int x = x1 + (int) ((x2 - x1) * ((double) v.getLocation() / (double) v.getRoad().getLength()));

				// draw an image of a car (with circle as background) and it identifier
				g.drawImage(_car, x, y - 6, 16, 16, this);
				g.drawString(v.getId(), x, y - 6);
				i++;
			}
		}
	}

	private void drawSrcJunctions(Graphics g, int i, Junction j) {

		g.setColor(_JUNCTION_COLOR);
		g.fillOval(INI - _JRADIUS / 2, YCoor[i] - _JRADIUS / 2, _JRADIUS, _JRADIUS);

		g.setColor(_JUNCTION_LABEL_COLOR);
		g.drawString(j.getId(), INI, YCoor[i]);
	}
	
	private void drawDestJunctions(Graphics g, int i, Junction j, Color semaforo) {
		
		g.setColor(semaforo);
		g.fillOval(Lenght - _JRADIUS / 2, YCoor[i] - _JRADIUS / 2, _JRADIUS, _JRADIUS);
		g.setColor(_JUNCTION_LABEL_COLOR);
		g.drawString(j.getId(), Lenght, YCoor[i]);
	}

	// this method is used to update the preffered and actual size of the component,
	// so when we draw outside the visible area the scrollbars show up
	private void updatePrefferedSize() {
		int maxW = 425;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		setPreferredSize(new Dimension(maxW, maxH));
		setSize(new Dimension(maxW, maxH));
	}
	
	private void drawLine(//
			Graphics g, //
			int x1, //
			int x2, int y, //
			int i, //
			Color lineColor, Color arrowColor,//
			Image status, Image weather) {
		
		g.drawImage(weather, x2+15, y-15, 32, 32, this);
		g.drawImage(status,x2+50 , y-15, 32, 32, this);
		
		g.setColor(lineColor);
		g.drawLine(x1, y, x2, y);
		g.setColor(arrowColor);
	}

	// loads an image from a file
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	public void update(RoadMap map) {
		_map = map;
		repaint();
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);	
	}

	@Override
	public void onError(String err) {
		
	}
}