package top.nustar.nustarcorebridge.subscriber;

import eos.moe.dragoncore.api.gui.event.CustomPacketEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import team.idealstate.sugar.next.context.annotation.component.Subscriber;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;
import top.nustar.nustarcorebridge.api.PacketEventBus;
import top.nustar.nustarcorebridge.sender.BukkitSender;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : NuStar
 * Date : 2025/7/23 01:25
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@Subscriber
@DependsOn(
        classes = "yslelf.cloudpick.bukkit.CloudPick",
        properties = @DependsOn.Property(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "false")
)
@SuppressWarnings("unused")
public class CloudPickSubscriber implements Listener {
    private volatile PacketEventBus packetEventBus;
    @EventHandler
    public void on(CustomPacketEvent event) {
        if (event.isCancelled()) return;
        String handleName = event.getData().get(0);
        Map<String, Object> argsMap = new HashMap<>();
        event.getData().remove(0);
        if (!event.getData().isEmpty()) {
            for (String arg : event.getData()) {
                String[] split = arg.split("=", -1);
                if (split.length != 2) continue;
                argsMap.put(split[0], split[1]);
            }
        }
        packetEventBus.post(new BukkitSender(event.getPlayer()), event.getIdentifier(), handleName, argsMap);
    }

    @Autowired
    public void setPacketEventBus(PacketEventBus packetEventBus) {
        this.packetEventBus = packetEventBus;
    }
}
