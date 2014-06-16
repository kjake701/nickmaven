package nick.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NickMaker extends JFrame implements Commons {
	Logging l = new Logging();
    /**
	 * 
	 */
	private static final long serialVersionUID = -7277652200588032482L;

	static final Logger logger = LogManager.getLogger(NickMaker.class.getName());
    
	File folder;
	File[] list;
	
	String[] filearray;
    
    //Selection id(Determines whether format or color is used for placeAtCaret();)
    protected int selectionId;
	
    //Colors Array
    Color[] colorList = new Color[16];
    
    //Colors (Essentials)
    private String[] EssentialsCOL = {"&0", "&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8",
    		                       "&9", "&a", "&b", "&c", "&d", "&e", "&f"};
    private String[] EssentialsCOLNAME = {"Colors", "Black", "Dark Blue", "Dark Green", "Dark Aqua", 
    		                              "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Grey",
    		                              "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow",
    		                              "White"};
    //Formats (Essentials)
    private String[] EssentialsFOR = {"&k", "&l", "&m", "&n", "&o", "&r"};
    private String[] EssentialsFORNAME = {"Formats", "Obfuscated", "Bold", "Strikethrough",
    		                              "Underline", "Italic", "Reset"};
    
    File file, file1;
    
    //Components
    protected JOptionPane j, exists;
    protected JFrame frame = new JFrame();
    protected JLabel console, background, saveToText, saveToTextName, loadText, versions;
    protected JTextField nickName;
    protected JPanel panel = new JPanel();
    protected JScrollPane ScrollConsole, TextAreaScroll;
    protected JTextArea textArea, textAreaView;
    public static JTextArea Console2;
    protected JTextField sPrice, bPrice, nameField;
    protected Rectangle rsPrice, rbPrice, rnameField;
    protected JButton button0, button1, copy, cut, controls, close, save, load, check, showFullConsole;
	@SuppressWarnings("rawtypes")
	protected JComboBox ecolors, eformats, loadFile;
	protected ImageIcon icon1 = new ImageIcon("backFinal.jpg"); 
    protected String fileexistsexception, textAreaText;

	//Programs
	
	public NickMaker(int id){
	     setVisible(true);
	        try {
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        setTitle("Nick Maker");
	                if (id == 0) {
	            		setContentPane(panel);
	                	panel.setLayout(null);
	            setHexColor();
	            init();
	            run();
	            l.logInfoToConsole("serialVersionUID: " + serialVersionUID + " Name: Nick Name Maker " + "Class: " + this.getClass() + " Version: " + version);
	            repaint();
	            l.updateConsole();
	        }
	        
	        setSize(new Dimension(300, 600));
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setResizable(false);
	           
	        frame.setLayout(null);
	        
	        
	}
	
	public void colorizeTextArea(String text){
		String text2;
		for(int i = 0; i < 16; i++){
			if(text.contains(EssentialsCOL[i])){
				textArea.setForeground(colorList[i]);
			}
		}
	}
	
	public String getTextAreaText(String text){
		text = textArea.getText();
		return textAreaText = text;
	}
	
	public boolean hasColorCode(boolean hasColorCode){
		
		for(int i = 0; i < EssentialsCOL.length; i++){
			if(textArea.getText().contains(EssentialsCOL[i])){
				hasColorCode = true;
				l.logInfoToConsole("Contains color code id: " + EssentialsCOL[i]);
			} else {
				l.logInfoToConsole("Has no color code");
			}
		}
		
		return hasColorCode;
	}
	
	public boolean hasFormatCode(boolean hasFormatCode){
		
		for(int i = 0; i < EssentialsFOR.length; i++){
			if(textArea.getText().contains(EssentialsFOR[i])){
				hasFormatCode = true;
				l.logInfoToConsole("Contains color code id: " + EssentialsFOR[i]);
			} else {
				l.logInfoToConsole("Has no format code");
			}
		}
		
		return hasFormatCode;
		
	}
	
	public void replaceStringWithColor(String text){
		
	}
	
	public void init(){
		check();
		
		getFiles();
           //Button(sPrice)
        ecolors = new JComboBox(EssentialsCOLNAME);
        ecolors.setBounds(60, 20, 60, 20);
        panel.add(ecolors);
        
        eformats = new JComboBox(EssentialsFORNAME);
        eformats.setBounds(180, 20, 65, 20);
        panel.add(eformats);
        
        button0 = new JButton("Insert Color");
        button0.setBounds(50, 60, 100, 20);
        panel.add(button0);
        
        copy = new JButton("Copy Selection");
        copy.setBounds(45, 185, 105, 20);
        panel.add(copy);
        
        cut = new JButton("Cut Selection");
        cut.setBounds(155, 185, 100, 20);
        panel.add(cut);
        
        button1 = new JButton("Insert Format");
        button1.setBounds(150, 60, 100, 20); //x, y, Width, Length
        panel.add(button1);
        
        textArea = new JTextArea("Click controls for more options" + "\n" + "(Delete all text and input nickname)");
        TextAreaScroll = new JScrollPane(textArea);
        TextAreaScroll.setBounds(50, 80, 200, 100);
        panel.add(TextAreaScroll);
        
        console = new JLabel("Limited Console");
        console.setBounds(110, 210, 100, 50);
        panel.add(console);
        
        Console2 = new JTextArea();
        Console2.setEditable(false);
        ScrollConsole = new JScrollPane(Console2);
        ScrollConsole.setBounds(50, 250, 200, 100);
        panel.add(ScrollConsole);
        
        controls = new JButton("Controls & Help");
        controls.setBounds(0, 550, 125, 20);
        panel.add(controls);
        
        close = new JButton("Close");
        close.setBounds(214, 550, 80, 20);
        panel.add(close);
        
        save = new JButton("Save");
        save.setBounds(214, 500, 80, 20);
        panel.add(save);
        
        saveToText = new JLabel("Save Nick to a text File");
        saveToText.setBounds(100, 480, 130, 20);
        panel.add(saveToText);
        
        nickName = new JTextField("Put file name here");
        nickName.setBounds(115, 500, 95, 20);
        panel.add(nickName);
        
        loadText = new JLabel("Load file");
        loadText.setBounds(115, 435, 95, 20);
        panel.add(loadText);
        
        check = new JButton("Check [Debug]");
        check.setBounds(110, 420, 110, 20);
        panel.add(check);
        
        
        if(list.length <= 0){
        	loadFile = new JComboBox();
        } else {
            loadFile = new JComboBox(filearray);
        }
        loadFile.setBounds(60, 455, 140, 20);
        panel.add(loadFile);
        
        load = new JButton("Load");
        load.setBounds(214, 455, 80, 20);
        panel.add(load);
        
        versions = new JLabel("Version: " + version);
        versions.setBounds(05, 01, 70, 20);
        panel.add(versions);
        
        showFullConsole = new JButton("Show full console");
        showFullConsole.setBounds(50, 370, 95, 20);
        panel.add(showFullConsole);
        
	}
	
	public void setHexColor(){
		colorList[0] = Color.decode("#000000");
		colorList[1] = Color.decode("#0000AA");
		colorList[2] = Color.decode("#00AA00");
		colorList[3] = Color.decode("#00AAAA");
		colorList[4] = Color.decode("#AA0000");
		colorList[5] = Color.decode("#AA00AA");
		colorList[6] = Color.decode("#FFAA00");
		colorList[7] = Color.decode("#AAAAAA");
		colorList[8] = Color.decode("#555555");
		colorList[9] = Color.decode("#5555FF");
		colorList[10] = Color.decode("#55FF55");
		colorList[11] = Color.decode("#55FFFF");
		colorList[12] = Color.decode("#FF5555");
		colorList[13] = Color.decode("#FF55FF");
		colorList[14] = Color.decode("#FFFF55");
		colorList[15] = Color.decode("#FFFFFF");
	}
	
	public void placeAtCaret(){
	if (selectionId == 0) {
		
		switch(ecolors.getSelectedIndex()){
		
		case 0:
			textArea.insert("", textArea.getCaretPosition());
			
			l.logInfoToConsole("NULL");
			l.updateConsole();
			break;
		case 1:
			textArea.setCaretColor(colorList[0]);
			textArea.insert(EssentialsCOL[0], textArea.getCaretPosition());
			
			l.logInfoToConsole("Wrapped text with " + EssentialsCOLNAME[1] + " with id " + 
			EssentialsCOL[0] + " and " + colorList[0].toString());
			l.updateConsole();
			break;
		case 2:
			textArea.setCaretColor(colorList[1]);
			textArea.insert(EssentialsCOL[1], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[2] + " with id " + 
			EssentialsCOL[1] + " and " + colorList[1].toString());
			l.updateConsole();
			break;
		case 3:
			textArea.setCaretColor(colorList[2]);
			textArea.insert(EssentialsCOL[2], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[3] + " with id " + 
			EssentialsCOL[2] + " and " + colorList[2].toString());
			l.updateConsole();
			break;
		case 4:
			textArea.setCaretColor(colorList[3]);
			textArea.insert(EssentialsCOL[3], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[4] + " with id " + 
			EssentialsCOL[3] + " and " + colorList[3].toString());
			l.updateConsole();
			break;
			
		case 5:
			textArea.setCaretColor(colorList[4]);
			textArea.insert(EssentialsCOL[4], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[5] + " with id " + 
			EssentialsCOL[4] + " and " + colorList[4].toString());
			l.updateConsole();
			break;
			
		case 6:
			textArea.setCaretColor(colorList[5]);
			textArea.insert(EssentialsCOL[5], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[6] + " with id " + 
			EssentialsCOL[5] + " and " + colorList[5].toString());
			l.updateConsole();
			break;
			
		case 7:
			textArea.setCaretColor(colorList[6]);
			textArea.insert(EssentialsCOL[6], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[7] + " with id " + 
			EssentialsCOL[6] + " and " + colorList[6].toString());
			l.updateConsole();
			break;
			
		case 8:
			textArea.setCaretColor(colorList[7]);
			textArea.insert(EssentialsCOL[7], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[8] + " with id " + 
			EssentialsCOL[7] + " and " + colorList[7].toString());
			l.updateConsole();
			break;
			
		case 9:
			textArea.setCaretColor(colorList[8]);
			textArea.insert(EssentialsCOL[8], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[9] + " with id " + 
			EssentialsCOL[8] + " and " + colorList[8].toString());
			l.updateConsole();
			break;
			
		case 10:
			textArea.setCaretColor(colorList[9]);
			textArea.insert(EssentialsCOL[9], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[10] + " with id " + 
			EssentialsCOL[9] + " and " + colorList[9].toString());
			l.updateConsole();
			break;
			
		case 11:
			textArea.setCaretColor(colorList[10]);
			textArea.insert(EssentialsCOL[10], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[11] + " with id " + 
			EssentialsCOL[10] + " and " + colorList[10].toString());
			l.updateConsole();
			break;
			
		case 12:
			textArea.setCaretColor(colorList[11]);
			textArea.insert(EssentialsCOL[11], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[12] + " with id " + 
			EssentialsCOL[11] + " and " + colorList[11].toString());
			l.updateConsole();
			break;
			
		case 13:
			textArea.setCaretColor(colorList[12]);
			textArea.insert(EssentialsCOL[12], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[13] + " with id " + 
			EssentialsCOL[12] + " and " + colorList[12].toString());
			l.updateConsole();
			break;
			
		case 14:
			textArea.setCaretColor(colorList[13]);
			textArea.insert(EssentialsCOL[13], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[14] + " with id " + 
			EssentialsCOL[13] + " and " + colorList[13].toString());
			l.updateConsole();
			break;
			
		case 15:
			textArea.setCaretColor(colorList[14]);
			textArea.insert(EssentialsCOL[14], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[15] + " with id " + 
			EssentialsCOL[14] + " and " + colorList[14].toString());
			l.updateConsole();
			break;
			
		case 16:
			textArea.setCaretColor(colorList[15]);
			textArea.insert(EssentialsCOL[15], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Color " + EssentialsCOLNAME[16] + " with id " + 
			EssentialsCOL[15] + " and " + colorList[15].toString());
			l.updateConsole();
			break;
			
		}
	} else if(selectionId == 1) {
		switch(eformats.getSelectedIndex()){
		case 0:
			l.logInfoToConsole("NULL");
			l.updateConsole();
			break;
		case 1:
			textArea.insert(EssentialsFOR[0], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Format " + EssentialsFORNAME[1] + " with id " + 
			EssentialsFOR[0]);
			l.updateConsole();
			break;
			
		case 2:
			textArea.insert(EssentialsFOR[1], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Format " + EssentialsFORNAME[2] + " with id " + 
			EssentialsFOR[1]);
			l.updateConsole();
			break;
			
		case 3:
			textArea.insert(EssentialsFOR[2], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Format " + EssentialsFORNAME[3] + " with id " + 
			EssentialsFOR[2]);
			l.updateConsole();
			break;
			
		case 4:
			textArea.insert(EssentialsFOR[3], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Format " + EssentialsFORNAME[4] + " with id " + 
			EssentialsFOR[3]);
			l.updateConsole();
			break;
			
		case 5:
			textArea.insert(EssentialsFOR[4], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Format " + EssentialsFORNAME[5] + " with id " + 
			EssentialsFOR[4]);
			l.updateConsole();
			break;
			
		case 6:
			textArea.insert(EssentialsFOR[5], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Format " + EssentialsFORNAME[6] + " with id " + 
			EssentialsFOR[5]);
			l.updateConsole();
			break;
			
		case 7:
			textArea.insert(EssentialsFOR[6], textArea.getCaretPosition());
			
			l.logInfoToConsole("Inserted Format " + EssentialsFORNAME[7] + " with id " + 
			EssentialsFOR[6]);
			l.updateConsole();
			break;
		}
	}
}
	
	public void saveNick() {
		int x = 0;
		file1 = new File("nickmaker/");
		
		if (!file1.exists()) {
			if (file1.mkdir()) {
				l.logInfoToConsole("Directory is created!");
			} else {
				l.logInfoToConsole("Failed to create directory!");
			}
		}
		
		File file = new File("nickmaker/" + nickName.getText() + ".txt");
		
		if(file.exists()){
			l.logInfoToConsole("Nick file already exists! Please choose another name!");
			exists = new JOptionPane();
			exists.showMessageDialog(panel, "Nick file name already exists! Please choose another file name!" + "\n" +
			                         "Error: " + fileexistsexception);
		} else {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				l.logErrorMessageToConsole(e1.getMessage());
				l.writeCrashLogToSystem(e1.getMessage());
			}
			
			
			FileWriter wr;
			try {
				wr = new FileWriter(file.getAbsoluteFile());
				BufferedWriter write = new BufferedWriter(wr);
				write.write(textArea.getText());
				write.close();
			} catch (IOException e) {
				l.logErrorMessageToConsole(e.getMessage());
				l.writeCrashLogToSystem(e.getMessage());
			}
			
		}
		
	}
	
	public void getFiles(){
		
		if(list.length <= 0){
			l.logInfoToConsole("list: Null and skip");
		} else {
			filearray = new String[list.length];
			
			
			for(int i = 0; i < list.length; i++){
				filearray[i] = list[i].getName();
			}
		}

	}
	
	public void check(){
		folder = new File("nickMaker/");
		l.checkForDirectoryAndCreate(folder);
		list = folder.listFiles();
	}
	
	public void loadNick(){
    	String filesname = (String)loadFile.getSelectedItem();
    	
    	for (int i = 0; i < filearray.length; i++) {
        	if(filesname.equals(filearray[i])){
        		String filename = ("nickmaker/" + filesname);

        		try {
					FileReader reader = new FileReader(filename);
					BufferedReader br = new BufferedReader(reader);
					StringBuilder everything = new StringBuilder();
					try {
						String text;
						while((text = br.readLine()) != null){
							everything.append(text);
						}
						
					    textArea.setText(everything.toString());
					} catch (IOException e) {
						l.logErrorMessageToConsole(e.getMessage());
						l.writeCrashLogToSystem(e.getMessage());
					}
				} catch (FileNotFoundException e1) {
					l.logErrorMessageToConsole(e1.getMessage());
					l.writeCrashLogToSystem(e1.getMessage());
				} 

        	}
    	}
	}
	
	public void placeColorAroundSelection() {
		switch(ecolors.getSelectedIndex()){
		
		case 0:
			textArea.insert("", textArea.getSelectionStart());
			
			l.logInfoToConsole("NULL");
			l.updateConsole();
			break;
		case 1:
			textArea.setCaretColor(colorList[0]);
			textArea.insert(EssentialsCOL[0], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[1] + " with color id " + 
			EssentialsCOL[0] + " and " + colorList[0].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
		case 2:
			textArea.setCaretColor(colorList[1]);
			textArea.insert(EssentialsCOL[1], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[2] + " with color id " + 
					EssentialsCOL[1] + " and " + colorList[1].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
		case 3:
			textArea.setCaretColor(colorList[2]);
			textArea.insert(EssentialsCOL[2], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[3] + " with color id " + 
					EssentialsCOL[2] + " and " + colorList[2].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
		case 4:
			textArea.setCaretColor(colorList[3]);
			textArea.insert(EssentialsCOL[3], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[4] + " with color id " + 
					EssentialsCOL[3] + " and " + colorList[3].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
			
		case 5:
			textArea.setCaretColor(colorList[4]);
			textArea.insert(EssentialsCOL[4], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[5] + " with color id " + 
					EssentialsCOL[4] + " and " + colorList[4].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
			
		case 6:
			textArea.setCaretColor(colorList[5]);
			textArea.insert(EssentialsCOL[5], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[6] + " with color id " + 
					EssentialsCOL[5] + " and " + colorList[5].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
			
		case 7:
			textArea.setCaretColor(colorList[6]);
			textArea.insert(EssentialsCOL[6], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[7] + " with color id " + 
					EssentialsCOL[6] + " and " + colorList[6].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
			
		case 8:
			textArea.setCaretColor(colorList[7]);
			textArea.insert(EssentialsCOL[7], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[8] + " with color id " + 
					EssentialsCOL[7] + " and " + colorList[7].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
			
		case 9:
			textArea.setCaretColor(colorList[8]);
			textArea.insert(EssentialsCOL[8], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[9] + " with color id " + 
					EssentialsCOL[8] + " and " + colorList[8].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
			
		case 10:
			textArea.setCaretColor(colorList[9]);
			textArea.insert(EssentialsCOL[9], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[10] + " with color id " + 
					EssentialsCOL[9] + " and " + colorList[9].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
			
		case 11:
			textArea.setCaretColor(colorList[10]);
			textArea.insert(EssentialsCOL[10], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[11] + " with color id " + 
					EssentialsCOL[10] + " and " + colorList[10].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
			
		case 12:
			textArea.setCaretColor(colorList[11]);
			textArea.insert(EssentialsCOL[11], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[12] + " with color id " + 
					EssentialsCOL[11] + " and " + colorList[11].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
			
		case 13:
			textArea.setCaretColor(colorList[12]);
			textArea.insert(EssentialsCOL[12], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[13] + " with color id " + 
					EssentialsCOL[12] + " and " + colorList[12].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
			
		case 14:
			textArea.setCaretColor(colorList[13]);
			textArea.insert(EssentialsCOL[13], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[14] + " with color id " + 
					EssentialsCOL[13] + " and " + colorList[13].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
			
		case 15:
			textArea.setCaretColor(colorList[14]);
			textArea.insert(EssentialsCOL[14], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[15] + " with color id " + 
					EssentialsCOL[14] + " and " + colorList[14].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
			
		case 16:
			textArea.setCaretColor(colorList[15]);
			textArea.insert(EssentialsCOL[15], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Wrapped text with color " + EssentialsCOLNAME[16] + " with color id " + 
					EssentialsCOL[15] + " and " + colorList[15].toString() + " and format " + EssentialsFORNAME[6] + " with id " + EssentialsFOR[5]);
			l.updateConsole();
			break;
		}
		
	}
	
	
	public void placeFormatAroundSelection(){
		switch(eformats.getSelectedIndex()){
		case 0:
			l.logInfoToConsole("NULL");
			break;
		case 1:
			textArea.insert(EssentialsFOR[0], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Inserted Format " + EssentialsFORNAME[1] + " with id " + 
			EssentialsFOR[0]);
			l.updateConsole();
			break;
			
		case 2:
			textArea.insert(EssentialsFOR[1], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Inserted Format " + EssentialsFORNAME[2] + " with id " + 
			EssentialsFOR[1]);
			l.updateConsole();
			break;
			
		case 3:
			textArea.insert(EssentialsFOR[2], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Inserted Format " + EssentialsFORNAME[3] + " with id " + 
			EssentialsFOR[2]);
			l.updateConsole();
			break;
			
		case 4:
			textArea.insert(EssentialsFOR[3], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Inserted Format " + EssentialsFORNAME[4] + " with id " + 
			EssentialsFOR[3]);
			l.updateConsole();
			break;
			
		case 5:
			textArea.insert(EssentialsFOR[4], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Inserted Format " + EssentialsFORNAME[5] + " with id " + 
			EssentialsFOR[4]);
			l.updateConsole();
			break;
			
		case 6:
			textArea.insert(EssentialsFOR[5], textArea.getSelectionStart());
			textArea.insert(EssentialsFOR[5], textArea.getSelectionEnd());
			
			l.logInfoToConsole("Inserted Format " + EssentialsFORNAME[6] + " with id " + 
			EssentialsFOR[5]);
			l.updateConsole();
			break;
		}
	}

	public void run(){

		textArea.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_F6){
	            	selectionId = 0;
	            	l.logInfoToConsole("Running placeAtCaret(); (Method:KEY_F6) ID: " + selectionId);
	            	l.updateConsole();
	            	
	            	placeAtCaret();
				} else if(e.getKeyCode() == KeyEvent.VK_F7){
	            	selectionId = 1;
	            	l.logInfoToConsole("Running placeAtCaret(); (Method:KEY_F7) ID: " + selectionId);
	            	l.updateConsole();
	            	
	            	placeAtCaret();
				} else if(e.getKeyCode() == KeyEvent.VK_F5){
					textArea.copy();
					l.logInfoToConsole("Ran copy(); (Method:KEY_F5) - Text should be copied to clipboard");
					l.updateConsole();
				} else if(e.getKeyCode() == KeyEvent.VK_F4){
					textArea.cut();
					l.logInfoToConsole("Ran cut(); (Method:KEY_F4) - Text should be copied to clipboard");
					l.updateConsole();
				} else if(e.getKeyCode() == KeyEvent.VK_F3){
					placeColorAroundSelection();
				} else if(e.getKeyCode() == KeyEvent.VK_F2){
					placeFormatAroundSelection();
				}
			}
			
			public void keyTyped(KeyEvent e){
				
			}
			
			public void keyReleased(KeyEvent e){
				
			}
		});
		
        button0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	selectionId = 0;
            	l.logInfoToConsole("Running placeAtCaret(); (Method:button) ID: " + selectionId);
            	l.updateConsole();
            	
            	placeAtCaret();
            }
            
        });
        
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	selectionId = 1;
            	l.logInfoToConsole("Running placeAtCaret(); (Method:button) ID: " + selectionId);
            	l.updateConsole();
            	
            	placeAtCaret();
            }
            
        });
        
        controls.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	l.logInfoToConsole("Opening Controls/Help Window");
            	j = new JOptionPane();
            	
            	j.showMessageDialog(panel, "F2: Wrap selected text with format code of choice" + "\n" +
                                            "F3: Wrap selected text with color code of choice" + "\n" +
            			                    "F4: Cut the selection and copy it to clipboard" + "\n" +
                                            "F5: Copy Selection to clipboard" + "\n" +
            			                    "F6: Place color code at caret" + "\n" +
                                            "F7: Place format code at caret" + "\n" +
            			                    "For more detailed console logs check the folder 'logs' in your install directory");
            }
            
        });
        
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	textArea.copy();
            	
            	l.logInfoToConsole("Ran copy(); (Method:button) - Text should be copied to clipboard");
            	l.updateConsole();
            }
            
        });
        
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	l.logInfoToConsole("Closing with System.exit(0)");
            	l.updateConsole();
            	System.exit(0);
            }
            
        });
		
		
        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	textArea.cut();
            	
            	l.logInfoToConsole("Ran cut(); (Method:button) - Text should be cut and copied to clipboard");
            	l.updateConsole();
            }
            
        });
        
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	saveNick();
            	folder = new File("nickMaker/");
            	list = folder.listFiles();
        		getFiles();
        		loadFile.removeAllItems();
        		for(int i = 0; i < filearray.length; i++){
        			loadFile.addItem(filearray[i]);
        		}
        		panel.validate();
            	
        		l.logInfoToConsole("Ran saveNick(); (Method:button) - Text should be saved to file");
        		l.updateConsole();
            }
            
        });
        
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	loadNick();
            	
            	l.logInfoToConsole("Ran loadNick(); (Method:button) - TextArea one should be updated");
            	l.updateConsole();
            }
            
        });
        
        check.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	boolean hasColor = false;
            	boolean hasFormat = false;
            	
            	hasColorCode(hasColor);
            	hasFormatCode(hasFormat);
            	getTextAreaText(textArea.getText());
            	colorizeTextArea(textAreaText);
            	
            	l.logInfoToConsole("hasColor = " + String.valueOf(hasColor));
            	l.logInfoToConsole("hasFormat = " + String.valueOf(hasFormat));
            	l.updateConsole();
            }
            
        });
        
        showFullConsole.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new FullConsole(1);
            }
            
        });
        
	}
  }
