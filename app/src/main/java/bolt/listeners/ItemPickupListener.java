package bolt.listeners;

import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.item.ItemStack;

public class ItemPickupListener implements EventListener<PickupItemEvent>{
    @Override
    public Class<PickupItemEvent> eventType() {
        return PickupItemEvent.class;
    }

    @Override
    public Result run(PickupItemEvent e) {
        if (!(e.getEntity() instanceof Player player)) {
            return Result.INVALID;
        }

        ItemEntity itemEntity = e.getItemEntity();
        ItemStack itemStack = itemEntity.getItemStack();

        if (player.getInventory().addItemStack(itemStack)) {
            itemEntity.remove();
        }

        player.sendMessage("You picked up a block!");
        
        return Result.SUCCESS;
    }    
}
