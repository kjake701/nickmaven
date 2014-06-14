package nick.util;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class FullConsole extends JFrame implements Commons {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -419069197156543764L;
	protected JFrame frame = new JFrame();
	protected JPanel panel = new JPanel();
	protected JButton close;
	public static JTextArea fullConsole;
	public static JScrollPane scrollConsole;
	public static boolean fullConsoleBool = false;
	
	Logging l = new Logging();
	
	public FullConsole(int id){
	     setVisible(true);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle("Nick Maker");
                if (id == 1) {
            		setContentPane(panel);
                	panel.setLayout(null);
            init();
            logic();
            l.logInfoToConsole("Opening Full Console");
            l.logInfoToConsole("serialVersionUID: " + serialVersionUID + " Name: Nick Name Maker " + "Class: " + this.getClass() + " Version: " + version);
            repaint();
            l.updateConsole();
        }
        
        setSize(new Dimension(1600, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
           
        frame.setLayout(null);
	}
	
	public void init(){
		fullConsole = new JTextArea();
		fullConsole.setEditable(false);
        scrollConsole = new JScrollPane(fullConsole);
        scrollConsole.setBounds(0, 0, 1500, 300);
        panel.add(scrollConsole);
        fullConsoleBool = true;
        
        close = new JButton("Close");
        close.setBounds(1, 300, 100, 50);
        panel.add(close);
        
        
	}
	
	public void logic(){
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
            	dispose();
            	fullConsoleBool = false;
            }
            
        });
	}
	


}