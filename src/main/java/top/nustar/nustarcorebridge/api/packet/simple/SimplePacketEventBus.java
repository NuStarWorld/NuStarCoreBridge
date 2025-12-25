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

package top.nustar.nustarcorebridge.api.packet.simple;

import team.idealstate.sugar.logging.Log;
import team.idealstate.sugar.next.context.annotation.component.Component;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import team.idealstate.sugar.next.context.annotation.feature.Scope;
import team.idealstate.sugar.next.context.lifecycle.Destroyable;
import team.idealstate.sugar.next.context.lifecycle.Initializable;
import team.idealstate.sugar.validate.Validation;
import team.idealstate.sugar.validate.annotation.NotNull;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;
import top.nustar.nustarcorebridge.api.packet.PacketEventBus;
import top.nustar.nustarcorebridge.api.packet.PacketProcessor;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketName;
import top.nustar.nustarcorebridge.api.packet.context.PacketContext;
import top.nustar.nustarcorebridge.api.packet.registry.PacketProcessorRegistry;
import top.nustar.nustarcorebridge.api.packet.registry.impl.DefaultPacketProcessorRegistry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope(Scope.SINGLETON)
@SuppressWarnings("unused")
@DependsOn(properties = @DependsOn.Property(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "false"))
public class SimplePacketEventBus implements PacketEventBus, Initializable, Destroyable {
    private final Map<String, PacketProcessorRegistry> packetProcessorRegistryMap = new ConcurrentHashMap<>();
    static volatile SimplePacketEventBus instance;

    @Override
    public void initialize() {
        Validation.isNull(instance, "PacketEventBus is already initialized");
        instance = this;
    }

    @Override
    public PacketEventBus addPacketProcessors(@NotNull Class<? extends PacketProcessor> packetProcessorClazz) {
        try {
            PacketProcessor packetProcessor = packetProcessorClazz.getDeclaredConstructor().newInstance();
            String packetName = packetProcessor.getPacketName();
            this.packetProcessorRegistryMap.compute(packetName, (key, value) -> {
                if (value != null) {
                    throw new IllegalArgumentException("PacketName " + packetName + " is already registered");
                }
                Log.info("Registering... " + packetName);
                return new DefaultPacketProcessorRegistry(packetProcessor);
            });
            return this;
        } catch (Throwable e) {
            Log.error(e);
            return this;
        }
    }

    @SafeVarargs
    @Override
    public final void addPacketProcessors(Class<? extends PacketProcessor>... packetProcessors) {
        for (Class<? extends PacketProcessor> packetProcessor : packetProcessors) {
            addPacketProcessors(packetProcessor);
        }
    }

    @Override
    public void destroy() {
        instance = null;
    }

    @Autowired
    public void addPacketProcessors(@NotNull List<PacketProcessor> packetProcessors) {
        for (PacketProcessor processor : packetProcessors) {
            if (!processor.getClass().isAnnotationPresent(PacketName.class)) {
                continue;
            }
            String packetName = processor.getPacketName();
            Log.info("Registering... " + packetName);
            this.packetProcessorRegistryMap.put(packetName, new DefaultPacketProcessorRegistry(processor));
        }
    }

    @Override
    public void post(@NotNull PacketContext<?> packetContext, @NotNull String packetName, @NotNull String handleName, @NotNull Map<String, Object> argsMap) {
        PacketProcessorRegistry packetProcessorRegistry = packetProcessorRegistryMap.get(packetName);
        if (packetProcessorRegistry == null) {
            return;
        }
        packetProcessorRegistry.invoke(handleName, packetContext, argsMap);
    }
}
