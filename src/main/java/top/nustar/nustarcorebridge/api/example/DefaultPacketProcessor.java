package top.nustar.nustarcorebridge.api.example;

import team.idealstate.sugar.next.context.annotation.component.Component;
import top.nustar.nustarcorebridge.api.PacketProcessor;
import top.nustar.nustarcorebridge.api.PacketSender;
import top.nustar.nustarcorebridge.api.annotations.PacketArgument;
import top.nustar.nustarcorebridge.api.annotations.PacketHandler;
import top.nustar.nustarcorebridge.api.annotations.PacketName;

@Component
@PacketName("DefaultPacket")
@SuppressWarnings("unused")
public class DefaultPacketProcessor implements PacketProcessor {


    @PacketHandler("sendMessage")
    public void runPacket(PacketSender<?> packetSender, @PacketArgument("name") String name, @PacketArgument("message") String message) {
        System.out.printf("DefaultPacketProcessor.runPacket(%s,%s, %s)\n", packetSender, name, message);
    }

    @PacketHandler("sendPlayerMessage")
    public void sendPlayerMessage(PacketSender<?> packetSender, @PacketArgument("target") String targetName, @PacketArgument("message") String message) {
        System.out.printf("DefaultPacketProcessor.sendPlayerMessage(%s,%s, %s)\n", packetSender, targetName, message);
    }
}