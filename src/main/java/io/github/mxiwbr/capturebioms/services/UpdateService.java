package io.github.mxiwbr.capturebioms.services;

import io.github.mxiwbr.capturebioms.CaptureBioms;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UpdateService {

    /**
     * Scans the GitHub page for new releases
     */
    public static Boolean checkForUpdates() {

        final String pluginVersion = CaptureBioms.INSTANCE.getPluginMeta().getVersion();

        CaptureBioms.LOGGER.info("Checking for updates...");

        try {

            // Get latest plugin release from GitHub
            var httpClient = HttpClient.newHttpClient();
            var httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.github.com/repos/mxiwbr/capture-biomes/releases/latest"))
                    .header("Accept", "\"application/vnd.github.v3+json\"")
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            httpClient.close();

            // Parse response to JSON and get the release tag as string
            String jsonString = httpResponse.body();
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            String latestPluginVersion = jsonObject.get("tag_name").getAsString();

            // Check if new version is available and log it
            if (!pluginVersion.equals(latestPluginVersion)) {

                CaptureBioms.LOGGER.info("A new plugin version is available: " + latestPluginVersion);
                CaptureBioms.LOGGER.info("You're on: " + pluginVersion);

                return true;
            }

            CaptureBioms.LOGGER.info("You're up to date!");

        } catch (Exception e) {

            CaptureBioms.LOGGER.warning("An error occurred while checking for updates:");
            CaptureBioms.LOGGER.warning(e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
        }

        return false;

    }

}
