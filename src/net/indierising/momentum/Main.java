package net.indierising.momentum;

import java.io.IOException;

import net.indierising.momentum.network.Network;


public class Main {
	
	public static void main(String args[]){
		try {
			Network network = new Network("localhost",9000,9001);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
