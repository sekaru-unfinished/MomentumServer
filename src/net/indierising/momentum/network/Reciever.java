package net.indierising.momentum.network;

import net.indierising.momentum.network.Network.Key;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;


public class Reciever extends Listener{
	public void connected (Connection connection) {}
	
	public void received (Connection connection, final Object object) {
		// TODO handle key presses in whatever entity system we use
		if(object instanceof Key){
			Key packet = (Key) object;
			System.out.println("keyCode: " + packet.keyCode + " pressed:" + packet.pressed);
		}
	}
	
	public void disconnected (Connection connection){}
}
