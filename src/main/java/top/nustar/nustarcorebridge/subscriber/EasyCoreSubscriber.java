package top.nustar.nustarcorebridge.subscriber;

import com.yuankong.easycore.api.event.CustomPacketEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.packet.PacketEventBus;
import top.nustar.nustarcorebridge.api.packet.context.PacketContext;
import top.nustar.nustarcorebridge.sender.BukkitSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author NuStar
 * @since 2026/4/16 16:06
 */
@Service
@DependsOn(classes = "com.yuankong.easycore.EasyCore")
@SuppressWarnings("unused")
public class EasyCoreSubscriber extends AbstractPostPacket implements Listener {
    private volatile PacketEventBus packetEventBus;

    @EventHandler
    public void on(CustomPacketEvent event) {
        List<String> argList = new ArrayList<>(event.getData());
        String handleName = argList.get(0);
        Map<String, Object> argsMap = getArgs(argList);
        packetEventBus.post(
                PacketContext.of(new BukkitSender(event.getPlayer()), argList, argsMap),
                event.getIdentifier(),
                handleName,
                argsMap
        );
    }

    @Autowired
    public void setPacketEventBus(PacketEventBus packetEventBus) {
        this.packetEventBus = packetEventBus;
    }
}
