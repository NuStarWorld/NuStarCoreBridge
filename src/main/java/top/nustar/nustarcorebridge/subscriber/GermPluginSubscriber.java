package top.nustar.nustarcorebridge.subscriber;

import com.germ.germplugin.api.event.GermReceiveDosEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import team.idealstate.sugar.next.context.annotation.component.Subscriber;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.PacketEventBus;
import top.nustar.nustarcorebridge.sender.BukkitSender;

import java.util.HashMap;
import java.util.Map;

@Subscriber
@DependsOn(classes = "com.germ.germplugin.GermPlugin")
@SuppressWarnings("unused")
public class GermPluginSubscriber implements Listener {
    private volatile PacketEventBus packetEventBus;
    @EventHandler
    public void germPacket(GermReceiveDosEvent event) {
        String[] args = event.getDosContent().split(" ");
        String handleName = args[0];
        Map<String, Object> argsMap = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            String[] split = args[i].split("=", -1);
            if (split.length != 2) continue;
            argsMap.put(split[0], split[1]);
        }
        packetEventBus.post(new BukkitSender(event.getPlayer()), event.getDosId(),handleName, argsMap);
    }
    @Autowired
    public void setPacketEventBus(PacketEventBus packetEventBus) {
        this.packetEventBus = packetEventBus;
    }
}
