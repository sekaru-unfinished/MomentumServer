package net.indierising.momentum.server.entities;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Entity {
	int id;
	private float width, height;
	protected Vector2f pos;
	private int dir;
	private float speed;
	private String imageLoc;
	private Rectangle entityRect;
	
	public Entity(int id, Vector2f pos, float width, float height, float speed, int dir, String imageLocation){
		this.pos = pos;
		this.setWidth(width);
		this.setHeight(height);
		this.id = id;
		this.setSpeed(speed);
		this.dir = dir;
		this.setImageLoc(imageLocation);
	}
	
	public int getID() {
		return id;
	}
	
	public String getImageLoc() {
		return imageLoc;
	}

	public void setImageLoc(String location) {
		this.imageLoc = location;
	}

	public float getX() {
		return pos.getX();
	}

	public void setX(float x) {
		pos.set(x, pos.getY());
		entityRect.setX(x);
	}

	public float getY() {
		return pos.getY();
	}

	public void setY(float y) {
		pos.set(pos.getX(), y);
		entityRect.setX(y);
	}
	
	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}
	
	public void setCollisionBox(float x, float y, float w, float h) {
		entityRect = new Rectangle(x, y, w, h);
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void update(int delta) {}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getWidth() {
		return width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getHeight() {
		return height;
	}
}
