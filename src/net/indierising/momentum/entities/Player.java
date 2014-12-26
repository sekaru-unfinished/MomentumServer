package net.indierising.momentum.entities;

import net.indierising.momentum.network.Network;

public class Player extends MovingEntity{
	
	boolean up,down,left,right;
	private int connectionID;
	
	public Player(int connectionID,float x,float y,int direction){
		// set our player up with the speed and a width and height of 32.
		super(x,y,32,32,0.3f,direction);
		this.setConnectionID(connectionID);
	}
	
	public void update(int delta){
		float dx = 0, dy = 0;
		if(up){
			dy-=speed;
		}
		if(down){
			dy+=speed;
		}
		if(left){
			dx-=speed;
		}
		if(right){
			dx+=speed;
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
}
