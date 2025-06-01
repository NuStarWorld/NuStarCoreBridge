package top.nustar.nustarcorebridge.service;

import eos.moe.dragoncore.network.PacketSender;
import org.bukkit.entity.Player;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.PlaceholderService;
import top.nustar.nustarcorebridge.utils.Pair;

import java.util.HashMap;
import java.util.Map;

@Service
@DependsOn(classes = "eos.moe.dragoncore.DragonCore")
@SuppressWarnings("unused")
public class DragonCorePlaceholderServiceImpl implements PlaceholderService {
    @Override
    public void sendPlaceholder(Player player, String placeholder, String value) {
        Map<String, String> placeholderMap = new HashMap<>();
        placeholderMap.put(placeholder, value);
        PacketSender.sendSyncPlaceholder(player, placeholderMap);
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
