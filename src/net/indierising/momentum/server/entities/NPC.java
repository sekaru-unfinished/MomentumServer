package net.indierising.momentum.server.entities;


import org.newdawn.slick.geom.Vector2f;

public class NPC extends Entity {
	private int health,damage;
	private String name;
	
	public NPC(int id, Vector2f pos, float width, float height, float speed, int direction, String imageLocation,int health,int damage,String name) {
		super(id, pos, width, height, speed, direction, imageLocation);
		this.setHealth(health);
		this.setDamage(damage);
		this.setName(name);
		this.setCollisionBox(getX(), getY(), getWidth(), getHeight());
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	
}
