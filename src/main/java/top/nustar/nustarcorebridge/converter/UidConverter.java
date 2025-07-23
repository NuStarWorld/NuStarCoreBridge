package top.nustar.nustarcorebridge.converter;

import top.nustar.nustarcorebridge.api.context.PacketContext;
import top.nustar.nustarcorebridge.api.converter.ArgumentConverter;

import java.util.Optional;
import java.util.UUID;

/**
 * @author : NuStar
 * Date : 2025/7/24 00:46
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@SuppressWarnings("unused")
public class UidConverter implements ArgumentConverter {
    @Override
    public Optional<Object> convert(PacketContext context, String value) {
        try {
            return Optional.of(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
