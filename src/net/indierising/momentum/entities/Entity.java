package net.indierising.momentum.entities;

public class Entity {
	// change to vector 2f?
	private float x,y;
	private String name;// the entities name referenced in the .txt
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public String getName(){
		return name;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
