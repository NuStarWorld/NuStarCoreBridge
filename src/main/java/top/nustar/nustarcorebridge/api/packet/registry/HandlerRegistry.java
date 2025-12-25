package top.nustar.nustarcorebridge.api.packet.registry;

import top.nustar.nustarcorebridge.api.packet.annotations.PacketArgument;
import top.nustar.nustarcorebridge.api.packet.context.PacketContext;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * @author NuStar<br>
 * @since 2025/11/21 23:20<br>
 */
public interface HandlerRegistry {

    /**
     * 获得方法的所有参数表，返回的是原始表的副本
     */
    Map<PacketArgument, Parameter> getParameters();

    /**
     * 获得方法的所有参数表，返回的是原始表
     */
    Map<PacketArgument, Parameter> getUnsafeParameters();

    /**
     * 执行方法
     * @param packetContext 发包上下文
     * @param argMap 参数表
     */
    void invoke(PacketContext<?> packetContext, Map<String, Object> argMap);
}
