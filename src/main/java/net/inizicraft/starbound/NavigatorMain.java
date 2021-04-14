package net.inizicraft.starbound;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class NavigatorMain extends JavaPlugin{
	
	ArrayList<Spot> Locations;
	
	@Override
	public void onEnable(){
		System.out.println("Toronto Navigator Enabled");

		PluginCommand TorontoCommand = getCommand("toronto");
		TorontoCommand.setTabCompleter(new ImplementsTabCompleter());

		Locations = loadSavedSpots();
	}
	
	@Override
	public void onDisable() {
		System.out.println("Toronto Navigator Disabled Smoothly");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player)
		try {
			if(label.equalsIgnoreCase("toronto")) {
				if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
					sender.sendMessage(ChatColor.YELLOW + " - /toronto help - This page.\n"
							+ " - /toronto goto [location] - Takes you to a given location in toronto.\n"
							+ " - /toronto menu - Opens the location selection GUI.\n"
							+ " - /toronto addLocation [name] - Adds your current location as a new teleport location with the given name.\n"
							+ " - /toronto removeLocation [name] - Removes a teleport location of the given name.\n"
							+ " - /toronto list - Lists all created locations.\n");
					return true;
				}
				else if(args[0].equalsIgnoreCase("goto")) {
					for(Spot e : Locations) {
						if(e.getSpotName().equalsIgnoreCase(args[1]))
							return ((Player) sender).getPlayer().teleport(e.getSpotLoc());
					}
				}
				else if(args[0].equalsIgnoreCase("addLocation")) {
					String name = "";
					for(int i = 1; i < args.length; i++)
						name += args[i];
					
					for(Spot e : Locations)
						if(name.equalsIgnoreCase(e.getSpotName())) {
							sender.sendMessage(ChatColor.RED + "A location by that name has already been created.");
							return false;
						}

					Spot newSpot = new Spot(name, ((Player) sender).getLocation());
					Locations.add(newSpot);
					saveNewSpot(newSpot);
					return true;
					
				}
				else if(args[0].equalsIgnoreCase("removeLocation")) {
					for(Spot e : Locations)
						if(e.getSpotName().equals(args[1]))
							Locations.remove(e);
				}
				else if(args[0].equalsIgnoreCase("menu")) {
					NavigatorMenu nav = new NavigatorMenu();
					nav.open(((Player) sender).getPlayer());
					return true;
				}
				else if(args[0].equalsIgnoreCase("list")){
					for(Spot s : Locations){
						sender.sendMessage(s.getSpotName());
					}
					return true;
				}
			}
			
		}
		catch(Exception e) {
			
		}
		return false;
	}

	public ArrayList<Spot> loadSavedSpots() {

		ArrayList<Spot> locs = new ArrayList<Spot>();
		FileReader fileIN = null;
		File file = new File("plugins/TorontoNavigator/locations.txt");

		String fileString = "";
		double x, y, z = 0.0;

		try{
			fileIN = new FileReader(file);

			int i;
			while((i = fileIN.read()) != -1){
				fileString += (char) i;
			}

			i =0;
			int lasti = 0;
			while(i < fileString.length()){
				//name
				while(fileString.charAt(i) != ' '){
					i++;
				}
				String name = fileString.substring(lasti, i);
				System.out.println("last i = " + lasti);
				System.out.println("i = " + i);
				lasti = i;
				i++;
				//x
				while(fileString.charAt(i) != ' ')
					i++;
				x = Double.parseDouble(fileString.substring(lasti+1, i));
				System.out.println("last i = " + lasti);
				System.out.println("i = " + i);
				lasti = i;
				i++;
				//y
				while(fileString.charAt(i) != ' ')
					i++;
				y = Double.parseDouble(fileString.substring(lasti+1, i));
				System.out.println("last i = " + lasti);
				System.out.println("i = " + i);
				lasti = i;
				i++;
				//z
				while(i < fileString.length() && fileString.charAt(i) != ' ')
					i++;
				z = Double.parseDouble(fileString.substring(lasti+1, i));
				System.out.println("last i = " + lasti);
				System.out.println("i = " + i);
				lasti = i;
				i++;

				World world = getServer().getWorld("buildingToronto");
				Location l = new Location(world, x, y, z);
				locs.add(new Spot(name, l));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return locs;
	}

	public void writeToConfig(){
		this.saveDefaultConfig();
	}

	public void saveNewSpot(Spot s){
		this.getConfig().createSection("locations."+s.getSpotName());
		this.getConfig().createSection("locations."+s.getSpotName()+".x");
		this.getConfig().createSection("locations."+s.getSpotName()+".y");
		this.getConfig().createSection("locations."+s.getSpotName()+".z");

		this.getConfig().set("locations."+s.getSpotName()+".x", s.getSpotLoc().getX());
		this.getConfig().set("locations."+s.getSpotName()+".x", s.getSpotLoc().getY());
		this.getConfig().set("locations."+s.getSpotName()+".x", s.getSpotLoc().getZ());

		writeToConfig();
	}

	public ItemStack pickWeightedItem(ArrayList<WeightedItem> list){

		Random rand = new Random();
		int loops = 0;
		int safety = 100; //The maximum number of times the search will loop. To avoid infinite loops.

		while(true){
			WeightedItem i = list.get(rand.nextInt(list.size()));
			double potentialWeight = rand.nextDouble(); //This generates doubles between 0.0 and 1.0
			if(potentialWeight < i.getWeight()) //switch the < to match however you have the weight formatted.
				return i.getItem();
			else if(loops >= safety)
				return i.getItem();
			else{
				loops++;
			}
		}
	}
}

