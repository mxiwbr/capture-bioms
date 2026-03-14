package io.github.mxiwbr.capturebioms.listener;

import io.github.mxiwbr.capturebioms.CaptureBioms;
import io.github.mxiwbr.capturebioms.factories.ParticleFactory;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * Listener class for entity events
 */
public class EntityListener implements Listener {

    @EventHandler
    public void onLingeringPotionSplash(LingeringPotionSplashEvent event) {

        // The potion that triggered the event
        ThrownPotion potionEntity = event.getEntity();
        ItemStack potionItem = potionEntity.getItem();

        // The areaEffectCloud that was spawned by splashing the potion
        AreaEffectCloud areaEffectCloud = event.getAreaEffectCloud();

        PersistentDataContainer pdc = potionItem.getItemMeta().getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(CaptureBioms.INSTANCE, "capturebioms.biomepotion");

        // Cancel and delete areaEffectCloud if not in overworld
        if (potionEntity.getLocation().getWorld().getEnvironment() != World.Environment.NORMAL) {
            areaEffectCloud.remove();
            return;
        }

        // Cancel if not biome potion (key capturebioms.biomepotion)
        if (!pdc.has(key)) { return; }

        // the tier (level) of the potion (1 - 4)
        final int tier = pdc.get(key, PersistentDataType.INTEGER);
        areaEffectCloud.setRadius(0);


        // particle effect up to max world height
        ParticleFactory.squareRisingEdges(potionEntity.getLocation(),
                potionEntity.getPotionMeta().getColor(),
                switch (tier) {
                    case 2 -> CaptureBioms.CONFIG.getBiomePotionSize()[1];
                    case 3 -> CaptureBioms.CONFIG.getBiomePotionSize()[2];
                    case 4 -> CaptureBioms.CONFIG.getBiomePotionSize()[3];
                    default -> CaptureBioms.CONFIG.getBiomePotionSize()[0];
                },
                potionEntity.getLocation().getWorld().getMaxHeight());
    }

}
