package bolt.core;

import bolt.core.config.ServerConfig;

import net.minestom.server.MinecraftServer;

public class BoltCore {
    private final ServerConfig config;
    private final MinecraftServer minecraftServer;

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
