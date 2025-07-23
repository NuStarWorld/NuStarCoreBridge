package top.nustar.nustarcorebridge.subscriber;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import priv.seventeen.artist.arcartx.event.client.ClientCustomPacketEvent;
import team.idealstate.sugar.next.context.annotation.component.Subscriber;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;
import top.nustar.nustarcorebridge.api.PacketEventBus;
import top.nustar.nustarcorebridge.sender.BukkitSender;

import java.util.Map;

/**
 * @author : NuStar
 * Date : 2025/7/23 20:58
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@Subscriber
@DependsOn(
        classes = "priv.seventeen.artist.arcartx.ArcartX",
        properties = @DependsOn.Property(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "false")
)
@SuppressWarnings("unused")
public class ArcartXSubscriber extends AbstractPostPacket implements Listener {
    private volatile PacketEventBus packetEventBus;
    @EventHandler
    public void on(ClientCustomPacketEvent event) {
        if (event.isCancelled()) return;
        String handleName = event.getData().get(0);
        Map<String, String> argsMap = getArgs(event.getData());
        packetEventBus.post(new BukkitSender(event.getPlayer()), event.getId(), handleName, argsMap);
    }

    @Autowired
    public void setPacketEventBus(PacketEventBus packetEventBus) {
        this.packetEventBus = packetEventBus;
    }
}
