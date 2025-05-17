package top.nustar.nustarcorebridge;

import org.bukkit.plugin.java.JavaPlugin;
import top.nustar.nustarcorebridge.api.example.DefaultPacketProcessor;
import top.nustar.nustarcorebridge.listeners.DragonPacketListener;
import top.nustar.nustarcorebridge.api.PacketEventBus;

public final class NuStarCoreBridge extends JavaPlugin {


    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new DragonPacketListener(), this);
        PacketEventBus.instance().registerPacket(DefaultPacketProcessor.class);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
