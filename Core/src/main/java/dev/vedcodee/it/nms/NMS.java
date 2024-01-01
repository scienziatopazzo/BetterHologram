package dev.vedcodee.it.nms;

import dev.vedcodee.it.NMSAdapter;
import dev.vedcodee.it.NMS_1_19_R3;
import dev.vedcodee.it.version.Version;
import lombok.Getter;
import org.bukkit.Bukkit;

public class NMS {


    @Getter
    private static Class<? extends NMSAdapter> adapter;
    @Getter
    private static Version version;

    public static void init() {
        loadVersion();

        if(version.NUMBER_VERSION > 8) { // Version up 1.8.x
            adapter = NMS_1_19_R3.class;
        }else{
            //adapter = NMS_1_8_R3.class;
        }

    }


    public static void loadVersion() {
        if (version == null) {
            version = new Version(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]);
        }
    }



}
