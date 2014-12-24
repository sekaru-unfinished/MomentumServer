package net.indierising.momentum;

import java.io.IOException;

import net.indierising.momentum.network.Network;
import net.indierising.momentum.network.Reciever;

import com.esotericsoftware.kryonet.Server;

public class Main {
	static Server server;
	
	public static void main(String args[]){
		server = new Server();
		// register all classes that need sending
		Network.register(server);
		// bind server to ports
		try {
			server.bind(Network.TCP_PORT,Network.UDP_PORT);
		}catch(IOException e){
			e.printStackTrace();
		}
		// start the server
		server.start();
		server.addListener(new Reciever());
	}
}
