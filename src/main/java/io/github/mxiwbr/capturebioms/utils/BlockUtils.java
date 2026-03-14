package io.github.mxiwbr.capturebioms.utils;

import org.bukkit.Location;

public class BlockUtils {

    /**
     * Gets the next solid block above a given location.
     * Returns the max height limit if there is no block above
     * @param location
     */
    public static int getNextSolidBlockY(Location location) {

        int y = location.getBlockY() + 1;

        while (y < location.getWorld().getMaxHeight() &&
                location.getWorld().getBlockAt(location.getBlockX(), y, location.getBlockZ()).isPassable()) {
            y++;
        }

        return y;
    }


}
