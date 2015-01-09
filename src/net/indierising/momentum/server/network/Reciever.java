package net.indierising.momentum.server.network;

import java.io.IOException;

import net.indierising.momentum.server.Main;
import net.indierising.momentum.server.entities.Entity;
import net.indierising.momentum.server.entities.EntityHandler;
import net.indierising.momentum.server.maps.Maps;
import net.indierising.momentum.server.network.Packets.ChatMessage;
import net.indierising.momentum.server.network.Packets.Key;
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
				EntityHandler.addPlayer(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Network.sendPlayer(connection.getID());
			
			// send a test npc
			EntityHandler.npcs.add(new Entity(2, new Vector2f(500, 32), Maps.TILE_SIZE, Maps.TILE_SIZE, 0.3f, Main.DIR_DOWN, "data/assets/images/Block.png"));
			Network.sendNPC(2);
		}
		
		if(object instanceof Key){
			Key packet = (Key) object;
			EntityHandler.getPlayerByID(connection.getID()).setKeys(packet);
		}
		
		if(object instanceof ChatMessage){
			ChatMessage packet = (ChatMessage) object;
			packet.name = EntityHandler.getPlayerByID(connection.getID()).getUsername();
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
