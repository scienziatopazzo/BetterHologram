package dev.vedcodee.it.factory;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MessageFactory {

    private List<String> textLines;
    private String command;

    public MessageFactory(String text) {
        this.textLines = Arrays.asList(
                ChatColor.translateAlternateColorCodes('&', text)
        );
    }


    public MessageFactory(List<String> texts) {
        this.textLines = texts.stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .collect(Collectors.toList());
        addPlaceholder("prefix", "&7[&bBetterHologram&7]");
    }

    public MessageFactory addPrefix() {
        this.textLines = textLines.stream()
                .map(line -> "§8[§bBetterHologram§8] " + line)
                .collect(Collectors.toList());
        return this;
    }

    public MessageFactory addPlaceholder(String placeholder, Object value) {
        this.textLines = this.textLines.stream()
                .map(line -> line.replace("%" + placeholder + "%", String.valueOf(value)))
                .collect(Collectors.toList());
        reloadColor();
        return this;
    }

    public MessageFactory setPlaceholder(Player player) {
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
            return this;
        this.textLines = this.textLines.stream()
                .map(line -> PlaceholderAPI.setPlaceholders(player, line))
                .collect(Collectors.toList());
        reloadColor();
        return this;
    }


    public MessageFactory suggestCommand(String command) {
        this.command = command;
        return this;
    }

    public void reloadColor() {
        if(textLines.size() != 1){
            this.textLines = textLines.stream()
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                    .collect(Collectors.toList());
            return;
        }
        this.textLines = Arrays.asList(
                ChatColor.translateAlternateColorCodes('&', textLines.get(0))
        );
    }


    public List<String> buildList() {
        return textLines;
    }

    public String buildString() {
        return textLines.size() == 1 ? textLines.get(0) : String.join("\n", textLines);
    }

    @Deprecated // Deprecated because is not optimized, look up for the new method!
    public String buildString_() {
        if(textLines.size() == 1) return textLines.get(0);
        StringBuilder result = new StringBuilder();
        for (String textLine : textLines) {
            result.append(textLine).append("\n");
        }
        return result.toString().trim();
    }


    public void sendToPlayer(Player player) {
        textLines.forEach(line -> {
            if (command != null) {
                TextComponent component = new TextComponent(line);
                component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
                player.spigot().sendMessage(component);
            }else {
                player.sendMessage(line);
            }
        });
    }


    public void sendToPlayers(List<Player> players) {
        players.forEach(this::sendToPlayer);
    }
}
