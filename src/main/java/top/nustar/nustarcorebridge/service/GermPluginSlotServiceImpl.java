package top.nustar.nustarcorebridge.service;

import com.germ.germplugin.api.GermPacketAPI;
import com.germ.germplugin.api.GermSlotAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.SlotService;

@Service
@DependsOn(classes = "com.germ.germplugin.GermPlugin")
@SuppressWarnings( "unused")
public class GermPluginSlotServiceImpl implements SlotService {
    @Override
    public void putSlotItem(Player player, String identifier, ItemStack item) {
        GermPacketAPI.sendSlotItemStack(player, identifier, item);
    }

    @Override
    public ItemStack getItemFromIdentifier(Player player, String identifier) {
        return GermSlotAPI.getItemStackFromIdentity(player, identifier);
    }
}
