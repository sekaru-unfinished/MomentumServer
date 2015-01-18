package net.indierising.momentum.server.entities;


import net.indierising.momentum.server.entitydata.NPCData;

import org.newdawn.slick.geom.Vector2f;

public class NPC extends Entity {
	private String name;
	private float health, damage;
	
	public NPC(NPCData data) {
		super(data.id, new Vector2f(data.x, data.y), data.width, data.height, data.speed, data.dir, data.imageLoc);
		this.setHealth(health);
		this.setDamage(damage);
		this.setName(name);
		this.setCollisionBox(getX(), getY(), getWidth(), getHeight());
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setHealth(float health) {
		this.health = health;
	}

	public float getHealth() {
		return health;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public float getDamage() {
		return damage;
	}
	
	// convert to a sendable object
	public NPCData toNPCData() {
		return new NPCData(this);
	}
}
