package io.github.mxiwbr.capturebiomes.services;

import io.github.mxiwbr.capturebiomes.CaptureBiomes;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.mxiwbr.capturebiomes.utils.ConsoleUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static io.github.mxiwbr.capturebiomes.utils.ConsoleUtils.log;

public class UpdateService {

    /**
     * Scans the GitHub page for new releases
     * @return true or false whether a new update is available
     */
    public static Boolean checkForUpdates() {

        final String pluginVersion = CaptureBiomes.INSTANCE.getPluginMeta().getVersion();

        log("Checking for updates...", ConsoleUtils.LogType.INFO);

        try {

            String latestPluginVersion = getLatestVersion();

            // Check if new version is available and log it
            if (!pluginVersion.equals(latestPluginVersion)) {

                log("A new plugin version is available: " + latestPluginVersion + ", you're on: " + pluginVersion, ConsoleUtils.LogType.INFO);

                return true;
            }

            log("You're up to date!", ConsoleUtils.LogType.INFO);

        } catch (Exception e) {

            log("An error occurred while checking for updates:", ConsoleUtils.LogType.WARNING);
            log(e.getClass().getSimpleName() + " - " + e.getMessage(), ConsoleUtils.LogType.WARNING);
            if (CaptureBiomes.CONFIG.isEnableConsoleLogging()) { e.printStackTrace(); }
        }

        return false;

    }

    /**
     * Gets latest plugin version from GitHub and returns it as string
     * @throws IOException
     * @throws InterruptedException
     */
    public static String getLatestVersion() throws IOException, InterruptedException {

        // Get latest plugin release from GitHub
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/repos/mxiwbr/capture-biomes/releases/latest"))
                .header("Accept", "application/vnd.github.v3+json")
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        httpClient.close();

        // Parse response to JSON and get the release tag as string
        String jsonString = httpResponse.body();
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("tag_name").getAsString();

    }

    /**
     * Sends the update available message to a player in the ingame chat
     * @param player
     */
    public static void sendUpdateMessageToPlayer(Player player) {

        try {

            player.sendMessage(Component.text("[CaptureBiomes] ", NamedTextColor.GREEN, TextDecoration.BOLD)
                    .append(Component.text("There is a newer plugin version available: "
                                    + UpdateService.getLatestVersion()
                                    + ", you're on: "
                                    + CaptureBiomes.INSTANCE.getPluginMeta().getVersion(), NamedTextColor.GREEN)
                            .decorationIfAbsent(TextDecoration.BOLD, TextDecoration.State.FALSE)));

        }
        catch (Exception e) {

            CaptureBiomes.LOGGER.severe(e.getMessage());

        }

    }

}
