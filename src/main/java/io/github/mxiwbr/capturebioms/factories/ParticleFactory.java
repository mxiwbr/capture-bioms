package io.github.mxiwbr.capturebioms.factories;

import io.github.mxiwbr.capturebioms.CaptureBioms;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleFactory {

    /**
     * Creates a double DNA-like particle spiral around a given location
     * @param center central coordinate of the spiral
     * @param color color of the spiral
     */
    public static void createParticleSpiral(Location center, Color color) {

        new BukkitRunnable() {

            double t = 0;
            // sets the color and size of the particles
            final Particle.DustOptions dust = new Particle.DustOptions(color, 1.5f);

            @Override
            public void run() {

                // Height of the spiral
                if (t > 25) {
                    cancel();
                    return;
                }

                // current height of the spiral
                double y = t * 0.2;

                // first spiral
                double x1 = Math.cos(t) * 0.6;
                double z1 = Math.sin(t) * 0.6;

                // second spiral (frist spiral 180° rotated)
                double x2 = Math.cos(t + Math.PI) * 0.6;
                double z2 = Math.sin(t + Math.PI) * 0.6;

                Location loc1 = center.clone().add(0.5 + x1, 1 + y, 0.5 + z1);
                Location loc2 = center.clone().add(0.5 + x2, 1 + y, 0.5 + z2);

                center.getWorld().spawnParticle(Particle.DUST, loc1, 1, dust);
                center.getWorld().spawnParticle(Particle.DUST, loc2, 1, dust);

                t += Math.PI / 8;
            }

        }.runTaskTimer(CaptureBioms.INSTANCE, 0L, 1L);
    }

    /**
     * Creates a "square edges" particle animation rising from start location uo to height
     * Always 15 seconds long
     * @param center center of animation
     * @param color
     * @param size
     * @param height y-coordinate of highest point
     */
    public static void squareRisingEdges(Location center, Color color, int size, double height) {

        final int totalTicks = 300;
        final double heightPerTick = (height - center.getY()) / totalTicks;

        new BukkitRunnable() {

            int tick = 0;

            @Override
            public void run() {

                if (tick > totalTicks) {
                    cancel();
                    return;
                }

                double yOffset = tick * heightPerTick;

                for (int x = -size / 2; x <= size / 2; x++) {
                    for (int z = -size / 2; z <= size / 2; z++) {
                        if (x == -size / 2 || x == size / 2 || z == -size / 2 || z == size / 2) {
                            Location loc = center.clone().add(x + 0.5, yOffset, z + 0.5);
                            center.getWorld().spawnParticle(
                                    Particle.DUST,
                                    loc,
                                    1,
                                    new Particle.DustOptions(color, 1.5f)
                            );
                        }
                    }
                }

                tick++;
            }

        }.runTaskTimer(CaptureBioms.INSTANCE, 0L, 1L);
    }

}