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
                meta.setDisplayName("{\"text\":\"Luminiscencia Misteriosa\",\"italic\":false,\"color\":\"green\"}");
                meta.setLore(Arrays.asList(
                        "{\"text\":\"Este papel otorgará una luminiscencia misteriosa.\",\"italic\":false,\"color\":\"gray\"}",
                        "{\"text\":\"\",\"italic\":false,\"color\":\"gray\"}",
                        "{\"text\":\"Usa este papel haciendo clic derecho con él en la mano.\",\"italic\":false,\"color\":\"light_purple\"}",
                        "{\"text\":\"Canjearás la luminiscencia correspondiente y podrás usarla.\",\"italic\":false,\"color\":\"light_purple\"}",
                        "{\"text\":\"\",\"italic\":false,\"color\":\"gray\"}",
                        "{\"text\":\"⚠ Este canje es de un solo uso. Al canjear\",\"italic\":false,\"color\":\"red\"}",
                        "{\"text\":\"la luminiscencia, este papel desaparecerá.\",\"italic\":false,\"color\":\"red\"}"
                ));
                break;
            case Espectral:
                meta.setDisplayName("{\"text\":\"Luminiscencia Espectral\",\"italic\":false,\"color\":\"aqua\"}");
                meta.setLore(Arrays.asList(
                        "{\"text\":\"Este papel otorgará una luminiscencia espectral.\",\"italic\":false,\"color\":\"gray\"}",
                        "{\"text\":\"\",\"italic\":false,\"color\":\"gray\"}",
                        "{\"text\":\"Usa este papel haciendo clic derecho con él en la mano.\",\"italic\":false,\"color\":\"light_purple\"}",
                        "{\"text\":\"Canjearás la luminiscencia correspondiente y podrás usarla.\",\"italic\":false,\"color\":\"light_purple\"}",
                        "{\"text\":\"\",\"italic\":false,\"color\":\"gray\"}",
                        "{\"text\":\"⚠ Este canje es de un solo uso. Al canjear\",\"italic\":false,\"color\":\"red\"}",
                        "{\"text\":\"la luminiscencia, este papel desaparecerá.\",\"italic\":false,\"color\":\"red\"}"
                ));
                break;
            case Eterna:
                meta.setDisplayName("{\"text\":\"Luminiscencia Eterna\",\"italic\":false,\"color\":\"gold\"}");
                meta.setLore(Arrays.asList(
                        "{\"text\":\"Este papel otorgará una luminiscencia eterna.\",\"italic\":false,\"color\":\"gray\"}",
                        "{\"text\":\"\",\"italic\":false,\"color\":\"gray\"}",
                        "{\"text\":\"Usa este papel haciendo clic derecho con él en la mano.\",\"italic\":false,\"color\":\"light_purple\"}",
                        "{\"text\":\"Canjearás la luminiscencia correspondiente y podrás usarla.\",\"italic\":false,\"color\":\"light_purple\"}",
                        "{\"text\":\"\",\"italic\":false,\"color\":\"gray\"}",
                        "{\"text\":\"⚠ Este canje es de un solo uso. Al canjear\",\"italic\":false,\"color\":\"red\"}",
                        "{\"text\":\"la luminiscencia, este papel desaparecerá.\",\"italic\":false,\"color\":\"red\"}"
                ));
                break;
        }

        item.setItemMeta(meta);
        return item;
    }
}
