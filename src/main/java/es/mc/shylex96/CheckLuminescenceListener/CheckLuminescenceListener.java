package es.mc.shylex96.CheckLuminescenceListener;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckLuminescenceListener implements Listener {

    private final EtheriumUtilities plugin;

    public CheckLuminescenceListener(EtheriumUtilities plugin) {
        this.plugin = plugin;
    }

    // Mapa de tipos de luminiscencia => comando a ejecutar
    private static final Map<String, String> luminiscenciaComandos = new HashMap<>();
    static {
        luminiscenciaComandos.put("Misteriosa", "luminiscencia misteriosa");
        luminiscenciaComandos.put("Espectral", "luminiscencia espectral");
        luminiscenciaComandos.put("Eterna", "luminiscencia eterna");
    }

    @EventHandler
    public void onPaperUse(PlayerInteractEvent event) {
        if (!event.getAction().toString().contains("RIGHT_CLICK")) return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.PAPER) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName() || !meta.hasLore()) return;

        List<String> lore = meta.getLore();
        String tipoStr = lore.stream()
                .map(ChatColor::stripColor)
                .filter(linea -> linea.toLowerCase().contains("luminiscencia"))
                .map(linea -> {
                    // Extraigo la última palabra de la frase (el tipo de luminiscencia)
                    String[] palabras = linea.split(" ");
                    String ultima = palabras[palabras.length - 1];
                    // Elimino el punto final
                    ultima = ultima.replaceAll("[^a-zA-Z]", "");
                    // Capitalizo la primera letra y minúsculas el resto
                    return ultima.substring(0, 1).toUpperCase() + ultima.substring(1).toLowerCase();
                })
                .findFirst()
                .orElse(null);


        if (tipoStr == null) {
            player.sendMessage(ChatColor.RED + "Ha ocurrido un error al canjear");
            return;
        }

        LuminiscenceFactory.TipoLuminiscencia tipo;
        try {
            tipo = LuminiscenceFactory.TipoLuminiscencia.valueOf(tipoStr);
        } catch (IllegalArgumentException e) {
            // player.sendMessage(ChatColor.RED + "El tipo de luminiscencia no es válido. Tipo extraído: " + tipoStr);
            player.sendMessage(ChatColor.RED + "El tipo de luminiscencia no es válido.");
            return;
        }

        // Eliminar el papel
        item.setAmount(item.getAmount() - 1);

        // Dar la luminiscencia
        player.getInventory().addItem(LuminiscenceFactory.crearLuminiscencia(tipo));

        player.sendMessage(ChatColor.GREEN + "¡Has canjeado con éxito una luminiscencia " + tipoStr.toLowerCase() + "!");
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1.2f);
    }
}
