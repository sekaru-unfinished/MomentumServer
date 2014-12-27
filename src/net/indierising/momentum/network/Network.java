package net.indierising.momentum.network;

import java.io.IOException;
import java.util.ArrayList;

import net.indierising.momentum.entities.Entity;
import net.indierising.momentum.entities.Handler;
import net.indierising.momentum.entities.MovingEntity;
import net.indierising.momentum.entities.Player;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

public class Network {
	public static String IP = "localhost";
	public static int TCP_PORT = 9000, UDP_PORT = 9001;
	
	public static Server server;
	
	// set your own ports, will be an option in a config file
	public Network(String IP,int TCP_PORT,int UDP_PORT) throws IOException{
		server = new Server();
		
		// register all classes that need to be sent - must be the same on client and server
		register(server);
		
		Network.IP = IP;
		Network.TCP_PORT = TCP_PORT;
		Network.UDP_PORT = UDP_PORT;
		
		// bind server to ports TODO use alternative constructor with loaded config
		server.bind(Network.TCP_PORT,Network.UDP_PORT);
		
		// start the server
		server.start();
		// add our listener
		server.addListener(new Reciever());
	}
	
	public static void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		

		// register the classes we'll be transferring
		kryo.register(Key.class);
		kryo.register(ArrayList.class);
		
		kryo.register(PlayerPacket.class);
		kryo.register(EntityPacket.class);
	}
	
	// inputs
	public static class Key{
		public int key;
		public boolean pressed;// whether the key was pressed or released.
	}

	public static class PlayerPacket{
		public int connectionID;
		public float x,y;
		public int direction;
		public String username;
		public float speed;
		public String imageLocation;
	}
	
	public static class EntityPacket{
		public float x,y,speed;
		public int direction;
		public String imageLocation;
		public int id;
	}

	public static void sendMovement(int connectionID) {
		Player player = Handler.getPlayerByID(connectionID);
		PlayerPacket packet = new PlayerPacket();
		packet.connectionID = player.getConnectionID();
		packet.x = player.getX();
		packet.y = player.getY();
		packet.direction = player.getDirection();
		packet.username = player.getUsername();
		packet.speed = player.getSpeed();
		server.sendToAllUDP(packet);
	}
	
	public static void sendNPCMovement(int id){
		MovingEntity entity = Handler.getNPCByID(id);
		EntityPacket packet = new EntityPacket();
		packet.x = entity.getX();
		packet.y = entity.getY();
		packet.direction = entity.getDirection();
		packet.speed = entity.getSpeed();
		packet.imageLocation = entity.getImageLocation();
		packet.id = id;
		server.sendToAllUDP(packet);
	}
	
	public static void sendNPC(int id){
		MovingEntity entity = Handler.getNPCByID(id);
		EntityPacket packet = new EntityPacket();
		packet.x = entity.getX();
		packet.y = entity.getY();
		packet.direction = entity.getDirection();
		packet.speed = entity.getSpeed();
		packet.imageLocation = entity.getImageLocation();
		packet.id = id;
		server.sendToAllTCP(packet);
	}

	public static void sendPlayer(int connectionID) {
		Player player = Handler.getPlayerByID(connectionID);
		PlayerPacket packet = new PlayerPacket();
		packet.connectionID = connectionID;
		packet.x = player.getX();
		packet.y = player.getY();
		packet.direction = player.getDirection();
		packet.username = player.getUsername();
		packet.speed = player.getSpeed();
		packet.imageLocation = player.getImageLocation();
		server.sendToAllTCP(packet);
	}
}
