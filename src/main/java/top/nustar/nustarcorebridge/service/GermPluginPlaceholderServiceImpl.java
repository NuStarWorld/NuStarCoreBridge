package top.nustar.nustarcorebridge.service;

import com.germ.germplugin.api.GermPacketAPI;
import org.bukkit.entity.Player;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.PlaceholderService;
import top.nustar.nustarcorebridge.utils.Pair;

import java.util.Map;

@Service
@DependsOn(classes = "com.germ.germplugin.GermPlugin")
@SuppressWarnings("unused")
public class GermPluginPlaceholderServiceImpl implements PlaceholderService {
    @Override
    public void sendPlaceholder(Player player, String placeholder, String value) {
        GermPacketAPI.sendPlaceholder(player, placeholder, value);
    }

    @Override
    public void sendPlaceholderMap(Player player, Map<String, String> placeholderMap) {
        placeholderMap.forEach((placeholder, value) -> GermPacketAPI.sendPlaceholder(player, placeholder, value));
    }

    @SafeVarargs
    @Override
    public final void sendPlaceholders(Player player, Pair<String, String>... pairs) {
        for (Pair<String, String> pair : pairs) {
            GermPacketAPI.sendPlaceholder(player, pair.getFirst(), pair.getSecond());
        }
    }

    @Override
    public void removePlaceholder(Player player, String placeholder, boolean startsWith) {
        if (startsWith) {
            GermPacketAPI.removePlaceholderIfContain(player, placeholder);
        } else {
            GermPacketAPI.removePlaceholder(player, placeholder);
        }
    }

    @Override
    public void removePlaceholders(Player player, String... placeholder) {
        for (String s : placeholder) {
            GermPacketAPI.removePlaceholder(player, s);
        }
    }
}
