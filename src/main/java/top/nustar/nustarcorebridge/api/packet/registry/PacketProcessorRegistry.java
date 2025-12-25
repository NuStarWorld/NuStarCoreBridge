package top.nustar.nustarcorebridge.api.packet.registry;

import top.nustar.nustarcorebridge.api.packet.context.PacketContext;

import java.util.Map;

/**
 * @author NuStar<br>
 * @since 2025/11/21 23:19<br>
 */
@SuppressWarnings("unused")
public interface PacketProcessorRegistry {

    /**
     * 获得发包名称
     */
    String getPacketName();

    /**
     * 获得注册的所有方法列表，返回的是原始表副本
     */
    Map<String, HandlerRegistry> getHandlerRegistry();

    /**
     * 获得注册的所有方法列表，返回的是原始表
     */
    Map<String, HandlerRegistry> getUnsafeHandlerRegistry();

    /**
     * 执行发包逻辑
     * @param handlerName 方法名
     * @param packetContext 发包上下文
     * @param argMap 参数表
     */
    void invoke(String handlerName, PacketContext<?> packetContext, Map<String, Object> argMap);
}
