package io.github.mxiwbr.capturebioms;

import io.github.mxiwbr.capturebioms.listener.ItemListener;
import io.github.mxiwbr.capturebioms.services.UpdateService;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class CaptureBioms extends JavaPlugin {

    // Console logger
    public static Logger LOGGER;
    public static CaptureBioms INSTANCE;
    public static Config CONFIG;

    // Called when the plugin is enabled
    @Override
    public void onEnable() {

        // Global plugin instance object
        INSTANCE = this;

        // creates a default config.yml if there is none
        this.saveDefaultConfig();
        // Loads config defaults from plugin resource
        getConfig().options().copyDefaults(true);
        // writes missing config options
        saveConfig();
        // Creates a config object to get config values
        CONFIG = new Config();

        // Set logger object to log from other classes
        LOGGER = getLogger();
        LOGGER.info("Enabled!");
        Boolean newVersionAvailable = UpdateService.checkForUpdates();

        // Register XpBottleListener() event
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
    }

}
