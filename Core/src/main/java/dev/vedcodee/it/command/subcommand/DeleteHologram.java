package dev.vedcodee.it.command.subcommand;

import dev.vedcodee.it.NMSAdapter;
import dev.vedcodee.it.command.SubCommand;
import dev.vedcodee.it.factory.HologramFactory;
import dev.vedcodee.it.factory.MessageFactory;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class DeleteHologram extends SubCommand {

    public DeleteHologram(List<String> aliases, String usage) {
        super(
                Arrays.asList(
                        "delete",
                        "del"
                ),
                "delete <name>"
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
            if(hologram.name.equalsIgnoreCase(name)) {
                hologram.delete();
                hologram.deleteConfig();
                HologramFactory.HOLOGRAMS_CACHE.remove(hologram);
                new MessageFactory("&aDeleted hologram %name%")
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
