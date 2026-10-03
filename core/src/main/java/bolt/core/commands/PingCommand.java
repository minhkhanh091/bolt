package bolt.core.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

public class PingCommand extends Command {
    public PingCommand() {
        super("ping");

        setCondition((sender, _) -> {
            return (sender instanceof Player);
        });

        setDefaultExecutor((sender, _) -> {
            if (sender instanceof Player player) {
                player.sendMessage("Your ping is " + player.getLatency() + "ms");
            }
        });
    }
}
