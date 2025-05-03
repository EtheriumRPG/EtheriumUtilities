package es.mc.shylex96.CheckLuminescenceListener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;

import java.util.List;

public class LuminiscenceFactory {
    public enum TipoLuminiscencia {
        Misteriosa,
        Espectral,
        Eterna
    }

    public static ItemStack crearLuminiscencia(TipoLuminiscencia tipo) {
        ItemStack item = new ItemStack(Material.GLOW_INK_SAC);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        switch (tipo) {
            case Misteriosa:
                meta.setDisplayName(ChatColor.GREEN + "Luminiscencia Misteriosa");
                meta.setLore(List.of(
                        ChatColor.GRAY + "Un objeto que desborda pura magia oscura,",
                        ChatColor.GRAY + "irradiando una energía negativa que crea un",
                        ChatColor.GRAY + "vacío eterno."
                ));
                break;
            case Espectral:
                meta.setDisplayName(ChatColor.AQUA + "Luminiscencia Espectral");
                meta.setLore(List.of(
                        ChatColor.GRAY + "Un objeto que posee un resplandor de origen",
                        ChatColor.GRAY + "cósmico que fluye desde el abismo estelar.",
                        ChatColor.GRAY + "Su brillo místico se emplea para fortalecer",
                        ChatColor.GRAY + "objetos a un nivel celestial."
                ));
                break;
            case Eterna:
                meta.setDisplayName(ChatColor.GOLD + "Luminiscencia Eterna");
                meta.setLore(List.of(
                        ChatColor.GRAY + "Creada a partir del primer estallido de",
                        ChatColor.GRAY + "luz cósmica y refinada en la calma del",
                        ChatColor.GRAY + "vacío infinito. Es inmutable, como parte",
                        ChatColor.GRAY + "del equilibrio universal."
                ));
                break;
        }

        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        return item;
    }
}