package net.indierising.momentum.entities;

/**
 * stores property names with a name attribute.
 */
public class Property {
	private String name;
	private Object data;// stores the variable relating to the property.
	
	public Property(String name,Object data){
		this.name = name;
		this.data = data;
	}
	
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public Object getData(){
		return data;
	}
	
	public void setData(Object data){
		this.data = data;
	}
}
