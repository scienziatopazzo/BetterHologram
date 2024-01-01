package dev.vedcodee.it.command.subcommand;

import com.google.common.primitives.Ints;
import dev.vedcodee.it.NMSAdapter;
import dev.vedcodee.it.command.SubCommand;
import dev.vedcodee.it.factory.HologramFactory;
import dev.vedcodee.it.factory.MessageFactory;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class LineHologram extends SubCommand {


    public LineHologram(List<String> aliases, String usage) {
        super(
                Arrays.asList(
                        "line"
                ),
                "line add <hologram> <content> || line remove <hologram> <line>"
        );
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args.length < 4){
            sendUsage(player);
            return;
        }

        String command = args[1];

        if(command.equalsIgnoreCase("add")){
            String name = args[2];
            String content = String.join(" ", Arrays.copyOfRange(args, 3, args.length));
            if(name == null || content.equalsIgnoreCase(" ") || content.equalsIgnoreCase("")){
                sendUsage(player);
                return;
            }
            for (NMSAdapter hologram : HologramFactory.HOLOGRAMS_CACHE) {
                if(hologram.name.equalsIgnoreCase(name)) {
                    hologram.addLine(content);
                    new MessageFactory("&aAdded line '%content%&a' to hologram %name%")
                            .addPlaceholder("content", content)
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
            return;
        }

        if (command.equalsIgnoreCase("remove")) {
            String name = args[2];
            Integer line = Ints.tryParse(args[3]);
            if(line == null){
                sendUsage(player);
                return;
            }
            for (NMSAdapter hologram : HologramFactory.HOLOGRAMS_CACHE) {
                if(hologram.name.equalsIgnoreCase(name)) {
                    if (!(line >= 0 && line < hologram.lines.size())) {
                        new MessageFactory("&cLine line not found")
                                .addPlaceholder("line", line)
                                .addPrefix()
                                .sendToPlayer(player);
                        return;
                    }
                    hologram.removeLine(line);
                    new MessageFactory("&aRemoved line '%line%&a' in hologram %name%")
                            .addPlaceholder("line", line)
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
            return;
        }

        sendUsage(player);
    }
}
