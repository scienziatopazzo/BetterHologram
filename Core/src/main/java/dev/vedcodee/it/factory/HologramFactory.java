package dev.vedcodee.it.factory;

import dev.vedcodee.it.BetterHologram;
import dev.vedcodee.it.NMSAdapter;
import dev.vedcodee.it.NMS_1_19_R3;
import dev.vedcodee.it.nms.NMS;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HologramFactory {

    public static final List<NMSAdapter> HOLOGRAMS_CACHE = new ArrayList<>();


    private String name;
    private Location location;
    private List<String> lines;

    public HologramFactory setName(String name) {
        this.name = name;
        return this;
    }

    public HologramFactory setLocation(Location location) {
        this.location = location;
        return this;
    }

    public HologramFactory setLines(String... lines) {
        this.lines = Arrays.asList(lines);
        return this;
    }

    public NMSAdapter buildHologram() {
        if(location == null || lines == null || lines.isEmpty() || name == null)
            throw new IllegalArgumentException("Location, lines and name must not be null");

        return createHologram(location, lines, name);
    }



    public static NMSAdapter load(FileConfiguration config) {
        String name = config.getString("name");
        World world = Bukkit.getWorld(config.getString("location.world"));
        double x = config.getDouble("location.x");
        double y = config.getDouble("location.y");
        double z = config.getDouble("location.z");
        Location location = new Location(world, x, y, z);

        List<String> lines = config.getStringList("lines");

        return createHologram(location, lines, name);
    }



    public static NMSAdapter createHologram(Location location, List<String> lines, String name) {
        if (NMS.getAdapter().equals(NMS_1_19_R3.class)) {
            return new NMS_1_19_R3(location, lines, name, BetterHologram.INSTANCE);
        }
        return null;
    }



}
