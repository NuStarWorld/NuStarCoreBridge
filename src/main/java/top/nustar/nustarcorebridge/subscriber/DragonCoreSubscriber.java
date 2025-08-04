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
import java.util.Map;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import team.idealstate.sugar.next.context.annotation.component.Subscriber;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;
import top.nustar.nustarcorebridge.api.packet.PacketEventBus;
import top.nustar.nustarcorebridge.sender.BukkitSender;

@Subscriber
@DependsOn(
        classes = "eos.moe.dragoncore.DragonCore",
        properties = @DependsOn.Property(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "false"))
@SuppressWarnings("unused")
public class DragonCoreSubscriber extends AbstractPostPacket implements Listener {
    private volatile PacketEventBus packetEventBus;

    @EventHandler
    public void onPacket(CustomPacketEvent event) {
        if (event.isCancelled()) return;
        String handleName = event.getData().get(0);
        Map<String, String> argsMap = getArgs(event.getData());
        packetEventBus.post(new BukkitSender(event.getPlayer()), event.getIdentifier(), handleName, argsMap);
    }

    @Autowired
    public void setPacketEventBus(PacketEventBus packetEventBus) {
        this.packetEventBus = packetEventBus;
    }
}
