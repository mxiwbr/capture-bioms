package io.github.mxiwbr.capturebiomes.commands;

import io.github.mxiwbr.capturebiomes.CaptureBiomes;
import io.github.mxiwbr.capturebiomes.factories.ItemFactory;
import io.github.mxiwbr.capturebiomes.utils.ConsoleUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

public class CaptureBiomesCommands {

    /**
     * Action of the /capturebiomes givebiomepotion command: Gives the executing player a biome potion
     * @param biome
     * @param tier
     * @param player
     */
    public static void commandGivebiomepotion(Biome biome, int tier, Player player) {

        try {
            player.getInventory().addItem(ItemFactory.createBiomePotion(biome, tier));
        }
        catch (Exception e) {

            ConsoleUtils.log("Failed to give biome potion to " + player.getName() + ": " + e.getMessage(), ConsoleUtils.LogType.SEVERE);
            player.sendMessage(Component.text("Oops! Something went wrong and your biome potion could not be delivered.", NamedTextColor.RED));

        }

    }

    /**
     * Actions of the /capturebiomes help command
     * @param player
     */
    public static void commandHelp(Player player) {

        player.sendMessage(Component.text("/capturebiomes enable", NamedTextColor.GREEN)
                .append(Component.text(" - Enables the plugin", NamedTextColor.WHITE)));
        player.sendMessage(Component.text("/capturebiomes disable", NamedTextColor.RED)
                .append(Component.text(" - Disables the plugin", NamedTextColor.WHITE)));
        player.sendMessage(Component.text("/capturebiomes givebiomepotion <biome> <tier>", NamedTextColor.GOLD)
                .append(Component.text(" - Gives a biome potion", NamedTextColor.WHITE)));

    }

    /**
     * Actions of the /capturebiomes enable command: Enables the plugin
     */
    public static void commandEnable(Player player) {

        CaptureBiomes.CONFIG.setPluginEnabled(true);
        CaptureBiomes.INSTANCE.getConfig().set("enabled", true);
        CaptureBiomes.INSTANCE.saveConfig();

        player.sendMessage(Component.text("[CaptureBiomes] ", NamedTextColor.GREEN, TextDecoration.BOLD)
                .append(Component.text("The plugin was enabled.", NamedTextColor.GREEN)
                        .decorationIfAbsent(TextDecoration.BOLD, TextDecoration.State.FALSE)));

        ConsoleUtils.log("The plugin was enabled by " + player.getName(), ConsoleUtils.LogType.ADDITIONAL_INFO);

    }

    /**
     * Actions of the /capturebiomes disable command: Disables the plugin
     */
    public static void commandDisable(Player player) {

        CaptureBiomes.CONFIG.setPluginEnabled(false);
        CaptureBiomes.INSTANCE.getConfig().set("enabled", false);
        CaptureBiomes.INSTANCE.saveConfig();

        player.sendMessage(Component.text("[CaptureBiomes] ", NamedTextColor.GREEN, TextDecoration.BOLD)
                .append(Component.text("The plugin was disabled.", NamedTextColor.RED)
                        .decorationIfAbsent(TextDecoration.BOLD, TextDecoration.State.FALSE)));

        ConsoleUtils.log("The plugin was disabled by " + player.getName(), ConsoleUtils.LogType.ADDITIONAL_INFO);

    }

}
