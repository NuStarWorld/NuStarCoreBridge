package top.nustar.nustarcorebridge.test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import top.nustar.nustarcorebridge.api.packet.PacketProcessor;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketArgument;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketHandler;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketName;
import top.nustar.nustarcorebridge.api.packet.context.PacketContext;
import top.nustar.nustarcorebridge.api.packet.registry.impl.DefaultPacketProcessorRegistry;
import top.nustar.nustarcorebridge.api.packet.simple.SimplePacketSender;

import java.util.Collections;


/**
 * @author NuStar<br>
 * @since 2025/12/2 13:35<br>
 */
public class UnitTest {
    @Test
    public void test() {
        try {
            DefaultPacketProcessorRegistry defaultPacketProcessorRegistry = new DefaultPacketProcessorRegistry(new MethodHandleTestClass());

            Assertions.assertAll(() -> {
                defaultPacketProcessorRegistry.invoke("testMethod", PacketContext.of(new SimplePacketSender<>(), Collections.emptyList(), Collections.emptyMap()), Collections.emptyMap());
                defaultPacketProcessorRegistry.invoke("testArgMethod", PacketContext.of(new SimplePacketSender<>(), Collections.emptyList(), Collections.emptyMap()), Collections.singletonMap("msg", "123456789"));
                defaultPacketProcessorRegistry.invoke("testContextMethod", PacketContext.of(new SimplePacketSender<>(), Collections.emptyList(), Collections.emptyMap()), Collections.singletonMap("msg", "987654321"));
            });
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    @PacketName("testPacket")
    public static class MethodHandleTestClass implements PacketProcessor {
        @PacketHandler(value = "testMethod", description = "测试方法")
        public void testMethod() {
            System.out.println("testMethod");
        }


        @PacketHandler(value = "testArgMethod")
        public void testArgMethod(@PacketArgument(value = "msg") String msg) {
            System.out.println(msg.substring(5));
        }

        @PacketHandler(value = "testContextMethod")
        public void testContextMethod(PacketContext<?> packetContext, @PacketArgument("arg1") String arg) {
            System.out.println(arg);
        }
    }
}
