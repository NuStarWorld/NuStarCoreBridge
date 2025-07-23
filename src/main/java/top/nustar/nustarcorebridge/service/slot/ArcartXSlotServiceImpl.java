package top.nustar.nustarcorebridge.service.slot;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import priv.seventeen.artist.arcartx.api.ArcartXAPI;
import priv.seventeen.artist.arcartx.core.entity.data.ArcartXPlayer;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.SlotService;

/**
 * @author : NuStar
 * Date : 2025/7/23 21:55
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@Service
@DependsOn(classes = "priv.seventeen.artist.arcartx.ArcartX")
@SuppressWarnings("unused")
public class ArcartXSlotServiceImpl implements SlotService {
    @Override
    public void putSlotItem(Player player, String identifier, ItemStack item) {
        ArcartXPlayer arcartXPlayer = ArcartXAPI.getEntityManager().getPlayer(player);
        if (arcartXPlayer == null) throw new NullPointerException("ArcartXPlayer is null");
        arcartXPlayer.setSlotItemStackOnlyClient(identifier, item);
    }

    @Override
    public ItemStack getItemFromIdentifier(Player player, String identifier) {
        ArcartXPlayer arcartXPlayer = ArcartXAPI.getEntityManager().getPlayer(player);
        if (arcartXPlayer == null) throw new NullPointerException("ArcartXPlayer is null");
        return arcartXPlayer.getSlotItemStack(identifier);
    }
}
