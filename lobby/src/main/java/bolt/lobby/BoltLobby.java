package bolt.lobby;

import bolt.core.BoltCore;
import bolt.core.listeners.BlockBreakListener;
import bolt.core.listeners.ItemDropListener;
import bolt.core.listeners.ItemPickupListener;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.*;
import net.minestom.server.instance.block.Block;
import net.minestom.server.coordinate.Pos;

public class BoltLobby {
    public static void main(String[] args) {
        BoltCore boltCore = new BoltCore("config.yml");

        registerListeners();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
        instanceContainer.setChunkSupplier(LightingChunk::new);

        instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
        });

        boltCore.start();
    }

    private static void registerListeners() {
        MinecraftServer.getGlobalEventHandler().addListener(new BlockBreakListener());
        MinecraftServer.getGlobalEventHandler().addListener(new ItemPickupListener());
        MinecraftServer.getGlobalEventHandler().addListener(new ItemDropListener());
    }    
}
