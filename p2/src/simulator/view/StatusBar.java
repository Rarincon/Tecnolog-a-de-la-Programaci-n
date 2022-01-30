package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private JLabel msg,time;
	public StatusBar(Controller c) {
		_ctrl=c;
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		msg= new JLabel("Event message");
		time= new JLabel("0");
		time.setPreferredSize(new Dimension(100,20));
		JSeparator sep= new JSeparator(SwingConstants.VERTICAL);
		sep.setPreferredSize(new Dimension(10,20));
		
		this.add(new JLabel("Time: "));
		this.add(time);
		this.add(sep);
		this.add(msg);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.time.setText(Integer.toString(time));
		this.msg.setText("Event message");
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.msg.setText("Event added ("+e.toString()+")");
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.time.setText(Integer.toString(time));
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onError(String err) {
		
	}
}
