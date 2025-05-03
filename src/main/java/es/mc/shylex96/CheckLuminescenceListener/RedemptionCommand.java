package es.mc.shylex96.CheckLuminescenceListener;

import es.mc.shylex96.CheckLuminescenceListener.RedemptionFactory.TipoLuminiscencia;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class RedemptionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Si el comando se ejecuta desde la consola
        if (sender instanceof CommandSender) {
            // Asegurar de que el nombre del jugador ha sido proporcionado
            if (args.length == 0) {
                Bukkit.getConsoleSender().sendMessage("No se especificó el nombre del jugador");
                return false;
            }

            // Obtener el jugador al que se le dará el ítem
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                Bukkit.getConsoleSender().sendMessage("El jugador especificado no está en línea.");
                return false;
            }

            // Comando /papelmisterioso
            if (command.getName().equalsIgnoreCase("papelmisterioso")) {
                ItemStack paper = RedemptionFactory.crearPapelCanje(TipoLuminiscencia.Misteriosa);
                targetPlayer.getInventory().addItem(paper);
                // targetPlayer.sendMessage(ChatColor.GREEN + "Has recibido un canje de luminiscencia misteriosa.");
            }
            // Comando /papelespectral
            else if (command.getName().equalsIgnoreCase("papelespectral")) {
                ItemStack paper = RedemptionFactory.crearPapelCanje(TipoLuminiscencia.Espectral);
                targetPlayer.getInventory().addItem(paper);
                // targetPlayer.sendMessage(ChatColor.GREEN + "Has recibido un canje de luminiscencia espectral.");
            }
            // Comando /papeleterno
            else if (command.getName().equalsIgnoreCase("papeleterno")) {
                ItemStack paper = RedemptionFactory.crearPapelCanje(TipoLuminiscencia.Eterna);
                targetPlayer.getInventory().addItem(paper);
                // targetPlayer.sendMessage(ChatColor.GREEN + "Has recibido un canje de luminiscencia eterna.");
            }

            return true;
        }

        return false;
    }
}
