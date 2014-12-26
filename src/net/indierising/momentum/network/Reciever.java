package net.indierising.momentum.network;


import java.io.IOException;

import net.indierising.momentum.Main;
import net.indierising.momentum.entities.Handler;
import net.indierising.momentum.entities.Player;
import net.indierising.momentum.network.Network.Key;
import net.indierising.momentum.network.Network.PlayerPacket;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;


public class Reciever extends Listener{
	public void connected (Connection connection) {
	}
	
	public void received (Connection connection, final Object object) {
		if(object instanceof PlayerPacket){
			PlayerPacket packet = (PlayerPacket) object;
			// start all players at location 32 x 32
			try {
				Handler.addPlayer(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Network.sendMovement(connection.getID());
		}
		
		// TODO handle key presses in whatever entity system we use
		if(object instanceof Key){
			Key packet = (Key) object;
			// send the block to this one user, just an example of sending.
//			System.out.println("keyCode: " + packet.keyCode + " pressed:" + packet.pressed);
		}
	}
	
	public void disconnected (Connection connection){}
}
