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

import eos.moe.dragoncore.api.gui.event.CustomPacketEvent;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import team.idealstate.sugar.next.context.annotation.component.Subscriber;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.PacketEventBus;
import top.nustar.nustarcorebridge.sender.BukkitSender;

@Subscriber
@DependsOn(classes = "eos.moe.dragoncore.DragonCore")
@SuppressWarnings("unused")
public class DragonCoreSubscriber implements Listener {
    private volatile PacketEventBus packetEventBus;

    @EventHandler
    public void onPacket(CustomPacketEvent event) {
        String handleName = event.getData().get(0);
        Map<String, Object> argsMap = new HashMap<>();
        event.getData().remove(0);
        for (String arg : event.getData()) {
            String[] split = arg.split("=", -1);
            if (split.length != 2) continue;
            argsMap.put(split[0], split[1]);
        }
        packetEventBus.post(new BukkitSender(event.getPlayer()), event.getIdentifier(), handleName, argsMap);
    }

    @Autowired
    public void setPacketEventBus(PacketEventBus packetEventBus) {
        this.packetEventBus = packetEventBus;
    }
}
