package net.indierising.momentum.network;


import java.io.IOException;

import net.indierising.momentum.Main;
import net.indierising.momentum.entities.Handler;
import net.indierising.momentum.entities.MovingEntity;
import net.indierising.momentum.network.Network.Key;
import net.indierising.momentum.network.Network.PlayerPacket;

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
				packet.connectionID = connection.getID();
				Handler.addPlayer(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Network.sendPlayer(connection.getID());
			Handler.npcs.add(new MovingEntity(2,500,32,32,32,0.4f,Main.DIRECTION_DOWN,"data/assets/images/Block.png"));
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
