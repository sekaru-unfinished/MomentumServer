package net.indierising.momentum.server.entities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.indierising.momentum.server.Globals;
import net.indierising.momentum.server.entitydata.NPCData;
import net.indierising.momentum.server.maps.Maps;
import net.indierising.momentum.server.network.Network;
import net.indierising.momentum.server.network.Packets.PlayerPacket;
import net.indierising.momentum.server.utils.TagReader;

public class EntityHandler {
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static ArrayList<NPC> npcs = new ArrayList<NPC>();
	
	public static Player getPlayerByID(int connectionID){
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getConnectionID() == connectionID){
				return players.get(i);
			}
		}
		// if we can't find them
		return null;
	}
	
	public static NPC getNPCByID(int id){
		for(int i = 0; i < npcs.size(); i++){
			if(npcs.get(i).id == id){
				return npcs.get(i);
			}
		}
		// if we can't find them
		return null;
	}

	// check if we have the player saved, otherwise create a new file with their username
	public static void addPlayer(PlayerPacket packet) throws IOException{
		float x = Maps.maps.get(Maps.spawnMap).spawnX, y = Maps.maps.get(Maps.spawnMap).spawnY;
		File userData = new File("data/entities/players/" + packet.data.username + ".mo");
	
		if(!userData.exists()){
			userData.createNewFile();
			FileWriter fw = new FileWriter(userData.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("<name>" + packet.data.username + "\n");
			bw.write("<x>" + x + "\n");
			bw.write("<y>" + y + "\n");
			bw.write("<sprite>player \n");
			bw.write("<map>" + Maps.spawnMap);
			bw.close();
		}
	
		// load our information about the user here.
		TagReader reader;
		reader = new TagReader(userData);
		packet.data.x = Float.parseFloat(reader.findData("x"));
		packet.data.y = Float.parseFloat(reader.findData("y"));
		packet.data.imageLoc = reader.findData("sprite");
		packet.data.map = Integer.parseInt(reader.findData("map", String.valueOf(Maps.spawnMap)));
		
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
			bw.write("<class>" + player.getPlayerClass());
			bw.write("<x>" + player.getX() + "\n");
			bw.write("<y>" + player.getY() + "\n");
			bw.write("<map>" + player.getMap());
			bw.close();
		}
		
		players.remove(player);
	}
	
	public static void addNPC(String name, int map, float x, float y){
		TagReader reader = new TagReader(new File("data/entities/npcs/" + name + ".mo"));
		NPCData data = new NPCData();
		data.id = npcs.size();
		data.name = reader.findData("name");
		data.imageLoc = reader.findData("sprite");
		data.map = map;
		data.x = x;
		data.y = y;
		data.dir = Integer.parseInt(reader.findData("dir", String.valueOf(Globals.DIR_DOWN)));
		data.speed = Float.parseFloat(reader.findData("speed"));
		data.health = Integer.parseInt(reader.findData("health"));
		data.damage = Integer.parseInt(reader.findData("damage"));
		data.width = Integer.parseInt(reader.findData("width"));
		data.height = Integer.parseInt(reader.findData("height"));
		npcs.add(new NPC(data));
	}
	
	public static void update(int delta) {
		// players
		for(int i = 0; i < players.size(); i++) {
			players.get(i).update(delta);
		}
		
		// npcs
		for(int i = 0; i < npcs.size(); i++){
			npcs.get(i).update(delta);
			Network.sendNPCMovement(npcs.get(i).id);
		}
	}
}
