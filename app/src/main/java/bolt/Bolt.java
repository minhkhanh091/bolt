package bolt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bolt.commands.*;
import bolt.commands.system.*;
import bolt.listeners.*;
import bolt.manager.ConsoleManager;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.*;
import net.minestom.server.instance.block.Block;
import net.minestom.server.coordinate.Pos;

public class Bolt {
    private final static Logger LOGGER = LoggerFactory.getLogger(Bolt.class);

    private static void registerCommands()
    {
        MinecraftServer.getCommandManager().register(new StopCommand());
        MinecraftServer.getCommandManager().register(new PingCommand());
    }

    private static void registerListeners() {
        MinecraftServer.getGlobalEventHandler().addListener(new BlockBreakListener());
        MinecraftServer.getGlobalEventHandler().addListener(new BlockPickupListener());
    }

    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();

        registerCommands();
        registerListeners();

        ConsoleManager consoleManager = new ConsoleManager();
        consoleManager.start();

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

        server.start("0.0.0.0", 25565);

        LOGGER.info("Server successfully started on 0.0.0.:25565");
    }
}
