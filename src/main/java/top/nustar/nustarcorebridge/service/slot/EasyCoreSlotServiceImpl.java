package top.nustar.nustarcorebridge.service.slot;

import com.yuankong.easycore.api.ui.SlotAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.service.PacketExecutorService;
import top.nustar.nustarcorebridge.api.service.SlotService;

/**
 * @author NuStar
 * @since 2026/4/16 15:58
 */
@Service
@DependsOn(classes = "com.yuankong.easycore.EasyCore")
@SuppressWarnings("unused")
public class EasyCoreSlotServiceImpl implements SlotService {

    private volatile PacketExecutorService packetExecutorService;

    @Override
    public void putSlotItem(Player player, String identifier, ItemStack item) {
        packetExecutorService.submitAsyncTask(() ->
                SlotAPI.sendCacheItemStack(player, identifier, item)
        );
    }

    @Override
    public ItemStack getItemFromIdentifier(Player player, String identifier) {
        return SlotAPI.getExtraSlotItem(player, identifier);
    }

    @Autowired
    public void setPacketExecutorService(PacketExecutorService packetExecutorService) {
        this.packetExecutorService = packetExecutorService;
    }
}
