package net.indierising.momentum.maps;

import java.util.ArrayList;

import net.indierising.momentum.Constants;

import org.newdawn.slick.SlickException;

public class Maps {
	public static final int MAP_TYPE_NORMAL = 0;
	public static final int MAP_TYPE_PVP = 1;
	
	public static final int MAX_MAP_NPCS = 30;
	public static ArrayList<MapData> maps = new ArrayList<MapData>();
	
	public static void initMaps() throws SlickException {
		// add them to the list of our maps
		for(int i=0; i<Constants.MAX_MAPS; i++) {
			maps.add(new MapData("data/maps/map" + (i+1)));
		}
	}
}
