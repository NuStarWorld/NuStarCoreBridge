package top.nustar.nustarcorebridge.subscriber;

import eos.moe.dragoncore.api.gui.event.CustomPacketEvent;
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
@DependsOn(classes = "eos.moe.dragoncore.DragonCore")
@SuppressWarnings( "unused")
public class DragonCoreSubscriber implements Listener {
    private volatile PacketEventBus packetEventBus;
    @EventHandler
    public void onPacket(CustomPacketEvent event) {
        String handleName = event.getData().get(0);
        Map<String, Object> argsMap = new HashMap<>();
        event.getData().remove(0);
        for (String arg : event.getData()) {
            String[] split = arg.split("=", -1);
            if (split.length != 2) continue;
            argsMap.put(split[0], split[1]);
        }
        packetEventBus.post(new BukkitSender(event.getPlayer()), event.getIdentifier(),handleName, argsMap);
    }
    @Autowired
    public void setPacketEventBus(PacketEventBus packetEventBus) {
        this.packetEventBus = packetEventBus;
    }
}
