package top.nustar.nustarcorebridge.listeners;

import com.germ.germplugin.api.event.GermReceiveDosEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import top.nustar.nustarcorebridge.api.PacketEventBus;

import java.util.HashMap;
import java.util.Map;

public class GermPacketListener implements Listener {
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
        argsMap.put("sender", event.getPlayer());
        PacketEventBus.instance().post(event.getDosId(),handleName, argsMap);
    }
}
