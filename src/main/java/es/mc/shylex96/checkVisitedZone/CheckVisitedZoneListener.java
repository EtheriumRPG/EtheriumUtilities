package es.mc.shylex96.checkVisitedZone;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CheckVisitedZoneListener implements Listener {
    private final Location OlympSpawn = new Location(Bukkit.getWorld("world"), -102.5, 271, -141.5);
    private final Location OlympMarket = new Location(Bukkit.getWorld("world"), -46.5, -2, -121.5);
    private final Location MinasHub = new Location(Bukkit.getWorld("world"), 15041.5, 223, 21.5);
    private final Location MaderaHub = new Location(Bukkit.getWorld("world"), 14861.5, 88, 35.5);
    private final Location Arena = new Location(Bukkit.getWorld("world"), 15014, 101, -827);
    private final Location FieryAbyss = new Location(Bukkit.getWorld("world"), 5444.5, 164, -482.5);
    private final Location Mina1 = new Location(Bukkit.getWorld("world"), 15315, 30, 138.9);
    private final Location Mina2 = new Location(Bukkit.getWorld("world"), 15300, 30, 138.9);
    private final Location Mina3 = new Location(Bukkit.getWorld("world"), 15185, 30, 138.9 );
    private final Location Mina4 = new Location(Bukkit.getWorld("world"), 15170, 30, 138.9);

    private final File zoneFile = new File("plugins/EtheriumUtilities/CheckVisitedZone/visited_zones.yml");
    private final FileConfiguration zoneData = YamlConfiguration.loadConfiguration(zoneFile);

    // Método para verificar si el jugador está dentro del radio de una zona
    private boolean isInZone(Location playerLocation, Location zoneLocation, int radius) {
        return Objects.equals(playerLocation.getWorld(), zoneLocation.getWorld()) &&
                playerLocation.distance(zoneLocation) <= radius;
    }

    // Verificar si el jugador ya visitó una zona
    private boolean hasVisitedZone(Player player, String zoneName) {
        String path = player.getUniqueId() + "." + zoneName;
        return zoneData.getBoolean(path, false);
    }

    // Marca la zona como visitada
    private void markZoneAsVisited(Player player, String zoneName) {
        String path = player.getUniqueId() + "." + zoneName;
        zoneData.set(path, true);
        saveZoneData();
    }

    // Guardar los datos en el archivo
    private void saveZoneData() {
        try {
            zoneData.save(zoneFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("No se pudo guardar el archivo visited_zones.yml");
        }
    }

    // Ejecutar acciones específicas según la zona
    private void executeZoneAction(Player player, String zoneName) {
        if (!hasVisitedZone(player, zoneName)) {
            switch (zoneName) {
                case "OlympSpawn" -> {
                    player.sendTitle("Bienvenido/a al Olympo", "", 10, 70, 20);
                }
                case "OlympMarket" -> {
                    player.sendTitle("Bienvenido/a al Mercado", "Explora las tiendas", 10, 70, 20);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ca grantimpossible " + player.getName() + " 1 server_quest.agorahub1");
                }
                case "MinasHub" -> {
                    player.sendTitle("Bienvenido/a a Minas Hub", "Prepárate para minar", 10, 70, 20);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ca grantimpossible " + player.getName() + " 1 server_quest.minehub1");
                }
                case "MaderaHub" -> {
                    player.sendTitle("Bienvenido/a al Hub de Madera", "Corta y recolecta recursos", 10, 70, 20);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ca grantimpossible " + player.getName() + " 1 server_quest.woodhub1");
                }
                case "Arena" -> {
                    player.sendTitle("Bienvenido/a al Coliseo", "Cuidado con las tormentas de arena", 10, 70, 20);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ca grantimpossible " + player.getName() + " 1 server_quest.arenahub1");
                }
                case "FieryAbyss" -> {
                    player.sendTitle("Bienvenido/a al Abismo Ardiente", "El calor es extremo aquí", 10, 70, 20);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ca grantimpossible " + player.getName() + " 1 server_quest.fieryabyss");
                }
                case "Mina1" -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ca grantimpossible " + player.getName() + " 1 server_quest.mine_d");
                }
                case "Mina2" -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ca grantimpossible " + player.getName() + " 1 server_quest.mine_c");
                }
                case "Mina3" -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ca grantimpossible " + player.getName() + " 1 server_quest.mine_b");
                }
                case "Mina4" -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ca grantimpossible " + player.getName() + " 1 server_quest.mine_a");
                }
            }
            markZoneAsVisited(player, zoneName);
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();

        if (to == null) return;

        // Verificar cada zona
        if (isInZone(to, OlympSpawn, 5)) {
            executeZoneAction(player, "OlympSpawn");
        } else if (isInZone(to, OlympMarket, 5)) {
            executeZoneAction(player, "OlympMarket");
        } else if (isInZone(to, MinasHub, 5)) {
            executeZoneAction(player, "MinasHub");
        } else if (isInZone(to, MaderaHub, 5)) {
            executeZoneAction(player, "MaderaHub");
        } else if (isInZone(to, Arena, 5)) {
            executeZoneAction(player, "Arena");
        } else if (isInZone(to, FieryAbyss, 5)) {
            executeZoneAction(player, "FieryAbyss");
        } else if (isInZone(to, Mina1, 3)) {
            executeZoneAction(player, "Mina1");
        } else if (isInZone(to, Mina2, 3)) {
            executeZoneAction(player, "Mina2");
        } else if (isInZone(to, Mina3, 3)) {
            executeZoneAction(player, "Mina3");
        } else if (isInZone(to, Mina4, 3)) {
            executeZoneAction(player, "Mina4");
        }
    }
}
