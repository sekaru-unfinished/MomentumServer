package net.indierising.momentum;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import net.indierising.momentum.network.Network;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Globals {
	public static Network network;
	
	public static String DATA_PACKAGE_NAME = "ServerData.zip";
	public static String DATA_URL = "http://www.indierising.net/momentum/" + DATA_PACKAGE_NAME;
	public static String DATA_DIR = "data/" + DATA_PACKAGE_NAME;
	
	public static void downloadData() throws IOException {
		URL dataFile = new URL(DATA_URL);
		ReadableByteChannel rbc = Channels.newChannel(dataFile.openStream());
		FileOutputStream fos = new FileOutputStream(DATA_DIR);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		
	    // unzip it
	    unZip(DATA_DIR, System.getProperty("user.dir") + "/data/");
	}
	
	public static void unZip(String fileName, String outputFolder) throws IOException{

	    try {
	         ZipFile zipFile = new ZipFile(fileName);
	         zipFile.extractAll(outputFolder);
	    } catch (ZipException e) {
	        e.printStackTrace();
	    }
	    
	   File file = new File(fileName);
	   file.delete();
    }
	
	public static ArrayList<String> log = new ArrayList<String>();
	public static void log(String message){
		log.add(message);
		// TODO save the log when exiting
		System.out.println(message);
	}
}

