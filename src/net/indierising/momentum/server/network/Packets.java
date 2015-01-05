package net.indierising.momentum.server.network;

import net.indierising.momentum.server.entitydata.PlayerData;

public class Packets {
	// inputs
	public static class Key {
		public int key;
		public boolean pressed;// whether the key was pressed or released.
	}

	public static class PlayerPacket {
		public PlayerData data;
	}
	
	public static class EntityPacket {
		public float x, y, speed;
		public int direction;
		public String imageLocation;
		public int id;
	}
}
