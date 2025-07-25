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

package top.nustar.nustarcorebridge.api.example;

import org.bukkit.entity.Player;
import team.idealstate.sugar.next.context.annotation.component.Component;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;
import top.nustar.nustarcorebridge.api.PacketProcessor;
import top.nustar.nustarcorebridge.api.annotations.PacketArgument;
import top.nustar.nustarcorebridge.api.annotations.PacketHandler;
import top.nustar.nustarcorebridge.api.annotations.PacketName;
import top.nustar.nustarcorebridge.api.sender.PacketSender;

@Component
@PacketName("DefaultPacket")
@SuppressWarnings("unused")
@DependsOn(properties = @DependsOn.Property(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "false"))
public class DefaultPacketProcessor implements PacketProcessor {

    @PacketHandler("sendMessage")
    public void runPacket(
            PacketSender<Player> packetSender,
            @PacketArgument("name") String name,
            @PacketArgument("message") String message) {
        System.out.printf("DefaultPacketProcessor.runPacket(%s,%s, %s)\n", packetSender, name, message);
    }

    @PacketHandler("sendPlayerMessage")
    public void sendPlayerMessage(
            PacketSender<Player> packetSender,
            @PacketArgument("target") String targetName,
            @PacketArgument("message") String message) {
        System.out.printf("DefaultPacketProcessor.sendPlayerMessage(%s,%s, %s)\n", packetSender, targetName, message);
    }
}
