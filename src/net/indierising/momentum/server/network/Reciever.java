package net.indierising.momentum.server.network;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.indierising.momentum.server.Main;
import net.indierising.momentum.server.entities.ClassSystem;
import net.indierising.momentum.server.entities.Entity;
import net.indierising.momentum.server.entities.EntityHandler;
import net.indierising.momentum.server.entities.NPC;
import net.indierising.momentum.server.maps.Maps;
import net.indierising.momentum.server.network.Packets.ChatMessage;
import net.indierising.momentum.server.network.Packets.Key;
import net.indierising.momentum.server.network.Packets.PlayerClass;
import net.indierising.momentum.server.network.Packets.PlayerPacket;

import org.newdawn.slick.geom.Vector2f;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Reciever extends Listener{
	public void connected (Connection connection) {}

	public void received (Connection connection, final Object object) {
		// handles players when they join
		if(object instanceof PlayerPacket) {
			// send them the constants
			Network.sendConstants(connection);
				
			// send them the new player
			PlayerPacket packet = (PlayerPacket) object;
			try {
				packet.data.connectionID = connection.getID();
				PlayerClass playerClass = new PlayerClass();
				// organise this at some point to happen when the player clicks their class.
				playerClass = ClassSystem.data.get(0);
				
				packet.data.playerClass = playerClass;
				System.out.println(packet.data.playerClass.name);
				EntityHandler.addPlayer(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Network.sendPlayer(connection.getID());
			
			Network.sendNPCS(connection.getID());
		}
		
		if(object instanceof Key){
			Key packet = (Key) object;
			EntityHandler.getPlayerByID(connection.getID()).setKeys(packet);
		}
		
		if(object instanceof ChatMessage){
			ChatMessage packet = (ChatMessage) object;
			
			DateFormat dateFormat = new SimpleDateFormat("HH:mm");
			Date date = new Date();
			packet.title = dateFormat.format(date) + ": ";
			packet.title += EntityHandler.getPlayerByID(connection.getID()).getUsername();
			packet.message = "'" + packet.message + "'";
			Network.server.sendToAllUDP(packet);
		}
	}
	
	public void disconnected(Connection connection){
		try {
			EntityHandler.logout(connection.getID());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
