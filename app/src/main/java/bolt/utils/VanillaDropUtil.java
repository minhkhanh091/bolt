package bolt.utils;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class VanillaDropUtil {

    public static void spawnVanillaDrop(Instance instance, Pos blockPos, ItemStack itemStack) {
        if (itemStack.isAir()) return;

        ThreadLocalRandom random = ThreadLocalRandom.current();

        double offsetX = random.nextDouble(-0.1, 0.1);
        double offsetY = random.nextDouble(0.0, 0.1);
        double offsetZ = random.nextDouble(-0.1, 0.1);

        Pos spawnPos = blockPos.add(0.5 + offsetX, 0.3 + offsetY, 0.5 + offsetZ);

        ItemEntity itemEntity = new ItemEntity(itemStack);

        itemEntity.setPickupDelay(Duration.ofMillis(500));

        itemEntity.setHasPhysics(true);

        itemEntity.setInstance(instance, spawnPos).thenRun(() -> {
            double vx = random.nextDouble(-0.1, 0.1) * MinecraftServer.TICK_PER_SECOND;
            double vy = random.nextDouble(0.15, 0.25) * MinecraftServer.TICK_PER_SECOND;
            double vz = random.nextDouble(-0.1, 0.1) * MinecraftServer.TICK_PER_SECOND;

            itemEntity.setVelocity(new Vec(vx, vy, vz));
        });
    }
    public static void dropFromPlayer(Player player, ItemStack itemStack) {
        if (itemStack.isAir()) return;

        Pos spawnPos = player.getPosition().add(0, player.getEyeHeight() - 0.3, 0);

        ItemEntity itemEntity = new ItemEntity(itemStack);

        itemEntity.setPickupDelay(Duration.ofMillis(1500));
        itemEntity.setHasPhysics(true);

        itemEntity.setInstance(player.getInstance(), spawnPos).thenRun(() -> {
            Vec direction = player.getPosition().direction();

            double speed = 6.0;
            double vx = direction.x() * speed;
            double vy = direction.y() * speed + 2.0;
            double vz = direction.z() * speed;

            itemEntity.setVelocity(new Vec(vx, vy, vz));
        });
    }
}