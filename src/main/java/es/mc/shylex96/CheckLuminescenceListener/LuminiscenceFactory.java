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
                        ChatColor.LIGHT_PURPLE + "Un objeto que desborda pura magia oscura,",
                        ChatColor.LIGHT_PURPLE + "irradiando una energía negativa que crea un",
                        ChatColor.LIGHT_PURPLE + "vacío eterno."
                ));
                break;
            case Espectral:
                meta.setDisplayName(ChatColor.AQUA + "Luminiscencia Espectral");
                meta.setLore(List.of(
                        ChatColor.LIGHT_PURPLE + "Un objeto que posee un resplandor de origen",
                        ChatColor.LIGHT_PURPLE + "cósmico que fluye desde el abismo estelar.",
                        ChatColor.LIGHT_PURPLE + "Su brillo místico se emplea para fortalecer",
                        ChatColor.LIGHT_PURPLE + "objetos a un nivel celestial."
                ));
                break;
            case Eterna:
                meta.setDisplayName(ChatColor.GOLD + "Luminiscencia Eterna");
                meta.setLore(List.of(
                        ChatColor.LIGHT_PURPLE + "Creada a partir del primer estallido de",
                        ChatColor.LIGHT_PURPLE + "luz cósmica y refinada en la calma del",
                        ChatColor.LIGHT_PURPLE + "vacío infinito. Es inmutable, como parte",
                        ChatColor.LIGHT_PURPLE + "del equilibrio universal."
                ));
                break;
        }

        Enchantment enchantment = Enchantment.getByName("DURABILITY");
        if (enchantment != null) {
            meta.addEnchant(enchantment, 1, true);
        }

        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        return item;
    }
}
