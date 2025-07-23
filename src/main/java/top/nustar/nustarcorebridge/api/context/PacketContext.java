package top.nustar.nustarcorebridge.api.context;

import team.idealstate.sugar.validate.annotation.NotNull;
import top.nustar.nustarcorebridge.api.sender.PacketSender;

import java.util.List;

/**
 * @author : NuStar
 * Date : 2025/7/23 22:59
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@SuppressWarnings("unused")
public interface PacketContext {
    @NotNull
    PacketSender<?> getPacketSender();
    @NotNull
    List<String> getArguments();

    @NotNull
    static PacketContext of(@NotNull PacketSender<?> sender, @NotNull List<String> argument) {
        return new SimplePacketContext(sender, argument);
    }
}
