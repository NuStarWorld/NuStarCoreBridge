package top.nustar.nustarcorebridge.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public interface SlotService {
    void putSlotItem(Player player, String identifier, ItemStack item);
    ItemStack getItemFromIdentifier(Player player, String identifier);
}
