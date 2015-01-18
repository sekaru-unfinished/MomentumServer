package net.indierising.momentum.server.convs;

public class Chat {
	public static final int MAX_OPTIONS = 4;
	
	public String text;
	public String option[] = new String[MAX_OPTIONS];
	public int goTo[] = new int[MAX_OPTIONS];
}
