package net.indierising.momentum;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

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
			log("Server started on: " + network.IP + " TCP: " + network.TCP_PORT + " UDP: " + network.UDP_PORT); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void log(String message){
		log.add(message);
		// TODO save the log when exiting
		System.out.println(message);
	}
}
