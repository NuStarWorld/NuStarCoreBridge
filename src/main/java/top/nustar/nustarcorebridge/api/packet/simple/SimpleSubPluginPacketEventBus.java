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

import team.idealstate.sugar.next.context.annotation.component.Component;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import team.idealstate.sugar.next.context.annotation.feature.Scope;
import team.idealstate.sugar.validate.Validation;
import team.idealstate.sugar.validate.annotation.NotNull;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;
import top.nustar.nustarcorebridge.api.packet.PacketEventBus;
import top.nustar.nustarcorebridge.api.packet.PacketProcessor;
import top.nustar.nustarcorebridge.api.packet.context.PacketContext;

import java.util.List;
import java.util.Map;

/**
 * @author : NuStar
 * Date : 2025/6/12 22:01
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@Component
@Scope(Scope.SINGLETON)
@SuppressWarnings("unused")
@DependsOn(properties = @DependsOn.Property(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "true"))
public class SimpleSubPluginPacketEventBus implements PacketEventBus {

    @NotNull
    private SimplePacketEventBus getDelegate() {
        return Validation.requireNotNull(SimplePacketEventBus.instance, "PacketEventBus is not initialized.");
    }

    @Autowired
    public void addPacketProcessors(List<PacketProcessor> packetProcessors) {
        getDelegate().addPacketProcessors(packetProcessors);
    }

    @SafeVarargs
    @Override
    public final void addPacketProcessors(Class<? extends PacketProcessor>... packetProcessors) {
        getDelegate().addPacketProcessors(packetProcessors);
    }

    @Override
    public void post(@NotNull PacketContext<?> packetContext, @NotNull String packetName, @NotNull String handleName, @NotNull Map<String, Object> argsMap) {
        getDelegate().post(packetContext, packetName, handleName, argsMap);
    }

    @Override
    public PacketEventBus addPacketProcessors(Class<? extends PacketProcessor> packetProcessorClazz) {
        return getDelegate().addPacketProcessors(packetProcessorClazz);
    }
}
