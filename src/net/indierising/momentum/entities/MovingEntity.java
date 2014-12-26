package net.indierising.momentum.entities;

import org.newdawn.slick.geom.Vector2f;

public class MovingEntity extends Entity{
	private float speed;
	int direction;
	
	public MovingEntity(Vector2f pos, float width, float height, float speed, int direction){
		super(pos, width, height);
		this.setSpeed(speed);
		this.direction = direction;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
