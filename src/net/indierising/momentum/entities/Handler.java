package net.indierising.momentum.entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import net.indierising.momentum.utils.TagReader;

public class Handler {
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	
	public Handler() throws IOException{
		// find all files ending in .go
		File f = new File("data/entities/");
		
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith("go");
		    }	    
		});
	
		for(int i = 0; i < matchingFiles.length; i++){
			// for every file found load a new game object
			GameObject newObject = new GameObject();
			
			// create a tag reader for the new object, get its file name via the matching files that have been found ending in .go
			TagReader reader = new TagReader(new FileInputStream(matchingFiles[i]));
			
			// load data
			reader.read();

			// give the gameobject a name
			String name = reader.findData("name");
			newObject.properties.add(new Property(name,name));
			for(int e = 0; e < reader.lines.size(); e++){
				// if its not the name attribute add it
				if(!reader.lines.get(e).equals(name)){
					// use the attribute name to store the attribute under and then get the associated data
					// really ffucking long line, gets the variable type and stores it
					newObject.properties.add(new Property(reader.getTagName(e),getVariable(reader.findData(reader.getTagName(e)))));
				}
			}
			newObject.describe();
		}
	}
	
	public Object getVariable(String s){
		Object object;
		if(isInteger(s)){
			object = Integer.parseInt(s);
			return object;
		}else if(isFloat(s)){
			object = Float.parseFloat(s);
			return object;
		}else if(isBoolean(s)){
			object = Boolean.parseBoolean(s);
			return object;
		}else if(isDouble(s)){
			object = Double.parseDouble(s);
			return object;
		}else{
			object = s;
			return object;
		}
	}
	
	// finds out what variable the data is
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	public static boolean isFloat(String s) {
	    try { 
	        Float.parseFloat(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	public static boolean isBoolean(String s) {
	    try { 
	        Boolean.parseBoolean(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	public static boolean isDouble(String s) {
	    try { 
	        Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
}
