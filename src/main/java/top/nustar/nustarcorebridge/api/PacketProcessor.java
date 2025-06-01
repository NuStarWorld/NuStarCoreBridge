package top.nustar.nustarcorebridge.api;

import top.nustar.nustarcorebridge.api.annotations.PacketHandler;
import top.nustar.nustarcorebridge.api.annotations.PacketName;

import java.lang.reflect.Method;

public interface PacketProcessor {
    default String getPacketName(PacketProcessor packetProcessor) {
        PacketName packetName = packetProcessor.getClass().getAnnotation(PacketName.class);
        return packetName.value();
    }

    default String getHandlerName(Method method) {
        return method.getAnnotation(PacketHandler.class).value();
    }
}
