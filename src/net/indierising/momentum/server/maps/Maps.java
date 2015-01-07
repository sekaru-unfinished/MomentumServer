package net.indierising.momentum.server.maps;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

public class Maps {
	public static final int TILE_SIZE = 32;
	public static final int MAX_MAPS = 1;
	
	public static final int MAP_TYPE_NORMAL = 0;
	public static final int MAP_TYPE_PVP = 1;
	
	public static final int MAX_MAP_NPCS = 30;
	public static ArrayList<MapData> maps = new ArrayList<MapData>();
	
	public static void initMaps() throws SlickException {
		maps.clear();
		
		// add them to the list of our maps
		for(int i=0; i<MAX_MAPS; i++) {
			maps.add(new MapData("data/maps/map" + (i+1)));
		}
	}
}
