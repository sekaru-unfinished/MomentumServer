package net.indierising.momentum.server.entities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import net.indierising.momentum.server.network.Network;
import net.indierising.momentum.server.network.Packets.PlayerPacket;
import net.indierising.momentum.server.utils.TagReader;

public class EntityHandler {
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static ArrayList<NPC> npcs = new ArrayList<NPC>();
	
	public static void update(int delta){
		for(int i = 0; i < players.size(); i++){
			players.get(i).update(delta);
		}
		for(int i = 0; i < npcs.size(); i++){
			npcs.get(i).update(delta);
			Network.sendNPCMovement(npcs.get(i).id);
		}
	}
	
	public static Player getPlayerByID(int connectionID){
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getConnectionID() == connectionID){
				return players.get(i);
			}
		}
		// if we can't find them sorry
		return null;
	}
	
	public static NPC getNPCByID(int id){
		for(int i = 0; i < npcs.size(); i++){
			if(npcs.get(i).id == id){
				return npcs.get(i);
			}
		}
		// if we can't find them sorry
		return null;
	}

	// check if we have the player saved, otherwise create a new file with their username
	public static void addPlayer(PlayerPacket packet) throws IOException{
		float x = 0, y = 0;
		File userData = new File("data/entities/players/" + packet.data.username + ".dat");
		
		if(!userData.exists()){
			userData.createNewFile();
			FileWriter fw = new FileWriter(userData.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("<name>" + packet.data.username + "\n");
			bw.write("<x>" + x + "\n");
			bw.write("<y>" + y);

			bw.close();
		}
	
		// load our information about the user here.
		TagReader reader;
		try {
			reader = new TagReader(userData);
			reader.read();
			x = Float.parseFloat(reader.findData("x"));
			y = Float.parseFloat(reader.findData("y"));
		} catch (FileNotFoundException e) {
			System.out.println("Data on player not found.");
		}
		
		players.add(new Player(packet.data));
	}
	
	public static void loadNPCS(){
		File f = new File("data/entities/npcs/");
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith("dat");
		    }
		});
		for(int i = 0; i < matchingFiles.length; i++){

			TagReader reader = new TagReader(new File("data/entities/npcs/" + matchingFiles[i].getName()));
			try {
				reader.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String name = reader.findData("name");
			String spriteLocation = reader.findData("sprite");
			float x = Float.parseFloat(reader.findData("x"));
			float y = Float.parseFloat(reader.findData("y"));
			float speed = Float.parseFloat(reader.findData("speed"));
			int health = Integer.parseInt(reader.findData("health"));
			int damage = Integer.parseInt(reader.findData("damage"));
			int width = Integer.parseInt(reader.findData("width"));
			int height = Integer.parseInt(reader.findData("height"));
			
			npcs.add(new NPC(i,new Vector2f(x,y),width,height,speed,0,spriteLocation,health,damage,name));
		}
	}
	
	public static void logout(int connectionID) throws IOException{
		Player player = getPlayerByID(connectionID);
		if(player==null) return;
		
		File userData = new File("data/entities/players/" + player.getUsername() + ".dat");
				
		if(userData.exists()) {
			userData.createNewFile();
			FileWriter fw = new FileWriter(userData.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("<name>" + player.getUsername() + "\n");
			bw.write("<x>" + player.getX() + "\n");
			bw.write("<y>" + player.getY() + "\n");
			bw.write("<render>" + player.getImageLoc());
			bw.close();
		}
		players.remove(player);
	}
}
