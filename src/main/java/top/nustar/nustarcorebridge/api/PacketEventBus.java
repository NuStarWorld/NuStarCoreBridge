package top.nustar.nustarcorebridge.api;


import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

public interface PacketEventBus {

    static PacketEventBus instance() {
        return instance(PacketEventBus.class.getClassLoader());
    }

    static PacketEventBus instance(ClassLoader classLoader) {
        if (Internal.instance == null) {
            synchronized (Internal.class) {
                if (Internal.instance == null) {
                    ServiceLoader<PacketEventBus> serviceLoader = ServiceLoader.load(PacketEventBus.class, classLoader);
                    Iterator<PacketEventBus> iterator = serviceLoader.iterator();
                    PacketEventBus packetEventBus = null;
                    while (iterator.hasNext()) {
                        if (packetEventBus != null) {
                            throw new IllegalStateException("PacketEventBus has been multiple implementations provided.");
                        }
                        packetEventBus = iterator.next();
                    }
                    if (packetEventBus == null) {
                        throw new IllegalStateException("PacketEventBus has not been provided.");
                    }
                    return (Internal.instance = packetEventBus);
                }
            }
        }
        return Internal.instance;
    }

    void registerPacket(Class<? extends PacketProcessor> packetProcessor);

    void post(String packetName , String handleName, Map<String, Object> argsMap);
}
