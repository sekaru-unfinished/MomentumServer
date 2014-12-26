package net.indierising.momentum;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import net.indierising.momentum.network.Network;
import net.indierising.momentum.utils.TagReader;

public class Main extends BasicGame{
	
	public Main() {
		super("Momentum Client");
	}

	static ArrayList<String> log = new ArrayList<String>();
	
	static AppGameContainer app;
	
	public static void main(String args[]) throws SlickException{
		app = new AppGameContainer(new Main());
	     app.setShowFPS(false);
	     app.setTargetFrameRate(60);
	     app.setMaximumLogicUpdateInterval(10);
		 app.setMaximumLogicUpdateInterval(60);
	     app.start();
	}
	
	public static void log(String message){
		log.add(message);
		// TODO save the log when exiting
		System.out.println(message);
	}
	
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {	}

	public void init(GameContainer gc) throws SlickException {
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

	public void update(GameContainer gc, int delta) throws SlickException {
		
	}
}
