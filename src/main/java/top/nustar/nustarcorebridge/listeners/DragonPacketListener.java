package top.nustar.nustarcorebridge.listeners;

import eos.moe.dragoncore.api.gui.event.CustomPacketEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import top.nustar.nustarcorebridge.api.PacketEventBus;

import java.util.HashMap;
import java.util.Map;

public class DragonPacketListener implements Listener {
    @EventHandler
    public void dragonPacket(CustomPacketEvent event) {
        System.out.println("收到默认龙核包" + event.getIdentifier() + event.getData());
        String handleName = event.getData().get(0);
        Map<String, Object> argsMap = new HashMap<>();
        event.getData().remove(0);
        for (String arg : event.getData()) {
            String[] split = arg.split("=", -1);
            if (split.length != 2) continue;
            argsMap.put(split[0], split[1]);
        }
        argsMap.put("sender", event.getPlayer());
        PacketEventBus.instance().post(event.getIdentifier(),handleName, argsMap);
    }
}
