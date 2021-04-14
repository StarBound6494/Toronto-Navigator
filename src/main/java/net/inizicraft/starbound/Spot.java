package net.inizicraft.starbound;

import org.bukkit.Location;

public class Spot {
	
	private String name;
	private Location loc;
	
	Spot(String n, Location e){
		name = n;
		loc = e;
	}
	
	public String getSpotName() {return name;}
	public Location getSpotLoc() {return loc;}
	
}
