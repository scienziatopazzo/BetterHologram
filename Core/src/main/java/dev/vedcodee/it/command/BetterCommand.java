package dev.vedcodee.it.command;

import dev.vedcodee.it.command.subcommand.*;
import dev.vedcodee.it.factory.MessageFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class BetterCommand implements CommandExecutor {
    
    private static final List<SubCommand> COMMANDS_CACHE = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender Sender, Command command, String s, String[] args) {
        if(!(Sender instanceof Player)) return true;
        Player player = (Player) Sender;
        
        if(args.length == 0)
            return true;

        for (SubCommand subCommand : COMMANDS_CACHE) {
            for (String alias : subCommand.getAliases()) {
                if(alias.equalsIgnoreCase(args[0])){
                    subCommand.execute(player, args);
                    return true;
                }
            }
        }

        new MessageFactory("&cCommand not found").sendToPlayer(player);
        
        return false;
    }


    static { // Load Sub Commands
        COMMANDS_CACHE.add(new CreateHologram(null, null));
        COMMANDS_CACHE.add(new LineHologram(null, null));
        COMMANDS_CACHE.add(new ReloadHologram(null, null));
        COMMANDS_CACHE.add(new MoveHologram(null, null));
        COMMANDS_CACHE.add(new DeleteHologram(null, null));
    }

}
