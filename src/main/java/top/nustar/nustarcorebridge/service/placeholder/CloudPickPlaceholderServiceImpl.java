package top.nustar.nustarcorebridge.service.placeholder;

import org.bukkit.entity.Player;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.PlaceholderService;
import top.nustar.nustarcorebridge.utils.Pair;
import yslelf.cloudpick.bukkit.api.PacketSender;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : NuStar
 * Date : 2025/7/23 01:29
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@Service
@DependsOn(classes = "yslelf.cloudpick.bukkit.CloudPick")
@SuppressWarnings("unused")
public class CloudPickPlaceholderServiceImpl implements PlaceholderService {
    @Override
    public void sendPlaceholder(Player player, String placeholder, String value) {
        PacketSender.sendSyncPlaceholder(player, Collections.singletonMap(placeholder, value));
    }

    @Override
    public void sendPlaceholderMap(Player player, Map<String, String> placeholderMap) {
        PacketSender.sendSyncPlaceholder(player, placeholderMap);
    }

    @SafeVarargs
    @Override
    public final void sendPlaceholders(Player player, Pair<String, String>... pairs) {
        Map<String, String> placeholderMap = new HashMap<>();
        for (Pair<String, String> pair : pairs) {
            placeholderMap.put(pair.getFirst(), pair.getSecond());
        }
        PacketSender.sendSyncPlaceholder(player, placeholderMap);
    }

    @Override
    public void removePlaceholder(Player player, String placeholder, boolean startsWith) {
        PacketSender.sendDeletePlaceholderCache(player, placeholder, startsWith);
    }

    @Override
    public void removePlaceholders(Player player, String... placeholder) {
        for (String s : placeholder) {
            PacketSender.sendDeletePlaceholderCache(player, s, false);
        }
    }
}
