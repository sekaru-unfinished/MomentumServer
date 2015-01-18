package net.indierising.momentum.server.network;

import java.io.IOException;
import java.util.ArrayList;

import net.indierising.momentum.server.entities.Entity;
import net.indierising.momentum.server.entities.EntityHandler;
import net.indierising.momentum.server.entities.NPC;
import net.indierising.momentum.server.entities.Player;
import net.indierising.momentum.server.entitydata.NPCData;
import net.indierising.momentum.server.entitydata.PlayerData;
import net.indierising.momentum.server.maps.Maps;
import net.indierising.momentum.server.network.Packets.ChatMessage;
import net.indierising.momentum.server.network.Packets.ConstantsPacket;
import net.indierising.momentum.server.network.Packets.Key;
import net.indierising.momentum.server.network.Packets.NPCMove;
import net.indierising.momentum.server.network.Packets.NPCPacket;
import net.indierising.momentum.server.network.Packets.PlayerClass;
import net.indierising.momentum.server.network.Packets.PlayerMove;
import net.indierising.momentum.server.network.Packets.PlayerPacket;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

public class Network {
	public String ip = "localhost";
	public final int tcpPort, udpPort;
	
	public static Server server;
	
	// set your own ports, will be an option in a config file
	public Network(String ip, int tcpPort, int udpPort) throws IOException {
		server = new Server();
		
		// register all classes that need to be sent - must be the same on client and server
		register(server);
		
		this.ip = ip;
		this.tcpPort = tcpPort;
		this.udpPort = udpPort;
		
		// bind server to ports TODO use alternative constructor with loaded config
		server.bind(tcpPort, udpPort);
		
		// start the server
		server.start();
		// add our listener
		server.addListener(new Reciever());
	}
	
	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();

		// register the classes we'll be transferring
		kryo.register(ConstantsPacket.class);
		kryo.register(Key.class);
		kryo.register(ArrayList.class);

		kryo.register(PlayerPacket.class);
		kryo.register(PlayerData.class);
		kryo.register(PlayerMove.class);
		kryo.register(PlayerClass.class);
		
		kryo.register(NPCData.class);
		kryo.register(NPCPacket.class);
		kryo.register(NPCMove.class);
		
		kryo.register(ChatMessage.class);
	}
	
	public static void sendPlayer(int connectionID) {
		Player player = EntityHandler.getPlayerByID(connectionID);
		PlayerPacket packet = new PlayerPacket();
		packet.data = player.toPlayerData();
		server.sendToAllTCP(packet);
	}
	
	public static void sendMovement(int connectionID) {
		Player player = EntityHandler.getPlayerByID(connectionID);
		PlayerMove packet = new PlayerMove();
		packet.connectionID = player.getConnectionID();
		packet.x = player.getX();
		packet.y = player.getY();
		packet.dir = player.getDir();
		server.sendToAllUDP(packet);
	}
	
	public static void sendNPC(int id, int connectionID) {
		NPC npc = EntityHandler.getNPCByID(id);
		NPCPacket packet = new NPCPacket();
		packet.data = npc.toNPCData();
		server.sendToTCP(connectionID, packet);
	}
	
	public static void sendNPCMovement(int id) {
		Entity npc = EntityHandler.getNPCByID(id);
		NPCMove packet = new NPCMove();
		packet.id = id;
		packet.x = npc.getX();
		packet.y = npc.getY();
		packet.dir = npc.getDir();
		server.sendToAllUDP(packet);
	}
	
	public static void sendConstants(Connection con) {
		ConstantsPacket packet = new ConstantsPacket();
		packet.TILE_SIZE = Maps.TILE_SIZE;
		packet.MAX_MAPS = Maps.MAX_MAPS;
		packet.MAX_MAP_NPCS = Maps.MAX_MAP_NPCS;
		con.sendTCP(packet);
	}

	public static void sendNPCS(int id) {
		for(int i = 0; i < EntityHandler.npcs.size(); i++){
			sendNPC(i,id);
		}
	}
}
