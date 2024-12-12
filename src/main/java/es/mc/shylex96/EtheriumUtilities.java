package es.mc.shylex96;

import es.mc.shylex96.ItemConversionListener.ItemConversionListener;
import es.mc.shylex96.checkVisitedZone.CheckVisitedZoneListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EtheriumUtilities extends JavaPlugin {

    public static String prefix = "§a[EtheriumUtilities]";
    private String version = getDescription().getVersion();

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(prefix +
                " §7Plugin creado por §eShylex §7ha cargado correctamente en la versión: §c" + version);
        getServer().getPluginManager().registerEvents(new ItemConversionListener(), this);
        getServer().getPluginManager().registerEvents(new CheckVisitedZoneListener(), this);
    }

    @Override
    public void onDisable() {
        // ...
    }
}
