package top.nustar.nustarcorebridge.service;

import org.bukkit.Bukkit;
import team.idealstate.sugar.next.context.Context;
import team.idealstate.sugar.next.context.annotation.component.Service;
import team.idealstate.sugar.next.context.aware.ContextAware;
import top.nustar.nustarcorebridge.NuStarCoreBridge;
import top.nustar.nustarcorebridge.api.service.PacketExecutorService;

/**
 * @author NuStar<br>
 * @since 2025/12/26 22:38<br>
 */
@Service
@SuppressWarnings("unused")
public class BukkitPacketExecutorService implements PacketExecutorService, ContextAware {

    private volatile Context context;

    @Override
    public void submitAsyncTask(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously((NuStarCoreBridge) context.getHolder(), runnable);
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}

