package top.nustar.nustarcorebridge.api.example;

import org.bukkit.entity.Player;
import top.nustar.nustarcorebridge.api.annotations.PacketArgument;
import top.nustar.nustarcorebridge.api.annotations.PacketHandler;
import top.nustar.nustarcorebridge.api.annotations.PacketName;
import top.nustar.nustarcorebridge.api.PacketProcessor;

@PacketName("DefaultPacket")
public class DefaultPacketProcessor implements PacketProcessor {

    @PacketHandler("sendMessage")
    public void runPacket(@PacketArgument("sender") Player sender, @PacketArgument("name")String name, @PacketArgument("message")String message) {
        System.out.printf("DefaultPacketProcessor.runPacket(%s,%s, %s)\n",sender, name, message);
    }
}
