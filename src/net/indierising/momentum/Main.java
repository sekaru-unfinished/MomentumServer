package net.indierising.momentum;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.indierising.momentum.entities.Handler;
import net.indierising.momentum.network.Network;
import net.indierising.momentum.utils.TagReader;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Main extends BasicGame{
	public static final int DIRECTION_DOWN = 1, DIRECTION_UP = 2, DIRECTION_LEFT = 3,DIRECTION_RIGHT = 4;
	
	public Main() {
		super("Momentum Server");
	}

	public void init(GameContainer gc) throws SlickException {
		// TODO error log in data folder	
		TagReader config = null;
		try {
			config = new TagReader(new FileInputStream("data/config.txt"));
			config.read();
		} catch (FileNotFoundException e) {
			e.printStackTrace();// error loading the config.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// get the port numbers from our config file
		int tcp_port = Integer.parseInt(config.findData("tcp_port"));
		int udp_port = Integer.parseInt(config.findData("udp_port"));
					
		// load ip address from config and add our parsed port numbers
		try {
			Globals.network = new Network(config.findData("ip"),tcp_port,udp_port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// let us know where the server has started
		Globals.log("Server started on: " + Globals.network.IP + " TCP: " + Globals.network.TCP_PORT + " UDP: " + Globals.network.UDP_PORT); 	
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Handler.update(delta);
	}
	
	public static AppGameContainer app;
	public static void main(String args[]) throws SlickException{
		app = new AppGameContainer(new Main());
	    app.setShowFPS(false);
	    app.setTargetFrameRate(60);
	    app.setMaximumLogicUpdateInterval(10);
		app.setMaximumLogicUpdateInterval(60);
	    app.start();
	}
}
