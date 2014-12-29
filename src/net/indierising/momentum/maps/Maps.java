package net.indierising.momentum.maps;

import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

public class Maps {
	public static ArrayList<MapData> maps = new ArrayList<MapData>();
	
	public static void initMaps() throws SlickException {
		// get the file count
		int mapCount = new File("data").list().length;
		
		// add them to the list of our maps
		for(int i=0; i<mapCount; i++) {
			maps.add(new MapData("data/map" + (i+1)));
		}
	}
}
