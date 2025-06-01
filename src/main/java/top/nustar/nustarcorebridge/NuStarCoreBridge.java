package top.nustar.nustarcorebridge;

import team.idealstate.minecraft.next.spigot.api.SpigotPlugin;
import team.idealstate.sugar.banner.Banner;
import team.idealstate.sugar.logging.Log;
import team.idealstate.sugar.next.context.Context;

public class NuStarCoreBridge extends SpigotPlugin {
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
        Banner.lines(getClass()).forEach(Log::info);
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
