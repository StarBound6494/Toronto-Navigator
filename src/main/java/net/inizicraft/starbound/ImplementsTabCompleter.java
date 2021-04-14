package net.inizicraft.starbound;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ImplementsTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args){

        ArrayList<String> out = new ArrayList<>();

        if(args.length == 1){
            out.add("help");
            out.add("?");
            out.add("goto");
            out.add("addLocation");
            out.add("removeLocation");
            out.add("menu");

            return out;
        }
        else if(args.length == 0){
            out.add("toronto");
            return out;
        }
        else return out;

    }
}
