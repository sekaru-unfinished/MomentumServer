package net.indierising.momentum;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.indierising.momentum.entities.Handler;
import net.indierising.momentum.entities.Property;
import net.indierising.momentum.network.Network;
import net.indierising.momentum.utils.TagReader;

public class Main {
	
	static ArrayList<String> log = new ArrayList<String>();
	
	public static void main(String args[]){
		// TODO error log in data folder
		TagReader config = null;
		try {
			config = new TagReader(new FileInputStream("data/config.txt"));
			config.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			// get the port numbers from our config file
			int tcp_port = Integer.parseInt(config.findData("tcp_port"));
			int udp_port = Integer.parseInt(config.findData("udp_port"));
			
			// load ip address from config and add our parsed port numbers
			Network network = new Network(config.findData("ip"),tcp_port,udp_port);
			
			// let us know where the server has started
			log("Server started on: " + Network.IP + " TCP: " + Network.TCP_PORT + " UDP: " + Network.UDP_PORT); 
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	// our loop for the server
	Runnable update = new Runnable(){
		public void run(){
			try{
				while (true){
					for(int i = 0 ; i < Handler.gameObjects.size(); i++){
						
					}
					Thread.sleep(1000L);
				}
		    }catch(InterruptedException iex) {}
		}
	};
	
	public static void log(String message){
		log.add(message);
		// TODO save the log when exiting
		System.out.println(message);
	}
	
	
	public static void describe(ArrayList<Property> properties){
		for(int i = 0; i < properties.size(); i++){
			System.out.print(properties.get(i).getName() + " -- " + properties.get(i).getData() + "  --  ");
			System.out.println(properties.get(i).getData().getClass().getName());
		}
	}
}
