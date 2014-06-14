package nick.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logging {

	boolean isUpdated;
	Random rand = new Random();
	int crashLogID = rand.nextInt(5000);
	static final Logger logger = LogManager.getLogger(Logging.class.getName());
	String directoryString = ("crash-logs/");
	String file = ("crash-logs/crashlog" + "[" + crashLogID + "]" + ".txt");
	
	String[] logDataArray;
	ArrayList<String> logDataArrayList = new ArrayList<String>();
	
	public Logging(){
	}
	
	public void logInfoToConsole(String info){
		logger.info(info);
	}
	
	public void logErrorMessageToConsole(String message){
		logger.error(message);
	}
	
	public void setTextColor(String text){
		text = "Hello I am Josh sir.";
		
		if(text.contains("&4")){
			text = ("");
		} else {
			logInfoToConsole("Hi");
		}
		
		}
	
	public void checkForDirectoryAndCreate(File file){
		logInfoToConsole("Checking to see if directory exists...");
		if(!file.exists()){
			logInfoToConsole("Directory does not exist");
			file.mkdir();
			logInfoToConsole("Directory Created: " + file.getAbsolutePath());
		} else {
			logInfoToConsole("Directory "+ file.getAbsolutePath() + " Already Exists");
		}
	}
	
	
	public void getLogData(){
      
      logDataArrayList.clear();
      BufferedReader br = null;
        
        try {
        	String sCurrentLine;
        	
        	br = new BufferedReader(new FileReader("logs/app.log"));
        	while ((sCurrentLine = br.readLine()) != null){
        		logDataArrayList.add(sCurrentLine.toString());
        	}
        	
        } catch (IOException e){
        	logErrorMessageToConsole(e.getMessage());
        	writeCrashLogToSystem(e.getMessage());
        } finally {
        	try {
        		if (br != null)br.close();
        	} catch (IOException ex){
        		logErrorMessageToConsole(ex.getMessage());
        		writeCrashLogToSystem(ex.getMessage());
        	}
        }
	}
	
	public void updateConsole(){
		getLogData();
		
		NickMaker.Console2.setText(null);
    	for(String logData : logDataArrayList){
    		NickMaker.Console2.insert(logData + "\n", 0);
    	}
    	
    	if(FullConsole.fullConsoleBool == true){
    		FullConsole.fullConsole.setText(null);
    		for(String logData : logDataArrayList){
    			FullConsole.fullConsole.insert(logData + "\n", 0);
    		}
    	}
		
	}
	
	public void writeCrashLogToSystem(String log){
		File directory = new File(directoryString);
		checkForDirectoryAndCreate(directory);
	    File logfile = new File(file);
		
		try {
			if(!logfile.exists()){
				FileWriter writer = new FileWriter(logfile);
				BufferedWriter br = new BufferedWriter(writer);
				br.write(log);
				br.close();
			} else {
				logInfoToConsole("Crash Log File Already Exists! Creating new.......");
				file = ("crash-logs/crashlog" + "[" + (crashLogID * 6) + "]" + ".txt");
				logfile = new File(file);
				FileWriter writer = new FileWriter(logfile);
				BufferedWriter br = new BufferedWriter(writer);
				br.write(log);
				br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			logInfoToConsole(e.getMessage());
		}
		
	}
	
}
