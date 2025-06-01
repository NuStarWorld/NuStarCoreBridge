package top.nustar.nustarcorebridge.api;

import org.bukkit.entity.Player;
import top.nustar.nustarcorebridge.utils.Pair;

import java.util.Map;

@SuppressWarnings("unused")
public interface PlaceholderService {
    void sendPlaceholder(Player player, String placeholder, String value);

    void sendPlaceholderMap(Player player, Map<String, String> placeholderMap);

    @SuppressWarnings("unchecked")
    void sendPlaceholders(Player player, Pair<String, String>... pairs);

    void removePlaceholder(Player player, String placeholder, boolean startsWith);

    void removePlaceholders(Player player, String... placeholder);
}
