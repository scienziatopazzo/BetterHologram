package dev.vedcodee.it.command.subcommand;

import dev.vedcodee.it.NMSAdapter;
import dev.vedcodee.it.command.SubCommand;
import dev.vedcodee.it.factory.HologramFactory;
import dev.vedcodee.it.factory.MessageFactory;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CreateHologram extends SubCommand {
    public CreateHologram(List<String> aliases, String usage) {
        super(
                Arrays.asList(
                        "create"
                ),
                "create <name>"
        );

    }


    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 2){
            sendUsage(player);
            return;
        }

        String name = args[1];

        if(HologramFactory.HOLOGRAMS_CACHE.contains(name)){
            new MessageFactory("&cHologram %name% already exists")
                    .addPlaceholder("name", name)
                    .addPrefix()
                    .sendToPlayer(player);
            return;
        }

        NMSAdapter hologram = new HologramFactory()
                .setName(name)
                .setLines("First line")
                .setLocation(player.getLocation())
                .buildHologram();

        hologram.create();

        HologramFactory.HOLOGRAMS_CACHE.add(hologram); // add hologram to cache
    }
}
