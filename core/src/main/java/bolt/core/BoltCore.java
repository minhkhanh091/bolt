package bolt.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bolt.core.commands.*;
import bolt.core.commands.system.*;
import bolt.core.config.ServerConfig;
import bolt.core.listeners.*;
import bolt.core.manager.ConsoleManager;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.*;
import net.minestom.server.instance.block.Block;
import net.minestom.server.coordinate.Pos;

public class BoltCore {
    private final ServerConfig config;
    private final MinecraftServer minecraftServer;
    
    private static void registerCommands()
    {
        MinecraftServer.getCommandManager().register(new StopCommand());
        MinecraftServer.getCommandManager().register(new PingCommand());
    }

    private static void registerListeners() {
        MinecraftServer.getGlobalEventHandler().addListener(new BlockBreakListener());
        MinecraftServer.getGlobalEventHandler().addListener(new ItemPickupListener());
        MinecraftServer.getGlobalEventHandler().addListener(new ItemDropListener());
    }

    public BoltCore(String configPath) {
        this.config = new ServerConfig(configPath);
        
        this.minecraftServer = MinecraftServer.init();
    }

    public void start() {
        System.out.println("Đang khởi chạy Bolt Server tại " + config.getHost() + ":" + config.getPort());
        minecraftServer.start(config.getHost(), config.getPort());
    }

    public MinecraftServer getMinecraftServer() {
        return minecraftServer;
    }
}
