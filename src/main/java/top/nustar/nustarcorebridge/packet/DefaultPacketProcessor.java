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

package top.nustar.nustarcorebridge.packet;

import org.bukkit.entity.Player;
import team.idealstate.sugar.next.context.annotation.component.Component;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;
import top.nustar.nustarcorebridge.api.packet.PacketProcessor;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketArgument;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketHandler;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketName;
import top.nustar.nustarcorebridge.api.packet.context.PacketContext;
import top.nustar.nustarcorebridge.converter.BukkitPlayerConverter;

@Component
@PacketName("DefaultPacket")
@SuppressWarnings("unused")
@DependsOn(properties = @DependsOn.Property(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "false"))
public class DefaultPacketProcessor implements PacketProcessor {

    /**
     * 向执行发包的玩家发送一条消息
     * @param packetContext 发包上下文
     * @param message 要发送的消息
     */
    @PacketHandler(value = "sendMessage", description = "向发包执行者发送一条消息")
    public void sendMessage(
            PacketContext<Player> packetContext,
            @PacketArgument(value = "message", description = "要发送的消息") String message) {
        packetContext.getPacketSender().sendMessage(message);
    }

    /**
     * 向指定玩家发送一条消息
     * @param target 目标玩家
     * @param message 要发送的消息
     */
    @PacketHandler(value = "sendPlayerMessage", description = "向指定玩家发送一条消息")
    public void sendPlayerMessage(
            @PacketArgument(value = "target", description = "目标玩家", converter = BukkitPlayerConverter.class) Player target,
            @PacketArgument(value = "message", description = "要发送的消息") String message
    ) {
        target.sendMessage(message);
    }

    /**
     * 向控制台发送一条消息
     */
    @PacketHandler(value = "sendConsoleMessage", description = "向控制台输出一条消息")
    public void sendConsoleMessage() {
        System.out.println("ConsoleMessage");
    }
}
