package top.nustar.nustarcorebridge.service.slot;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.SlotService;
import yslelf.cloudpick.bukkit.api.PacketSender;
import yslelf.cloudpick.bukkit.api.SlotAPI;

/**
 * @author : NuStar
 * Date : 2025/7/23 01:34
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@Service
@DependsOn(classes = "yslelf.cloudpick.bukkit.CloudPick")
@SuppressWarnings("unused")
public class CloudPickSlotServiceImpl implements SlotService {
    @Override
    public void putSlotItem(Player player, String identifier, ItemStack item) {
        PacketSender.putClientSlotItem(player, identifier, item);
    }

    @Override
    public ItemStack getItemFromIdentifier(Player player, String identifier) {
        return SlotAPI.getCacheSlotItem(player, identifier);
    }
}
