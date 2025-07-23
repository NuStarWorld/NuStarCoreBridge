package top.nustar.nustarcorebridge.api.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import top.nustar.nustarcorebridge.api.sender.PacketSender;

import java.util.List;

/**
 * @author : NuStar
 * Date : 2025/7/23 23:00
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@RequiredArgsConstructor
@Getter
public class SimplePacketContext implements PacketContext{
    private final PacketSender<?> packetSender;
    private final List<String> arguments;
}
