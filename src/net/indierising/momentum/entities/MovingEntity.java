package net.indierising.momentum.entities;

public class MovingEntity extends Entity{
	private float speed;
	int direction;
	
	public MovingEntity(float x,float y,int width,int height,float speed,int direction){
		super(x,y,width,height);
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
