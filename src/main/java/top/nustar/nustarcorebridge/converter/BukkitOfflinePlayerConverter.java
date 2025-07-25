package top.nustar.nustarcorebridge.converter;

import org.bukkit.Bukkit;
import top.nustar.nustarcorebridge.api.context.PacketContext;
import top.nustar.nustarcorebridge.api.converter.ArgumentConverter;

import java.util.Optional;
import java.util.UUID;

/**
 * @author : NuStar
 * Date : 2025/7/25 22:18
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@SuppressWarnings("unuesd")
public class BukkitOfflinePlayerConverter implements ArgumentConverter {
    @Override
    public Optional<Object> convert(PacketContext context, String value) {
        UUID uuid;
        try {
            uuid = UUID.fromString(value);
            return Optional.ofNullable(Bukkit.getOfflinePlayer(uuid));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
