package net.indierising.momentum.server;

import java.util.ArrayList;

import net.indierising.momentum.server.network.Network;

public class Globals {
	public static Network network;
	
	public static ArrayList<String> log = new ArrayList<String>();
	public static void log(String message){
		log.add(message);
		// TODO save the log when exiting
		System.out.println(message);
	}
}

