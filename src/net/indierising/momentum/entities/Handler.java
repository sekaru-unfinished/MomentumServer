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
			
			// go through the data and print it out.
			for(int e = 0; e < reader.lines.size(); e++){
				String line = reader.lines.get(e);
				System.out.println(line);
			}
		}
	}
}
