package net.indierising.momentum.maps;

import java.io.File;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class MapData {
	File dataFile;
	Rectangle blockedRect[][];
	
	public MapData(String path) throws SlickException {
		TiledMap map = new TiledMap(path + ".tmx");
		
		dataFile = new File(path + ".mo");
	}
}
