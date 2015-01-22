package net.indierising.momentum.server.entities;

import net.indierising.momentum.server.Globals;
import net.indierising.momentum.server.entitydata.PlayerData;
import net.indierising.momentum.server.maps.Maps;
import net.indierising.momentum.server.network.Network;
import net.indierising.momentum.server.network.Packets.Key;
import net.indierising.momentum.server.network.Packets.PlayerClass;
import net.indierising.momentum.server.network.Packets.PlayerMapChange;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Player extends Entity {
	public final static float WIDTH = 32, HEIGHT = 64;
	
	boolean up, down, left, right;
	private int connectionID;
	private String username;
	private int map;
	private PlayerClass playerClass;

	public Player(PlayerData data) {
		super(data.connectionID, new Vector2f(data.x, data.y), WIDTH, HEIGHT, 4f, data.dir, data.imageLoc);
		this.setConnectionID(data.connectionID);
		this.setUsername(data.username);
		this.setMap(data.map);
		this.setPlayerClass(data.playerClass);
		this.setCollisionBox(getX(), getY(), getWidth(), getHeight());
	}

	public void update(int delta){
		float dx = 0, dy = 0;
		
		if(up) {
			dy-=getSpeed();
			dir = Globals.DIR_UP;
		}
		
		if(down) {
			dy+=getSpeed();
			dir = Globals.DIR_DOWN;
		}
		
		if(left) {
			dx-=getSpeed();
			dir = Globals.DIR_LEFT;
		}
		
		if(right) {
			dx+=getSpeed();
			dir = Globals.DIR_RIGHT;
		}
		
		float nx = getX()+dx;
		float ny = getY()+dy;
		if(dx != 0 || dy != 0){
			if(!isBlocked(nx,ny)){
				setX(nx);
				setY(ny);
				// send this to our players
				Network.sendMovement(getConnectionID());
			}
		}
	}
	
	public void setKeys(Key packet){
		// sets the keys that were sent
		if(packet.key == Keyboard.KEY_W) {
			up = packet.pressed;
		} else if(packet.key == Keyboard.KEY_A) {
			left = packet.pressed;
		} else if(packet.key == Keyboard.KEY_S) {
			down = packet.pressed;
		} else if(packet.key == Keyboard.KEY_D) {
			right = packet.pressed;
		}
	}
	
	public boolean isBlocked(float nx, float ny) {
		// set the collision box to this position
		Rectangle collisionBox = new Rectangle(nx, ny+getHeight()/2, getWidth(), getHeight()/2);
		
		float mapW = Maps.maps.get(getMap()).map.getWidth();
		float mapH = Maps.maps.get(getMap()).map.getHeight();
		
		for(int x=0; x<mapW; x++) {
			for(int y=0; y<mapH; y++) {
				if(collisionBox.intersects(Maps.maps.get(getMap()).blockedRect[x][y])) {
					return true;
				}
			}
		}
		
		// check if they've gone outside the map
		if(getY()<0) {
			// north
			if(Maps.maps.get(getMap()).nextMap[Globals.DIR_UP]!=-1) {
				PlayerMapChange packet = new PlayerMapChange();
				packet.playerID = getConnectionID();
				packet.mapID = Maps.maps.get(getMap()).nextMap[Globals.DIR_UP]-1;
				packet.mapName = Maps.maps.get(packet.mapID).name;
				Network.server.sendToAllTCP(packet);
			}
		} else if(getY()+getHeight()/2>Maps.maps.get(getMap()).map.getHeight()*Maps.TILE_SIZE) {
			// south
			if(Maps.maps.get(getMap()).nextMap[Globals.DIR_DOWN]!=-1) {
				PlayerMapChange packet = new PlayerMapChange();
				packet.playerID = getConnectionID();
				packet.mapID = Maps.maps.get(getMap()).nextMap[Globals.DIR_DOWN]-1;
				packet.mapName = Maps.maps.get(packet.mapID).name;
				Network.server.sendToAllTCP(packet);
			}
		}
		
		return false;
	}
	
	public int getConnectionID() {
		return connectionID;
	}

	public void setConnectionID(int connectionID) {
		this.connectionID = connectionID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getMap() {
		return map;
	}
	
	public void setMap(int map) {
		this.map = map;
	}
	
	// convert to a sendable object
	public PlayerData toPlayerData() {
		return new PlayerData(this);
	}

	public void setPlayerClass(PlayerClass playerClass) {
		this.playerClass = playerClass;
	}

	public PlayerClass getPlayerClass() {
		return playerClass;
	}
}
