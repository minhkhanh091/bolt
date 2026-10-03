package bolt.core.listeners;

import net.minestom.server.event.EventListener;
import bolt.core.utils.VanillaDropUtil;
import net.minestom.server.codec.Result;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class ItemDropListener implements EventListener<ItemDropEvent> {
    @Override 
    public Class<ItemDropEvent> eventType() {
        return ItemDropEvent.class;
    }

    @Override
    public Result run(ItemDropEvent e) {
        ItemStack itemStack = e.getItemStack();

        VanillaDropUtil.dropFromPlayer(e.getPlayer(), itemStack);

        e.getPlayer().sendMessage("You dropped a block!");
        
        return Result.SUCCESS;
    }
}
