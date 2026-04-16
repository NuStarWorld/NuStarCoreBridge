package top.nustar.nustarcorebridge.service.placeholder;

import com.yuankong.easycore.api.EasyCoreAPI;
import org.bukkit.entity.Player;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.service.PacketExecutorService;
import top.nustar.nustarcorebridge.api.service.PlaceholderService;
import top.nustar.nustarcorebridge.utils.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author NuStar
 * @since 2026/4/16 15:50
 */
@Service
@DependsOn(classes = "com.yuankong.easycore.EasyCore")
@SuppressWarnings("unused")
public class EasyCorePlaceholderServiceImpl implements PlaceholderService {

    private volatile PacketExecutorService packetExecutorService;

    @Override
    public void sendPlaceholder(Player player, String placeholder, String value) {
        packetExecutorService.submitAsyncTask(() ->
                EasyCoreAPI.sendPlaceholder(player, Collections.singletonMap(placeholder, value))
        );
    }

    @Override
    public void sendPlaceholderMap(Player player, Map<String, String> placeholderMap) {
        Map<String, Object> convertMap = placeholderMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        packetExecutorService.submitAsyncTask(() ->
                EasyCoreAPI.sendPlaceholder(player, convertMap)
        );
    }

    @SafeVarargs
    @Override
    public final void sendPlaceholders(Player player, Pair<String, String>... pairs) {
        packetExecutorService.submitAsyncTask(() -> {
            Map<String, Object> placeholderMap = new HashMap<>();
            for (Pair<String, String> pair : pairs) {
                placeholderMap.put(pair.getFirst(), pair.getSecond());
            }
            EasyCoreAPI.sendPlaceholder(player, placeholderMap);
        });
    }

    @Override
    public void removePlaceholder(Player player, String placeholder, boolean startsWith) {
        packetExecutorService.submitAsyncTask(() -> {
            if (startsWith) {
                EasyCoreAPI.removePlaceholderStartswith(player, placeholder);
            } else {
                EasyCoreAPI.removePlaceholder(player, placeholder);
            }
        });
    }

    @Override
    public void removePlaceholders(Player player, String... placeholder) {
        packetExecutorService.submitAsyncTask(() -> {
            for (String s : placeholder) {
                EasyCoreAPI.removePlaceholder(player, s);
            }
        });
    }

    @Autowired
    public void setPacketExecutorService(PacketExecutorService packetExecutorService) {
        this.packetExecutorService = packetExecutorService;
    }
}
