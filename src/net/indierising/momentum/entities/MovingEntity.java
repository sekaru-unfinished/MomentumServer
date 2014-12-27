package net.indierising.momentum.entities;

import org.newdawn.slick.geom.Vector2f;

public class MovingEntity extends Entity{
	int id;
	private float speed;
	int direction;
	private String imageLocation;
	
	public MovingEntity(int id, Vector2f pos, float width, float height, float speed, int direction, String imageLocation){
		super(pos, width, height);
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
