package es.mc.shylex96.checkBackpacks;

import es.mc.shylex96.EtheriumUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CheckBackpackListener implements Listener {

    private final EtheriumUtilities plugin;

    public CheckBackpackListener(EtheriumUtilities plugin) {
        this.plugin = plugin;
    }

    // Mapa de displayName (sin color) => nombre del comando relacionado con la mochila
    private static final Map<String, String> mochilaKeyMap = new HashMap<>();
    static {
        mochilaKeyMap.put("Mochila Básica", "basic_backpack");
        mochilaKeyMap.put("Mochila Reforzada", "reinforced_backpack");
        mochilaKeyMap.put("Mochila Avanzada", "advanced_backpack");
        mochilaKeyMap.put("Mochila Experta", "expert_backpack");
        mochilaKeyMap.put("Mochila Definitiva", "ultimate_backpack");
        mochilaKeyMap.put("Mochila Legendaria", "legendary_backpack");
    }

    @EventHandler
    public void onPaperUse(PlayerInteractEvent event) {
        if (!event.getAction().toString().contains("RIGHT_CLICK")) return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.PAPER) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName() || !meta.hasLore()) return;

        String paperName = ChatColor.stripColor(meta.getDisplayName()).trim();
        List<String> lore = meta.getLore();

        // Extraigo el nombre de la mochila requerida del lore
        String mochilaRequerida = lore.stream()
                .filter(linea -> ChatColor.stripColor(linea).contains("« mochila") && linea.contains("inventario"))
                .map(ChatColor::stripColor)
                .map(linea -> {
                    int start = linea.indexOf("« ");
                    int end = linea.indexOf(" »");
                    if (start == -1 || end == -1 || start + 2 >= end) return null;
                    return linea.substring(start + 2, end).trim().toLowerCase().replace(" ", "_");
                })
                .filter(str -> str != null)
                .findFirst()
                .orElse(null);

        if (mochilaRequerida == null) return;

        boolean tieneMochila = false;
        for (ItemStack invItem : player.getInventory().getContents()) {
            if (invItem == null || invItem.getType() != Material.BUNDLE) continue;

            ItemMeta invMeta = invItem.getItemMeta();
            if (invMeta != null && invMeta.hasDisplayName()) {
                String nombre = ChatColor.stripColor(invMeta.getDisplayName()).toLowerCase().replace(" ", "_");
                if (nombre.contains(mochilaRequerida)) {
                    tieneMochila = true;
                    invItem.setAmount(invItem.getAmount() - 1);
                    break;
                }
            }
        }

        if (!tieneMochila) {
            player.sendMessage(ChatColor.RED + "No tienes la mochila requerida para usar este canje.");
            return;
        }

        // Busco y elimino el papel específico usado (comparando displayName y lore)
        for (ItemStack invItem : player.getInventory().getContents()) {
            if (invItem == null || invItem.getType() != Material.PAPER) continue;

            ItemMeta invMeta = invItem.getItemMeta();
            if (invMeta == null || !invMeta.hasDisplayName() || !invMeta.hasLore()) continue;

            String nombre = ChatColor.stripColor(invMeta.getDisplayName()).trim();
            List<String> loreItem = invMeta.getLore();

            if (nombre.equalsIgnoreCase(paperName) && loreItem.equals(lore)) {
                invItem.setAmount(invItem.getAmount() - 1);
                break;
            }
        }

        // Busco el nombre interno correcto de la mochila a entregar
        String nuevaMochila = mochilaKeyMap.getOrDefault(paperName, null);
        if (nuevaMochila == null) {
            return;
        }

        // Ejecutar el comando como si fuera el jugador y otorgar temporalmente el permiso necesario para ejecutar el comando
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            // Dar permiso
            PermissionAttachment attachment = player.addAttachment(plugin);
            attachment.setPermission("powerfulbackpacks.use", true);

            // Ejecutar el comando como jugador
            player.performCommand("backpack give " + nuevaMochila);

            // Retirar permiso tras 1seg
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.removeAttachment(attachment);
            }, 20L);
        }, 1L);

        // Mensaje + sonido
        player.sendMessage(ChatColor.GREEN + "¡Has mejorado tu mochila con éxito!");
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
    }
}
