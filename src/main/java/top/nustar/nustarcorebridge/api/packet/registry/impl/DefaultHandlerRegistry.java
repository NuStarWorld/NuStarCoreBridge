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

package top.nustar.nustarcorebridge.api.packet.registry.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;
import team.idealstate.sugar.logging.Log;
import top.nustar.nustarcorebridge.api.packet.PacketProcessor;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketArgument;
import top.nustar.nustarcorebridge.api.packet.context.PacketContext;
import top.nustar.nustarcorebridge.api.packet.converter.ArgumentConverter;
import top.nustar.nustarcorebridge.api.packet.registry.HandlerRegistry;
import top.nustar.nustarcorebridge.api.packet.sender.PacketSender;
import top.nustar.nustarcorebridge.exception.PacketArgumentException;

/**
 * @author NuStar<br>
 * @since 2025/12/16 13:12<br>
 */
public class DefaultHandlerRegistry implements HandlerRegistry {
    private static final Map<Class<? extends ArgumentConverter>, ArgumentConverter> converterCacheMap = new HashMap<>();
    // private static final MethodHandles.Lookup PUBLIC_LOOKUP = MethodHandles.lookup();
    @Getter
    private final String handlerName;

    private final String packetName;
    private final PacketProcessor processor;
    // private final MethodHandle methodHandle;
    private final Method method;
    private final Parameter[] parameterArray;
    private final Map<PacketArgument, Parameter> parameters;

    public DefaultHandlerRegistry(PacketProcessor processor, Method method) {
        this.processor = processor;
        this.parameterArray = method.getParameters();
        this.packetName = processor.getPacketName();
        this.handlerName = processor.getHandlerName(method);
        this.method = method;
        this.parameters = new LinkedHashMap<>(parameterArray.length);

        // 解析方法参数
        for (Parameter parameter : parameterArray) {
            if (PacketContext.class.isAssignableFrom(parameter.getType())) continue;
            PacketArgument argument = parameter.getAnnotation(PacketArgument.class);
            if (argument == null || "".equals(argument.value())) {
                String errorMsg = String.format(
                        "发包处理器 %s 的方法 %s 参数 %s 缺少 @PacketArgument 注解或值为空！",
                        packetName, method.getName(), parameter.getName());
                Log.warn(errorMsg);
                throw new PacketArgumentException(errorMsg);
            }
            this.parameters.put(argument, parameter);
        }
    }

    @Override
    public void invoke(PacketContext<?> packetContext, Map<String, Object> argMap) {
        Object[] argObjects = new Object[parameterArray.length];
        int i = 0;
        // 判断第一个参数是否为 PacketContext
        if (parameterArray.length > 0 && PacketContext.class.isAssignableFrom(parameterArray[0].getType())) {
            argObjects[i++] = packetContext;
            System.out.println("添加上下文参数");
        }

        List<String> missingParams = new ArrayList<>();
        for (Map.Entry<PacketArgument, Parameter> packetArgumentParameterEntry : parameters.entrySet()) {
            String paramName = packetArgumentParameterEntry.getKey().value();
            Class<? extends ArgumentConverter> converter =
                    packetArgumentParameterEntry.getKey().converter();
            Object param = argMap.get(paramName);
            if (param == null) {
                missingParams.add(paramName);
                continue;
            }

            Optional<Object> convert = convert(packetContext, param, converter);

            if (!convert.isPresent()) {
                missingParams.add(paramName);
                continue;
            }
            argObjects[i++] = packetArgumentParameterEntry.getValue().getType().cast(convert.get());
        }

        if (!missingParams.isEmpty()) {
            PacketSender<?> packetSender = packetContext.getPacketSender();
            if (packetSender.isOp()) {
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
        try {
            method.invoke(processor, argObjects);
        } catch (Throwable e) {
            Log.error(String.format("执行发包方法 %s 出错", handlerName));
            throw new RuntimeException(e);
        }
    }

    private Optional<Object> convert(
            PacketContext<?> context, Object value, Class<? extends ArgumentConverter> converter) {
        ArgumentConverter argumentConverter = converterCacheMap.computeIfAbsent(converter, k -> {
            try {
                return k.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                Log.error(e);
                return null;
            }
        });
        if (argumentConverter == null) return Optional.of(value);
        return argumentConverter.convert(context, value instanceof String ? (String) value : String.valueOf(value));
    }

    @Override
    public Map<PacketArgument, Parameter> getParameters() {
        return new LinkedHashMap<>(getUnsafeParameters());
    }

    @Override
    public Map<PacketArgument, Parameter> getUnsafeParameters() {
        return parameters;
    }
}
