package top.nustar.nustarcorebridge.api.packet.simple;

import top.nustar.nustarcorebridge.api.packet.sender.PacketSender;

import java.util.UUID;

/**
 * @author NuStar<br>
 * @since 2025/12/25 23:38<br>
 */
public class SimplePacketSender<P> implements PacketSender<P> {
    @Override
    public P getSender() {
        return null;
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public UUID getUid() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    @Override
    public String getName() {
        return "Console";
    }
}
