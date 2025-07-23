package top.nustar.nustarcorebridge.converter;

import org.bukkit.Bukkit;
import top.nustar.nustarcorebridge.api.context.PacketContext;
import top.nustar.nustarcorebridge.api.converter.ArgumentConverter;

import java.util.Optional;

/**
 * @author : NuStar
 * Date : 2025/7/24 00:47
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@SuppressWarnings("unused")
public class BukkitPlayerConverter implements ArgumentConverter {
    @Override
    public Optional<Object> convert(PacketContext context, String value) {
        return Optional.ofNullable(Bukkit.getPlayer(value));
    }
}
