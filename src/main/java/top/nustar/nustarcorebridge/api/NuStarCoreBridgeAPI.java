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

package top.nustar.nustarcorebridge.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import team.idealstate.sugar.next.context.annotation.component.Component;
import team.idealstate.sugar.next.context.annotation.feature.Autowired;
import team.idealstate.sugar.next.context.annotation.feature.Scope;
import top.nustar.nustarcorebridge.api.packet.PacketEventBus;
import top.nustar.nustarcorebridge.api.packet.PacketProcessor;
import top.nustar.nustarcorebridge.api.service.PlaceholderService;
import top.nustar.nustarcorebridge.api.service.SlotService;
import top.nustar.nustarcorebridge.utils.Pair;

import java.util.Map;

/**
 * @author NuStar
 * @since 2025/8/4 20:25
 */
@Component
@Scope(Scope.SINGLETON)
@SuppressWarnings("unused")
public class NuStarCoreBridgeAPI {
    private static volatile PacketEventBus packetEventBus;
    private static volatile PlaceholderService placeholderService;
    private static volatile SlotService slotService;

    public static PacketEventBus addPacketProcessors(Class<? extends PacketProcessor> packetProcessorClazz) {
        return packetEventBus.addPacketProcessors(packetProcessorClazz);
    }

    @SafeVarargs
    public static void addPacketProcessors(Class<? extends PacketProcessor>... packetProcessorClasses) {
        packetEventBus.addPacketProcessors(packetProcessorClasses);
    }

    public static void putSlotItem(Player player, String identifier, ItemStack item) {
        slotService.putSlotItem(player, identifier, item);
    }

    public static ItemStack getItemFromIdentifier(Player player, String identifier) {
        return slotService.getItemFromIdentifier(player, identifier);
    }

    public static void sendPlaceholder(Player player, String placeholder, String value) {
        placeholderService.sendPlaceholder(player, placeholder, value);
    }

    public static void sendPlaceholderMap(Player player, Map<String, String> placeholderMap) {
        placeholderService.sendPlaceholderMap(player, placeholderMap);
    }

    @SuppressWarnings("unchecked")
    public static void sendPlaceholders(Player player, Pair<String, String>... pairs) {
        placeholderService.sendPlaceholders(player, pairs);
    }

    public static void removePlaceholder(Player player, String placeholder, boolean startsWith) {
        placeholderService.removePlaceholder(player, placeholder, startsWith);
    }

    public static void removePlaceholders(Player player, String... placeholder) {
        placeholderService.removePlaceholders(player, placeholder);
    }

    @Autowired
    public void setPlaceholderService(PlaceholderService placeholderService) {
        NuStarCoreBridgeAPI.placeholderService = placeholderService;
    }

    @Autowired
    public void setSlotService(SlotService slotService) {
        NuStarCoreBridgeAPI.slotService = slotService;
    }

    @Autowired
    public void setPacketEventBus(PacketEventBus packetEventBus) {
        NuStarCoreBridgeAPI.packetEventBus = packetEventBus;
    }
}
