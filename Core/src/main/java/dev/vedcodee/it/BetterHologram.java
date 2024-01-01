package dev.vedcodee.it;

import dev.vedcodee.it.command.BetterCommand;
import dev.vedcodee.it.factory.HologramFactory;
import dev.vedcodee.it.nms.NMS;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class BetterHologram extends JavaPlugin {

    public static BetterHologram INSTANCE;


    @Override
    public void onEnable() {
        INSTANCE = this;
        NMS.init();
        getLogger().log(Level.INFO, "BetterHologram has enabled NMS! (" + NMS.getVersion() + ") [" + NMS.getAdapter().getName() + "]");
        getCommand("hologram").setExecutor(new BetterCommand());
        if(!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
            getLogger().log(Level.INFO, "BetterHologram don't see PlaceholderAPI!");

        File directory = new File(getDataFolder(), "holograms");
        if(!directory.exists())
            directory.mkdir();

        if(directory.listFiles() != null) {
            for (File file : directory.listFiles()) {
                try {
                    FileConfiguration configuration = new YamlConfiguration();
                    configuration.load(file);
                    NMSAdapter hologram = HologramFactory.load(configuration);
                    HologramFactory.HOLOGRAMS_CACHE.add(hologram);
                    hologram.create();
                } catch (IOException | InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }
            }

            getLogger().log(Level.INFO, "Loaded " + HologramFactory.HOLOGRAMS_CACHE.size() + " Holograms!");
        }



        getServer().getScheduler().runTaskTimer(this, () -> {
            for (NMSAdapter hologram : HologramFactory.HOLOGRAMS_CACHE) {
                hologram.delete();
                hologram.create();
            }
        }, 0L, 100L);



    }

    @Override
    public void onDisable() {

        HologramFactory.HOLOGRAMS_CACHE.forEach(NMSAdapter::delete);

    }
}
