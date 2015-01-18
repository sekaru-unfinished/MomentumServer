package net.indierising.momentum.server.convs;

import org.newdawn.slick.Image;

public class ConvData {
	public String name;
	public Chat chat[] = new Chat[Convs.MAX_CHATS];
	public Image face[] = new Image[Convs.MAX_CHATS];
}