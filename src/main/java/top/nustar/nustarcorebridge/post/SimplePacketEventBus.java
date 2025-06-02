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
import team.idealstate.sugar.next.context.annotation.feature.Scope;
import top.nustar.nustarcorebridge.api.PacketEventBus;
import top.nustar.nustarcorebridge.api.PacketProcessor;
import top.nustar.nustarcorebridge.api.PacketSender;
import top.nustar.nustarcorebridge.api.annotations.PacketArgument;
import top.nustar.nustarcorebridge.api.annotations.PacketHandler;
import top.nustar.nustarcorebridge.api.annotations.PacketName;

@Component
@Scope(Scope.SINGLETON)
@SuppressWarnings("unused")
public class SimplePacketEventBus implements PacketEventBus {
    private Map<String, PacketProcessorDetail> packetProcessors = new HashMap<>();

    @Autowired
    public void setPacketProcessors(List<PacketProcessor> packetProcessors) {
        this.packetProcessors = packetProcessors.stream()
                .filter(packetProcessor -> packetProcessor.getClass().isAnnotationPresent(PacketName.class))
                .peek(packetProcessor -> Log.info("Registering... " + packetProcessor.getPacketName(packetProcessor)))
                .collect(Collectors.toMap(
                        packetProcessor -> packetProcessor.getPacketName(packetProcessor), PacketProcessorDetail::new));
    }

    @Override
    public void post(PacketSender<?> packetSender, String packetName, String handleName, Map<String, Object> argsMap) {
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
            Method[] methods = type.getDeclaredMethods();
            this.handlerTable = new LinkedHashMap<>(methods.length);
            int i = 0;
            for (Method method : methods) {
                PacketHandler handler = method.getAnnotation(PacketHandler.class);
                String handle;
                if (handler == null || "".equals((handle = handler.value()))) {
                    continue;
                }
                HandlerDetail handlerDetail = new HandlerDetail(method);
                this.handlerTable.put(handle, handlerDetail);
                i++;
                Log.info("               ┝── " + handler.value() + " " + handlerDetail.parameters.keySet());
                if (i == methods.length) {
                    Log.info("               ┕── 加载 " + methods.length + " 个方法");
                }
            }
        }

        public void invoke(PacketSender<?> packetSender, String handleName, Map<String, Object> args)
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

        public void invoke(PacketSender<?> packetSender, PacketProcessor processor, Map<String, Object> args)
                throws InvocationTargetException, IllegalAccessException {
            Object[] argObjects = new Object[parameters.size() + 1];
            int i = 0;
            argObjects[i++] = packetSender;
            List<String> missingParams = new ArrayList<>();

            for (Map.Entry<String, Parameter> entry : parameters.entrySet()) {
                String paramName = entry.getKey();
                Object value = args.get(paramName);

                if (value == null) {
                    missingParams.add(paramName);
                }
                argObjects[i++] = entry.getValue().getType().cast(value);
            }
            if (!missingParams.isEmpty()) {
                if (packetSender.isOp()) {
                    String packetName = processor.getPacketName(processor);
                    String handlerName = processor.getHandlerName(method);
                    String allParams = String.join("§7, §b", parameters.keySet());

                    packetSender.sendMessage("§c§l[!] §6NuStarCoreBridge §f- 参数错误");
                    packetSender.sendMessage("§7发包: §e" + packetName + " §7| §7方法: §a" + handlerName);
                    packetSender.sendMessage("§7缺失: §c" + String.join("§7, §c", missingParams));
                    packetSender.sendMessage("§7需要: §b" + allParams);
                }
                return;
            }
            method.invoke(processor, argObjects);
        }
    }
}
