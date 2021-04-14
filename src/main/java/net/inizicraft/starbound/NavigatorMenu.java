package net.inizicraft.starbound;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NavigatorMenu {

	Inventory inv;
	ItemStack blankSpot = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
	
	NavigatorMenu(){
		InitMenu(9, "Toronto Navigator");
	}

	public void open(Player p){
		p.openInventory(inv);
	}

	private void InitMenu(int size, String title){
		inv = Bukkit.createInventory(null, size, title);

		ItemMeta meta = blankSpot.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Disabled Slot");
		blankSpot.setItemMeta(meta);

		for(int i = 0; i < size; i++)
			inv.setItem(i, blankSpot);
	}

}
