package net.indierising.momentum.server.entities;

import net.indierising.momentum.server.Globals;
import net.indierising.momentum.server.entitydata.PlayerData;
import net.indierising.momentum.server.network.Network;
import net.indierising.momentum.server.network.Packets.Key;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.geom.Vector2f;

public class Player extends Entity {
	boolean up, down, left, right;
	private int connectionID;
	private String username;

	public Player(PlayerData data) {
		super(data.connectionID, new Vector2f(data.x, data.y), Globals.TILE_SIZE, Globals.TILE_SIZE, 0.3f, data.dir, data.imageLoc);
		this.setConnectionID(data.connectionID);
		this.setUsername(data.username);
	}

	public void update(int delta){
		float dx = 0, dy = 0;
		if(up){
			dy-=getSpeed();
		}
		if(down){
			dy+=getSpeed();
		}
		if(left){
			dx-=getSpeed();
		}
		if(right){
			dx+=getSpeed();
		}
		
		float nx = getX()+dx;
		float ny = getY()+dy;
		if(dx != 0 || dy != 0){
			if(clearLocation(nx,ny)){
				setX(nx);
				setY(ny);
				// send this to our players
				Network.sendMovement(getConnectionID());
			}
		}
	}
	
	public void setKeys(Key packet){
		System.out.println(packet.key + " " + packet.pressed);
		// sets the keys that were sent
		if(packet.key == Keyboard.KEY_W){
			up = packet.pressed;
		}else if(packet.key == Keyboard.KEY_A){
			left = packet.pressed;
		}else if(packet.key == Keyboard.KEY_S){
			down = packet.pressed;
		}else if(packet.key == Keyboard.KEY_D){
			right = packet.pressed;
		}
	}
	
	// TODO collisions
	public boolean clearLocation(float nx,float ny){
		return true;
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
	
	// convert to a sendable object
	public PlayerData toPlayerData() {
		return new PlayerData(this);
	}
}
