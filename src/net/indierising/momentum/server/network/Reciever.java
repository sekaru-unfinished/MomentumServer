package net.indierising.momentum.server.network;

import java.io.IOException;

import net.indierising.momentum.server.Globals;
import net.indierising.momentum.server.Main;
import net.indierising.momentum.server.entities.Entity;
import net.indierising.momentum.server.entities.Handler;
import net.indierising.momentum.server.network.Packets.Key;
import net.indierising.momentum.server.network.Packets.PlayerPacket;

import org.newdawn.slick.geom.Vector2f;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Reciever extends Listener{
	public void connected (Connection connection) {
	}
	
	public void received (Connection connection, final Object object) {
		// handles players when they join.
		if(object instanceof PlayerPacket){
			PlayerPacket packet = (PlayerPacket) object;
			// start all players at location 32 x 32
			try {
				packet.data.connectionID = connection.getID();
				Handler.addPlayer(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Network.sendPlayer(connection.getID());
			Handler.npcs.add(new Entity(2, new Vector2f(500, 32), Globals.TILE_SIZE, Globals.TILE_SIZE, 0.3f, Main.DIR_DOWN, "data/assets/images/Block.png"));
			Network.sendNPC(2);
		}
		
		// TODO handle key presses in whatever entity system we use
		if(object instanceof Key){
			Key packet = (Key) object;
			Handler.getPlayerByID(connection.getID()).setKeys(packet);
		}
	}
	
	public void disconnected (Connection connection){
		try {
			Handler.logout(connection.getID());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
