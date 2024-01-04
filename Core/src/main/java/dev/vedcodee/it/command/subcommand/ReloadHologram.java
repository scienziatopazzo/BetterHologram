package dev.vedcodee.it.command.subcommand;

import dev.vedcodee.it.NMSAdapter;
import dev.vedcodee.it.command.SubCommand;
import dev.vedcodee.it.factory.HologramFactory;
import dev.vedcodee.it.factory.MessageFactory;
import dev.vedcodee.it.hologram.Hologram;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class ReloadHologram extends SubCommand {
    public ReloadHologram(List<String> aliases, String usage) {
        super(
                Arrays.asList(
                        "reload"
                ),
                "reload"
        );
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 1){
            sendUsage(player);
            return;
        }

        for (Hologram hologram : HologramFactory.HOLOGRAMS_CACHE) {
            hologram.delete();
            hologram.create();
        }

        new MessageFactory("&aHolograms have been reloaded").sendToPlayer(player);

    }
}
