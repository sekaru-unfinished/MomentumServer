package net.indierising.momentum;

import java.util.ArrayList;

import net.indierising.momentum.network.Network;

public class Globals {
	public static int TILE_SIZE = 32;
	
	public static Network network;
	
	public static ArrayList<String> log = new ArrayList<String>();
	public static void log(String message){
		log.add(message);
		// TODO save the log when exiting
		System.out.println(message);
	}
}

