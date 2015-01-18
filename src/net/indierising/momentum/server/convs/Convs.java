package net.indierising.momentum.server.convs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Convs {
	final static int MAX_CHATS=20;
	public static ConvData conv = new ConvData();

	public static void loadConv(String convName) throws IOException {
		int index=0, optionIndex=0; String line;

		// load the file
		InputStream convFile = new Convs().getClass().getClassLoader().getResourceAsStream("data/convs/" + convName + ".mo");
		conv = new ConvData();
		conv.name = convName;
		
		// init
		for(int i=0; i<MAX_CHATS; i++) {
			conv.chat[i] = new Chat();
			for(int j=0; j<Chat.MAX_OPTIONS; j++) {
				conv.chat[i].option[j] = "";
			}
		}
		
		Reader reader = new InputStreamReader(convFile);
	    BufferedReader in = new BufferedReader(reader);
	    while ((line = in.readLine()) != null) {
	    	// chats
	    	if(line.startsWith("<chat>")) {
	    		conv.chat[index].text = line.substring(6);
	    	// face
	    	} else if(line.startsWith("<face>")) {
				conv.face[index] = "data/assets/faces/" + line.substring(6) + ".png";
	    	// options
	    	} else if(line.startsWith("<option>")) {
	    		conv.chat[index].option[optionIndex] = line.substring(8);
	    		if(optionIndex<Chat.MAX_OPTIONS) optionIndex++;
	    	// goto
	    	} else if(line.startsWith("<goto>")) {
	    		conv.chat[index].goTo[optionIndex-1] = Integer.valueOf(line.substring(6));
	    	// move on to the next chat
	    	} else if(line.startsWith("<next>")) {
	        	if(index<MAX_CHATS-1) {
	        		index++;
	        		optionIndex=0;
	        	} else {
	        		// break out so we don't overflow
	        		break;
	        	}
	        }
	    }
	    in.close();
	}

	public static void initConv(String convName) {
		// load it
		try {
			loadConv(convName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*Play.setChatOptions = true;
		Play.inConv = true;
		Play.chatIndex = 0;*/
	}
	
	public static void closeConv() {
		/*Play.inConv = false;
		Play.chatIndex = 0;*/
	}
}