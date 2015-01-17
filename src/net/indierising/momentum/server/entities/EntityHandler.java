package net.indierising.momentum.server.entities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.indierising.momentum.server.network.Network;
import net.indierising.momentum.server.network.Packets.PlayerPacket;
import net.indierising.momentum.server.utils.TagReader;

public class EntityHandler {
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static ArrayList<Entity> npcs = new ArrayList<Entity>();
	
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
	
	public static Entity getNPCByID(int id){
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
