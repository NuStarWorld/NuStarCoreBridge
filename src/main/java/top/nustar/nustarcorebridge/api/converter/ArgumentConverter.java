package top.nustar.nustarcorebridge.api.converter;

import team.idealstate.sugar.validate.annotation.NotNull;
import top.nustar.nustarcorebridge.api.context.PacketContext;

import java.util.Optional;

/**
 * @author : NuStar
 * Date : 2025/7/23 22:54
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
public interface ArgumentConverter {
    @NotNull
    Optional<Object> convert(@NotNull PacketContext context, @NotNull String value);
}
