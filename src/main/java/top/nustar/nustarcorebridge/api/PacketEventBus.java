package top.nustar.nustarcorebridge.api;

import java.util.Map;

public interface PacketEventBus {
    void post(PacketSender<?> packetSender, String packetName , String handleName, Map<String, Object> argsMap);
}
