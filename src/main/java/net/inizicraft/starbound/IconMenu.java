package net.inizicraft.starbound;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class IconMenu {

    Inventory inv;
    ItemStack blankSpot = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

    IconMenu(int size, String title){
        inv = Bukkit.createInventory(null, size, title);

        ItemMeta meta = blankSpot.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Disabled Slot");
        blankSpot.setItemMeta(meta);

        for(int i = 0; i < size; i++)
            inv.setItem(i, blankSpot);
    }

    public void initTrialMenu(){

    }

}
