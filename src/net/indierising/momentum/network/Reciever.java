package net.indierising.momentum.network;

import java.io.File;
import java.io.IOException;

import net.indierising.momentum.Functions;
import net.indierising.momentum.Main;
import net.indierising.momentum.entities.GameObject;
import net.indierising.momentum.entities.Handler;
import net.indierising.momentum.network.Network.Key;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;


public class Reciever extends Listener{
	public void connected (Connection connection) {
		// on connect add a new game object
		try {
			GameObject object = Handler.loadGameObject(new File("data/entities/player.go"));
			Functions.setProperty(object, "con-id", connection.getID());
			
			// generate a name for now, we'll add recieving names later
			Functions.setProperty(object, "player-name", "Mali" + connection.getID());
			// add the object to our big list of objects
			Main.describe(object.properties);
			Handler.gameObjects.add(object);
			Network.server.sendToAllUDP(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void received (Connection connection, final Object object) {
		// TODO handle key presses in whatever entity system we use
		if(object instanceof Key){
			Key packet = (Key) object;
			// send the block to this one user, just an example of sending.
			Network.server.sendToAllTCP(Handler.getGameObject("Block"));
//			System.out.println("keyCode: " + packet.keyCode + " pressed:" + packet.pressed);
		}
	}
	
	public void disconnected (Connection connection){}
}
