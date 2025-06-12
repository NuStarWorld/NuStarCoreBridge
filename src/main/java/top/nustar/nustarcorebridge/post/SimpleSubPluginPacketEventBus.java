package top.nustar.nustarcorebridge.post;

import team.idealstate.sugar.next.context.annotation.component.Component;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import team.idealstate.sugar.next.context.annotation.feature.Scope;
import team.idealstate.sugar.validate.Validation;
import team.idealstate.sugar.validate.annotation.NotNull;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;
import top.nustar.nustarcorebridge.api.PacketEventBus;
import top.nustar.nustarcorebridge.api.PacketProcessor;
import top.nustar.nustarcorebridge.api.PacketSender;

import java.util.List;
import java.util.Map;

/**
 * @author : NuStar
 * Date : 2025/6/12 22:01
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@Component
@Scope(Scope.SINGLETON)
@DependsOn(
        properties = @DependsOn.Property(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "true")
)
public class SimpleSubPluginPacketEventBus implements PacketEventBus {
    
    @NotNull
    private SimplePacketEventBus getDelegate() {
        return Validation.requireNotNull(SimplePacketEventBus.instance, "PacketEventBus is not initialized.");
    }

    @Autowired
    public void addPacketProcessors(List<PacketProcessor> packetProcessors) {
        getDelegate().addPacketProcessors(packetProcessors);
    }

    @Override
    public void post(PacketSender<?> packetSender, String packetName, String handleName, Map<String, Object> argsMap) {
        getDelegate().post(packetSender, packetName, handleName, argsMap);
    }
}
