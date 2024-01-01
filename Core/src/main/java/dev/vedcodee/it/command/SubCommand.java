package dev.vedcodee.it.command;

import dev.vedcodee.it.factory.MessageFactory;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
public abstract class SubCommand {


    private final List<String> aliases;
    private final String usage;


    public SubCommand(List<String> aliases, String usage) {
         this.aliases = aliases;
         this.usage = usage;
    }


    public abstract void execute(Player player, String[] args);


    public void sendUsage(Player player) {
        new MessageFactory("&cUsage: /holo %usage%")
                .addPlaceholder("usage", usage)
                .addPrefix()
                .sendToPlayer(player);
    }

}
