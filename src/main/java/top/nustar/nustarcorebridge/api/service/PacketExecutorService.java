package top.nustar.nustarcorebridge.api.service;

/**
 * @author NuStar<br>
 * @since 2025/12/26 22:37<br>
 */
public interface PacketExecutorService {

    /**
     * 提交异步任务
     * @param runnable 任务
     */
    void submitAsyncTask(Runnable runnable);
}
