package net.indierising.momentum.entities;

import net.indierising.momentum.network.Network;

public class MovingEntity extends Entity{
	int id;
	private float speed;
	int direction;
	private String imageLocation;
	
	public MovingEntity(int id,float x,float y,int width,int height,float speed,int direction,String imageLocation){
		super(x,y,width,height);
		this.id = id;
		this.setSpeed(speed);
		this.direction = direction;
		this.setImageLocation(imageLocation);
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void update(int delta) {
		setX(getX() + 0.3f);
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}
}
