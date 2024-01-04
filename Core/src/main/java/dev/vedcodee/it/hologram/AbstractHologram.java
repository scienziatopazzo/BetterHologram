package dev.vedcodee.it.hologram;

import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractHologram {

    public Location location;
    public List<String> lines;
    public final JavaPlugin plugin;
    public final String name;
    public FileConfiguration config;

    public AbstractHologram(Location location, List<String> lines, String name, JavaPlugin plugin) {
        this.location = location;
        this.lines = lines;
        this.plugin = plugin;
        this.name = name;
        createConfig();
    }

    public abstract void create();
    public abstract void delete();
    public abstract void addLine(int line, String content);
    public abstract void addLine(String content);
    public abstract void removeLine(int line);
    public abstract void change(int line, String content);
    public abstract void change(List<String> lines);
    public abstract void move(Location newLocation);


    public void saveConfig() {
        File file = new File(plugin.getDataFolder() + "/holograms", name + ".yml");
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void saveLinesToConfig() {
        config.set("name", name);
        config.set("location", location.serialize());

        config.set("lines",
                lines
                        .stream()
                        .map(line -> line.replace("&", "§"))
                        .collect(Collectors.toList()));
        saveConfig();
    }

    public void deleteConfig() {
        File folder = new File(plugin.getDataFolder(), "holograms");
        if(!folder.exists())
            folder.mkdir();

        File file = new File(folder, name + ".yml");
        if (file.exists())
            file.delete();

    }

    public void createConfig() {
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();
        File folder = new File(plugin.getDataFolder(), "holograms");
        if(!folder.exists())
            folder.mkdir();

        File file = new File(folder, name + ".yml");
        try {
            if (!file.exists())
                file.createNewFile();

            this.config = new YamlConfiguration();
            config.load(file);

            if(config == null)
                System.out.println("Config is null!");

        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }


}
