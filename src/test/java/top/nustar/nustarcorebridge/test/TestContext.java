package top.nustar.nustarcorebridge.test;

import team.idealstate.sugar.next.context.Context;
import team.idealstate.sugar.next.context.ContextHolder;
import team.idealstate.sugar.next.context.ContextLifecycle;
import team.idealstate.sugar.next.eventbus.EventBus;

import java.io.File;

/**
 * @author NuStar<br>
 * @since 2026/3/2 00:00<br>
 */
public class TestContext implements ContextHolder, ContextLifecycle {

    private final Context context = Context.of(this, this, EventBus.instance());

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getVersion() {
        return "";
    }

    @Override
    public File getDataFolder() {
        return null;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void onInitialize(Context context) {
    }

    @Override
    public void onInitialized(Context context) {

    }

    @Override
    public void onLoad(Context context) {

    }

    @Override
    public void onLoaded(Context context) {

    }

    @Override
    public void onEnable(Context context) {

    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisable(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }

    @Override
    public void onDestroy(Context context) {

    }

    @Override
    public void onDestroyed(Context context) {

    }
}
