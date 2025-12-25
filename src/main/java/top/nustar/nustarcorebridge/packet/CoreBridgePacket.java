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

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import team.idealstate.sugar.logging.Log;
import team.idealstate.sugar.next.context.annotation.component.Component;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;
import top.nustar.nustarcorebridge.api.packet.PacketProcessor;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketArgument;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketHandler;
import top.nustar.nustarcorebridge.api.packet.annotations.PacketName;
import top.nustar.nustarcorebridge.api.packet.context.PacketContext;
import top.nustar.nustarcorebridge.api.service.PlaceholderService;
import top.nustar.nustarcorebridge.configuration.NuStarCoreBridgeConfiguration;
import top.nustar.nustarcorebridge.converter.BukkitOfflinePlayerConverter;

/**
 * @author : NuStar Date : 2025/7/25 21:58 Website : <a href="https://www.nustar.top">nustar's web</a> Github : <a
 *     href="https://github.com/nustarworld">nustar's github</a> QQ : 3318029085
 */
@Component
@PacketName("NuStarCoreBridge")
@DependsOn(properties = @DependsOn.Property(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "false"))
@SuppressWarnings("unused")
public class CoreBridgePacket implements PacketProcessor {

    private volatile PlaceholderService placeholderService;
    private volatile NuStarCoreBridgeConfiguration configuration;

    @PacketHandler(value = "parsePapi", description = "为目标解析一个 Papi 变量")
    public void parsePapi(
            PacketContext<Player> packetContext,
            @PacketArgument(
                            value = "playerUid",
                            description = "玩家 UUID",
                            converter = BukkitOfflinePlayerConverter.class)
                    OfflinePlayer offlinePlayer,
            @PacketArgument(value = "placeholder", description = "Papi 变量") String papiPlaceholder) {
        String papi = "%" + papiPlaceholder.replace("#", "_") + "%";
        for (String blackPlaceholder : configuration.getBlackPlaceholderList()) {
            if (papi.contains(blackPlaceholder)) {
                Log.error(String.format(
                        "玩家 %s 尝试为 %s 解析一个黑名单变量 %s！",
                        packetContext.getPacketSender().getName(), offlinePlayer.getName(), papi));
                return;
            }
        }
        String parsePapi = PlaceholderAPI.setPlaceholders(offlinePlayer, papi);
        String placeholder = String.format(
                "NuStarCoreBridge_PapiPlaceholder_%s_%s",
                offlinePlayer.getUniqueId().toString(), papiPlaceholder);
        placeholderService.sendPlaceholder(packetContext.getPacketSender().getSender(), placeholder, parsePapi);
    }

    @Autowired
    public void setPlaceholderService(PlaceholderService placeholderService) {
        this.placeholderService = placeholderService;
    }

    @Autowired
    public void setConfiguration(NuStarCoreBridgeConfiguration configuration) {
        this.configuration = configuration;
    }
}
