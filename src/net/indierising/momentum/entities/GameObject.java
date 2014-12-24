package net.indierising.momentum.entities;

import java.util.ArrayList;

public class GameObject{
	ArrayList<Property> properties = new ArrayList<Property>();
	
	public void describe(){
		for(int i = 0; i < properties.size(); i++){
			System.out.println(properties.get(i).getName() + "--");
			System.out.println(properties.get(i).getData().getClass().getName());
		}
	}
	
	
}
