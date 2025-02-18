package pen.untitled24;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class FixPearlsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("fixpearls").setExecutor(new FixPearlsCommand());
    }

    @Override
    public void onDisable() {
        /*
        :P little easter egg :D
         https://imgur.com/a/S834yFV
       */

    }
}

class FixPearlsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /fixpearls <subcommand>");
            return true;
        }

        if (args[0].equalsIgnoreCase("credits")) {
            sender.sendMessage("YT: @stabbyMcgrim \n MC: fastmeteor");
            return true;
        }

        sender.sendMessage("Unknown subcommand. Use /fixpearls credits");
        return true;
    }
}