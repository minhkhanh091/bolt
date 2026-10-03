package bolt.core.commands.system;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.EntityStatuses.Player;

public class StopCommand extends Command {
    public StopCommand() {
        super("shutdown");
        
        setCondition((sender, commandString) -> {
            return true;
        });

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Shutting down the server...");
            MinecraftServer.stopCleanly();
        });
    }
}
