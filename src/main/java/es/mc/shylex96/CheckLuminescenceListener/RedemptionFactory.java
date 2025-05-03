package es.mc.shylex96.CheckLuminescenceListener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class RedemptionFactory {

    public enum TipoLuminiscencia {
        Misteriosa,
        Espectral,
        Eterna
    }

    public static ItemStack crearPapelCanje(TipoLuminiscencia tipo) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        switch (tipo) {
            case Misteriosa:
                meta.setDisplayName(ChatColor.GREEN + "Luminiscencia Misteriosa");
                meta.setLore(Arrays.asList(
                        ChatColor.GRAY + "Este papel otorgará una luminiscencia misteriosa.",
                        "",
                        ChatColor.LIGHT_PURPLE + "Usa este papel haciendo clic derecho con él en la mano.",
                        ChatColor.LIGHT_PURPLE + "Canjearás la luminiscencia correspondiente y podrás usarla.",
                        "",
                        ChatColor.RED + "⚠ Este canje es de un solo uso. Al canjear",
                        ChatColor.RED + "la luminiscencia, este papel desaparecerá."
                ));
                break;
            case Espectral:
                meta.setDisplayName(ChatColor.AQUA + "Luminiscencia Espectral");
                meta.setLore(Arrays.asList(
                        ChatColor.GRAY + "Este papel otorgará una luminiscencia espectral.",
                        "",
                        ChatColor.LIGHT_PURPLE + "Usa este papel haciendo clic derecho con él en la mano.",
                        ChatColor.LIGHT_PURPLE + "Canjearás la luminiscencia correspondiente y podrás usarla.",
                        "",
                        ChatColor.RED + "⚠ Este canje es de un solo uso. Al canjear",
                        ChatColor.RED + "la luminiscencia, este papel desaparecerá."
                ));
                break;
            case Eterna:
                meta.setDisplayName(ChatColor.GOLD + "Luminiscencia Eterna");
                meta.setLore(Arrays.asList(
                        ChatColor.GRAY + "Este papel otorgará una luminiscencia eterna.",
                        "",
                        ChatColor.LIGHT_PURPLE + "Usa este papel haciendo clic derecho con él en la mano.",
                        ChatColor.LIGHT_PURPLE + "Canjearás la luminiscencia correspondiente y podrás usarla.",
                        "",
                        ChatColor.RED + "⚠ Este canje es de un solo uso. Al canjear",
                        ChatColor.RED + "la luminiscencia, este papel desaparecerá."
                ));
                break;
        }

        item.setItemMeta(meta);
        return item;
    }
}
