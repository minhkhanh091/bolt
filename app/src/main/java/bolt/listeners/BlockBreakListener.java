package bolt.listeners;

import bolt.utils.VanillaDropUtil;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.Material;
import net.minestom.server.item.ItemStack;

public class BlockBreakListener implements EventListener<PlayerBlockBreakEvent>{
    @Override
    public Class<PlayerBlockBreakEvent> eventType() {
        return PlayerBlockBreakEvent.class;
    }

    @Override
    public Result run(PlayerBlockBreakEvent e) {
        Block block = e.getBlock();
        BlockVec blockPos = e.getBlockPosition();
        Instance instance = e.getInstance();
        
        Material dropMaterial = block.material();
        ItemStack itemStack = ItemStack.of(dropMaterial, 1);
        Pos dropPosition = new Pos(blockPos.x(), blockPos.y(), blockPos.z());

        VanillaDropUtil.spawnVanillaDrop(instance, dropPosition, itemStack);

        e.getPlayer().sendMessage("You break a block!");
        
        return Result.SUCCESS;
    }
}
