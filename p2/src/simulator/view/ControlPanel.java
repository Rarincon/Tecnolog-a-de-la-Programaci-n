package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver{
	private static final long serialVersionUID = 1L;
	
	private Controller _ctrl;
	private RoadMap _map;
	private boolean _stopped;
	private JButton Load,changeCO2,changeWeather,Run,Quit,Stop, Reset;
	private JToolBar tBar;
	private int _time;
	private JSpinner spinner;
	private JFileChooser fc;
	
	private JMenuBar tMenu;
	private JMenu file, changes, Sim, speed;
	private JMenuItem load, Weather, CO2, run, stop, quit,reset,slow,fast,medium,save;
	
	private JSlider Speed;
	
	
	public ControlPanel(Controller c) {
		_ctrl=c;
		_stopped=false;
		_time=0;
		_ctrl.addObserver(this);
		fc= new JFileChooser();
		fc.setCurrentDirectory(new File("resources"));
		InitGui();
		createMenu();
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				Thread.sleep(Speed.getValue()*100); //Paradas del programa, para ir un poco mas lento
				_ctrl.run(1); 
			} catch (Exception e) {
				_stopped = true;
				enableToolBar(true);//Cuando peta, por si no salta al else se queda todo bloqueado
				enableJMenu(true);
				System.out.print(e);
				return;
			}
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
				}
			});
		}
		else {
			enableToolBar(true);
			enableJMenu(true);
			_stopped = false; 
		}
	}
		
	private void stop() {
		_stopped = true;
	}

	private void enableToolBar(boolean c) {
		changeCO2.setEnabled(c);
		changeWeather.setEnabled(c);
		Load.setEnabled(c);
		Run.setEnabled(c);
		Quit.setEnabled(c);
		
		Reset.setEnabled(c);
	}
	
	private void InitGui() {
		tBar = new JToolBar();
		Load= new JButton();
		changeCO2 = new JButton();
		changeWeather = new JButton();
		Run= new JButton();
		Stop= new JButton();
		Quit= new JButton();
		Reset = new JButton();

		
		spinner= new JSpinner(new SpinnerNumberModel(10,0,1000,1));
		spinner.setToolTipText("Simulation tick to run: 1-10000");
		spinner.setMaximumSize(new Dimension(80, 40));
		spinner.setMinimumSize(new Dimension(80, 40));
		spinner.setPreferredSize(new Dimension(80, 40));

		
		Load.setToolTipText("Load a file");
		Load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		Load.setIcon(new ImageIcon("resources/icons/open.png"));
		
		changeCO2.setToolTipText("Change the CO2");
		changeCO2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCO2();
			}
		});
		changeCO2.setIcon(new ImageIcon("resources/icons/co2class.png"));
		
		changeWeather.setToolTipText("Change the weather");
		changeWeather.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeWeather();
			}
		});
		changeWeather.setIcon(new ImageIcon("resources/icons/weather.png"));
		
		Run.setToolTipText("Run/Resume the Simulation");
		Run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ticks=(int) spinner.getValue();
				_stopped=false;
				enableToolBar(false);
				enableJMenu(false);
				run_sim(ticks);
			}
		});
		Run.setIcon(new ImageIcon("resources/icons/run.png"));
		
		Stop.setToolTipText("Stop the Simulation");
		Stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop();
			}
		});
		Stop.setIcon(new ImageIcon("resources/icons/stop.png"));
		
		Quit.setToolTipText("Quit the Simulation");
		Quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});
		Quit.setIcon(new ImageIcon("resources/icons/exit.png"));
		
		Reset.setToolTipText("Reset the Simulation");
		Reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_ctrl.reset();
				_ctrl.loadEvents();
			}
		});
		Reset.setIcon(new ImageIcon("resources/icons/reset.png"));
		
		//Slider de velocidad de simulacion
		Speed= new JSlider(JSlider.HORIZONTAL,0 ,10, 5);
		Speed.setPreferredSize(new Dimension(150,45));
		Speed.setMaximumSize(new Dimension(150, 45));
		Speed.setMinimumSize(new Dimension(100, 45));
		Speed.setInverted(true);
		Speed.setToolTipText("Speed of the Simulation");
		Hashtable<Integer,JLabel> labelTable = new Hashtable<Integer,JLabel>();
		labelTable.put(new Integer(0), new JLabel("Fast"));
		labelTable.put(new Integer(5), new JLabel("Medium"));
		labelTable.put(new Integer(10), new JLabel("Slow"));
		Speed.setLabelTable( labelTable );
		Speed.setMajorTickSpacing(5);
		Speed.setMinorTickSpacing(1);
		Speed.setPaintTicks(true);
		Speed.setPaintLabels(true);
		
		tBar.add(Load);
		tBar.addSeparator();
		tBar.add(changeCO2);
		tBar.add(changeWeather);
		tBar.addSeparator();
		tBar.add(Run);
		tBar.add(Stop);
		tBar.add(Reset);
		tBar.addSeparator();
		tBar.add(new JLabel("Ticks: "));
		tBar.add(spinner);
		tBar.addSeparator();
		tBar.add(new JLabel("Speed "));
		tBar.add(Speed);
		tBar.add(Box.createHorizontalGlue());
		tBar.add(Quit);
		
		this.setLayout(new BorderLayout());
		this.add(tBar, BorderLayout.PAGE_END);
		this.setPreferredSize(new Dimension(200,75));
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_map=map;
		_time=time;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_map=map;
		_time=time;
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {

	}

	@Override
	public void onError(String err) {
		JOptionPane.showMessageDialog(this, err,"Error",JOptionPane.ERROR_MESSAGE);	
	}
	
	protected void changeCO2() {
		ChangeCO2Dialog dialogCO2= new ChangeCO2Dialog((Frame) SwingUtilities.getWindowAncestor(this));
		
		int option= dialogCO2.open(_map);
		if(option==1) { //pulsado el ok en la ventana
			List<Pair<String, Integer>> cs = new ArrayList<>();
			cs.add(new Pair<String, Integer>(dialogCO2.getCaja1(), dialogCO2.getCaja2()));
			_ctrl.addEvent(new NewSetContClassEvent(_time + dialogCO2.getTicks(), cs));
		}
	}
	
	protected void changeWeather() {
		ChangeWeatherDialog dialogWeather= new ChangeWeatherDialog((Frame) SwingUtilities.getWindowAncestor(this));
		
		int option= dialogWeather.open(_map);
		if(option==1) {
			List<Pair<String, Weather>> ws = new ArrayList<>();
			ws.add(new Pair<String, Weather>(dialogWeather.getCaja1(), dialogWeather.getCaja2()));
			_ctrl.addEvent(new SetWeatherEvent(_time + dialogWeather.getTicks(), ws));
		}
	}
	
	protected void quit() {
		int option = JOptionPane.showOptionDialog(/*null*/(Frame) SwingUtilities.getWindowAncestor(this),"Are you sure?" ,"Quit"
				, JOptionPane.YES_NO_OPTION, 1, null, null,null);
		if(option==0)
			System.exit(0);
	}
	
	protected void load() {
		int v=  fc.showOpenDialog(this.getParent());//null);
		if(v== JFileChooser.APPROVE_OPTION) {
			File file= fc.getSelectedFile();
			_ctrl.reset();
			try {
				_ctrl.loadEvents(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else System.out.println("load cancelled by user");	
	}
	
	private void createMenu() {
		tMenu = new JMenuBar();
		file = new JMenu("Menu");
		changes = new JMenu("Changes");
		Sim= new JMenu("Simulation");
		speed = new JMenu("Speed Simulation");
		tMenu.add(file);
		tMenu.add(changes);
		tMenu.add(Sim);
		
		load = new JMenuItem("Load File");
		Weather = new JMenuItem("Change Weather");
		CO2 = new JMenuItem("Change CO2");
		run = new JMenuItem("Run");
		stop = new JMenuItem("Stop");
		quit =new JMenuItem("Quit");
		reset = new JMenuItem("Reset");
		save= new JMenuItem("Save");
		
		fast = new JMenuItem("Fast");
		slow = new JMenuItem("Slow");
		medium = new JMenuItem("Medium");
		speed.add(slow);
		speed.add(medium);
		speed.add(fast);
		
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		Weather.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeWeather();
			}
		});
		CO2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCO2();
			}
		});
		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ticks=(int) spinner.getValue();
				_stopped=false;
				enableToolBar(false);
				enableJMenu(false);
				run_sim(ticks);
			}
		});
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});	
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_ctrl.reset();
				_ctrl.loadEvents();
			}
		});
		slow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Speed.setValue(10);
			}
		});
		medium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Speed.setValue(5);
			}
		});
		fast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Speed.setValue(0);
			}
		});
		
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveSim();
			}
		});
		save.setToolTipText("Save a simulation");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				 ActionEvent.CTRL_MASK));

		load.setToolTipText("Load a File");
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				 ActionEvent.CTRL_MASK));
		run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				 ActionEvent.CTRL_MASK));
		stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				 ActionEvent.CTRL_MASK));
		reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				 ActionEvent.SHIFT_MASK));
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				 ActionEvent.CTRL_MASK));
		
		
		file.add(load);
		changes.add(CO2);
		changes.add(Weather);
		Sim.add(run);
		Sim.add(stop);
		Sim.add(reset);
		Sim.addSeparator();
		Sim.add(speed);
		file.addSeparator();
		file.add(save);
		file.addSeparator();
		file.add(quit);
		
		this.add(tMenu,  BorderLayout.PAGE_START);
	}
	
	private void enableJMenu(boolean c) {
		load.setEnabled(c);
		Weather.setEnabled(c);
		CO2.setEnabled(c); 
		run.setEnabled(c);
		quit.setEnabled(c);
		reset.setEnabled(c);
	}
	
	private void saveSim() {
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			 File file = fc.getSelectedFile();
			 try {
				writeFile(file);
				 JOptionPane.showMessageDialog(this,"Todo guardado correctamente","Save",JOptionPane.INFORMATION_MESSAGE);	
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(this, e,"Error",JOptionPane.ERROR_MESSAGE);	
				e.printStackTrace();
			}
		}
	}

	private void writeFile(File file) throws FileNotFoundException {
		String j = file.getAbsolutePath();
		j+=".json";
		PrintStream p = new PrintStream(j);
		p.println("{ \"simulation\": [");
		p.println(_ctrl.saveSim());
		p.println(",");
		p.println(_ctrl.saveEvents());
		p.println("]}");
		p.close();
	}
}