package net.indierising.momentum.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
	static public String ip = "localhost";
	static public final int port = 9000;
	
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		
	}
}
