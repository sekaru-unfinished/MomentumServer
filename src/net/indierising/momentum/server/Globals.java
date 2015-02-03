package net.indierising.momentum.server;

import java.util.ArrayList;

import net.indierising.momentum.server.network.Network;

public class Globals {
	// packet sending/receiving/handling etc
	public static Network network;
	
	// directional constants
	public static final int DIR_UP = 0;
	public static final int DIR_DOWN = 1;
	public static final int DIR_LEFT = 2;
	public static final int DIR_RIGHT = 3;
	
	// logging
	public static ArrayList<String> log = new ArrayList<String>();
	public static void log(String message){
		log.add(message);
		
		// TODO save the log when exiting
		System.out.println(message);
	}
}

