package es.mc.shylex96.ItemConversionListener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemConversionListener implements Listener {

    // AL TIRAR 64 ÍTEMS Y RECOGER 64 MÁS, SOLO SE CREAN 7 BLOQUES,
    // DEJANDO UN RESTO DE 2 ÍTEMS POR CADA STACK DE 64 EN EL INVENTARIO.
    // SI LUEGO TIRO OTROS 64 ÍTEMS, EL RESTO SE DUPLICA EN AMBOS STACKS RESTANTES,
    // ES DECIR, A LOS 2 LAPISLÁZULI QUE QUEDABAN EN EL INVENTARIO SE LES SUMA 1 A CADA UNO.

    // TODO: ESTE COMPORTAMIENTO ES UN BUG VISUAL. AL MOVER LOS ÍTEMS RESTANTES
    // EN EL INVENTARIO, SE RESTAURA EL STACK COMPLETO DE 64, EL ÍTEM "PERDIDO


    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player player) {
            Inventory inventory = player.getInventory();

            // Convierte los ítems nuevos en bloques si es necesario
            convertItemsToBlocks(inventory);
        }
    }

    private void convertItemsToBlocks(Inventory inventory) {
        // Dependiendo del tipo de ítem, realizar la conversión correspondiente
        convertItemToBlock(inventory, Material.COAL, Material.COAL_BLOCK);
        convertItemToBlock(inventory, Material.LAPIS_LAZULI, Material.COAL_BLOCK);
        convertItemToBlock(inventory, Material.REDSTONE, Material.LAPIS_BLOCK);
        convertItemToBlock(inventory, Material.COPPER_INGOT, Material.COPPER_BLOCK);
        convertItemToBlock(inventory, Material.IRON_INGOT, Material.IRON_BLOCK);
        convertItemToBlock(inventory, Material.GOLD_INGOT, Material.GOLD_BLOCK);
        convertItemToBlock(inventory, Material.EMERALD, Material.EMERALD_BLOCK);
        convertItemToBlock(inventory, Material.DIAMOND, Material.DIAMOND_BLOCK);
    }

    private void convertItemToBlock(Inventory inventory, Material itemMaterial, Material blockMaterial) {
        int totalItemCount = 0;
        int itemsToRemove = 0;
        boolean blocksCreated = false;

        // Paso 1: Contar solo los ítems sueltos del tipo específico, ignorando bloques
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null && itemStack.getType() == itemMaterial && !isBlock(itemMaterial)) {
                totalItemCount += itemStack.getAmount();
            }
        }

        //Bukkit.getConsoleSender().sendMessage("Número de ítems reconocidos: " + totalItemCount + " del tipo: " + itemMaterial);

        // Calcular cuántos bloques se pueden crear
        int blocksToCreate = totalItemCount / 9;
        int remainingItems = totalItemCount % 9;

        //Bukkit.getConsoleSender().sendMessage("Bloques que puedo hacer: " + blocksToCreate + " del tipo: " +itemMaterial);
        //Bukkit.getConsoleSender().sendMessage("Minerales de resto: " + remainingItems + " del tipo: " +itemMaterial);

        if (blocksToCreate > 0) {
            itemsToRemove = blocksToCreate * 9;
            //Bukkit.getConsoleSender().sendMessage("Minerales eliminados: " + itemsToRemove + " del tipo: " +itemMaterial);

            // Primero, eliminamos los ítems del inventario
            for (ItemStack itemStack : inventory.getContents()) {
                if (itemStack != null && itemStack.getType() == itemMaterial) {
                    if (itemStack.getAmount() <= itemsToRemove) {
                        itemsToRemove -= itemStack.getAmount();
                        itemStack.setAmount(0);
                    } else {
                        itemStack.setAmount(itemStack.getAmount() - itemsToRemove);
                        itemsToRemove = 0;
                    }
                    if (itemsToRemove <= 0) {
                        break;
                    }
                }
            }

            // Luego, agregamos los bloques creados
            ItemStack blockStack = new ItemStack(blockMaterial, blocksToCreate);
            inventory.addItem(blockStack);
            //Bukkit.getConsoleSender().sendMessage("Minerales añadidos: " + blockStack + " del tipo: " +itemMaterial);

            blocksCreated = true;
        }

        // Primero eliminamos los ítems que podrían haber quedado del mismo tipo, si se han creado bloques
        if (blocksCreated && remainingItems > 0) {
            // Eliminar todos los ítems del tipo específico del inventario
            for (ItemStack itemStack : inventory.getContents()) {
                if (itemStack != null && itemStack.getType() == itemMaterial) {
                    itemStack.setAmount(0);
                }
            }

            // Luego, agregamos los ítems restantes
            ItemStack remainingStack = new ItemStack(itemMaterial, remainingItems);
            inventory.addItem(remainingStack);
        }

        totalItemCount = 0;
        itemsToRemove = 0;
    }

    private boolean isBlock(Material itemMaterial) {
        return switch (itemMaterial) {
            case COAL, LAPIS_LAZULI, REDSTONE, COPPER_INGOT, IRON_INGOT, GOLD_INGOT, EMERALD, DIAMOND -> false;
            default -> true;
        };
    }
}
