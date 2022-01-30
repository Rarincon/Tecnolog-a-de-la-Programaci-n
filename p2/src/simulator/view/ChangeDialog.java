package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Weather;

public abstract class ChangeDialog<T, P extends Object> extends JDialog{

	private static final long serialVersionUID = 1L;
	protected JSpinner ticks;
	protected JComboBox<T> Caja1;
	protected JComboBox<P> Caja2;
	protected  DefaultComboBoxModel<T> caja1;
	protected  DefaultComboBoxModel<P> caja2;
	protected int _status;
	private String MSG;
	
	protected JPanel viewsPanel;
	
	
	public ChangeDialog(Frame parent, String msg) {
		super(parent, true);
		MSG=msg;
		initGUI();
	}

	private void initGUI() {
		_status=0;
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
				
		JTextArea msg = new JTextArea();
		msg.setEditable(false);
		msg.setVisible(true);
		msg.setOpaque(false);
		msg.setFocusable(false);
		msg.setLineWrap(true);
	    msg.setWrapStyleWord(true);
		msg.setText(MSG);
		mainPanel.add(msg);

		viewsPanel = new JPanel();
		viewsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(viewsPanel);
		
		caja1 = new DefaultComboBoxModel<T>();
		Caja1= new JComboBox<T>(caja1);
		Caja1.setPreferredSize(new Dimension(80,25));
		
		caja2 = new DefaultComboBoxModel<P>();
		Caja2= new JComboBox<P>(caja2);
		Caja2.setPreferredSize(new Dimension(100,25));
		
		ticks= new JSpinner(new SpinnerNumberModel(1,0,1000,1));
		ticks.setToolTipText("Simulation tick to run: 1-10000");
		ticks.setPreferredSize(new Dimension(80, 40));
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ChangeDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Caja1.getSelectedItem() != null) {
					_status = 1;
					ChangeDialog.this.setVisible(false);
				}
			}
		});
		buttonsPanel.add(okButton);
		
		setPreferredSize(new Dimension(450, 200));;
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	public abstract int open(RoadMap map);
	public T getCaja1() {  return  (T) caja1.getSelectedItem();}
	//public P getCaja2(){ return (P) caja2.getSelectedItem();}
	public int getTicks() { return (int) ticks.getValue();}
}	