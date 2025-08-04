/*
 *    NuStarCoreBridge
 *    Copyright (C) 2025  NuStar
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.nustar.nustarcorebridge.post;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;
import team.idealstate.sugar.logging.Log;
import team.idealstate.sugar.next.context.annotation.component.Component;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import team.idealstate.sugar.next.context.annotation.feature.Scope;
import team.idealstate.sugar.next.context.lifecycle.Destroyable;
import team.idealstate.sugar.next.context.lifecycle.Initializable;
import team.idealstate.sugar.validate.Validation;
import top.nustar.nustarcorebridge.api.*;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketArgument;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketHandler;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketName;
import top.nustar.nustarcorebridge.api.packet.context.PacketContext;
import top.nustar.nustarcorebridge.api.packet.converter.ArgumentConverter;
import top.nustar.nustarcorebridge.api.packet.PacketEventBus;
import top.nustar.nustarcorebridge.api.packet.PacketProcessor;
import top.nustar.nustarcorebridge.api.packet.sender.PacketSender;

@Component
@Scope(Scope.SINGLETON)
@SuppressWarnings("unused")
@DependsOn(properties = @DependsOn.Property(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "false"))
public class SimplePacketEventBus implements PacketEventBus, Initializable, Destroyable {
    private final Map<String, PacketProcessorDetail> packetProcessors = new HashMap<>();
    static volatile SimplePacketEventBus instance;

    @Override
    public void initialize() {
        Validation.isNull(instance, "PacketEventBus is already initialized");
        instance = this;
    }

    @Override
    public PacketEventBus addPacketProcessors(Class<? extends PacketProcessor> packetProcessorClazz) {
        try {
            PacketProcessor packetProcessor = packetProcessorClazz.getDeclaredConstructor().newInstance();
            String packetName = packetProcessor.getPacketName(packetProcessor);
            this.packetProcessors.compute(packetName, (key, value) -> {
                if (value != null) {
                    throw new IllegalArgumentException("PacketName " + packetName + " is already registered");
                }
                Log.info("Registering... " + packetName);
                return new PacketProcessorDetail(packetProcessor);
            });
            return this;
        } catch (Throwable e) {
            Log.error(e);
            return this;
        }
    }

    @Override
    public void destroy() {
        instance = null;
    }

    @Autowired
    public void addPacketProcessors(List<PacketProcessor> packetProcessors) {
        for (PacketProcessor processor : packetProcessors) {
            if (!processor.getClass().isAnnotationPresent(PacketName.class)) {
                continue;
            }
            String packetName = processor.getPacketName(processor);
            Log.info("Registering... " + packetName);
            this.packetProcessors.put(packetName, new PacketProcessorDetail(processor));
        }
    }

    @Override
    public void post(PacketSender<?> packetSender, String packetName, String handleName, Map<String, String> argsMap) {
        PacketProcessorDetail packetProcessorDetail = packetProcessors.get(packetName);
        if (packetProcessorDetail == null) {
            return;
        }
        try {
            packetProcessorDetail.invoke(packetSender, handleName, argsMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Log.error(e);
        }
    }

    private static final class PacketProcessorDetail {
        private final PacketProcessor processor;
        private final Map<String, HandlerDetail> handlerTable;

        private PacketProcessorDetail(PacketProcessor processor) {
            this.processor = processor;
            Class<? extends PacketProcessor> type = processor.getClass();
            List<Method> methods = Arrays.stream(type.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(PacketHandler.class))
                    .filter(method ->
                            !method.getAnnotation(PacketHandler.class).value().isEmpty())
                    .collect(Collectors.toList());
            this.handlerTable = new LinkedHashMap<>(methods.size());
            int i = 0;
            for (Method method : methods) {
                PacketHandler handler = method.getAnnotation(PacketHandler.class);
                String handle = method.getAnnotation(PacketHandler.class).value();

                HandlerDetail handlerDetail = new HandlerDetail(method);
                this.handlerTable.put(handle, handlerDetail);
                i++;
                Log.info("               ┝── " + handler.value() + " "
                        + handlerDetail.parameters.keySet().stream()
                                .map(packetArgument -> packetArgument.value() + ":" + packetArgument.description())
                                .collect(Collectors.toList())
                        + " - " + handler.description());
                if (i == methods.size()) {
                    Log.info("               ┕── 加载 " + methods.size() + " 个方法");
                }
            }
        }

        public void invoke(PacketSender<?> packetSender, String handleName, Map<String, String> args)
                throws InvocationTargetException, IllegalAccessException {
            HandlerDetail detail = handlerTable.get(handleName);
            if (detail == null) {
                return;
            }
            detail.invoke(packetSender, processor, args);
        }
    }

    private static final class HandlerDetail {
        private final Method method;
        private final Map<PacketArgument, Parameter> parameters;

        private HandlerDetail(Method method) {
            this.method = method;
            method.setAccessible(true);
            Parameter[] parameters1 = method.getParameters();
            this.parameters = new LinkedHashMap<>(parameters1.length);
            for (Parameter parameter : parameters1) {
                PacketArgument argument = parameter.getAnnotation(PacketArgument.class);
                String argumentName;
                if (argument == null || "".equals(argument.value())) {
                    continue;
                }
                parameters.put(argument, parameter);
            }
        }

        public void invoke(PacketSender<?> packetSender, PacketProcessor processor, Map<String, String> args)
                throws InvocationTargetException, IllegalAccessException {
            Object[] argObjects = new Object[parameters.size() + 1];
            int i = 0;
            argObjects[i++] = packetSender;
            List<String> missingParams = new ArrayList<>();

            for (Map.Entry<PacketArgument, Parameter> entry : parameters.entrySet()) {
                String paramName = entry.getKey().value();
                Class<? extends ArgumentConverter> converter = entry.getKey().converter();
                String param = args.get(paramName);
                if (param == null) {
                    missingParams.add(paramName);
                    continue;
                }

                Optional<Object> convert =
                        convert(PacketContext.of(packetSender, new ArrayList<>(args.values())), param, converter);

                if (!convert.isPresent()) {
                    missingParams.add(paramName);
                    continue;
                }
                argObjects[i++] = entry.getValue().getType().cast(convert.get());
            }
            if (!missingParams.isEmpty()) {
                if (packetSender.isOp()) {
                    String packetName = processor.getPacketName(processor);
                    String handlerName = processor.getHandlerName(method);
                    String allParams = parameters.keySet().stream()
                            .map(packetArgument -> packetArgument.value() + ":" + packetArgument.description())
                            .collect(Collectors.joining("§7, §b"));

                    packetSender.sendMessage("§c§l[!] §6NuStarCoreBridge §f- 参数错误");
                    packetSender.sendMessage("§7发包: §e" + packetName + " §7| §7方法: §a" + handlerName);
                    packetSender.sendMessage("§7缺失: §c" + String.join("§7, §c", missingParams));
                    packetSender.sendMessage("§7需要: §b" + allParams);
                }
                return;
            }
            method.invoke(processor, argObjects);
        }

        private Optional<Object> convert(
                PacketContext context, String value, Class<? extends ArgumentConverter> converter) {
            try {
                return converter.newInstance().convert(context, value);
            } catch (InstantiationException | IllegalAccessException e) {
                Log.error(e);
                return Optional.of(value);
            }
        }
    }
}
