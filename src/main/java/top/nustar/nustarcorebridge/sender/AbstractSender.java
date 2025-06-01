package top.nustar.nustarcorebridge.sender;

import lombok.Getter;
import top.nustar.nustarcorebridge.api.PacketSender;

@Getter
public abstract class AbstractSender<T> implements PacketSender<T> {
    T sender;

    protected AbstractSender (T sender) {
        this.sender = sender;
    }
}
