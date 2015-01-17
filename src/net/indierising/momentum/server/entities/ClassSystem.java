package net.indierising.momentum.server.entities;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import net.indierising.momentum.server.utils.TagReader;
import net.indierising.momentum.server.network.Packets.PlayerClass;;
/**
 * This class contains information about player classes, including definitions and loading.
 */
public class ClassSystem {
	public static ArrayList<PlayerClass> data = new ArrayList<PlayerClass>();
	
	public static void loadClasses(){
		File f = new File("data/entities/classes");
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith("dat");
		    }
		});
		for(int i = 0; i < matchingFiles.length; i++){
			TagReader reader = new TagReader(new File("data/entities/classes/" + matchingFiles[i].getName()));
			try {
				reader.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// create a temporary class to add to our data
			PlayerClass temp = new PlayerClass();
			temp.name = reader.findData("name");
			temp.description = reader.findData("description");
			temp.id = i;
			temp.damage = Integer.parseInt(reader.findData("damage"));
			temp.health = Integer.parseInt(reader.findData("health"));
			
			data.add(temp);
			System.out.println(temp.name);
		}
	}

}
