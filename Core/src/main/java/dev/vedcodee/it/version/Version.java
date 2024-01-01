 package dev.vedcodee.it.version;

 import java.util.List;

 public class Version {

    public final String version;
    public final int NUMBER_VERSION; // for example 8 = 1.8.8, 19 = 1.19.4 || 1.19.2 etc...

    public Version(String version) {
        this.version = version;
        this.NUMBER_VERSION = Integer.parseInt(version.split("_")[1]);
    }








}
