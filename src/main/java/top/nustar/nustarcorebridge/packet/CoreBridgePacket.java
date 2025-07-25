package top.nustar.nustarcorebridge.packet;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import team.idealstate.sugar.next.context.annotation.component.Component;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;
import top.nustar.nustarcorebridge.api.PacketProcessor;
import top.nustar.nustarcorebridge.api.PlaceholderService;
import top.nustar.nustarcorebridge.api.annotations.PacketArgument;
import top.nustar.nustarcorebridge.api.annotations.PacketHandler;
import top.nustar.nustarcorebridge.api.annotations.PacketName;
import top.nustar.nustarcorebridge.api.sender.PacketSender;
import top.nustar.nustarcorebridge.converter.BukkitOfflinePlayerConverter;

/**
 * @author : NuStar
 * Date : 2025/7/25 21:58
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@Component
@PacketName("NuStarCoreBridge")
@DependsOn(properties = @DependsOn.Property(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "false"))
@SuppressWarnings("unused")
public class CoreBridgePacket implements PacketProcessor {

    private volatile PlaceholderService placeholderService;

    @PacketHandler(value = "parsePapi", description = "为目标解析一个 Papi 变量")
    public void parsePapi(
            PacketSender<Player> sender,
            @PacketArgument(value = "playerUid", description = "玩家 UUID", converter = BukkitOfflinePlayerConverter.class) OfflinePlayer offlinePlayer,
            @PacketArgument(value = "placeholder", description = "Papi 变量") String papiPlaceholder
    ) {
        String parsePapi = PlaceholderAPI.setPlaceholders(offlinePlayer, papiPlaceholder);
        System.out.println(offlinePlayer.getUniqueId().toString());
        String placeholder = String.format("NuStarCoreBridge_PapiPlaceholder_%s_%s", offlinePlayer.getUniqueId().toString(), papiPlaceholder);
        placeholderService.sendPlaceholder(sender.getSender(), placeholder, parsePapi);
    }

    @Autowired
    public void setPlaceholderService(PlaceholderService placeholderService) {
        this.placeholderService = placeholderService;
    }
}
