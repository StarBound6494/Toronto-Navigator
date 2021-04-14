package net.inizicraft.starbound;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class WeightedItem {

    ItemStack item;
    double weight;

    WeightedItem(ItemStack i, double w){
        item = i;
        weight = w;
    }

    public ItemStack getItem() {return item;}
    public double getWeight() {return weight;}
}
