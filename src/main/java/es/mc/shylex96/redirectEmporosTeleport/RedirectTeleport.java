package es.mc.shylex96.redirectEmporosTeleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class RedirectTeleport implements Listener {
    // Coordenadas originales (positivas)
    private final double POS_X = 77.5;
    private final double POS_Y = 220;
    private final double POS_Z = 165.5;

    // Coordenadas alternativas (negativas)
    private final double NEG_X = -77.5;
    private final double NEG_Y = 220;
    private final double NEG_Z = -165.5;

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        //Bukkit.getConsoleSender().sendMessage("Teleport detectado");
        Player player = event.getPlayer();
        Location destination = event.getTo();

        // Comprobamos si el jugador está dentro del rango de las coordenadas positivas
        if (destination != null && isInRange(destination)) {
            //Bukkit.getConsoleSender().sendMessage("Son las coordenadas");
            event.setCancelled(true);

            // Teletransportar al jugador a las coordenadas negativas
            Location negativeLocation = new Location(destination.getWorld(), NEG_X, NEG_Y, NEG_Z);
            player.teleport(negativeLocation);
        }
    }

    // Método para comprobar si el jugador está cerca de las coordenadas positivas
    private boolean isInRange(Location loc) {
        // Comprobar si el jugador está dentro de un radio de 3 bloques de las coordenadas positivas
        return loc.getX() >= POS_X - 3 && loc.getX() <= POS_X + 3
                && loc.getY() >= POS_Y - 3 && loc.getY() <= POS_Y + 3
                && loc.getZ() >= POS_Z - 3 && loc.getZ() <= POS_Z + 3;
    }
}
