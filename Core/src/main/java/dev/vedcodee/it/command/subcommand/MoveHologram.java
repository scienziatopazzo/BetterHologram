package dev.vedcodee.it.command.subcommand;

import dev.vedcodee.it.NMSAdapter;
import dev.vedcodee.it.command.SubCommand;
import dev.vedcodee.it.factory.HologramFactory;
import dev.vedcodee.it.factory.MessageFactory;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class MoveHologram extends SubCommand {
    public MoveHologram(List<String> aliases, String usage) {
        super(
                Arrays.asList(
                        "move",
                        "movehere"
                )
                ,
                "move <hologram>"
        );
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 2){
            sendUsage(player);
            return;
        }

        String name = args[1];

        for (NMSAdapter hologram : HologramFactory.HOLOGRAMS_CACHE) {
            if (hologram.name.equalsIgnoreCase(name)) {

                hologram.move(player.getLocation());

                new MessageFactory("&aMoved hologram %name%")
                        .addPlaceholder("name", name)
                        .addPrefix()
                        .sendToPlayer(player);
                return;
            }
        }

        new MessageFactory("&cHologram %name% not found")
                .addPlaceholder("name", name)
                .addPrefix()
                .sendToPlayer(player);
    }



}
