package net.indierising.momentum.entities;

import java.util.ArrayList;

public class Handler {
	public static ArrayList<Player> players = new ArrayList<Player>();

	public static Player getPlayerByID(int connectionID){
		for(int i = 0; i < players.size(); i++){
			if(players.get(i).getConnectionID() == connectionID){
				return players.get(i);
			}
		}
		// if we can't find them sorry.
		return null;
	}
}
