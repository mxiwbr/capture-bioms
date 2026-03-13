package io.github.mxiwbr.capturebioms.utils;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    /**
     * Removes a given amount of items from a stack
     * @param item
     * @param amount
     */
    public static void removeItemsFromStack(Item item, int amount) {

        // get stack of given item
        ItemStack itemStack = item.getItemStack();

        if (itemStack.getAmount() > amount) {

            itemStack.setAmount(itemStack.getAmount() - amount);
        }
        else if (itemStack.getAmount() == amount) {

            item.remove();
        }

    }

}
