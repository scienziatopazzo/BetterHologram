package dev.vedcodee.it;

import dev.vedcodee.it.NMSAdapter;
import dev.vedcodee.it.factory.MessageFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class NMS_1_19_R3 extends NMSAdapter {


    private final List<ArmorStand> hologram; // holograms of lines

    public NMS_1_19_R3(Location location, List<String> lines, String name, JavaPlugin plugin) {
        super(location, new MessageFactory(lines).setPlaceholder(null).buildList(), name, plugin);
        this.hologram = new ArrayList<>();
        this.lines = new ArrayList<>(lines); // Ensure that lines is an ArrayList
    }

    @Override
    public void create() {
        createConfig();
        lines = new MessageFactory(lines).setPlaceholder(null).buildList();
        for (int i = 0; i < lines.size(); i++) {
            ArmorStand armorStand = (ArmorStand) location.getWorld().spawn(location.clone().add(0, 0.3 * i, 0), ArmorStand.class);
            armorStand.setVisible(false);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(lines.get(i));
            armorStand.setGravity(false);
            armorStand.setInvulnerable(true);
            hologram.add(armorStand);
        }
        saveLinesToConfig();
    }

    @Override
    public void delete() {
        for (ArmorStand armorStand : hologram) {
            armorStand.remove();
        }
        hologram.clear();
    }

    @Override
    public void addLine(int line, String content) {
        content = new MessageFactory(content).setPlaceholder(null).buildString();



        ArmorStand armorStand = (ArmorStand) location.getWorld().spawn(location.clone().add(0, 0.3 * line, 0), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(content);
        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        hologram.add(armorStand);

        List<String> updatedLines = new ArrayList<>(lines);
        updatedLines.add(line, content);
        lines = updatedLines;

        updateLines();
        saveLinesToConfig();

        // reload
        delete();
        create();
    }


    @Override
    public void addLine(String content) {
        content = new MessageFactory(content).setPlaceholder(null).buildString();
        addLine(0, content);
    }

    @Override
    public void removeLine(int line) {
        if (line >= 0 && line < hologram.size()) {
            hologram.get(line).remove();
            hologram.remove(line);
            lines.remove(line);
            updateLines();
            saveLinesToConfig();
        }
    }

    /*
    @Override
    public void change(List<String> lines) {
        delete();
        this.lines = lines;
        this.lines = new MessageFactory(lines).setPlaceholder(null).buildList();
        create();
     */


    @Override
    public void change(List<String> lines) {
        // Remove any extra holograms if the new list is shorter
        for (int i = lines.size(); i < hologram.size(); i++) {
            removeLine(i);
        }

        for (int i = 0; i < lines.size(); i++) {
            String content = new MessageFactory(lines.get(i)).setPlaceholder(null).buildString();
            if (i < hologram.size()) {
                change(i, content);
            } else {
                addLine(i, content);
            }
        }

        updateLines();
        saveLinesToConfig();
    }


    @Override
    public void change(int line, String content) {
        content = new MessageFactory(content).setPlaceholder(null).buildString();
        if (line >= 0 && line < hologram.size()) {
            hologram.get(line).setCustomName(content);
            saveLinesToConfig();
        }
    }

    @Override
    public void move(Location newLocation) {
        this.location = newLocation;
        updateLines();
    }

    private void updateLines() {
        for (int i = 0; i < hologram.size(); i++) {
            hologram.get(i).teleport(location.clone().add(0, 0.3 * i, 0));
        }
    }






}
