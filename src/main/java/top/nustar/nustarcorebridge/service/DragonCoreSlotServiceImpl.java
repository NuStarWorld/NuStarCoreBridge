package top.nustar.nustarcorebridge.service;

import eos.moe.dragoncore.api.SlotAPI;
import eos.moe.dragoncore.network.PacketSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.SlotService;

@Service
@DependsOn(classes = "eos.moe.dragoncore.DragonCore")
@SuppressWarnings("unused")
public class DragonCoreSlotServiceImpl implements SlotService {

    @Override
    public void putSlotItem(Player player, String identifier, ItemStack item) {
        PacketSender.putClientSlotItem(player, identifier, item);
    }

    @Override
    public ItemStack getItemFromIdentifier(Player player, String identifier) {
        return SlotAPI.getCacheSlotItem(player, identifier);
    }
}
