package net.indierising.momentum.server.maps;

import java.io.File;

import net.indierising.momentum.server.utils.TagReader;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class MapData {
	File dataFile;
	String name; int mapType;
	public TiledMap map; public Rectangle blockedRect[][];
	
	public MapData(String mapNum) throws SlickException {
		map = new TiledMap(Maps.MAP_PATH + mapNum + ".tmx");
		
		// load up the data file for reading
		dataFile = new File(Maps.MAP_PATH + "properties/" + mapNum + ".mo");
		TagReader reader = new TagReader(dataFile);
		
		// get some useful info about the map
		name = reader.findData("map", "A Map");
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
				tileID = map.getTileId(x, y, map.getLayerIndex("Attributes"));
				
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
