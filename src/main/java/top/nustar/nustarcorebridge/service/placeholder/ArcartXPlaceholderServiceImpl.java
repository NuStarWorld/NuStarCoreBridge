package top.nustar.nustarcorebridge.service.placeholder;

import org.bukkit.entity.Player;
import priv.seventeen.artist.arcartx.api.ArcartXAPI;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.PlaceholderService;
import top.nustar.nustarcorebridge.utils.Pair;

import java.util.Map;

/**
 * @author : NuStar
 * Date : 2025/7/23 21:53
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@Service
@DependsOn(classes = "priv.seventeen.artist.arcartx.ArcartX")
@SuppressWarnings("unused")
public class ArcartXPlaceholderServiceImpl implements PlaceholderService {
    @Override
    public void sendPlaceholder(Player player, String placeholder, String value) {
        ArcartXAPI.getNetworkSender().sendServerVariable(player, placeholder, value);
    }

    @Override
    public void sendPlaceholderMap(Player player, Map<String, String> placeholderMap) {
        ArcartXAPI.getNetworkSender().sendMultipleServerVariable(player, placeholderMap);
    }

    @SafeVarargs
    @Override
    public final void sendPlaceholders(Player player, Pair<String, String>... pairs) {
        for (Pair<String, String> pair : pairs) {
            ArcartXAPI.getNetworkSender().sendServerVariable(player, pair.getFirst(), pair.getSecond());
        }

    }

    @Override
    public void removePlaceholder(Player player, String placeholder, boolean startsWith) {
        ArcartXAPI.getNetworkSender().removeServerVariable(player, placeholder, startsWith);
    }

    @Override
    public void removePlaceholders(Player player, String... placeholder) {
        for (String s : placeholder) {
            ArcartXAPI.getNetworkSender().removeServerVariable(player, s, false);
        }
    }
}
