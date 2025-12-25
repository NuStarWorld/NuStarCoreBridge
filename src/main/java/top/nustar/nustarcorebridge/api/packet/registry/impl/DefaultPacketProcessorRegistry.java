package top.nustar.nustarcorebridge.api.packet.registry.impl;

import lombok.Getter;
import team.idealstate.sugar.logging.Log;
import top.nustar.nustarcorebridge.api.packet.PacketProcessor;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketHandler;
import top.nustar.nustarcorebridge.api.packet.context.PacketContext;
import top.nustar.nustarcorebridge.api.packet.registry.HandlerRegistry;
import top.nustar.nustarcorebridge.api.packet.registry.PacketProcessorRegistry;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author NuStar<br>
 * @since 2025/12/2 18:23<br>
 */
public class DefaultPacketProcessorRegistry implements PacketProcessorRegistry {

    @Getter
    private final String packetName;
    private final Map<String, HandlerRegistry> handlerRegistryMap = new ConcurrentHashMap<>();

    public DefaultPacketProcessorRegistry(PacketProcessor processor) {
        this.packetName = processor.getPacketName();

        List<Method> handlerMethods = Arrays.stream(processor.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PacketHandler.class))
                .collect(Collectors.toList());

        for (Method handlerMethod : handlerMethods) {
            PacketHandler handlerMethodAnnotation = handlerMethod.getAnnotation(PacketHandler.class);
            String handlerName = handlerMethodAnnotation.value();
            DefaultHandlerRegistry defaultHandlerRegistry = new DefaultHandlerRegistry(processor, handlerMethod);
            handlerRegistryMap.put(handlerName, defaultHandlerRegistry);

            Log.info("               ┝── " + handlerName + " "
                    + defaultHandlerRegistry.getParameters().keySet().stream()
                    .map(packetArgument -> packetArgument.value() + ":" + packetArgument.description())
                    .collect(Collectors.toList())
                    + " - " + handlerMethodAnnotation.description());
        }
        Log.info("               ┕── 加载 " + handlerMethods.size() + " 个方法");
    }

    @Override
    public void invoke(String handlerName, PacketContext<?> packetContext, Map<String, Object> argMap) {
        HandlerRegistry handlerRegistry = handlerRegistryMap.get(handlerName);
        if (handlerRegistry == null) {
            Log.warn(String.format("尝试调用发包 %s 的一个不存在的方法: %s", packetName, handlerName));
            return;
        }
        handlerRegistry.invoke(packetContext, argMap);
    }

    @Override
    public Map<String, HandlerRegistry> getHandlerRegistry() {
        return new ConcurrentHashMap<>(getUnsafeHandlerRegistry());
    }

    @Override
    public Map<String, HandlerRegistry> getUnsafeHandlerRegistry() {
        return handlerRegistryMap;
    }
}
