package top.nustar.nustarcorebridge.api;


@SuppressWarnings("unused")
public interface PacketSender<T> {
    T getSender();

    void sendMessage(String message);
}
