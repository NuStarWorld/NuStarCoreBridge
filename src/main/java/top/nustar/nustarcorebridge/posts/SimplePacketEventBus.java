package top.nustar.nustarcorebridge.posts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.nustar.nustarcorebridge.api.PacketEventBus;
import top.nustar.nustarcorebridge.api.annotations.PacketArgument;
import top.nustar.nustarcorebridge.api.annotations.PacketHandler;
import top.nustar.nustarcorebridge.api.annotations.PacketName;
import top.nustar.nustarcorebridge.api.PacketProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class SimplePacketEventBus implements PacketEventBus {
    private static final Logger logger =  LoggerFactory.getLogger(SimplePacketEventBus.class);
    private final Map<String, PacketProcessorDetail> packetProcessors = new HashMap<>();

    @Override
    public void registerPacket(Class<? extends PacketProcessor> packetProcessor) {
        String packetName = packetProcessor.getAnnotation(PacketName.class).value();
        try {
            packetProcessors.put(packetName, new PacketProcessorDetail(packetProcessor.newInstance()));
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        logger.info("成功注册发包处理器: {}", packetName);
    }

    @Override
    public void post(String packetName, String handleName, Map<String, Object> argsMap) {
        PacketProcessorDetail packetProcessorDetail = packetProcessors.get(packetName);
        if (packetProcessorDetail == null) {
            return;
        }
        try {
            packetProcessorDetail.invoke(handleName, argsMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static final class PacketProcessorDetail {
        private final PacketProcessor processor;
        private final Map<String, HandlerDetail> handlerTable;

        private PacketProcessorDetail(PacketProcessor processor) {
            this.processor = processor;
            Class<? extends PacketProcessor> type = processor.getClass();
            Method[] methods = type.getDeclaredMethods();
            this.handlerTable = new LinkedHashMap<>(methods.length);
            for (Method method : methods) {
                PacketHandler handler = method.getAnnotation(PacketHandler.class);
                String handle;
                if (handler == null || "".equals((handle = handler.value()))) {
                    continue;
                }
                this.handlerTable.put(handle, new HandlerDetail(method));
            }
        }

        public void invoke(String handleName, Map<String, Object> args) throws InvocationTargetException, IllegalAccessException {
            HandlerDetail detail = handlerTable.get(handleName);
            if (detail == null) {
                return;
            }
            detail.invoke(processor, args);
        }
    }

    private static final class HandlerDetail {
        private final Method method;
        private final Map<String, Parameter> parameters;

        private HandlerDetail(Method method) {
            this.method = method;
            method.setAccessible(true);
            Parameter[] parameters1 = method.getParameters();
            this.parameters = new LinkedHashMap<>(parameters1.length);
            for (Parameter parameter : parameters1) {
                PacketArgument argument = parameter.getAnnotation(PacketArgument.class);
                String argumentName;
                if (argument == null || "".equals((argumentName = argument.value()))) {
                    continue;
                }
                parameters.put(argumentName, parameter);
            }
        }

        public void invoke(PacketProcessor processor, Map<String, Object> args) throws InvocationTargetException, IllegalAccessException {
            Object[] argObjects = new Object[parameters.size()];
            int i = 0;
            for (Map.Entry<String, Parameter> entry : parameters.entrySet()) {
                argObjects[i++] = entry.getValue().getType().cast(args.get(entry.getKey()));
            }
            method.invoke(processor, argObjects);
        }
    }
}
