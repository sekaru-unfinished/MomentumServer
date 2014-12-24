package net.indierising.momentum;

import net.indierising.momentum.entities.GameObject;
import net.indierising.momentum.entities.Property;

public class Functions {

	// gets the property name
		public static Property findProperty(GameObject object,String propertyTitle){
			for(int i = 0; i < object.properties.size(); i++){
				if(object.properties.get(i).getName().equals(propertyTitle)){
					// we have a match!
					return object.properties.get(i);
				}
			}
			// if we can't find any then there is something messed up with the object being sent
			return null;
		}
		
		// sets the property data to what we want, used for image loading.
		public static void setProperty(GameObject object,String propertyTitle,Object data){
			for(int i = 0; i < object.properties.size(); i++){
				if(object.properties.get(i).getName().equals(propertyTitle)){
					object.properties.get(i).setData(data);
				}
			}
		}
		
		
}
