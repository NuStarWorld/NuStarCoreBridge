package top.nustar.nustarcorebridge.api.converter;

import top.nustar.nustarcorebridge.api.context.PacketContext;

import java.util.Optional;

/**
 * @author : NuStar
 * Date : 2025/7/23 23:26
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
public class SimpleArgumentConverter implements ArgumentConverter{
    @Override
    public Optional<Object> convert(PacketContext context, String value) {
        return Optional.of(value);
    }
}
