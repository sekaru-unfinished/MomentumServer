package net.indierising.momentum.entities;

import net.indierising.momentum.network.Network;
import net.indierising.momentum.network.Network.Key;

import org.lwjgl.input.Keyboard;

public class Player extends MovingEntity{
	
	boolean up,down,left,right;
	private int connectionID;
	private String username;

	public Player(int connectionID,String username,float x,float y,int direction,String imageLocation){
		// set our player up with the speed and a width and height of 32.
		super(connectionID,x,y,32,32,0.3f,direction,imageLocation);
		this.setConnectionID(connectionID);
		this.setUsername(username);
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
}
