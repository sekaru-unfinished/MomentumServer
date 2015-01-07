package net.indierising.momentum.server.maps;

import java.io.File;

import net.indierising.momentum.server.utils.TagReader;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class MapData {
	File dataFile;
	String name; int attributesLayer, mapType;
	TiledMap map; Rectangle blockedRect[][];
	
	public MapData(String path) throws SlickException {
		map = new TiledMap(path + ".tmx");
		
		// load up the data file for reading
		dataFile = new File(path + ".mo");
		TagReader reader = new TagReader(dataFile);
		
		// get some useful info about the map
		name = reader.findData("map", "A Map");
		attributesLayer = Integer.valueOf(reader.findData("attributes", "0"));
		mapType = Integer.valueOf(reader.findData("type", "0"));
		
		// search for NPCs
		for(int i=0; i<Maps.MAX_MAP_NPCS; i++) {
			if(reader.findData("npc" + (i+1))!=null) {
				// TODO add to an NPC arraylist
			}
		}
		
		// set up the map and its attributes
		loadMap();
	}
	
	private void loadMap() {
		int tileID; String value;
		
		blockedRect = new Rectangle[map.getWidth()][map.getHeight()];
		for(int x=0; x<map.getWidth(); x++) {
			for(int y=0; y<map.getHeight(); y++) {
				// get the tile ID
				tileID = map.getTileId(x, y, attributesLayer);
				
				// check for blocked tiles
				value = map.getTileProperty(tileID, "blocked", "false");
				if(value.equals("true")) {
					blockedRect[x][y] = new Rectangle(x*Maps.TILE_SIZE, y*Maps.TILE_SIZE, Maps.TILE_SIZE, Maps.TILE_SIZE);
				} else {
					blockedRect[x][y] = new Rectangle(0, 0, 0, 0);
				}
			}
		}
	}
}
