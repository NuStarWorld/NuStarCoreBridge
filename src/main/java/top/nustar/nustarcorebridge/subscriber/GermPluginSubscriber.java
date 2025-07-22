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

package top.nustar.nustarcorebridge.subscriber;

import com.germ.germplugin.api.event.GermReceiveDosEvent;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import team.idealstate.sugar.next.context.annotation.component.Subscriber;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;
import top.nustar.nustarcorebridge.api.PacketEventBus;
import top.nustar.nustarcorebridge.sender.BukkitSender;

@Subscriber
@DependsOn(
        classes = "com.germ.germplugin.GermPlugin",
        properties = @DependsOn.Property(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "false"))
@SuppressWarnings("unused")
public class GermPluginSubscriber implements Listener {
    private volatile PacketEventBus packetEventBus;

    @EventHandler
    public void germPacket(GermReceiveDosEvent event) {
        if (event.isCancelled()) return;
        String[] args = event.getDosContent().split(" ");
        String handleName = args[0];
        Map<String, Object> argsMap = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            String[] split = args[i].split("=", -1);
            if (split.length != 2) continue;
            argsMap.put(split[0], split[1]);
        }
        packetEventBus.post(new BukkitSender(event.getPlayer()), event.getDosId(), handleName, argsMap);
    }

    @Autowired
    public void setPacketEventBus(PacketEventBus packetEventBus) {
        this.packetEventBus = packetEventBus;
    }
}
